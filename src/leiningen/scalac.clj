(ns leiningen.scalac
  (:require [leiningen.classpath :as classpath]
            [leiningen.core.eval :as eval]))

(defn- task-props [project]
  (merge {:srcdir (:scala-source-path project)
          :destdir (:compile-path project)}
         (:scalac-options project)))

(defn- scalac-compile-form [project classpath]
  `(do
     (import scala.tools.ant.Scalac)
     (import org.apache.tools.ant.types.Path)

     (.addTaskDefinition lancet/ant-project "scalac" scala.tools.ant.Scalac)

     (lancet/define-ant-task ~'ant-scalac ~'scalac)

     (let [task# (doto (lancet/instantiate-task lancet/ant-project "scalac" ~(task-props project))
                   (.setClasspath (Path. lancet/ant-project ~classpath)))]
       (lancet/mkdir {:dir ~(:compile-path project)})
       (.execute task#))))

(defn scalac
  "Compile Scala source in :scala-source-path to :compile-path.

Set :scalac-options in project.clj to pass options to the Scala compiler.
See http://www.scala-lang.org/node/98 for details."
  [project]
  (let [scala-version (:scala-version project)
        depped-proj (update-in project [:dependencies] concat
                               [['lancet "1.0.1"]
                                ['org.scala-lang/scala-compiler scala-version]
                                ['org.clojure/clojure "1.5.0"]])
        ;;depped-proj project
        classpath (classpath/get-classpath-string depped-proj)]

    (eval/eval-in-project

     ;; pass in the project.
     depped-proj

     ;; the actual compilation task.
     (scalac-compile-form depped-proj classpath)

     ;; imports to avoid Garibaldi's Issue.
     '(require '[lancet.core :as lancet]))))
