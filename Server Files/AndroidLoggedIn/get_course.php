<?php 
require "conn.php";
$instructor_id = $_POST["id"];
$mysql_qry = "select * from course inner join Instructor_courses on course.CourseID=Instructor_courses.CourseID where Instructor_courses.InstructorID='$instructor_id';";
$result = mysqli_query($conn ,$mysql_qry);
if(mysqli_num_rows($result) > 0) {
        while($row = mysqli_fetch_assoc($result))
        {
            echo $row['CourseID']." ".$row['CourseName']." ".$row['CourseTitle']." ".$row['CourseID']." ".$row['InstructorID']." ".$row['no_of_enrolled_students']."#";
        }
}
else {
    echo "no_course";
}
 
?>