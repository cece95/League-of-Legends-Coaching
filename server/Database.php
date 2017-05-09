<?php
class Database{

	public static function connect(){
			self::set();
			$setUTF8 = array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8");
			$connection = new PDO("mysql::host=".HOST."; dbname=".DB, USER, PASSWORD, $setUTF8);
			return $connection;
	}
	
	public static function closeConnection($conn){
		$conn = null; // chiusura connessione
	}

	private function set(){
		defined('DB') || define('DB', 'database_anime');
		defined('HOST') || define('HOST', "localhost");
		defined('USER') || define('USER', 'root');
		defined('PASSWORD') || define('PASSWORD', '');
	}
	}
?>