<?php
	function mark_red(){
		$output = header("refresh:1;http://127.0.0.1:5000/markface");
		if( $output = "done" ){
			$dir = "/var/www/html/csp203/red_marked";
			if( is_dir($dir)){
				if( $dh = opendir($dir)){
					while(( $file = readdir($dh)) !== false){
						if( is_file($file)){
							$image = $_POST['$file'];
						}
					}
				}
			}
		}
	}
?>
