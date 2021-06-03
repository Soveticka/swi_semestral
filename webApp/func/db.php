<?php
$conn=mysqli_connect(_DB_IP,_DB_USER,_DB_PASS) or die(_DB_ERR_CON);
$sele=mysqli_select_db($conn,_DB_NAME) or die(_DB_ERR_SEL);
$conn->set_charset("utf8");
