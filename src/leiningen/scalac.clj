(ns leiningen.scalac
  (:require [leiningen.core.eval :as eval])
  (:import (org.apache.tools.ant.types Path)))

(defn task-props [project]
  (merge {:srcdir (:scala-source-path project)
          :destdir (:compile-path project)}
         (:scalac-options project)))

(defn- scalac-compile-form [project]
  `(do
     (import scala.tools.ant.Scalac)
     (println "ok")

     (.addTaskDefinition lancet/ant-project "scalac" scala.tools.ant.Scalac)
     (println "ok1")

     ;(lancet/define-ant-task ant-scalac scalac)
     ;(println "ok2")

     (let [proj-classpath# (classpath/get-classpath-string project)
           task# (doto (lancet/instantiate-task lancet/ant-project "scalac"
                                               (task-props project))
                  (.setClasspath (Path. lancet/ant-project proj-classpath#)))]
       (lancet/mkdir {:dir (:compile-path project)})
       (.execute task#))))

(defn scalac
  "Compile Scala source in :scala-source-path to :compile-path.

Set :scalac-options in project.clj to pass options to the Scala compiler.
See http://www.scala-lang.org/node/98 for details."
  [project]
  (println project)
  (let [depped-proj (update-in project [:dependencies] conj ['lancet "1.0.1"])]
    (eval/eval-in-project

     ;; pass in the project.
     depped-proj

     ;; the actual compilation task.
     (scalac-compile-form depped-proj)

     ;; imports to avoid Garibaldi's Issue.
     '(require '[lancet.core :as lancet]))))
