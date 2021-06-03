<?php
require "init.php";

$q = $_GET['q'];

if ($q != "") {
    ajaxGetVehicle($q, $conn);
}

function getModelsFromDB($brand, $conn): mysqli_result
{
    return mysqli_query($conn, 'SELECT model FROM ' . _VEHICLE_MODEL . ' WHERE vehicleBrand_brand="' . $brand . '"');
}

function ajaxGetVehicle($brand, $conn): void
{
    echo json_encode(mysqli_fetch_all(getModelsFromDB($brand, $conn)));
}

function getVehicleModel($model, $brand, $conn) : void
{
    $queryModel = getModelsFromDB($brand, $conn);

    if (mysqli_num_rows($queryModel) > 0) {
        while ($entry = mysqli_fetch_array($queryModel)) {
            if ($model == $entry['model']) {
                echo "<option value='" . $entry['model'] . "' selected>" . $entry['model'] . "</option>";
                continue;
            }
            echo "<option value='" . $entry['model'] . "'>" . $entry['model'] . "</option>";
        }
    }
}



