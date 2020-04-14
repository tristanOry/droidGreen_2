<?php

class DbOperations {

    private $con;

    function __construct() {
        require_once dirname(__FILE__) . '/DbConnect.php';
        $db = new DbConnect();
        $this->con = $db->connect();
    }

    function isUserExists($mail) {
        $stmt = $this->con->prepare("SELECT * FROM `utilisateur` WHERE `u_mail`=?");
        $stmt->bind_param("s", $mail);
        $stmt->execute();
        $stmt->store_result();
        return $stmt->num_rows > 0;
    }

    function getUserByUserName($u_mail) {
        $stmt = $this->con->prepare("SELECT * FROM `utilisateur` WHERE `u_mail` = ?");
        $stmt->bind_param("s", $u_mail);
        $stmt->execute();
        $result = $stmt->get_result();
        return $result->fetch_assoc();
    }

    public function userLogin($u_mail) { // Renvoie le tableau associatif de résultat si l'utilisateur existe
        $a = $this->getUserByUserName($u_mail);
        if (!(empty($a))) {
            return $a;
        } else {
            return false;
        }
    }

}
