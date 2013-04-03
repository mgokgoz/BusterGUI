<!DOCTYPE html>
<html>
<body>

<p>
JavaScript can write directly into the HTML output stream:
</p>

<script>
document.write("<h1>This is a heading</h1>");
document.write("<p>This is a paragraph.</p>");
function getTodoItem(cb) {
    var xmlhttp,object="";
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else {
        xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            object=xmlhttp.responseText;
        }
    };
    xmlhttp.open('GET', 'http://localhost:8080/', true);
    xmlhttp.send();
    return object;
}
var a;
var b=getTodoItem(a);
document.write("<h1>"+ b +"</h1>");
</script>

<p>
You can only use <strong>document.write</strong> in the HTML output.
If you use it after the document has loaded (e.g. in a function), the whole document will be overwritten.
</p>

</body>
</html>
