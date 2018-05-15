<html>
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>OneClick</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Free HTML5 Website Template by GetTemplates.co" />
	<meta name="keywords" content="free website templates, free html5, free template, free bootstrap, free website template, html5, css3, mobile first, responsive" />
	<meta name="author" content="GetTemplates.co" />
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700" rel="stylesheet">
	<link rel="stylesheet" href="css/animate.css">
	<link rel="stylesheet" href="css/icomoon.css">
	<link rel="stylesheet" href="css/themify-icons.css">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/magnific-popup.css">
	<link rel="stylesheet" href="css/owl.carousel.min.css">
	<link rel="stylesheet" href="css/owl.theme.default.min.css">
	<link rel="stylesheet" href="css/style.css">
	<script src="js/modernizr-2.6.2.min.js"></script>
	</head>
<body>

<?php

if($result = $db->query("SELECT * FROM student")){
	if($count = $result->num_rows){
		while($row = $result->fetch_object(){
			echo "<div class=\"gtco-section gtco-gray-bg\"><div class=\"gtco-container\"><div class=\"row\"><div class=\"col-lg-4 col-md-4 col-sm-6\"><a href=\"#\" class=\"gtco-card-item\"><figure><div class=\"overlay\"><i class=\"ti-plus\"></i></div><img src=\"images/img_1.jpg\" alt=\"Image\" class=\"img-responsive\"></figure><div class=\"gtco-text\"><h2>$coursename</h2><p>$coursetitle</p><p class=\"gtco-category\"><span style=\"font-size: 20px;\">Click to view the attendance details ...</span></p></div></a></div></div></div></div>" ;

		}
	}
}
?>
    
    </body>
</html>
