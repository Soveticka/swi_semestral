function getFreeTimeByDate(str) {
    var parentTimeSelect = document.getElementById("timeI");
    var dateDiv = document.getElementById("date");
    var error;
    if (new Date() > new Date(str)) {
        if (!$("#date > span.error-message").length) {
            parentTimeSelect.innerHTML = '';
            parentTimeSelect.setAttribute("disabled", "");
            error = document.createElement("span");
            error.className = "text-danger error-message";
            error.innerText = "Nelze zarezervovat termín který již byl!";
            dateDiv.appendChild(error);
        }
        return;
    }

    $.ajax({
        url: 'func/getFreeTime.php?q=' + str,
        type: "get",
        success: function (msg) {
            $("#date > span.error-message").remove();
            if (parseInt(msg) >= 2) {
                parentTimeSelect.innerHTML = '';
                var option = document.createElement("option");
                option.setAttribute("value", "0");
                option.text = "Všechny časy jsou zabrány";
                parentTimeSelect.appendChild(option);
                parentTimeSelect.setAttribute("disabled", "");
            }
        },
        dataType: "json"
    })
}

function getVehicleModelByBrand(str) {
    var parent = document.getElementById("vehicleModel");
    $.ajax({
        url: 'func/getVehicleModel.php?q=' + str,
        type: "get",
        success: function (msg) {
            parent.innerHTML = '';
            parent.removeAttribute("disabled");
            for (let i = 0; i < msg.length; i++) {
                var option = document.createElement("option");
                option.setAttribute("value", msg[i]);
                option.text = msg[i];
                parent.appendChild(option);
            }
        },
        dataType: "json"
    })
}

$(document).ready(function() {
    $("#close").click(function() {
        $("#popups").removeClass("popup");
        //$("#popups").modal("hide");
    });
});

