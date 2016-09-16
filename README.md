# sbt-sigs

An SBT plugin to show class signatures

This tool performs a similar function to `javap` and `scalap` except
without having to put the tools in path, or even have them installed.
Additionally, the full classpath of the project being built will be
used for searching and introspection.

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

The commands `class` and `object` will now be available in the SBT REPL

### Examples

A local scala object in the project

```
$ sbt "object com.hanhuy.test.ThisIsATest"
[info] Loading global plugins from /home/pfnguyen/.sbt/0.13/plugins
[info] Loading project definition from /home/pfnguyen/local/sigtest/project
[info] Updating sigtest-build
[info] Resolved sigtest-build dependencies
[info] Fetching artifacts of sigtest-build
[info] Fetched artifacts of sigtest-build
[info] Set current project to sigtest (in build file:/home/pfnguyen/local/sigtest/)
[info] Updating sigtest
[info] Resolved sigtest dependencies
[info] Fetching artifacts of sigtest
[info] Fetched artifacts of sigtest
[info] object ThisIsATest {
[info]   def bar: <?>;
[info]   def foo: <?>;
[info]   def <init>(): com.hanhuy.test.ThisIsATest.type;
[info]   final def $asInstanceOf[T0](): T0;
[info]   final def $isInstanceOf[T0](): Boolean;
[info]   final def synchronized[T0](x$1: T0): T0;
[info]   final def ##(): Int;
[info]   final def !=(x$1: AnyRef): Boolean;
[info]   final def ==(x$1: AnyRef): Boolean;
[info]   final def ne(x$1: AnyRef): Boolean;
[info]   final def eq(x$1: AnyRef): Boolean;
[info]   final def notifyAll(): Unit;
[info]   final def notify(): Unit;
[info]   protected[package lang] def clone(): java.lang.Object;
[info]   final def getClass(): java.lang.Class[_];
[info]   def hashCode(): Int;
[info]   def toString(): java.lang.String;
[info]   def equals(x$1: Any): Boolean;
[info]   final def wait(): Unit;
[info]   final def wait(x$1: Long): Unit;
[info]   final def wait(x$1: Long,x$2: Int): Unit;
[info]   protected[package lang] def finalize(): Unit;
[info]   final def asInstanceOf[T0]: T0;
[info]   final def isInstanceOf[T0]: Boolean;
[info]   final def !=(x$1: Any): Boolean;
[info]   final def ==(x$1: Any): Boolean
[info] }
[success] Total time: 1 s, completed Sep 16, 2016 8:41:54 AM
```

Scala class
```
$ sbt "class scala.Option"
[info] Loading global plugins from /home/pfnguyen/.sbt/0.13/plugins
[info] Loading project definition from /home/pfnguyen/local/sigtest/project
[info] Updating sigtest-build
[info] Resolved sigtest-build dependencies
[info] Fetching artifacts of sigtest-build
[info] Fetched artifacts of sigtest-build
[info] Set current project to sigtest (in build file:/home/pfnguyen/local/sigtest/)
[info] Updating sigtest
[info] Resolved sigtest dependencies
[info] Fetching artifacts of sigtest
[info] Fetched artifacts of sigtest
[info] class Option {
[info]   final def toLeft[X <: <?>](right: <?>): scala.Product with scala.Serializable with scala.util.Either[A,X];
[info]   final def toRight[X <: <?>](left: <?>): scala.Product with scala.Serializable with scala.util.Either[X,A];
[info]   def toList: <?>;
[info]   def iterator: <?>;
[info]   final def orElse[B <: <?>](alternative: <?>): scala.Option[B];
[info]   final def collect[B <: <?>](pf: <?>): scala.Option[B];
[info]   final def foreach[U <: <?>](f: <?>): scala.Unit;
[info]   final def forall(p: <?>): scala.Boolean;
[info]   final def exists(p: <?>): scala.Boolean;
[info]   final def contains: <?>;
[info]   class WithFilter extends ;
[info]   final def withFilter(p: <?>): Option.this.WithFilter;
[info]   final def nonEmpty: <?>;
[info]   final def filterNot(p: <?>): scala.Option[A];
[info]   final def filter(p: <?>): scala.Option[A];
[info]   def flatten: <?>;
[info]   final def flatMap[B <: <?>](f: <?>): scala.Option[B];
[info]   final def fold[B <: <?>](ifEmpty: <?>)(f: <?>): B;
[info]   final def map[B <: <?>](f: <?>): scala.Option[B];
[info]   final def orNull[A1 <: <?>](implicit ev: <?>): A1;
[info]   final def getOrElse[B <: <?>](default: <?>): B;
[info]   def isDefined: <?>;
[info]   def <init>(): scala.Option[A];
[info]   def productPrefix: <?>;
[info]   def productIterator: <?>;
[info]   def $init$: <?>;
[info]   final def $asInstanceOf[T0](): T0;
[info]   final def $isInstanceOf[T0](): Boolean;
[info]   final def synchronized[T0](x$1: T0): T0;
[info]   final def ##(): Int;
[info]   final def !=(x$1: AnyRef): Boolean;
[info]   final def ==(x$1: AnyRef): Boolean;
[info]   final def ne(x$1: AnyRef): Boolean;
[info]   final def eq(x$1: AnyRef): Boolean;
[info]   final def notifyAll(): Unit;
[info]   final def notify(): Unit;
[info]   protected[package lang] def clone(): java.lang.Object;
[info]   final def getClass(): java.lang.Class[_];
[info]   def hashCode(): Int;
[info]   def toString(): java.lang.String;
[info]   def equals(x$1: Any): Boolean;
[info]   final def wait(): Unit;
[info]   final def wait(x$1: Long): Unit;
[info]   final def wait(x$1: Long,x$2: Int): Unit;
[info]   protected[package lang] def finalize(): Unit;
[info]   final def asInstanceOf[T0]: T0;
[info]   final def isInstanceOf[T0]: Boolean;
[info]   final def !=(x$1: Any): Boolean;
[info]   final def ==(x$1: Any): Boolean;
[info]   def get: <?>;
[info]   def isEmpty: <?>;
[info]   def productArity: <?>;
[info]   def productElement: <?>;
[info]   def canEqual: <?>
[info] }
[success] Total time: 1 s, completed Sep 16, 2016 8:39:50 AM
```

