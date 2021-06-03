<?php
    session_start();

    include "./pages/parts/header.php";
    include "./func/init.php";

    $page = $_GET['p'];

    if($page == 'review'){
        include "./pages/review.php";
    }else{
        include "./pages/createOrder.php";
    }

    include("./pages/parts/footer.php");
?>