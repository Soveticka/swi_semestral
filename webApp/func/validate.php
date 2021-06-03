<?php

function isValidPhoneNumber($phoneNumber): bool
{
    $regex = "/^(\\\\+\d{1,3}( )?)?(\d{3}[- .]?){2}\d{3}$/m";
    return preg_match($regex, $phoneNumber);
}

function isValidAddress($address): bool
{
    $regex = "/[A-Za-zÀ-ž -.]+([0-9]+)/m";
    return preg_match($regex, $address);
}

function isValidCity($city): bool
{
    $regex = "/[A-Za-zÀ-ž -]+/m";
    return preg_match($regex, $city);
}


function isValidZip($zip): bool
{
    $regex = "/(\d{3}( )?)(\d{2})/m";
    return preg_match($regex, $zip);
}

function isValidSPZ($spz): bool
{
    $regex = "/[A-Za-z0-9]{7}/m";
    return preg_match($regex, $spz);
}

function isValidYearOfProd($year): bool
{
    return intval($year) > 1855 && intval($year) <= date("Y");
}

?>