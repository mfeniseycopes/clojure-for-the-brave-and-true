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

;; exercises
;; 1
(defn use-fns
  []
  (do
    (str "when " 2 " become " 1)
    (vector '(1 2 "eight"))
    (list [1 2 3])
    (hash-map :key1 1 :key2 2 :key1 3)
    (hash-set 1 2 3 4 3 :val :potato)))

;; 2
(defn plus-100 [num]
  (+ 100 num))

;; 3
(defn dec-maker
  [dec-amnt]
  #(- % dec-amnt))

;; 4
(defn mapset
  [func col]
  (reduce  
    #(conj %1 (func %2))
    #{} col))

;; 5
(defn radial-complementary-parts
  [part]
  (map 
    #(hash-map 
      :name (clojure.string/replace (:name part) #"^1-" (str % "-"))
      :size (:size part) ) 
    (range 2 6)))

(defn radial-symmetrize-body-part
  [final-parts part]
  (into final-parts (set (cons part (radial-complementary-parts part)))))

(defn radial-symmetrize-body-parts
  [asym-body-parts]
  (reduce radial-symmetrize-body-part [] asym-body-parts))

;; 6
(defn n-complementary-parts
  [others matcher] 
  (fn [part]
    (map
      #(hash-map 
         :name (clojure.string/replace (:name part) matcher %)
         :size (:size part) ) 
      others)))

(defn n-symmetrize-body-part
  [others matcher] 
  (fn [final-parts part]
    (into final-parts 
          (set (cons part ((n-complementary-parts others matcher) part))))))

(defn n-symmetrize-body-parts
  [others matcher] 
  (fn [asym-body-parts]
    (reduce (n-symmetrize-body-part others matcher) [] asym-body-parts)))
