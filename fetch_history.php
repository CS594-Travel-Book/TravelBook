<?php

$servername = "mysql3.gear.host";
$username = "travelbookdb";
$password = "Gm8vF-MV?LW2";
$dbname="travelbookdb";


// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM locations";
$result = $conn->query($sql);


$jsonData = array();
while ($row = $result->fetch_assoc()) {
    $jsonData[] = $row;
}
echo json_encode($jsonData);

?>