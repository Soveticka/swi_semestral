<?php
    session_start();

    include "./pages/parts/header.php";
    include "./func/init.php";

    $page = $_GET['p'];

    if ($_SESSION[_RESET_SESSION]) {
        session_unset();
        session_destroy();
    }

    if ($page == 'review') {
        include "./pages/review.php";
    } else {
        include "./pages/createOrder.php";
    }

    include("./pages/parts/footer.php");
?>