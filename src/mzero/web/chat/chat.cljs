(ns mzero.web.chat.chat
  (:require [reagent.core :as r]))

(def messages [{:user :me
                :text "Salut, ça va?"}
               {:user :you
                :text "Bien et toi?"}
               {:user :me
                :text "Oui pas mal"}
               {:user :me
                :text "Tu kiffes?"}
               {:user :you
                :text "Ça va"}               {:user :you
                :text "Ça va"}               {:user :you
                :text "Ça va"}               {:user :you
                :text "Ça va"}               {:user :you
                :text "Ça va"}               {:user :you
                :text "Ça va"}])

(def chat-data (r/atom {:messages messages}))

(defn post-message [chat-data user text]
  (update chat-data :messages conj {:user user :text text}))

(defn post-message! [chat-data user text]
  (swap! chat-data post-message user text))

(defn post-my-message [chat-data]
  (-> chat-data
      (post-message :me (:current-message chat-data))
      (assoc :current-message "")))

(defn message-row [index message]
  [:div {:class (name (:user message))
         :key (str "msg" index)}
   [:div (:text message)]])

(defn- send-message! []
  (swap! chat-data post-my-message)
  (let [messages-div (.getElementById js/document "mzc-messages")
        messages-height (.-offsetHeight messages-div)]
    ;; wait for the component to render again then scroll down
    (.setTimeout js/window #(set! (.-scrollTop messages-div) messages-height) 100)))

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
        :on-key-up #(when (= "Enter" (.-key %)) (send-message!))
        :on-change #(swap! chat-data assoc :current-message (.. % -target -value))}]
      [:button.btn.btn-primary
       {:type "button"
        :on-click send-message!}
       "Send!"]]]]])
