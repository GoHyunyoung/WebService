<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
</head>
<body>
	<?php
	$id=$_GET[id];
	$pw=$_GET[pw];
	$isAgree=$_GET[agree];
	
	if ($id=="admin" && $pw==1234 && $isAgree) {
		echo "Welcome Admin!";
	}
	else{
		echo "Sorry.. Access denied";
	}
	?>

</body>
</html>