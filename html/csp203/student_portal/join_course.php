<?php
	@ob_start();
    session_start();
    include('database.php');
?>
<!DOCTYPE HTML>
<!--
	Aesthetic by gettemplates.co
	Twitter: http://twitter.com/gettemplateco
	URL: http://gettemplates.co
-->
<html>
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>OneClick</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Free HTML5 Website Template by GetTemplates.co" />
	<meta name="keywords" content="free website templates, free html5, free template, free bootstrap, free website template, html5, css3, mobile first, responsive" />
	<meta name="author" content="GetTemplates.co" />

  	<!-- Facebook and Twitter integration -->
	<meta property="og:title" content=""/>
	<meta property="og:image" content=""/>
	<meta property="og:url" content=""/>
	<meta property="og:site_name" content=""/>
	<meta property="og:description" content=""/>
	<meta name="twitter:title" content="" />
	<meta name="twitter:image" content="" />
	<meta name="twitter:url" content="" />
	<meta name="twitter:card" content="" />

	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700" rel="stylesheet">
	
	<!-- Animate.css -->
	<link rel="stylesheet" href="css/animate.css">
	<!-- Icomoon Icon Fonts-->
	<link rel="stylesheet" href="css/icomoon.css">
	<!-- Themify Icons-->
	<link rel="stylesheet" href="css/themify-icons.css">
	<!-- Bootstrap  -->
	<link rel="stylesheet" href="css/bootstrap.css">

	<!-- Magnific Popup -->
	<link rel="stylesheet" href="css/magnific-popup.css">

	<!-- Owl Carousel  -->
	<link rel="stylesheet" href="css/owl.carousel.min.css">
	<link rel="stylesheet" href="css/owl.theme.default.min.css">

	<!-- Theme style  -->
	<link rel="stylesheet" href="css/style.css">

	<!-- Modernizr JS -->
	<script src="js/modernizr-2.6.2.min.js"></script>
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->

	<style>
	.button {
		background-color: #4CAF50;
		border: none;
		color: white;
		padding: 15px 32px;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 16px;
		margin: 4px 2px;
		cursor: pointer;
	}

