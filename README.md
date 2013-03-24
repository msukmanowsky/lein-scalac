# lein-scalac

Compile Scala source from Leiningen.

## Usage

Add `[io.tomw/lein-scalac "0.1.2"]` to `:plugins` project.clj.

Set `:scala-source-path` in project.clj, usually to "src/scala", and
place your `.scala` source files in there.

Run `lein scalac` to compile them to `.class` files.

If you want `scalac` to run automatically, add `:prep-tasks ["scalac"]`
to `project.clj`

You must include the version of the scala compiler you want to use in your project.clj by specifying a `:scala-version` key as a string.

## Thanks

Many thanks to Phil and Stu Halloway for doing all the hard work.

## License

Copyright © 2013 Tom Wanielista, Phil Hagelberg and Scott Clasen

Distributed under the Eclipse Public License, the same as Clojure.
