(ns clojure-noob.core
  (:gen-class))

(def asym-hobbit-body-parts [
   {:name "head" :size 3}
   {:name "left-eye" :size 1}
   {:name "left-ear" :size 1}
   {:name "mouth" :size 1}
   {:name "nose" :size 1}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part) })

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(defn symmetrize-body-part 
  "Creates right of given body part and inserts into vector"
  [final-body-parts part]
  (into final-body-parts (set [part (matching-part part)])))

(defn my-reduce
  "Accumulates value for each element in list"
  ([fn initial col]
  (loop [result initial remaining col]
    (if (empty? remaining)
      result 
      (recur (fn result (first remaining)) (rest remaining)))))
  ([fn [head & tail]]
  (my-reduce fn head tail)))

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce symmetrize-body-part [] asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
   (loop [[part & remaining] sym-parts
          acc-size (:size part)]
     (if (> acc-size target)
       part
       (recur remaining (+ acc-size (:size (first remaining))))))))

(defn -main
  "Do violence unto a poor hobbit"
  [& args]
  (println (hit asym-hobbit-body-parts)))
