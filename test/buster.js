var config = module.exports;

config["My tests"] = {
    rootPath: "../",
    environment: "browser",//"browser", // or "node"

    sources: [
	"lib/*.js"
    ],

    tests: [
	"test/*-test.js"
    ]
}
