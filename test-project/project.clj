(defproject test-project "0.1.1"
  :description "test project with some scala"
  :dependencies [[org.scala-lang/scala-library "2.10.1"]]
  :scala-source-path "scala"
  :scala-version "2.10.1"
;  :plugins [[lein-scalac "0.1.0"]]
  :prep-tasks ["scalac"]
  :main test.core)
