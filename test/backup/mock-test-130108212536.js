if (typeof require != "undefined") {
    var buster = require("../../lib/buster");
}

var assert = buster.assert;

buster.testCase("Get object", {
    "test1": function () {
        assert.equals(getTodoItem(), "Hello World!");
    } 
});
