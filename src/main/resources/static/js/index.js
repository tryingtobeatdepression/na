'use strict';

function onClick() {
    alert("Hello World!");
}

$('#login').submit(function (e) {
    e.preventDefault();
});
