<?php
function image_with_recognized_faces($location)
{
    $path=$location;
    //$path = 'myfolder/myimage.png';
    $type = pathinfo($path, PATHINFO_EXTENSION);
    $data = file_get_contents($path);
    $base64 = base64_encode($data);
    return $base64;
}

/*- User set variables ----------------------------------------------------------------- */
// Variables
$api_password = "qw2e3erty6uiop";


/*- Get extention ---------------------------------------------------------------------- */
function getExtension($str) {
	$i = strrpos($str,".");
	if (!$i) { return ""; } 
	$l = strlen($str) - $i;
	$ext = substr($str,$i+1,$l);
	return $ext;
}

/*- Script Start ----------------------------------------------------------------------- */

// API Password
if(isset($_POST['inp_api_password'])){
	$inp_api_password = $_POST['inp_api_password'];
	
	if($inp_api_password != "$api_password"){
		echo"Wrong API Password";
		die;
	}
}
else{
	echo"Missing API Password";
	die;
}


// Image
$img_code = $_POST['inp_image_base'];
$instructor_name = $_POST['i_name'];
$course_name = $_POST['c_name'];
$img_data = base64_decode($img_code);

$datetime = date("YmdHis");

$image_path = "../images/" . $datetime . ".jpg";
$extension = getExtension($image_path);
file_put_contents($image_path,$img_data);

// Convert it to PNG
if(file_exists("$image_path")){
	list($width,$height) = getimagesize($image_path);
	
	if($width == "" OR $height == ""){
		echo"getimagesize_failed";
		unlink("$image_path");
	}
	else{	
		$image_final_path = "../images/" .$course_name . ".jpeg";
		$prerna = $course_name . ".jpeg" ;
		// Image should be 1000 x dynamic
		if($width > 1000){
			$newwidth=1000;
			$newheight=($height/$width)*$newwidth; 
		}
		else{
			$newwidth = $width;
			$newheight = $height;
		}
		$tmp=imagecreatetruecolor($newwidth,$newheight);
						
		if($extension == "jpg" || $extension == "jpeg" ){
			$src = imagecreatefromjpeg($image_path);
		}
		else{
			$src = imagecreatefrompng($image_path);
		}

		imagecopyresampled($tmp,$src,0,0,0,0,$newwidth,$newheight, $width,$height);
		imagejpeg($tmp, $image_final_path);
		imagedestroy($tmp);

		// Unlink image path
		unlink("$image_path");

		// Give path
		//echo"$image_final_path";
		//printf("\n%s and %s\n",$instructor_name,$course_name);
		printf("Please wait While we process the attendance\n #");
		#exec("google-chrome http://10.20.254.48:5000/markface/" . $prerna);
#		header("refresh:0;http://10.20.254.48:5000/markface/" . $prerna );
		#echo image_with_recognized_faces($image_final_path);
	}
}

?>
