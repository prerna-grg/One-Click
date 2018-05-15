
<?php
session_start();
if(session_destroy()) // Destroying All Sessions
{
header("Location: http://localhost/csp203/index.php"); // Redirecting To Home Page
}
?>
