# sbt-sigs

An SBT plugin to show class signatures

This plugins performs a similar function to `javap` and `scalap` but
without having to put the tools in path.

Additionally, the full classpath of the project being built will automatically
be used for searching and introspection.

### Usage

Currently in a SNAPSHOT, you will need to clone and publish locally prior to use
```
git clone https://github.com/pfn/sbt-sigs.git
cd sbt-sigs
sbt publish-local
```

throw the following line into `~/.sbt/0.13/plugins/sigs.sbt`

```scala
addSbtPlugin("com.hanhuy.sbt" % "sbt-sigs" % "0.1-SNAPSHOT")
```

The commands `javap` and `scalap` will now be available in the SBT REPL

### Examples

```
$ sbt "scalap scala.Option"
$ sbt "javap java.lang.Integer"
```

