<?php 

$command = escapeshellcmd("python /var/www/html/csp203/mark_face.py 2>&1");
echo $command;
echo "<BR>\n";
$array;
$output = exec("python /var/www/html/csp203/mark_face.py 2>&1",$array);
#var_dump($output);
if($output==null){
	echo "Failed\n";
}
echo $output;
echo "<BR>\nPrinting Array[0]\n";
echo $array[0];
//echo "<BR>\n";
//echo $array[1];
//echo "<BR>\n";
//echo $array[2];
//echo "<BR>\n";
//echo $array[3];
//echo "<BR>\n";

?>
