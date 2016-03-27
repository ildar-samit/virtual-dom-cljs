src/virtual_dom/virtual-dom.min.js: src/virtual_dom/virtual-dom.js
	node_modules/.bin/uglifyjs src/virtual_dom/virtual-dom.js -o src/virtual_dom/virtual-dom.min.js

src/virtual_dom/virtual-dom.js: src/virtual_dom/virtual-dom-main.js
	node_modules/.bin/browserify src/virtual_dom/virtual-dom-main.js -o src/virtual_dom/virtual-dom.js
