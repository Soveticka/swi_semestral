<?php
ob_start();
require "./func/init.php";
require "./func/validate.php";
if (!isset($_GET['p']) || $_GET['p'] != 'review' || !$_SESSION[_REVIEW_ENABLE]) {
    header("Location: ./");
    exit();
}

?>
<h1>Rezervační systém</h1>
<h2>Rekapitulace objednávky</h2>
<form action="" method="POST">
    <div class="row mb-3">
        <div class="col-4">
            <label class="form-label" for="firstName">Jméno<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="firstName" name="firstName" required readonly <?php if (isset($_SESSION['firstName'])) echo "value='" . $_SESSION['firstName'] . "'" ?>>
        </div>
        <div class="col-4">
            <label class="form-label" for="emailaddress">Email<span class="text-danger">*</span></label>
            <input class="form-control" type="email" id="emailaddress" name="emailaddress" readonly <?php if (isset($_SESSION['emailaddress'])) echo "value='" . $_SESSION['emailaddress'] . "'" ?>>
        </div>
        <div class="col-4">
            <label class="form-label" for="vehicleBrand">Značka automobilu<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="vehicleBrand" name="vehicleBrand" readonly <?php if (isset($_SESSION['vehicleBrand'])) echo "value='" . $_SESSION['vehicleBrand'] . "'" ?>>
        </div>
    </div>
    <div class="row mb-3">
        <div class="col-4">
            <label class="form-label" for="lastName">Příjmení<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="lastName" name="lastName" readonly <?php if (isset($_SESSION['lastName'])) echo "value='" . $_SESSION['lastName'] . "'" ?>>
        </div>
        <div class="col-4">
            <label class="form-label" for="phonenumber">Telefon<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="phonenumber" name="phonenumber" readonly <?php if (isset($_SESSION['phonenumber'])) echo "value='" . $_SESSION['phonenumber'] . "'" ?>>
        </div>
        <div class="col-4">
            <label class="form-label" for="vehicleModel">Model automobilu<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="vehicleModel" name="vehicleModel" readonly <?php if (isset($_SESSION['vehicleModel'])) echo "value='" . $_SESSION['vehicleModel'] . "'" ?>>
        </div>
    </div>
    <div class="row mb-3">
        <div class="col-4">
            <label class="form-label" for="ico">IČO</label>
            <input class="form-control" type="text" id="ico" name="ico" readonly <?php if (isset($_SESSION['ico'])) echo "value='" . $_SESSION['ico'] . "'" ?>>
        </div>
        <div class="col-4">
            <label class="form-label" for="address">Ulice a č.p<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="address" name="address" readonly <?php if (isset($_SESSION['address'])) echo "value='" . $_SESSION['address'] . "'" ?>>
        </div>
        <div class="col-4">
            <label class="form-label" for="spz">SPZ<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="spz" name="spz" readonly <?php if (isset($_SESSION['spz'])) echo "value='" . $_SESSION['spz'] . "'" ?>>
        </div>
    </div>
    <div class="row mb-3">
        <div class="col-4" id="date">
            <label class="form-label" for="dateI">Datum<span class="text-danger">*</span></label>
            <input class="form-select form-control" id="dateI" type="date" name="dateI" readonly oninput="getFreeTimeByDate(this.value)" <?php if (isset($_SESSION['dateI'])) echo "value='" . $_SESSION['dateI'] . "'" ?>>
        </div>
        <div class="col-4">
            <label class="form-label" for="city">Město<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="city" readonly name="city" <?php if (isset($_SESSION['city'])) echo "value='" . $_SESSION['city'] . "'" ?>>
        </div>
        <div class="col-4">
            <label class="form-label" for="yearOfProduction">Rok výroby<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="yearOfProduction" name="yearOfProduction" readonly <?php if (isset($_SESSION['yearOfProduction'])) echo "value='" . $_SESSION['yearOfProduction'] . "'" ?>>
        </div>
    </div>
    <div class="row mb-3">
        <div class="col-4">
            <label class="form-label" for="timeI">Čas<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="timeI" name="timeI" readonly <?php if (isset($_SESSION['timeI'])) echo "value='" . $_SESSION['timeI'] . "'" ?>>
        </div>
        <div class="col-4">
            <label class="form-label" for="zip">PSČ<span class="text-danger">*</span></label>
            <input class="form-control" type="text" id="zip" readonly name="zip" <?php if (isset($_SESSION['zip'])) echo "value='" . $_SESSION['zip'] . "'" ?>>
        </div>
        <div class="col-4 d-xl-flex align-items-xl-center">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="tow" name="tow" value="Ano" readonly onclick="return false;" <?php if (isset($_SESSION['tow'])) echo "checked" ?>><label class="form-check-label" for="tow">Využít odvoz vozidla</label>
            </div>
        </div>
    </div>
    <div class="row mb-3">
        <div class="col-4 col-md-12">
            <label class="form-label" for="description">Popis závady</label>
            <textarea class="form-control" id="description" readonly name="description"><?php if (isset($_SESSION['description'])) echo urldecode($_SESSION['description']); ?></textarea>
        </div>
    </div>
    <div class="row mb-3">
        <div class="col-6 d-md-flex justify-content-md-start">
            <button class="btn btn-secondary" id="edit" type="submit" name="edit">Upravit</button>
        </div>
        <div class="col-6 d-md-flex justify-content-md-end">
            <button class="btn btn-success" id="submit" type="submit" name="submit">Rezervovat</button>
        </div>
    </div>
</form>

<?php


if (isset($_POST['submit'])) {
    if (!isValidPhoneNumber($_POST['phonenumber'])) return;
    if (!isValidAddress($_POST['address'])) return;
    if (!isValidCity($_POST['city'])) return;
    if (!isValidZip($_POST['zip'])) return;
    if (!isValidSPZ($_POST['spz'])) return;
    if (!isValidYearOfProd($_POST['yearOfProduction'])) return;
    $query = "INSERT INTO " . _ORDERS . "(firstname, lastname, emailAddress, phoneNumber, dateI, timeI, ico, city, address, zip, vehicleBrand, vehiclemodel, spz, yearOfProduction, tow, description)" .
        " VALUES("
        . '"' . $_POST['firstName'] . '",'
        . '"' . $_POST['lastName'] . '",'
        . '"' . $_POST['emailaddress'] . '",'
        . '"' . $_POST['phonenumber'] . '",'
        . '"' . $_POST['dateI'] . '",'
        . '"' . $_POST['timeI'] . '",'
        . '"' . $_POST['ico'] . '",'
        . '"' . $_POST['city'] . '",'
        . '"' . $_POST['address'] . '",'
        . '"' . $_POST['zip'] . '",'
        . '"' . $_POST['vehicleBrand'] . '",'
        . '"' . $_POST['vehicleModel'] . '",'
        . '"' . $_POST['spz'] . '",'
        . '"' . $_POST['yearOfProduction'] . '",'
        . '"' . $_POST['tow'] . '",'
        . '"' . $_POST['description'] . '"' .
        ")";
    if (mysqli_query($conn, $query)) {
        require_once "./func/popup.php";
        createPopup("Rezervace úspěšně vytvořena, za <span id='countdown'>5</span>s budete přesměrováni na hlavní stránku.", "Úspěch");
    } else {
        require_once "./func/popup.php";
        createPopup("Během vytváření rezervace se něco nezdařilo, zkuste to prosím později. <br> Pokud i nadále nebudete moct vytvořit rezervaci, kontaktujte autoservis: kontakt!", "Error");
    }
}

if (isset($_POST['edit'])) {
    header('Location: ./');
    exit();
}
?>


