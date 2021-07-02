<?php
require "init.php";

$q = $_GET['q'];

if ($q != "") {
    getNumberOfTimes($q, $conn);
}

function getFreeTime($time, $date, $conn): void
{
    $timeList = ['8:00', '10:00', '12:00', '14:00', '16:00'];
    if (isset($time)) {
        foreach ($timeList as $item) {
            if ($time == $item) {
                echo "<option value='" . $item . "' selected>" . $item . "</option>";
                continue;
            }
            echo "<option value='" . $item . "'>" . $item . "</option>";
        }
    } else {
        foreach ($timeList as $item) {
            echo "<option value='" . $item . "'>" . $item . "</option>";
        }
    }
}

function getNumberOfTimes($date, $conn): void
{
    $usedTimesQuery = mysqli_query($conn, 'SELECT timeI FROM ' . _ORDERS . ' WHERE dateI="' . $date . '"');
    echo mysqli_num_rows($usedTimesQuery);
}


