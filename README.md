## What is it?

A tiny interoperability library for passing JRuby/Scala collections back
and forth.

https://rubygems.org/gems/jruby-scala-collections

## Installation

$ gem install jruby-scala-collections

## How do you use it?

Each ```Object``` has two methods: ```#to_scala``` and ```#from_scala```.
These can be used to wrap JRuby/Scala collections.

Example:

    gem 'jruby-scala-collections'
    require 'jruby/scala_support'
    
    r_arr = [1,2,3,4]
    scala_arr = scala_object.do_stuff(r_arr.to_scala)
    scala_arr.from_scala

It also adds ```#Some``` and ```None``` so you could pass values to Scala
methods:

    scala_object.set(Some(3))
    scala_object.get(None)

## Disclaimer

This library was written by Artūras 'arturaz' Šlajus for personal
usage. ```#to_scala``` should work pretty well, however Ruby wrappers
may be missing methods. Patches are welcome.
