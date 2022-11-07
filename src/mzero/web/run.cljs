(ns mzero.web.run
  (:require  [mzero.web.chat.chat :as chat]
             [reagent.dom :refer [render]]
             [goog.dom :as gdom]))

(defn- run-game []
  (render [chat/chat-component] (gdom/getElement "app")))

(run-game)
