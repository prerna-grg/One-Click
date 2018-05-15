<?php
// Establishing Connection with Server by passing server_name, user_id and password as a parameter
include('database.php');
session_start();// Starting Session
// Storing Session
$user_check=$_SESSION['login_user'];
// SQL Query To Fetch Complete Information Of User
$ses_sql=mysqli_query($connection,"select email from student where student.email='$user_check'" );
$row = mysqli_fetch_assoc($ses_sql);
$login_session =$row['email'];
if(!isset($login_session) and $_SESSION['login_user'] != -1){
    mysqli_close($connection); // Closing Connection
    header('Location: login_page.php'); // Redirecting To Home Page
}
?>
