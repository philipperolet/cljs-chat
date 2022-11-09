(defproject org.clojars.philipperolet/cljs-chat "0.3"
  :description "A simple chat component"
  :url "https://github.com/philipperolet/cljs-chat"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.758"]
                 [cljsjs/react "17.0.2-0"]
                 [cljsjs/react-dom "17.0.2-0"]
                 [reagent "1.1.1"]]
  :source-paths ["src"]

  :aliases {"fig"       ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:prod"   ["run" "-m" "figwheel.main" "--output-to" "resources/public/js/cljs-chat.js" "-O" "advanced" "-bo" "dev"]}

  :profiles {:dev {:dependencies [[com.bhauman/figwheel-main "0.2.18"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]]}})
