<?php
	#if(isset($_POST['submit'])){//to run PHP script on submit
		if(!empty($_POST["course_name"])){
			$prerna = $_POST["course_name"] . ".jpeg";
			echo "<script>alert(\"Attendance can be viewed in the course folder\")</script>";
			header("refresh:0;http://127.0.0.1:5000/markface/" . $prerna);
		}else{
			echo "<script>alert(\"Please enter the course name\")</script>";
		}
		#header("refresh:0;index.php");
	#}
?>
