<?php
include('session.php'); // Includes Login Script
$courseName=$_GET['courseName'];
$csvpath=    '/var/www/html/csp203/course/' . $courseName . '/' . $courseName .'.csv';
   if(isset($_FILES['image'])){
      $errors= array();
      $file_name = $_FILES['image']['name'];
      $file_size =$_FILES['image']['size'];
      $file_tmp =$_FILES['image']['tmp_name'];
      $file_type=$_FILES['image']['type'];
      $file_ext=strtolower(end(explode('.',$_FILES['image']['name'])));
      
      $expensions= array("csv");
      
      if(in_array($file_ext,$expensions)=== false){
         $errors[]="extension not allowed, please choose a CSV file.";
      }
      
      if($file_size > 2097152){
         $errors[]='File size must be excately 2 MB';
      }
      
      if(empty($errors)==true){
         move_uploaded_file($file_tmp,$csvpath);
         echo "<script>alert(\"FILE UPLOADED\")</script>";
         header("refresh:0;course_details.php?courseName=".$courseName."");
      }else{
         print_r($errors);
      }
   }
?>
