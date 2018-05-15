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
        header("refresh:0;signup.html");
        exit();
    }
    $isValid="Select email from student";
    $result = mysqli_query($connection,$isValid);
    if ($result)
    {
        while ($row=mysqli_fetch_row($result))
        {
            if (strcmp($row[0],$signup_email)==0)
            {
                //printf("The Email ID provided is already registered to another user\n");
                
                exit();
            }
        }
    }
    $enter_data="Insert into student(`IDPass`,`FirstName`,`LastName`,`email`) values('$signup_pass1','$signup_first_name','$signup_second_name','$signup_email')";
    $result=$conn->query($enter_data);
    if ($result==true)
    {
        //printf("Signed Up Sucessfully\n Redirecting to login page");
        header("refresh:0;index.php");
    }
    else 
    {
        //printf("Error occured,Please Retry\nRedirecting to signup page");
        //echo "Error: " . $enter_data . "<br>" . $conn->error;
    }
    mysqli_close($conn);
?>
