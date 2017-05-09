<?php
	class Student{
		public $name;
		public $inGameName;
		public $password;

		public $favouriteCoaches[];
	
		function __construct($name, $inGameName, $password){
			$this->name = $name;
			$this->inGameName = $inGameName;
			$this->password = $password;
		}
	}




?>