<?php
	class User_DAO{
		public static function insertStudent($name, $inGameName, $password){
			$conn = Database::connect();
			$q_name = mysql_real_escape_string($name);
			$q_ign = mysql_real_escape_string($inGameName);
			//password già in SHA1?

			//check con API riot se l'utente esiste
		
			$sql = $conn->prepare('INSERT INTO students(name, ign, password) VALUES(?,?,?)');
			$sql->execute(array($q_name, $q_ign, $password));	
		
			Database::closeConnection($conn);
		}


	}

?>