## What is it?

A tiny interoperability library for passing JRuby/Scala collections back
and forth.

https://rubygems.org/gems/jruby-scala-collections

## Installation

$ gem install jruby-scala-collections

With bundler:

```gem "jruby-scala-collections", ">=0.1.1", require: "jruby/scala_support"```

Beware that you have to have ```scala-library.jar``` loaded before you 
load ```jruby/scala_support```. In Rails this generally means it loading in
```config/boot.rb```.

## How do you use it?

Each ```Object``` has two methods: ```#to_scala``` and ```#from_scala```.
These can be used to wrap JRuby/Scala collections.

Example:

    gem 'jruby-scala-collections'
    require 'jruby/scala_support'
    
    r_arr = [1,2,3,4]
    scala_arr = scala_object.do_stuff(r_arr.to_scala)
    scala_arr.from_scala

* ```Array#to_scala``` becomes ```scala.collection.mutable.Buffer```
* ```Hash#to_scala``` becomes ```scala.collection.mutable.Map```
* ```Set#to_scala``` becomes ```scala.collection.mutable.Set```

Take node that even collections inside collections are wrapped:

    > a = [1,[2,3],{4=>5}].to_scala
     => #<Java::JrubyCollection::ListWrapper:0x768bdb> 
    > a.apply(1)
     => #<Java::JrubyCollection::ListWrapper:0x884ab9> 
    > a.apply(2)
     => #<Java::JrubyCollection::MapWrapper:0x1bb605> 

From Scala side Ruby primitives are converted using default JRuby conversions
that are listed in https://github.com/jruby/jruby/wiki/CallingJavaFromJRuby section
"Conversion of Types".

So if you expect Array of Fixnums coming to your scala method, it should accept:

    // Either
    def scalaMethod(args: collection.mutable.Buffer[Long])
    // Or
    def scalaMethod(args: collection.Seq[Long])

It also adds ```#Some``` and ```None``` so you could pass values to Scala
methods:

    scala_object.set(Some(3))
    scala_object.get(None)

## Disclaimer

This library was written by Artūras 'arturaz' Šlajus for personal
usage. ```#to_scala``` should work pretty well, however Ruby wrappers 
in ```#from_scala``` may be missing methods. Patches are welcome.
