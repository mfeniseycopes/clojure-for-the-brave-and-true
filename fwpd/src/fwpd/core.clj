(ns fwpd.core)

;; we can refer to filename more easily
(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions 
  {:name identity :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 5000}"
  [rows]
  (map 
    (fn [row]
      (reduce 
        (fn [row-map [vamp-key value]]
          (assoc row-map vamp-key (convert vamp-key value))) 
        {} (map vector vamp-keys row)))
    rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn -main
  [& args]
  (glitter-filter 2 (mapify (parse (slurp filename)))))

;; exercises
;; 1
(defn glitter-filter-names
  [minimum-glitter records]
  (map :name (glitter-filter minimum-glitter records)))

;; 2
(defn append
  [suspect-list new-suspect]
  (concat suspect-list (list new-suspect)))

;; 3
(defn validate
  [keywords record]
  (every? #(contains? record %) keywords))

(defn safe-append
  [keywords]
  (fn
    [suspect-list new-suspect]
    (if (validate keywords new-suspect)
      (append suspect-list new-suspect)
      suspect-list)))

;; 4
(defn suspects-to-csv
  [suspects]
  (clojure.string/join "\n"
    (map #(clojure.string/join "," (vals %)) suspects)))
