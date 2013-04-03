if (typeof require != "undefined") {
    var buster = require("../../lib/buster");
}

var assert = buster.assert;

buster.testCase("Fake server", {
    setUp: function () {
        this.server = sinon.fakeServer.create();
    },

    "should send GET to /url": function () {
        FUNCTION_NAME();
        assert.equals(this.server.requests.length, 1);
        assert.match(this.server.requests[0], {
            method: REQUEST_METHOD,
            url: SERVER_URL
        });
    },

    "should GET object from fake server": function () {
        this.server.respondWith(
            REQUEST_METHOD,
            SERVER_URL,
            [200, {"content-type": "CONTENTTYPE"},
            "RESPONSE"]);

        var callback = this.spy();
        FUNCTION_NAME(callback);
        this.server.respond();

        assert(callback.called);
        assert.calledWith(callback,"RESPONSE");
        assert.equals(callback.getCall(0).args[0], 'RESPONSE');
    }
});
