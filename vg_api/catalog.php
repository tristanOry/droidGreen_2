<?php

require_once 'includes/DbCatalog.php';
$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	if(isset $_POST['cat']){
		$db=new dbCatalog();
		// Si une requête post a été envoyée et qu'il s'agit bien des requêtes catalogue
		switch($_POST['cat']){
			case 'listecat':
				$response['result']=$db->listeCat();
				$response['error']=false;
				break;
			case 'listesouscat':
				$response['result']=$db->listeSousCat($_POST['parent']);
				$response['error']=false;
				break;
			case 'produits':
				$response['result']=$db->listeProd($_POST['filtre'],$_POST['tri']);
				$response['error']=false;
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

echo json_encode($response);