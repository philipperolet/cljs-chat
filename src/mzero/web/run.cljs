(ns mzero.web.run
  (:require  [mzero.web.chat :as chat]
             [reagent.dom :refer [render]]
             [goog.dom :as gdom]))

(defn- run-chat []
  (render [chat/chat-component] (gdom/getElement "cljs-chat")))

(run-chat)
