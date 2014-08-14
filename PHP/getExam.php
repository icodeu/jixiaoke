<?php

	include 'mysql_connect.php';
	
	$data = array();
	$n = 0;
	
	$result = mysql_query("SELECT * FROM exam order by rand() limit 1");
	$row = mysql_fetch_array($result);
	
	$_id = $row['_id'];
		$title = $row['title'];
		$radioA = $row['radioA'];
		$radioB = $row['radioB'];
		$radioC = $row['radioC'];
		$radioD = $row['radioD'];
		$answer = $row['answer'];
		
		$data[$n++] = $_id;
		
		$arr = array(
			"_id"=>$_id,
			"title"=>$title,
			"radioA"=>$radioA,
			"radioB"=>$radioB,
			"radioC"=>$radioC,
			"radioD"=>$radioD,
			"answer"=>$answer
		);

		echo "[";
		
		echo json_encode($arr);
	
    for (;;)
	{
		if ($n==9)
			break;
		$result = mysql_query("SELECT * FROM exam order by rand() limit 1");
		$row = mysql_fetch_array($result);
		$_id = $row['_id'];
		if (!in_array($_id, $data))
		{
			$title = $row['title'];
			$radioA = $row['radioA'];
			$radioB = $row['radioB'];
			$radioC = $row['radioC'];
			$radioD = $row['radioD'];
			$answer = $row['answer'];
			
			$arr = array(
				"_id"=>$_id,
				"title"=>$title,
				"radioA"=>$radioA,
				"radioB"=>$radioB,
				"radioC"=>$radioC,
				"radioD"=>$radioD,
				"answer"=>$answer
			);
			$data[$n++] = $_id;
			echo ",";
			echo json_encode($arr);
	   	}
	}
	
 	echo "]";

?>