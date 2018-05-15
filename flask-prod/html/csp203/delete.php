<?php 

$command = escapeshellcmd("php test.php");
$array=[];
$output = system($command,$array);
#var_dump($output);
if($output==null){
	echo "Failed\n";
}
echo $output;
echo "<BR>\n";
echo $array[0];
//echo "<BR>\n";
//echo $array[1];
//echo "<BR>\n";
//echo $array[2];
//echo "<BR>\n";
//echo $array[3];
//echo "<BR>\n";

?>
