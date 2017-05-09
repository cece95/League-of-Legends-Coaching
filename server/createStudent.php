<?php 
	require_once 'User_DAO';

	$name = $_POST['name'];
	$ign = $_POST['ign']; 
	$password = $_POST['password']
	
	User_DAO::insertUser($name, $ign, $password);
	}