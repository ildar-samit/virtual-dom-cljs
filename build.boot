(set-env!
 :resource-paths #{"src"}
 :dependencies '[[org.clojure/clojurescript "1.8.34"]
                 [org.clojure/core.async "0.2.374"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[adzerk.bootlaces :refer :all])

(def +version+ "0.3.0-SNAPSHOT")
(bootlaces! +version+)

(task-options!
 pom  {:project     'ildar/virtual-dom
       :version     +version+
       :description "Virtual DOM in ClojureScript"
       :url         "https://github.com/ildar-samit/virtual-dom-cljs.git"})

(deftask build []
  (comp (pom) (jar)))
