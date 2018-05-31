window.onload = function() {
    document.getElementById("generateButton").onclick = function (ev) {
        document.getElementById("input").value = app.newTicket();
    };
    online();
}
function offline(total) {
    document.getElementById("offline").style.display = "block";
    document.getElementById("total").innerText = total;
}
function online() {
    document.getElementById("offline").style.display = "none";
}