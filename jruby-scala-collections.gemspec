# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name        = "jruby-scala-collections"
  s.version     = "0.1.4"
  s.authors     = "Artūras Šlajus"
  s.email       = "arturas.slajus@gmail.com"
  s.homepage    = "http://github.com/arturaz/jruby-scala-collections"
  s.summary     = "Compiled against JRuby 1.6.7/Scala 2.10.2"
  s.description = "Interoperability layer for passing JRuby & Scala collections back and forth. See README.md for more info."

  s.files        = Dir.glob("lib/**/*") + %w(README.md ext/dist/collections.jar)
  s.require_path = 'lib'
  s.add_development_dependency('rspec')
  s.add_development_dependency('mspec')
  s.add_development_dependency('guard')
  s.add_development_dependency('guard-rspec-jruby')
  s.add_development_dependency('guard-bundler')
  s.add_development_dependency('guard-shell')
end
