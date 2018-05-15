<?php
	include('database.php');
    session_start();
    $userEmail=$_SESSION['login_user'];

    if ($connection->connect_error) {
        die("Connection failed: " . $connection->connect_error);
    }
    $register_course_name=$_POST['course_name'];
    $register_course_title=$_POST['course_title'];
    $isValid="Select CourseName from course where course.CourseName='$register_course_name'";
    $result = mysqli_query($connection,$isValid);
    
    if ($result)
    {
        while ($row=mysqli_fetch_row($result))
        {
            if (strcmp($row[0],$register_course_name)==0)
            {
            	echo "<script>alert(\"Course already exists\")</script>";
                header("refresh:0;register_course.php");
            }
        }
    }

    $enter_data="Insert into course(`CourseName`,`CourseTitle`) values('$register_course_name','$register_course_title')";
    $result=$connection->query($enter_data);
    if ($result)
    {   
    	echo "<script>alert(\"Course registered successfully\")</script>";
    	
        $result1 = mysqli_query($connection,"Select InstructorID from instructor where instructor.email='$userEmail'");
        $result2 = mysqli_query($connection,"Select CourseID from course where course.CourseName = '$register_course_name'");
        if($result1 and $result2)
        {   $row1=mysqli_fetch_row($result1);
            $row2=mysqli_fetch_row($result2);
            $InstructorID=$row1[0];
            $CourseID=$row2[0];
            $result3=$connection->query("Insert into Instructor_courses(`CourseID`,`InstructorID`) values('$CourseID','$InstructorID')");
            if($result3)
            {
                //printf("Data inserted into Instructor_course table");
            }
            else
            {
            	echo "<script>alert(\"Error Occurred:Try Again1\")</script>";
            	header("refresh:0;register_course.php");
                //printf("error while inserting into instructor_course table");   
            }
        }
        else{
        	echo "<script>alert(\"Error Occurred:Try Again2\")</script>";
        	header("refresh:0;register_course.php");
            //printf("Connection problem.Entry not reflected in instructorCourse table");
        }
        ///CHANGE THE PATH OF SAVING AS PER THE HOSTING SERVER . NOW ITS REFERS TO MY PATH IN  MY COMPUTER
        
        $sDirPath = '/var/www/html/csp203/course/' . $register_course_name . '/' ; //Specified Pathname
        $csvpath=    '/var/www/html/csp203/course/' . $register_course_name . '/' . $register_course_name .'.csv';
        //echo $sDirPath . " " . $csvpath . " " . $register_course_name . "<br>";
        if (file_exists ($sDirPath))
        {
        	echo "<script>alert(\"Course Already exists\")</script>";
        	header("refresh:0;register_course.php");
         }
         else{    
              mkdir($sDirPath,0777,true);
              exec('sudo -u root -S chmod 777 ' + $sDirPath + ' < password.txt');
         }
        $file = fopen($csvpath, 'w');
 
        // save the column headers
        fputcsv($file, array('SerialNumber','EntryNumber', 'StudentName'));
        header("refresh:0;register_course.php");
        //printf("Course CSV FILE created at ");
    }
    else 
    {
    	echo "<script>alert(\"Error Occurred:Try Again\")</script>";
       	header("refresh:0;register_course.php");
    }
    mysqli_close($connection);
?>
    
