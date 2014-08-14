<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Handle_Add_Question</title>
</head>
<body>
	<?php include 'mysql_connect.php';?>
    <?php
		$_title = $_POST['add_title'];
		$_A = $_POST['A'];
		$_B = $_POST['B'];
		$_C = $_POST['C'];
		$_D = $_POST['D'];
		$_answer = $_POST['right_answer'];
		mysql_query("INSERT INTO exam(title, radioA, radioB, radioC, radioD, answer) VALUES('$_title','$_A','$_B','$_C','$_D','$_answer')");
	?>
    <script>
		alert("添加成功，点击确定后返回");
	</script>
    <?php
		$url = "addExam.php";
			echo "<script language='javascript' type='text/javascript'>";
			echo "window.location.href='$url'";
			echo "</script>";
	?>
</body>
</html>