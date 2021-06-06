<?php
require "init.php";
$timeList = ['8:00', '10:00', '12:00', '14:00', '16:00'];

$q = $_GET['q'];

if ($q != "") {
    getNumberOfTimes($q, $conn);
}

function getTimesFromDB($timeList, $date, $conn): array
{
    $usedTimesQuery = mysqli_query($conn, 'SELECT timeI FROM ' . _ORDERS . ' WHERE dateI="' . $date . '"');
    while ($row = mysqli_fetch_array($usedTimesQuery, MYSQLI_ASSOC)) {
        if (in_array($row['timeI'], $timeList)) {
            if ($key = array_search($row['timeI'], $timeList)) {
                unset($timeList[$key]);
            }
        }
    }
    return array_values($timeList);
}

function ajaxGetFreeTime($timeList, $date, $conn): void
{
    echo json_encode(getTimesFromDB($timeList, $date, $conn));
}

function getFreeTime($time, $date, $conn): void
{
    $timeList = ['8:00', '10:00', '12:00', '14:00', '16:00'];
    if (isset($time)) {
        $timeList = getTimesFromDB($timeList, $date, $conn);
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


