<?php

require_once 'includes/DbCatalog.php';
$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	if(isset ($_POST['cat'])){
		$db=new dbCatalog();
		// Si une requête post a été envoyée et qu'il s'agit bien des requêtes catalogue
		switch($_POST['cat']){
			case 'listecat':
				$response=$db->listeCat();				
				break;
			case 'listesouscat':
				$response=$db->listeSousCat($_POST['parent']);
				$response['error']=false;
				break;
			case 'produits':
				$filtre=(isset($_POST['filtre']))?$_POST['filtre']:"";
				$cle=(isset($_POST['cle']))?$_POST['cle']:"";
				$val_filtre=(isset($_POST['vfiltre']))?$_POST['vfiltre']:"";
				$ordre=(isset($_POST['ordre']))?$_POST['ordre']:"";
				$response['result']=$db->listeProd($filtre,$val_filtre,$cle,$ordre);
				break;
			default:
				$response['error']=true;
				$response['message']="Commande non reconnue";			
		}			
	} else {
		$response['error']=true;
		$response['message']="Appel d'API erronné";
	}
} else {
	$response['error']=true;
	$response['message']="Mauvaise méthode d'appel de l'API";
}
//var_dump($_POST);
echo json_encode($response);