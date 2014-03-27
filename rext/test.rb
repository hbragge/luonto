require 'mytest'
include MyTest

class A
    attr_accessor :name
    attr_accessor :value
    attr_accessor :fun
    @printvalue
    def initialize(name, value)
      @name = name
      @value = value
      @printvalue = lambda { |n| n }
    end
end

class B < A
    def print
      puts "name = #{name}"
      printvalue = fun.call(value)
      puts "value = #{printvalue}"
    end
end

class C < A
    def print
      puts "nimi = #{name}"
      printvalue = fun.call(value) + test1
      puts "arvo = #{printvalue}"
    end
end

b = B.new('test', 5)
b.name = "eika"
b.fun = lambda { |n| n * 2 }
b.print
c = C.new('test', 5)
c.name = "eiku"
c.fun = lambda { |n| n + 15 }
c.print
