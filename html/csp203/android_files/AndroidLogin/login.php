<?php 
require "conn.php";
$user_name = $_POST["user_name"];
$user_pass = $_POST["password"];
$mysql_qry = "Select InstructorID as id,concat(FirstName,' ',LastName) as name from instructor where instructor.email='$user_name' and instructor.IDPass='$user_pass';";
$result = mysqli_query($conn ,$mysql_qry);
if(mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        echo $row['id']." ".$row['name'];
}
else {
    echo "invalid";
}
 
?>