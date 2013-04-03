var fs, esprima, fname, content, options, syntax;

if (typeof require === 'function') {
    fs = require('fs');
    esprima = require('esprima');
    escodegen = require('escodegen');
} else if (typeof load === 'function') {
    try {
        load('esprima.js');
    } catch (e) {
        load('../esprima.js');
    }
}

// Shims to Node.js objects when running under Rhino.
if (typeof console === 'undefined' && typeof process === 'undefined') {
    console = { log: print };
    fs = { readFileSync: readFile };
    process = { argv: arguments, exit: quit };
    process.argv.unshift('esparse.js');
    process.argv.unshift('rhino');
}


try {
    code = escodegen.generate(AAAAAAAAAAAAAAA);
    console.log(code);
} catch (e) {
    console.log('Error: ' + e.message);
    process.exit(1);
}
