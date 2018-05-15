<?php

require('database.php');
session_start(); // Starting Session
$error=''; // Variable To Store Error Message
$test_string="well this is a test string";


if (isset($_POST['submit']) ) {
    if (empty($_POST['email']) || empty($_POST['pass'])) 
    {
        $error = "Entry Number or Password is invalid";
    }
    else
    {
        // Define $username and $password
        $email=$_POST['email'];
        $password=$_POST['pass'];
        // Establishing Connection with Server by passing server_name, user_id and password as a parameter
        
        $username = stripslashes($email);
        $password = stripslashes($password);
        // SQL query to fetch information of registerd users and finds user match.
        $query = mysqli_query($connection,"Select*from student where student.email='$email' and student.IDPass= '$password'" );
        $rows = mysqli_num_rows($query);
        if ( $rows == 1) 
        {
            $_SESSION['login_user']=$email; // Initializing Session
            $test_string="Redirecting";
            header("location: index.php"); // Redirecting To Other Page
        } 
        else 
        {
            $error = "Email or Password is invalid";
        }
        mysqli_close($connection); // Closing Connection

    }
}
?>


