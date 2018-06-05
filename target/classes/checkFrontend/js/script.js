window.onload = function() {
    document.getElementById("generateButton").onclick = function (ev) {
        clear();
        var result = app.scanTicket(document.getElementById("input").value);
        setTimeout(function () {
            if(result === -1){
                danger();
            }
            if(result === -2){
                info();
            }
            if(result >= 0){
                success(result);
            }
        }, 100);
    };
    online();
    clear();
};

function clear() {
    document.getElementById("info").style.display = "none";
    document.getElementById("danger").style.display = "none";
    document.getElementById("valid").style.display = "none";
}
function offline(total) {
    document.getElementById("offline").style.display = "block";
    document.getElementById("total").innerText = total;
}
function online() {
    document.getElementById("offline").style.display = "none";
}
function success(total) {
    document.getElementById("valid").style.display = "block";
    document.getElementById("count").innerText = total;

    document.getElementById("danger").style.display = "none";
    document.getElementById("info").style.display = "none";
}

function danger() {
    document.getElementById("danger").style.display = "block";
    document.getElementById("valid").style.display = "none";
    document.getElementById("info").style.display = "none";
}

function info() {
    document.getElementById("info").style.display = "block";
    document.getElementById("danger").style.display = "none";
    document.getElementById("valid").style.display = "none";
}