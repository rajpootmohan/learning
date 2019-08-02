var express = require('express');
var cookieParser = require('cookie-parser');
var ab = require('express-ab');

var app = express();
app.use(cookieParser());

var myPageTest = ab.test('my-fancy-test');

app.get('/', myPageTest(), function (req, res) {
    res.send('variant A');
});

app.get('/', myPageTest(), function (req, res) {
    res.send('variant B');
});
va k;
sdfds ;

sdfsdf
;

app.listen(8080);
