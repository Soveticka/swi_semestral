<?php

function createPopup($message, $mode): void
{
    echo "
        <div class='modal popup' id='popups' tabindex='-1' role='dialog' aria-labelledby='popupLabel' aria-hidden='true'>
            <div class='modal-dialog modal-dialog-centered' role='document'>
                <div class='modal-content'>
                    <div class='modal-header'>
    ";
    if ($mode == "Error") {
        echo "<h5 class='modal-title' id='popupLabel' style='color:#ff5555'>" . $mode . "</h5>";
    } else {
        echo "<h5 class='modal-title' id='popupLabel' style='color:#50fa7b'>" . $mode . "</h5>";
    }
    echo "   
                        <button id='close' type='button' class='close' data-dismiss='modal' aria-label='Close'>
                            <span aria-hidden='true'>&times;</span>
                        </button>
                    </div>
                    <div class='modal-body'>
                        " . $message . "
                    </div>
                </div>
            </div>
        </div>
    ";
    if ($mode == "Úspěch") {
        echo "
            <script>
                var elem = document.getElementById('countdown');
                var counter = parseInt(elem.innerText);
                var inst = setInterval(change, 1000);
            
                function change() {
                    elem.innerHTML = counter;
                    counter--;
                    if (counter <= 0) {
                        counter = 0;
                        clearInterval(inst); // uncomment this if you want to stop refreshing after one cycle
                    }
                }
            </script>
        ";

        $_SESSION[_RESET_SESSION] = true;
        header('Refresh: 5;URL=http://uni.soveticka.eu/swi_semestral/');
        exit();
    }
}
