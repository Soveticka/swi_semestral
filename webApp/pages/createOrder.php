<?php
ob_start();

require "./func/validate.php";

$result_Brand = mysqli_query($conn, 'SELECT * FROM ' . _VEHICLE_BRAND . '');
$result_Model = mysqli_query($conn, 'SELECT * FROM ' . _VEHICLE_MODEL . '');

?>
    <h1>Rezervační systém</h1>
    <h2>Vytvoření objednávky</h2>
    <form action="" method="POST">
        <div class="row mb-3">
            <div class="col-4">
                <label class="form-label" for="firstName">Jméno <span>*</span></label>
                <input class="form-control" type="text" id="firstName" name="firstName" required="" <?php if (isset($_SESSION['firstName'])) echo "value='" . $_SESSION['firstName'] . "'" ?>>
            </div>
            <div class="col-4">
                <label class="form-label" for="emailaddress">Email <span>*</span></label>
                <input class="form-control" type="email" id="emailaddress" name="emailaddress" required="" <?php if (isset($_SESSION['emailaddress'])) echo "value='" . $_SESSION['emailaddress'] . "'" ?>>
            </div>
            <div class="col-4">
                <label class="form-label" for="vehicleBrand">Značka automobilu <span>*</span></label>
                <select class="form-select form-control" id="vehicleBrand" name="vehicleBrand" required="" onchange="getVehicleModelByBrand(this.value)">
                    <?php
                    require_once "./func/getVehicleBrand.php";
                    getVehicleBrand($_SESSION['vehicleBrand'], $conn);
                    ?>
                </select>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-4">
                <label class="form-label" for="lastName">Příjmení <span>*</span></label>
                <input class="form-control" type="text" id="lastName" name="lastName" required="" <?php if (isset($_SESSION['lastName'])) echo "value='" . $_SESSION['lastName'] . "'" ?>>
            </div>
            <div class="col-4">
                <label class="form-label" for="phonenumber">Telefon <span>*</span></label>
                <input class="form-control" type="text" id="phonenumber" name="phonenumber" required="" <?php if (isset($_SESSION['phonenumber'])) echo "value='" . $_SESSION['phonenumber'] . "'" ?>>
                <?php
                if (isset($_GET['errPhone'])) {
                    echo '<div class="alert alert-danger alert-dismissible fade show mt-1 mb-0" role="alert">';
                    echo 'Zadali jste chybně telefonní číslo';
                    echo '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
                    echo '</div>';
                }
                ?>
            </div>
            <div class="col-4">
                <label class="form-label" for="vehicleModel">Model automobilu <span>*</span></label>
                <select class="form-control form-select" id="vehicleModel" name="vehicleModel" required="">
                    <?php
                    if (isset($_SESSION['vehicleModel'])) {
                        require_once "./func/getVehicleModel.php";
                        getVehicleModel($_SESSION['vehicleModel'], $_SESSION['vehicleBrand'], $conn);
                    }
                    ?>
                </select>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-4">
                <label class="form-label" for="ico">IČO</label>
                <input class="form-control" type="text" id="ico" name="ico" <?php if (isset($_GET['ico'])) echo "value='" . $_GET['ico'] . "'" ?>>
            </div>
            <div class="col-4">
                <label class="form-label" for="address">Ulice a č.p <span>*</span></label>
                <input class="form-control" type="text" id="address" name="address" required="" <?php if (isset($_SESSION['address'])) echo "value='" . $_SESSION['address'] . "'" ?>>
                <?php
                if (isset($_GET['errAddress'])) {
                    echo '<div class="alert alert-danger alert-dismissible fade show mt-1 mb-0" role="alert">';
                    echo 'Zadali jste chybně adresu.';
                    echo '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
                    echo '</div>';
                }
                ?>
            </div>
            <div class="col-4">
                <label class="form-label" for="spz">SPZ <span>*</span></label>
                <input class="form-control" type="text" id="spz" name="spz" required="" <?php if (isset($_SESSION['spz'])) echo "value='" . $_SESSION['spz'] . "'" ?>>
                <?php
                if (isset($_GET['errSpz'])) {
                    echo '<div class="alert alert-danger alert-dismissible fade show mt-1 mb-0" role="alert">';
                    echo 'Zadali jste chybně SPZ.';
                    echo '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
                    echo '</div>';
                }
                ?>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-4" id="date">
                <label class="form-label" for="dateI">Datum <span>*</span></label>
                <input class="form-select form-control" id="dateI" type="date" name="dateI" required="" oninput="getFreeTimeByDate(this.value)" <?php if (isset($_SESSION['dateI'])) echo "value='" . $_SESSION['dateI'] . "'" ?>>
            </div>
            <div class="col-4">
                <label class="form-label" for="city">Město <span>*</span></label>
                <input class="form-control" type="text" id="city" required="" name="city" <?php if (isset($_SESSION['city'])) echo "value='" . $_SESSION['city'] . "'" ?>>
                <?php
                if (isset($_GET['errCity'])) {
                    echo '<div class="alert alert-danger alert-dismissible fade show mt-1 mb-0" role="alert">';
                    echo 'Zadali jste jméno Města';
                    echo '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
                    echo '</div>';
                }
                ?>
            </div>
            <div class="col-4">
                <label class="form-label" for="yearOfProduction">Rok výroby <span>*</span></label>
                <input class="form-control" type="text" id="yearOfProduction" name="yearOfProduction" required="" <?php if (isset($_SESSION['yearOfProduction'])) echo "value='" . $_SESSION['yearOfProduction'] . "'" ?>>
                <?php
                if (isset($_GET['errYoF'])) {
                    echo '<div class="alert alert-danger alert-dismissible fade show mt-1 mb-0" role="alert">';
                    echo 'Zadali jste nesprávný rok výroby. Povolené roky 1856 - ' . date('Y');
                    echo '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
                    echo '</div>';
                }
                ?>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-4">
                <label class="form-label" for="timeI">Čas <span>*</span></label>
                <select class="form-select form-control" id="timeI" name="timeI" required="" <?php if (!isset($_SESSION['timeI'])) echo 'disabled=""'; ?>>
                    <?php
                    if (isset($_SESSION['timeI'])) {
                        require_once "./func/getFreeTime.php";
                        getFreeTime($_SESSION['timeI'], $_SESSION['dateI'], $conn);
                    }
                    ?>
                </select>
            </div>
            <div class="col-4">
                <label class="form-label" for="zip">PSČ <span>*</span></label>
                <input class="form-control" type="text" id="zip" required="" name="zip" <?php if (isset($_SESSION['zip'])) echo "value='" . $_SESSION['zip'] . "'" ?>>
                <?php
                if (isset($_GET['errZip'])) {
                    echo '<div class="alert alert-danger alert-dismissible fade show mt-1 mb-0" role="alert">';
                    echo 'Zadali poštovní směrovací číslo';
                    echo '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
                    echo '</div>';
                }
                ?>
            </div>
            <div class="col-4 d-md-flex align-items-md-center">
                <div class="form-check d-md-flex justify-content-md-start align-items-md-center"">
                    <input class="form-check-input" type="checkbox" id="tow" name="tow" value="Ano" <?php if (isset($_SESSION['tow'])) echo "checked" ?>><label class="form-check-label" for="tow">Využít odvoz vozidla</label>
                </div>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col-4 col-md-12">
                <label class="form-label" for="description">Popis závady</label>
                <textarea class="form-control" id="description" name="description"><?php if (isset($_SESSION['description'])) echo $_SESSION['description']; ?></textarea>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col d-md-flex justify-content-md-end">
                <button class="btn btn-success" id="submit" type="submit" name="submit">Pokračovat</button>
            </div>
        </div>
    </form>
