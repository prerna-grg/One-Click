<?php
session_start(); // Starting Session
$error=''; // Variable To Store Error Message
$test_string="well this is a test string";
include('database.php');

if (isset($_POST['submit']) ) {
    if (empty($_POST['email']) || empty($_POST['pass'])) 
    {
        $error = "Username or Password is invalid";
    }
    else
    {
           
        if ($connection->connect_error) {
                die("Connection failed: " . $connection->connect_error);
            }
        $username=$_POST['email'];
        $password=$_POST["pass"];
        // To protect MySQL injection for Security purpose
        $username = stripslashes($username);
        $password = stripslashes($password);
        //$username = mysqli_real_escape_string($username);
        //$password = mysqli_real_escape_string($password);
        // SQL query to fetch information of registerd users and finds user match.
        $query = mysqli_query($connection,"Select * from instructor where instructor.email='$username' and instructor.IDPass='$password'" );
        $rows = mysqli_num_rows($query);
        if ($rows==0) 
        {   $error = "Username or Password is invalid";
            
        } 
        else 
        {   $_SESSION['login_user']=$username;
            //printf($rows);
            //printf($rows[0]); // Initializing Session
            $test_string="Redirecting";
            header("location: index.php"); // Redirecting To Other Page
        
    
        }
        mysqli_close($connection); // Closing Connection

    }
}
?>
<?php


