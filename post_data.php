<?php
// if text data was posted;

$servername = "mysql3.gear.host";
$username = "travelbookdb";
$password = "Gm8vF-MV?LW2";
$dbname="travelbookdb";

$conn = new mysqli($servername, $username, $password, $dbname);
	
$email = $_POST["username"];
$passwd = $_POST["password"];
$name = $_POST["name"];
$active = $_POST["active"];


$sql = "insert into users(username, passwd, name, active) values('".$email."','".$passwd."','".$name."','true')";
if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>