<?php


if (isset($_POST['submit'])) {
    $errors = "";
    if (!isValidPhoneNumber($_POST['phonenumber'])) $errors .= "errPhone=&";
    if (!isValidAddress($_POST['address'])) $errors .= "errAddress&";
    if (!isValidCity($_POST['city'])) $errors .= "errCity&";
    if (!isValidZip($_POST['zip'])) $errors .= "errZip&";
    if (!isValidSPZ($_POST['spz'])) $errors .= "errSpz&";
    if (!isValidYearOfProd($_POST['yearOfProduction'])) $errors .= "errYoF&";
    $_SESSION['firstName'] = $_POST['firstName'];
    $_SESSION['lastName'] = $_POST['lastName'];
    $_SESSION['emailaddress'] = $_POST['emailaddress'];
    $_SESSION['vehicleBrand'] = $_POST['vehicleBrand'];
    $_SESSION['phonenumber'] = $_POST['phonenumber'];
    $_SESSION['vehicleModel'] = $_POST['vehicleModel'];
    $_SESSION['ico'] = $_POST['ico'];
    $_SESSION['address'] = $_POST['address'];
    $_SESSION['spz'] = $_POST['spz'];
    $_SESSION['dateI'] = $_POST['dateI'];
    $_SESSION['zip'] = $_POST['zip'];
    $_SESSION['city'] = $_POST['city'];
    $_SESSION['yearOfProduction'] = $_POST['yearOfProduction'];
    $_SESSION['timeI'] = $_POST['timeI'];
    $_SESSION['zip'] = $_POST['zip'];
    $_SESSION['description'] = urlencode($_POST['description']);
    $_SESSION['tow'] = $_POST['tow'];
    if (strlen($errors) > 0) {
        header('Location: ./index.php?' . $errors);
        exit();
    }
    $_SESSION[_REVIEW_ENABLE] = true;
    header('Location: ./index.php?p=review');
    exit();
}


