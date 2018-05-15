<?php 

$command = escapeshellcmd('python mark_face.py');
$output = shell_exec($command);
#var_dump($output);
if($output==null){
	echo "Abey yaar kya h";
}
echo $output;

?>
