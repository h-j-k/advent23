(ns advent23.day20
  (:require [clojure.string :as str]))

(defn handle-broadcaster [{:keys [name targets] :as module} pulse] [module (mapv (fn [t] [pulse name t]) targets)])

(defn handle-% [{:keys [name targets state] :as module} pulse]
  (if (= pulse :high)
    [module []]
    (let [is-off? (= state :off)]
      [(assoc module :state (if is-off? :on :off)) (mapv (fn [t] [(if is-off? :high :low) name t]) targets)])))

(defn handle-& [{:keys [name targets inputs] :as module} pulse source]
  (let [updated (assoc inputs source pulse) is-high-pulse? (every? #(= :high %) (vals updated))]
    [(assoc module :inputs updated) (mapv (fn [t] [(if is-high-pulse? :low :high) name t]) targets)]))

(defn handle [module pulse source]
  (cond
    (= (:name module) "broadcaster") (handle-broadcaster module pulse)
    (= (:type module) "%") (handle-% module pulse)
    (= (:type module) "&") (handle-& module pulse source)
    :else [module []]))

(defn parse-row [row]
  (let [[_ type name targets] (re-find #"^([%&])*([^ ]+) -> (.+)$" row)]
    {:type type :name name :targets (str/split targets #", ")}))

(defn find-sources [modules {:keys [name]}]
  (reduce (fn [acc m] (if (contains? (into #{} (:targets m)) name) (assoc acc (:name m) :low) acc)) {} (vals modules)))

(defn enrich [modules module]
  (cond
    (= (:type module) "%") [(:name module) (assoc module :state :off)]
    (= (:type module) "&") [(:name module) (assoc module :inputs (find-sources modules module))]
    :else [(:name module) module]))

(defn parse [input]
  (let [modules-list (map parse-row input) modules (zipmap (map #(:name %) modules-list) modules-list)]
    (into {} (map #(enrich modules %) modules-list))))

(defn process [modules pulses to-process]
  (if (empty? to-process)
    (do (prn pulses) [modules pulses])
    (do
      (let [[updated-modules next-targets]
            (reduce (fn [[acc next] [pulse source target]]
                      (let [[updated targets] (handle (get acc target {:name target}) pulse source)]
                        [(assoc acc (:name updated) updated) (concat next targets)]))
                    [modules []]
                    to-process)]
        (recur updated-modules (merge-with + pulses (frequencies (map first to-process))) next-targets)))))

(defn part1 [input]
  (loop [i 0 [modules pulses] [(parse input) {}]]
    (if (= i 1000)
      (str (apply * (.values pulses)))
      (recur (inc i) (process modules pulses [[:low "button" "broadcaster"]])))))
