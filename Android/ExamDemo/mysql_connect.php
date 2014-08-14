<?php
		$con = mysql_connect("localhost","root","");
		mysql_query("SET NAMES 'utf8'"); 
		mysql_query("SET CHARACTER SET utf8"); 
		mysql_query("SET CHARACTER_SET_RESULTS=utf8'"); 
		if (!$con)
		{
			die('Could not connect: ' . mysql_error());
		}
		mysql_select_db("examdemo", $con);
?>