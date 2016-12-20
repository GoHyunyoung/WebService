<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
</head>
<body>
	<h1>JavaScript</h1>
	<script>
	list = new Array("one","two","three");
	document.write(list+"<br />");
	document.write(list.length)
	</script>

	<h1>php</h1>
	<?php
	$list = array("one","two","three");
	for ($i=0; $i <count($list) ; $i++) {
		echo $list[$i].", ";
	}
	echo "<br />";
	echo count($list);
	?>
</body>
</html>