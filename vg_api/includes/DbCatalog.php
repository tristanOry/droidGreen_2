<?php

class DbCatalog {

    private $con;
	private $result;
	function __construct() {
		require_once dirname(__FILE__) . '/DbConnect.php';
		$db = new DbConnect();
		$this->con = $db->connect();
		$this->result=array();
	}
	
	function listeCat(){
		$stmt=$this->con->prepare("SELECT * FROM `categories` WHERE `cat_parent` IS NULL");
		$stmt->execute();
		$this->result=$stmt->get_result()->fetch_assoc();
		$this->result['error']=false;
		return $this->result;
	}
	
	function listeSousCat($parent){
		$stmt=$this->con->prepare("SELECT * FROM `categories` WHERE `cat_parent`=?");
		$stmt->bind_param("i",$parent);
		$stmt->execute();
		$this->result=$stmt->get_result()->fetch_assoc();
		$this->result['error']=false;
		return $this->result;
	}
	
	function listProd($filtre="", $valFiltre="", $cle="", $ordre=""){
		$i=0;
		$req="SELECT * FROM `produit`";
		// Si $filtre est défini
		if(! empty($filtre)){
			$tmp=strtolower($filtre);
			$p1=$valFiltre;
			switch ($tmp){
				case 'souscat':
					$req.=" WHERE `pro_cat_id`=?";					
					$i++;
					break;
				case 'cat':
				// écrire le texte compliqué de la requête pour les produits de la famille...
				case 'fou':
					$req.=" WHERE `pro_fou_id`=?";
					$i++;
					break;
				case 'all':
					break;
				default:
					$this->result['error']=true;
					$this->result['message']="Filtre non reconnu";
			}
		} 
		// Si il y a une demande de tri et que les paramètres de filtre sont OK
		if(!(empty($cle) || isset($this->result['error'])) {
			$tmp=strtolower($cle);
			// Si une valeur a été passée pour le filtre, alors on a un second paramètre, sinon c'est le premier
			switch($tmp){
				case 'nom':
					$req.=" SORT BY `pro_nom`";
					break;
				case 'prix':
					$req.=" SORT BY `pro_prix`";
					break;
				case 'souscat':
					$req=" SORT BY `pro_cat_id`";
					break;
				default:
					$this->result['error']=true;
					$this->result['message']="Clé de tri non reconnue";					
			}
			if($ordre<0) $req.=" DESC";
		}
		// Si toujours pas d'erreur dans la requête
		if(!isset($this->result['error']){
			$stmt=$this->con->prepare($req);
			if($i>0) $stmt->bind_param("s",$p1);
			$this->result=$stmt->get_result()->fetch_assoc();
			$this->result['error']=false;
		}
		return $this->result;
	}
}