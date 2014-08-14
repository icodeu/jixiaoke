<?php

	include 'mysql_connect.php';
	
	//每次随机生成总题数，必须和android客户端中保持一致
	$TOTAL_NUM = 10;
	
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
		if ($n==$TOTAL_NUM-1)
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