if (typeof require != "undefined") {
    var buster = require("../../lib/buster");
}

var assert = buster.assert;

buster.testCase("Fake server", {
    setUp: function () {
        this.server = sinon.fakeServer.create();
    },

    "should send GET to /url": function () {
        getTodoItem();
        assert.equals(this.server.requests.length, 1);
        assert.match(this.server.requests[0], {
            method: 'GET',
            url:  '/url'
        });
    },

    "should GET object from fake server": function () {
        this.server.respondWith(
            'GET',
             '/url',
            [200, {"content-type": "text/html"},
            "egg"]);

        var callback = this.spy();
        getTodoItem(callback);
        this.server.respond();

        assert(callback.called);
        assert.calledWith(callback,"egg");
        assert.equals(callback.getCall(0).args[0], 'egg');
    }
});
