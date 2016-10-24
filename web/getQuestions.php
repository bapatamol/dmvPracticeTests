<?php
// connect to database
require "../dbconnect.php"; 

$result = mysql_query("select question, answer, choice1, choice2, choice3, choice4 from dmvquestions ORDER BY RAND() LIMIT 1");  

// loop through results
while ($row = mysql_fetch_array($result, MYSQL_NUM))
{
	// print out results to standard out
	echo $row['0'];
        echo "|";
	echo $row['1'];
        echo "|";
	echo $row['2'];
        echo "|";
	echo $row['3'];
        echo "|";
	echo $row['4'];
        echo "|";
	echo $row['5'];
}


?>