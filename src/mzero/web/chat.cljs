(ns mzero.web.chat
  (:require [reagent.core :as r]))

(def messages [])

(def chat-data (r/atom {:messages messages}))

(defn add-message [chat-data user text]
  (update chat-data :messages conj {:user user :text text}))

(defn ^:export get-chat-data [] @chat-data)

(defn ^:export send-message [user text]
  (when (not (some #(= user %) ["you" "me"]))
    (throw (js/Error. "User should be 'me' or 'you'")))
  (swap! chat-data add-message user text)
  (let [messages-div (.getElementById js/document "mzc-messages")
        messages-height (.-offsetHeight messages-div)]
    ;; wait a few ms for the component to render again then scroll down
    (.setTimeout js/window #(set! (.-scrollTop messages-div) messages-height) 25)))

(defn send-my-message! []
  (send-message "me" (:current-message chat-data))
  (swap! chat-data assoc :current-message ""))

(defn message-row [index message]
  [:div {:class (name (:user message))
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
        :placeholder "Talk to the AI"
        :value (:current-message @chat-data)
        :auto-focus true
        :on-key-up #(when (= "Enter" (.-key %)) (send-my-message!))
        :on-change #(swap! chat-data assoc :current-message (.. % -target -value))}]
      [:button.btn.btn-primary
       {:type "button"
        :on-click send-my-message!}
       "Send!"]]]]])
