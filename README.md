# virtual-dom

A Clojure library for constructing virtual DOMs using [virtual-dom](https://github.com/Matt-Esch/virtual-dom).

## Installation

```clojure
[ildar/virtual-dom "0.3.1-SNAPSHOT"]
```

Then require `virtual-dom.core` or, if you'd like a more reactive experience, `virtual-dom.elm`.

## Building

After installing Node modules with `npm install`, build the Javascript files by running `make`.

## Usage

### virtual-dom.core

The primary interface for virtual-dom is the `renderer` function in `virtual-dom.core`. Call it with a DOM element to get a function that renders a tree of Clojure data into HTML within that element.

For example,

```clojure
(let [render (virtual-dom.core/renderer js/document.body)]
  (render [:div {} "Hello, world"]))
```

To update the HTML, call the render function again with new data.

Rendering works well with core.async channels and goroutines. For instance, assuming you have a channel called `ui` on which UI data trees are passed, the following updates the HTML every time there's a new UI state.

```clojure
(let [render (virtual-dom.core/renderer js/document.body)]
  (go-loop []
    (render (<! ui))
    (recur))
```

### UI data trees

virtual-dom is based on [virtual-dom](https://github.com/Matt-Esch/virtual-dom), with a transparent mapping between Clojure data structures and the virtual-dom functions `VNode`, `VText`, and `svg`.

For example, the Clojure tree

```clojure
[:div {:id "root" :className "test"}
 [:span {} "Hello, "]
 [:span {} "world"]]
```

is equivalent to the JavaScript

```javascript
new VNode('div', {id: 'root', className: 'test'}, [
  VNode('span', {}, VText('Hello, ')),
  VNode('span', {}, VText('world'))
])
new VText("Hello, world"));
```

Any children that are seqs are flattened, allowing, for instance,

```clojure
[:ul {}
 (for [i (range 1 6)]
   [:li {} i])]
```

which produces the HTML

```html
<ul>
  <li>1</li>
  <li>2</li>
  <li>3</li>
  <li>4</li>
  <li>5</li>
</ul>
```

virtual-dom handles SVG nodes transparently, so long as an `svg` node is part of the tree. Descendant nodes of `svg` are constructed with the `virtual-hyperscript/svg` function rather than `VNode`. Descendants of a `foreignObject` tag are constructed with `VNode`.

## Hooks

To get the actual DOM element in your UI tree, include in a node's attributes a virtual-dom hook. Provide a value by calling `virtual-dom.hooks/hook` with a function that takes a DOM node. For instance,

```
[:input {:hookFocus (virtual-dom.hooks/hook (fn [node] (.focus node)))}]
```
