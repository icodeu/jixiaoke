<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css">
<title>增加题库系统</title>
</head>
<body>

<div class="container" style="width:600px">
	<h1 class="page-header">增加一道题目</h1>

    <form role="form" action="handle_add_exam.php" method="post">
      <div class="form-group">
        <label for="questiontitle">问题</label>
        <input type="text" class="form-control" id="questiontitle" name="add_title" placeholder="Enter Title">
      </div>
      <div class="form-group">
        <label for="choiceA">A</label>
        <input type="text" class="form-control" id="choiceA" name="A" placeholder="A">
      </div>
      <div class="form-group">
        <label for="choiceB">B</label>
        <input type="text" class="form-control" id="choiceB" name="B" placeholder="B">
      </div>
      <div class="form-group">
        <label for="choiceC">C</label>
        <input type="text" class="form-control" id="choiceC" name="C" placeholder="C">
      </div>
      <div class="form-group">
        <label for="choiceD">D</label>
        <input type="text" class="form-control" id="choiceD" name="D" placeholder="D">
      </div>
      <div class="form-group">
        <label for="answer">答案</label>
        <input type="text" class="form-control" id="answer" name="right_answer" placeholder="正确答案">
      </div>
      <button type="submit" class="btn btn-success">Submit</button>
    </form>
</div>

<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/js/bootstrap.min.js"></script>
</body>
</html>