Java class

```
$ sbt "class java.util.ArrayList"
[info] Loading global plugins from /home/pfnguyen/.sbt/0.13/plugins
[info] Updating {file:/home/pfnguyen/.sbt/0.13/plugins/}global-plugins...
[info] Resolving org.fusesource.jansi#jansi;1.4 ...
[info] Done updating.
[info] Loading project definition from /home/pfnguyen/local/sigtest/project
[info] Updating sigtest-build
[info] Resolved sigtest-build dependencies
[info] Fetching artifacts of sigtest-build
[info] Fetched artifacts of sigtest-build
[info] Set current project to sigtest (in build file:/home/pfnguyen/local/sigtest/)
[info] Updating sigtest
[info] Resolved sigtest dependencies
[info] Fetching artifacts of sigtest
[info] Fetched artifacts of sigtest
[info] public class java.util.ArrayList extends java.util.AbstractList
[info] implements java.util.List, java.util.RandomAccess, java.lang.Cloneable, java.io.Serializable {
[info]     private static final long serialVersionUID;
[info]     private static final int DEFAULT_CAPACITY;
[info]     private static final java.lang.Object[] EMPTY_ELEMENTDATA;
[info]     private static final java.lang.Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
[info]     transient java.lang.Object[] elementData;
[info]     private int size;
[info]     private static final int MAX_ARRAY_SIZE;
[info]     public java.util.ArrayList(java.util.Collection);
[info]     public java.util.ArrayList();
[info]     public java.util.ArrayList(int);
[info]     public boolean add(E);
[info]     public void add(int,E);
[info]     public boolean remove(java.lang.Object);
[info]     public E remove(int);
[info]     public E get(int);
[info]     public java.lang.Object clone();
[info]     public int indexOf(java.lang.Object);
[info]     public void clear();
[info]     public boolean isEmpty();
[info]     public int lastIndexOf(java.lang.Object);
[info]     public boolean contains(java.lang.Object);
[info]     public void replaceAll(java.util.function.UnaryOperator<E>);
[info]     public int size();
[info]     public java.util.List<E> subList(int,int);
[info]     public T[] toArray(T[]);
[info]     public java.lang.Object[] toArray();
[info]     public java.util.Iterator<E> iterator();
[info]     public java.util.Spliterator<E> spliterator();
[info]     public boolean addAll(int,java.util.Collection<? extends E>);
[info]     public boolean addAll(java.util.Collection<? extends E>);
[info]     static int access$100(java.util.ArrayList);
[info]     private void readObject(java.io.ObjectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException;
[info]     private void writeObject(java.io.ObjectOutputStream) throws java.io.IOException;
[info]     public void forEach(java.util.function.Consumer<? super E>);
[info]     public E set(int,E);
[info]     public void ensureCapacity(int);
[info]     public void trimToSize();
[info]     private void ensureCapacityInternal(int);
[info]     E elementData(int);
[info]     private void grow(int);
[info]     private static int hugeCapacity(int);
[info]     public boolean removeAll(java.util.Collection<?>);
[info]     public boolean retainAll(java.util.Collection<?>);
[info]     protected void removeRange(int,int);
[info]     public java.util.ListIterator<E> listIterator();
[info]     public java.util.ListIterator<E> listIterator(int);
[info]     public boolean removeIf(java.util.function.Predicate<? super E>);
[info]     public void sort(java.util.Comparator<? super E>);
[info]     private void rangeCheckForAdd(int);
[info]     private java.lang.String outOfBoundsMsg(int);
[info]     private void ensureExplicitCapacity(int);
[info]     private void fastRemove(int);
[info]     private void rangeCheck(int);
[info]     private boolean batchRemove(java.util.Collection<?>,boolean);
[info]     static void subListRangeCheck(int,int,int);
[info] }
[success] Total time: 1 s, completed Sep 16, 2016 8:36:17 AM
```
