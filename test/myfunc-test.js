if (typeof require != "undefined") {
    var buster = require("../../lib/buster");
    require("./myfunc");
}

var assert = buster.assert;
var refute = buster.assertions.refute;

buster.testCase("Do some math", {
    "test1": function () {
        assert.equals(doMath(2,3), 5);
    } 
});

