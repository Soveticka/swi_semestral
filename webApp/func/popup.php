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
        echo "<h5 class='modal-title' id='popupLabel' style='color:#50fa7b'>". $mode . "</h5>";
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
}
