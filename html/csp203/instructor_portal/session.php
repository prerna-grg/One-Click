<?php
include('database.php');
// Establishing Connection with Server by passing server_name, user_id and password as a parameter

session_start();// Starting Session
// Storing Session
$userEmail=$_SESSION['login_user'];
// SQL Query To Fetch Complete Information Of User
$ses_sql=mysqli_query($connection,"select email from instructor where instructor.email='$userEmail'" );
$row = mysqli_fetch_assoc($ses_sql);
$login_session =$row['email'];
if(!isset($login_session)){
    mysqli_close($connection); // Closing Connection
    header('Location: login_page.php'); // Redirecting To Home Page
}
?>
