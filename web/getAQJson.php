<?php
// connect to database
require "../dbconnect.php"; 

$r = rand(0,5000);

if ($r % 7 == 0) {
     $result = mysql_query("select question, choice4, choice3, choice2, choice1, attachmentLink from acctQuestions ORDER BY RAND() LIMIT 1");  
} else if ($r % 3 == 0) {
     $result = mysql_query("select question, choice1, choice4, choice2, choice3, attachmentLink from acctQuestions ORDER BY RAND() LIMIT 1");  
} else if ($r % 5 == 0) {
     $result = mysql_query("select question, choice3, choice2, choice1, choice4, attachmentLink from acctQuestions ORDER BY RAND() LIMIT 1");  
} else {
     $result = mysql_query("select question, choice1, choice2, choice3, choice4, attachmentLink from acctQuestions ORDER BY RAND() LIMIT 1");  
}

// loop through results
while ($row = mysql_fetch_array($result, MYSQL_NUM))
{
	// print out results to standard out
	echo $row['0'];
        echo "|";
	if (strpos($row['1'], "*") !== false) {
             echo "1";
             $row['1'] = substr($row['1'], 1);
        } else if (strpos($row['2'], "*") !== false) {
             echo "2";
             $row['2'] = substr($row['2'], 1);
        } else if (strpos($row['3'], "*") !== false){
             echo "3";
             $row['3'] = substr($row['3'], 1);
        } else if (strpos($row['4'], "*") !== false){
             echo "4";
             $row['4'] = substr($row['4'], 1);
        } 
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