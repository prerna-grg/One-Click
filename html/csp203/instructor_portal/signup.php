<?php
    include('database.php');
    
    $signup_first_name=$_POST['first_name'];
    $signup_second_name=$_POST['second_name'];
    $signup_email=$_POST['email'];
    $signup_pass1=$_POST["pass1"];
    $signup_pass2=$_POST["pass2"];
    if ($signup_pass1!=$signup_pass2)
    {
        //printf("Passwords do not match\nRedirecting back to signup page");
        //$_SESSION["error"] = "Passwords do not match\nRedirecting back to signup page";
        echo "<script>alert(\"Sign Up unsuccessful\")</script>";
        header("refresh:0;signup.html");
    }
    $isValid="Select all email from instructor where email='$signup_email';";
    $result = mysqli_query($connection,$isValid);
    
    if (mysqli_num_rows($result)>0)
    {
        echo "<script>alert(\"Email ID provided is already registered to another user\")</script>";
    }
    else{
		$enter_data="Insert into instructor(`IDPass`,`FirstName`,`LastName`,`email`) values('$signup_pass1','$signup_first_name','$signup_second_name','$signup_email');";
		$result=$connection->query($enter_data);
	}
    if (!$result)
    {
    	echo "<script>alert(\"Failed!\")</script>"; 
        header("refresh:0;signup.html");
    }else{
    	header("refresh:0;login_page.php");
    }
    mysqli_close($connection);
?>
