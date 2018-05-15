<?php	
	$host="localhost";
    $host_user="root";
    $host_pass="password";
    $connection = mysqli_connect($host, $host_user, $host_pass,"oneclick");
    if ($connection->connect_error) {
        die("Connection failed: " . $connection->connect_error);
    }
?>
