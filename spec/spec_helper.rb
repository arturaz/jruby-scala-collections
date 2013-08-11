require 'java'


classpath_file = File.expand_path("../ext/target/.classpath", File.dirname(__FILE__))
scala_classpath = File.read(classpath_file).split(":")
jars, paths = scala_classpath.partition {|sc| sc.end_with?(".jar")}

paths.each {|p| $CLASSPATH << p }
jars.each {|jar| require jar }

Dir.glob(File.dirname(__FILE__)+'/../lib/**/*.rb') { |file| require file }

def jruby
  Java::Jruby
end

def scala
  Java::Scala
end
