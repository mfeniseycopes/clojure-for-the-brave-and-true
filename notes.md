# clojure

## getting-started
- clojure is both a language and a compiler
- compiler runs on the JVM

## Leiningen
- `project.clj` is config file for project
  - lists dependencies, preprocesses
- code goes in `src/<project_name>`
- `tests/` holds tests
- `resources/` stores assets
- `src/<project_name>/core.clj#-main` is the *entry_point* of the program:w
- `lein run` runs the program
- `lein uberjar` creates java executed `.jar` file
  - in `target/uberjar/<project_name>-xxx-SNAPSHOT-standalone.jar`
  - execute with `java -jar <filename>` 

## REPL
- `lein repl` to start
- starting repl in project starts in `<project_name>.core` namespace

## Clojure syntax
- **form** valid code (aka *expression*)
- each form is evaluated to produce a value
- two types of structures:
  - literal data representations (numbers, strings, maps, vectors)
  - operations
- most code will be operations
- `(operator operand1 operand2 ... operandn)`
- uses whitespace to separate operands

## Operators
- `(+)`
- `( if boolean-form
     then-form
      optional-else-form)`
  - each branch can have only one form/expression
  - but the `(do )` operation can extend this
- `(do )` allows wrapping of multiple forms in parenthesis and run each
  - the last form is returned
- `(when )` but without the `else` branch
  - returns nil when false

- only `nil`/`false` are falsey values, everything else is truthy
- `(= )` equality operator
- `(or )` returns first truthy or last value (can take more than 2 args!)
- `(and )` returns first falsey or last truthy
- `(def )` binds a name to value
  - these bindings can be reassigned, but not mutated
  - recommend not doing this, but using functional programming instead

## Data structures
- numerics (integer, float, ratio)
- strings
  - only `" double quotes "`
  - no interpolation only concat via `str`
- maps
  - like a hash, object or dictionary
  - `{:a 1 :b 2}`
  - `(hash-map :a 1 :b 2)`
  - to retrieve a value `(get {:a 1 :b 2} :a)`
  - to retrieve nested value `(get-in {:a {:b 0}} [:a :b])`
  - or treat map as function with key as arg `({:name "heyo"} :name)`
  - or use key as function with map as arg `(:name {:name "heyo"})`
  - can provide default value which will be returned if key not found
    - `(get {:a 1} :a 5)`
- vectors
  - zero indexed collection
  - can be accessed with `get` or function style ([0 1 2] 2)
  - can create with `vector` function `(vector "hi" "hello" "howdy")`
  - elements can be added to end with `conj`
    - `(conj [1 2 3] 4 5)` 
- lists
  - like a vector, but elements cannot be retrieved with `get`
  - `'(1 2 3 4)`
  - elements can be retrieved with `nth` `(nth '("0" "1") 0)`
  - lists are accessed like linked lists traversing each element
  - `list` can be used to create lists
  - elements can be added to the beginning of a list
    - `(conj '(1 2 3) 4) ; (4 1 2 3)`
- sets
  - `#{"kurt" "douglas" 1 :five}`
  - `(hash-set 1 1 2 3) ; #{1 2 3}`
  - `conj` adds to the set
  - can create set from vector or list with `set`
  - `contains?` returns true/false if set contains element
    - `(contains? #{1 2 :a} :a) ; true`
  - can also use `get`

## Functions
- arguments are evaluated recursively 
- `map` allows generalizing a function over a collection
- function definition begins with `defn` a docstring, parameters and the
  function body
```clj
(defn hello
  "Says hello"
  [name]
  (str "hello " name "!"))
```
- docstrings can be viewed with `(doc <function-name>)`
- **arity** number of parameters
- support arity overloading (different function body for different args)
```clj
(defn multi-arity
  ([first second]
    (+ first second))
  ([first]
    [first])) 
```
- variable-arity can be captured with the rest parameter `&` before the arg name
- arguments will be treated as a list
- rest args must come last
- destructuring can pull meaningful argument names out of a vector or map arg 
- anonymous functions can be defined with `fn` just like `defn`
  - or like this `#(* % 3)`
  - where `%` is where the argument will be injected
  - or use `%1`, `%2` ... `%n` for named arguments
  - the rest parameter `&` can capture all args in a vector
- can return functions that close over all variables in the first

- `let` creates new, scoped variable(s) and returns
  - `(let arr [1 2 3] (first arr))` creates array of 1, 2, 3 and returns the
    first element
- `into` adds elements to a vector or list or set 
  - `(into [] (set [:a :a]))`
