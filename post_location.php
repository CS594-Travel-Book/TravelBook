<?php
// if text data was posted;

$servername = "mysql3.gear.host";
$username = "travelbookdb";
$password = "Gm8vF-MV?LW2";
$dbname="travelbookdb";

$conn = new mysqli($servername, $username, $password, $dbname);
	
$address = $_POST["address"];
$date = $_POST["date"];
$category = $_POST["category"];
$notes = $_POST["notes"];
$email = $_POST["email"];


$sql = "insert into locations(loc_address, visit_date, category, notes, email) values('".$address."','".$date."','".$category."','".$notes."','".$email."')";
if ($conn->query($sql) == TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>