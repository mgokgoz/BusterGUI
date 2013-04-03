if (typeof require != "undefined") {
    var buster = require("../../lib/buster");
}

var assert = buster.assert;
var refute = buster.assertions.refute;

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

    "should GET object from fake server, call one of the function stub but not the other with given response": function () 	{
        this.server.respondWith(
            'GET',
             '/url',
            [200, {"content-type": "text/html"},
            "armut"]);

        var callback = this.spy();
        var spy1=this.spy();
        var spy2=this.spy();
        getTodoItem(callback,spy1,spy2);
        this.server.respond();

        assert(callback.called);
        assert(spy2.called);
        refute(spy1.called);
        assert.calledWith(callback,"armut");
        assert.equals(callback.getCall(0).args[0], 'armut');
    },
        "should GET object from fake server, call the other function stub with given response": function () {
        this.server.respondWith(
            'GET',
             '/url',
            [200, {"content-type": "text/html"},
            "egg"]);

        var callback = this.spy();
        var spy1=this.spy();
        var spy2=this.spy();
        var spy3=this.spy();
        getTodoItem(callback,spy1,spy2,spy3);
        this.server.respond();

        assert(callback.called);
        assert(spy1.called);
        assert(spy3.called);
        refute(spy2.called);
        assert.calledWith(callback,"egg");
        assert.equals(callback.getCall(0).args[0], 'egg');
    }
});

