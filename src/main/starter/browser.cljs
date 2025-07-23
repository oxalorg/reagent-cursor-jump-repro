(ns starter.browser
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            ["react-dom/client" :as rdom-client]
            [shadow.cljs.modern :refer [defclass]]))

(defonce shadow-value (r/atom ""))
(defonce normal-value (r/atom ""))
(defonce rendered? (r/atom false))

(defn shadow-input-component []
  [:div {:style {:padding "20px" :background "lightgray"}}
   [:h3 "Shadow DOM Component"]
   [:p "Write something in the box below, then move the cursor (to anything but last position) and write something again. It will move the cursor."]
   [:input {:type "text"
            :placeholder "Shadow DOM input"
            :value @shadow-value
            :on-change #(reset! shadow-value (.. % -target -value))
            :style {:padding "8px"
                    :border "2px solid #ccc"
                    :border-radius "4px"
                    :font-size "16px"
                    :width "200px"}}]
   [:p "Shadow input value: " @shadow-value]])

(defclass ShadowInput (extends js/HTMLElement)
  (constructor
   [this]
   (super)
   (js/console.log "Rendering shadow input")
   (let [shadow (.attachShadow this #js {:mode "open"})
         container (.createElement js/document "div")
         root (.createRoot rdom-client container)]
     (.appendChild shadow container)
     (.render root (r/as-element [shadow-input-component])))))

(defn normal-input []
  [:div
   [:h3 "Normal Component"]
   [:input {:type "text"
            :placeholder "Normal input"
            :value @normal-value
            :on-change #(reset! normal-value (.. % -target -value))
            :style {:padding "8px"
                    :border "2px solid #ccc"
                    :border-radius "4px"
                    :font-size "16px"
                    :width "200px"}}]
   [:p "Normal input value: " @normal-value]])

(defn normal-app []
  [:div {:style {:padding "20px"}}
   [normal-input]])

(defn setup-shadow-component []
  (when-not (.get js/customElements "shadow-input")
    (.define js/customElements "shadow-input" ShadowInput))
  (let [shadow-app-div (.getElementById js/document "shadow-app")
        shadow-element (.createElement js/document "shadow-input")]
    (set! (.-innerHTML shadow-app-div) "")
    (.appendChild shadow-app-div shadow-element)))

(defonce app-root (atom nil))

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (js/console.log "start")
  (when-not @app-root
    (reset! app-root (.createRoot rdom-client (.getElementById js/document "app"))))
  (.render @app-root (r/as-element [normal-app]))
  (setup-shadow-component))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
