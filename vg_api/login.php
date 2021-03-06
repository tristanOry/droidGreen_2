<?php

require_once 'includes/DbLogin.php';
$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (isset($_POST['mail'])) {
        $db = new DbLogin();
        if ($_POST['mail'] != '') {
            $a = $db->userLogin($_POST['mail']);
            if ($a === false) {
                $response['error'] = true;
                $response['err_msg'] = "Utilisateur inconnu";
            } else {
                // Si l'utilisateur existe
                /* TODO : Ajouter des contrôles supplémentaires
                 * Est-ce un client ?
                 * Son compte est-il actif ?
                 */

                if (password_verify($_POST['pass'], $a['u_pass'])) {
                    $response['error'] = false;
                    $response['id'] = $a['u_id'];
                    $response['message'] = "bienvenue " . $a['u_mail'];
                } else {
                    $response['error'] = true;
                    $response['message']="mot de passe invalide";
                }
            }
        } else {
            $response['error'] = true;
            $response['err_msg'] = 'Pas de login envoyé';
        }
    }
}

echo json_encode($response);