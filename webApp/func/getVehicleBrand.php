<?php

function getVehicleBrand($brand = "", $conn): void
{
    $result_Brand = mysqli_query($conn, 'SELECT * FROM ' . _VEHICLE_BRAND . '');
    if (mysqli_num_rows($result_Brand) > 0) {
        while ($entry = mysqli_fetch_array($result_Brand)) {
            if ($brand != "") {
                if ($entry['brand'] == $brand) {
                    echo "<option value='" . $entry['brand'] . "' selected>" . $entry['brand'] . "</option>";
                    continue;
                }
                echo "<option value='" . $entry['brand'] . "'>" . $entry['brand'] . "</option>";
                continue;
            }
            echo "<option value='" . $entry['brand'] . "'>" . $entry['brand'] . "</option>";
        }
    }
}

