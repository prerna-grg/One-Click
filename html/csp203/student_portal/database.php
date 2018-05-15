<?php	
	$host="localhost";
    $host_user="root";
    $host_pass="password";
    $connection = mysqli_connect($host, $host_user, $host_pass,"oneclick");
    if ($connection->connect_error) {
        die("Connection failed: " . $connection->connect_error);
    }
    else
    {

    	/*echo"Hi there";
    	$sql = "select * from student";
    	$query="Insert into student(`IDPass`,`FirstName`,`LastName`,`email`) values(`12qwaszx`,`Aditya`,Tiwari`,`2016csb1029@iitrpr.ac.in`)";
    	$result = mysqli_query($connection,$query);
    	echo $result->error();*/
    }
    
?>
