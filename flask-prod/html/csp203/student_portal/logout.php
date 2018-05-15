
<?php
session_start();
if(session_destroy()) // Destroying All Sessions
{
header("Location: login_page.php"); // Redirecting To Home Page
}
?>