</style>
	</head>
	<body>
		
	<div class="gtco-loader"></div>
	
	<div id="page">

	
	<div class="page-inner">

	<div id="head-top" style="position: absolute; width: 100%; top: 0; ">
		<div class="gtco-top">
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-6 col-xs-6">
						<div id="gtco-logo"><a href="index.php">OneClick <em>.</em></a></div>
					</div>
					<div class="col-md-6 col-xs-6 social-icons">
						<ul class="gtco-social-top">
							<li><a href="#"><i class="icon-facebook"></i></a></li>
							<li><a href="#"><i class="icon-twitter"></i></a></li>
							<li><a href="#"><i class="icon-linkedin"></i></a></li>
							<li><a href="#"><i class="icon-instagram"></i></a></li>
						</ul>
					</div>
				</div>
			</div>	
		</div>
		<nav class="gtco-nav sticky-banner" role="navigation">
			<div class="gtco-container">
				<div class="row">
					<div class="col-xs-12 text-center menu-1">
						<ul>
							<li ><a href="index.php">Home</a></li>
							<li><a href="mycourse.php">My Courses</a></li>
							<li><a href="contact.html">Contact Us</a></li>
                            <li><a href="logout.php">Log Out</a></li>
						</ul>
					</div>
				</div>
				
			</div>
		</nav>
	</div>
	
	<header id="gtco-header" class="gtco-cover gtco-cover-md" role="banner" style="background-image: url(images/img_bg_4.jpg)" data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="gtco-container">
			<div class="row row-mt-15em">
				<div class="col-md-12 mt-text text-center animate-box" data-animate-effect="fadeInUp">
					<h1>Displaying list of courses not registered for..</h1>	
					<h2 style="font-size:30px">Click on the course you want to enroll for, Hope you have already uploaded the photos required for your attendance.</h2>
				</div>
			</div>
		</div>
	</header>

	<div class="flex-section gtco-gray-bg">
		<div class="col-1">
			<div class="text">

				<div class="row">
                <?php
                //ini_set('max_execution_time', 300);
                    
                    $email = $_SESSION['login_user'];
                    //STUDENT TXT FILES
                    $entryNum=explode('@',$email)[0];
                    $student_dir = "/var/www/html/csp203/student_images/".$entryNum."i".'/';
                    //echo $student_dir;
                    $files = glob($student_dir . "*.txt");
                    
                    $get_sql = "SELECT StudentID FROM student WHERE email=\"$email\"";
                    $res = mysqli_query($connection,$get_sql);
                    if($res){
                        $get_id = 0;
                        //echo $get_id;
                        while ($row=mysqli_fetch_row($res)){
                            $get_id = $row[0];
                        }
                        $sql="SELECT FirstName,LastName,cn,instructor.InstructorID,ccid FROM instructor INNER JOIN (SELECT InstructorID, alias1.CourseName as cn,cid as ccid FROM Instructor_courses INNER JOIN (Select CourseName,CourseID as cid from course WHERE CourseID NOT IN (SELECT CourseID FROM Student_Attendance WHERE StudentID = $get_id ))alias1 ON alias1.cid = Instructor_courses.CourseID)alias2 ON alias2.InstructorID = instructor.InstructorID;";
                        $result = mysqli_query($connection,$sql);
                        
                        if (mysqli_num_rows($result)!=0)
                        {
                        	
	                        echo "<h1>Following is the list of available courses:</h1><br>";
                            echo "<form method= \"post\" >"; 
                            $iter = 0;               
                            $data = array();
                            while ($row=mysqli_fetch_row($result))
                            {
                            	$data[] = $row;
                                $cour = $row[2];
                                $prof = $row[0];
                                $prof2 = $row[1];
                                echo "<h3><label><input type=\"checkbox\" name=\"check_list[]\" value=\"$iter\"> <strong>Course:</strong> $cour - <strong>Instructor:</strong> $prof $prof2</label></h3>";
                                $iter = $iter + 1;
                            }
                            
                            echo "<input class=\"button\" type=\"submit\" name=\"submit\" value=\"Submit\"/></form>";
		                    
		                    if(isset($_POST['submit'])){//to run PHP script on submit
								if(!empty($_POST['check_list'])){
								$a = [];
								// Loop to store and display values of individual checked checkbox.
									foreach($_POST['check_list'] as $selected){
										$a[] = $selected;
						        	}
						        	foreach ($a as $index){
						        		$course_id =  $data[$index][4];
						        		$prof_id = $data[$index][3];
						        		$sql = "INSERT INTO Student_Attendance (CourseID,StudentID,InstructorID) VALUES ($course_id,$get_id,$prof_id)";
						        		$result = mysqli_query($connection,$sql);
						        	//COPYING TXT FILES OF STUDENT IN COURSE
						        		$result1=mysqli_query($connection,"Select CourseName from course where course.CourseID='$course_id'");				
						        		$row = mysqli_fetch_assoc($result1);
										$courseName =$row['CourseName'];
										$DirPath = '/var/www/html/csp203/course/' . $courseName . '/' ;
										$i=0;
										
									foreach($files as $filename)
								{	//copy('foo/test.php', 'bar/test.php');
											$path=$DirPath.$entryNum."_".$i.".txt";
											echo $path;
											//echo "<br>";
											copy($filename,$path);
											//GETTING NUMBER OF ROWS AND COLUMNS
									if($i==0){
											$csvpath=$DirPath.$courseName.".csv";
											$file = fopen($csvpath,"r");
											$numRows=0;
											while(! feof($file))
  											{
  											if($numRows==0)
  												{$numCols=sizeof(fgetcsv($file));
  												$numRows=$numRows+1;}
  												else
  												{fgetcsv($file);
  												//print_r(fgetcsv($file));
 										    	$numRows=$numRows+1;}
 										    
 										    }	
											fclose($file);
											$numRows=$numRows-1;
											echo "number of columns are".$numCols;
											echo "number of rows are".$numRows;
											///GETTING NAME FROM DATABASE
										$result2=mysqli_query($connection,"Select FirstName,LastName from student where student.StudentID='$get_id'");
						        		$row = mysqli_fetch_assoc($result2);
										$Fname =$row['FirstName'];
										$Lname =$row['LastName'];
										$Name=$Fname." ".$Lname;
										echo $Name;
											$arr=array($numRows,$entryNum,$Name);
											 $numCols=$numCols-3;
											 while($numCols){
											 array_push($arr,"0");
											 $numCols=$numCols-1;
											 }
											$filea = fopen($csvpath,"a");
											fputcsv($filea, $arr);
											echo $arr;
											fclose($filea);
											}
											$i=$i +1;
								}
										
						        		
						        		
						        	}
						        }else{
						        	echo '<script language="javascript">alert("Please select courses before you submit.")</script>';
						        }
						        header("Refresh:0");
						    }
                        }else{
                    		echo "<h1>No courses found to register for. Please talk to your course instructor to verify if he has added the course you are looking for.</h1><br><h2>Have a nice day :)</h2>";
                   		}
                    }
                    mysqli_close($connection);
                ?>
				</div>
			</div>
		</div>
	</div>

	</div>

	<div class="gototop js-top">
		<a href="#" class="js-gotop"><i class="icon-arrow-up"></i></a>
	</div>
	
	<!-- jQuery -->
	<script src="js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Waypoints -->
	<script src="js/jquery.waypoints.min.js"></script>
	<script src="js/sticky.js"></script>
	<!-- Carousel -->
	<script src="js/owl.carousel.min.js"></script>
	<!-- countTo -->
	<script src="js/jquery.countTo.js"></script>

	<!-- Stellar Parallax -->
	<script src="js/jquery.stellar.min.js"></script>

	<!-- Magnific Popup -->
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/magnific-popup-options.js"></script>
	
	<!-- Main -->
	<script src="js/main.js"></script>
        </div>
	</body>
</html>
