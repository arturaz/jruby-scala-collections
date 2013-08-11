class Object
  # The following helpers provide a level of indirection for running the specs
  # against a Hash implementation that has a different name than Hash.

  # Returns the Hash class.
  def hash_class
    scala.collection.mutable.HashMap
  end

  # Returns a new instance of hash_class.
  def new_hash(hsh={})
    sh = scala.collection.mutable.HashMap.new
    unless hsh.is_a? Hash
      sh = sh.with_default_value(hsh)
      h = sh.from_scala
    else
      h = sh.from_scala
      hsh.each {|k,v| h[k] = v}
    end
    h
  end
  #def new_hash(*args, &block)
  #  if block
  #    hash_class.new(&block)
  #  elsif args.size == 1
  #    value = args.first
  #    if value.is_a?(Hash) or value.is_a?(hash_class)
  #      hash_class[value]
  #    else
  #      hash_class.new value
  #    end
  #  else
  #    hash_class[*args]
  #  end
  #end
end
