<?php
include('session.php');
include('database.php');
$user_check=$_SESSION['login_user'];
$id=$_GET['id'];
$entryNum=explode('@',$user_check)[0];
//echo $user_check;
//echo $entryNum;
$target_dir = "/var/www/html/csp203/student_images/".$entryNum.'/';
    if (file_exists ($target_dir))
      {printf("Folder for you  is already exist ");
       }
       else{    
             mkdir($target_dir,0777,true);
            echo "student folder created"; 
      }

if(isset($_POST['submit'])){
    if(count($_FILES['upload']['name']) > 0){
        //Loop through each file
        for($i=0; $i<count($_FILES['upload']['name']); $i++) {
          //Get the temp file path
            $tmpFilePath = $_FILES['upload']['tmp_name'][$i];

            //Make sure we have a filepath
            if($tmpFilePath != ""){
            
                //save the filename
                $shortname = $_FILES['upload']['name'][$i];
		
                //save the url and the file
                $filePath = $target_dir.$_FILES['upload']['name'][$i];
				$imageFileType = strtolower(pathinfo($filePath,PATHINFO_EXTENSION));
				$filePath = $target_dir.$entryNum."_".$i.".".$imageFileType;
                //Upload the file into the temp dir
                if(move_uploaded_file($tmpFilePath, $filePath)) {

                    $files[] = $shortname;
                    //insert into db 
                    //use $shortname for the filename
                    //use $filePath for the relative url to the file

                }
              }
        }
    }
//show success message
    echo "<h1>Uploaded:</h1>";    
    if(is_array($files)){
        echo "<ul>";
        foreach($files as $file){
            echo "<li>$file</li>";
        }
        echo "</ul>";
    }
    header("refresh:1;http://127.0.0.1:5000/storeF/$entryNum");
    
}
?>
