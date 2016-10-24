<?php
	if (get_magic_quotes_gpc()) 
	{
		// magic_quotes_gpc is ON
		// so we don't need to do anything
		$questionText = htmlentities(strip_tags($_POST['questionText']));
		$choice1 = htmlentities(strip_tags($_POST['choice1']));
		$choice2 = htmlentities(strip_tags($_POST['choice2']));
		$choice3 = htmlentities(strip_tags($_POST['choice3']));
		$choice4 = htmlentities(strip_tags($_POST['choice4']));
		$answer = htmlentities(strip_tags($_POST['answer']));
                $attachmentLink = htmlentities(strip_tags($_POST['attachmentLink']));
	}
	else 
	{
		// if magic quotes is off, we need to add slashes in order to prevent injection attacks.
		$questionText = htmlentities(strip_tags(addslashes($_POST['questionText'])));
		$choice1 = htmlentities(strip_tags(addslashes($_POST['choice1'])));
		$choice2 = htmlentities(strip_tags(addslashes($_POST['choice2'])));
		$choice3 = htmlentities(strip_tags(addslashes($_POST['choice3'])));
		$choice4 = htmlentities(strip_tags(addslashes($_POST['choice4'])));
		$answer = htmlentities(strip_tags(addslashes($_POST['answer'])));
		$attachmentLink = htmlentities(strip_tags(addslashes($_POST['attachmentLink'])));
	}

        switch ($answer) {
               case 1: $choice1 = "*" . $choice1; break;
               case 2: $choice2 = "*" . $choice2;  break;
               case 3: $choice3 = "*" . $choice3;  break;
               case 4: $choice4 = "*" . $choice4;  break;
        }

	require "../dbconnect.php"; // connect to db
	$query = "INSERT INTO  acctQuestions VALUES ('$questionText', '$choice1',  '$choice2',  '$choice3',  '$choice4', '$attachmentLink', NULL)";
	if (!mysql_query($query))
	{
		die('Error: ' . mysql_error());
	} else {
                echo "<html> <head><meta http-equiv=\"refresh\" content=\"3;url=fillAcctQs.html\" /></head>";
                echo "<body> <center> <h1> Question successfully added; Taking you back now </h1> </center> </body>";
                echo "</html>";
        }
	

?>		