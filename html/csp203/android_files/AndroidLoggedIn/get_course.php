<?php 
require "conn.php";
if (!empty($_POST["id"]) && isset($_POST["id"]))
{
	$instructor_id = $_POST["id"];
	//echo $instructor_id
	//$instructor_id = 5;
	$mysql_qry = "select * from course inner join Instructor_courses on course.CourseID=Instructor_courses.CourseID where Instructor_courses.InstructorID='$instructor_id';";
	$result = mysqli_query($conn ,$mysql_qry);
	if(mysqli_num_rows($result) > 0) {
		    while($row = mysqli_fetch_assoc($result))
		    {
		        echo $row['CourseID']." ".$row['CourseName']." ".$row['no_of_enrolled_students']." ".$row['CourseID']." ".$row['InstructorID']." ".$row['CourseTitle']."#";
		    }
	}
	else {
		echo "no_course#";
	}
}
else
{
	echo "Waiting for course Request#";
}

 
?>
