if (typeof require != "undefined") {
    var buster = require("../../lib/buster");
}

var assert = buster.assert;
buster.testCase("Sinon tests", {
"test should call all subscribers, even if there are exceptions" : function(){
    var message = 'an example message';
    var error = 'an example error message';
    var stub = sinon.stub().throws();
    var spy1 = sinon.spy();
    var spy2 = sinon.spy();

    PubSub.subscribe(message, stub);
    PubSub.subscribe(message, spy1);
    PubSub.subscribe(message, spy2);

    PubSub.publishSync(message, undefined);

    assert(spy1.called);
    assert(spy2.called);
    assert(stub.calledBefore(spy1));
}
});
