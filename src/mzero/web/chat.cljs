(ns mzero.web.chat
  (:require [reagent.core :as r]
            [cljs.spec.alpha :as s]))

(def ^:export placeholder-message "Talk here")
(def ^:export send-button-callback (constantly nil))

(s/def ::user #{"me" "you"})
(s/def ::text string?)

(s/def ::message (s/keys :req-un [::user ::text]))
(s/def ::messages (s/every ::message))

(def messages [])

(def chat-data (r/atom {:messages messages}))

(defn- validate!
  ([spec obj message]
   (when (not (s/valid? spec obj))
     (throw (js/Error. message))))
  ([spec obj]
   (validate! spec obj (str "Object has invalid specs: " (s/explain-str spec obj)))))

(defn ^:export get-messages [] (clj->js (:messages @chat-data)))

(defn ^:export set-messages [raw-messages]
  (let [messages
        (if (array? raw-messages) ;; if messages is a JS array
          (js->clj raw-messages :keywordize-keys true)
          raw-messages)]
    (validate! ::messages messages)
    (swap! chat-data assoc :messages messages)))

(defn ^:export send-message
  ([user text callback]
   ;; add message to history
   (let [message {:user user :text text}]
     (validate! ::message message)
     (swap! chat-data update :messages conj message)
     
     ;; scroll chat window, then callback
     (let [messages-div (.getElementById js/document "mzc-messages")
           messages-height (.-offsetHeight messages-div)
           scroll-and-callback
           (fn []
             (set! (.-scrollTop messages-div) messages-height)
             (callback message))]
       ;; wait a few ms for the component to render again then scroll & callback
       (.setTimeout js/window scroll-and-callback 25))))
  ([user text]
   (send-message user text (constantly nil))))

(defn send-my-message! []
  (send-message "me" (:current-message @chat-data) send-button-callback)
  (swap! chat-data assoc :current-message ""))

(defn message-row [index message]
  [:div {:class (:user message)
         :key (str "msg" index)}
   [:div (:text message)]])

(defn chat-component []
  [:div
   [:div#mzero-chat.mzero-chat
    [:div.bottom
     [:div#mzc-messages.messages
      (map-indexed message-row (:messages @chat-data))]
     [:div.new-message
      [:input.message-input
       {:type "text"
        :placeholder placeholder-message
        :value (:current-message @chat-data)
        :auto-focus true
        :on-key-up #(when (= "Enter" (.-key %)) (send-my-message!))
        :on-change #(swap! chat-data assoc :current-message (.. % -target -value))}]
      [:button.btn.btn-primary
       {:type "button"
        :on-click send-my-message!}
       "Send!"]]]]])
