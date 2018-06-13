window.onload = function() {
    document.getElementById("generateButton").onclick = function (ev) {
        document.getElementById("input").value = app.newTicket();
    };
    online();
    showGenerate();
    document.getElementById("clickGenerate").onclick = showGenerate;
    document.getElementById("clickStats").onclick = showStats;

};
function offline(total) {
    document.getElementById("offline").style.display = "block";
    document.getElementById("total").innerText = total;
}
function online() {
    document.getElementById("offline").style.display = "none";
}
function showGenerate() {
    document.getElementById("generateShow").style.display = "block";
    document.getElementById("statsShow").style.display = "none";
}
function showStats() {
    document.getElementById("generateShow").style.display = "none";
    document.getElementById("statsShow").style.display = "block";
    handleShowOfTable();
}

function handleShowOfTable() {
    var data = JSON.parse(app.getStats());
    var obj = document.getElementById("statsShow");
    var html = '<table class="table">';
    for(var i = 0; i < data.length; i++){
        var time = new Date(data[i].Date);
        html += '<tr><td>'+time.getDay()+'-'+time.getMonth()+'-'+time.getFullYear()+' '+time.getHours()+':00:00</td><td>'+data[i].Amount+'</td></tr>'
    }
    html += '</table>';
    obj.innerHTML = html;
}