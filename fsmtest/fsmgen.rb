$:.unshift File.join(File.dirname(__FILE__),"..","lib")

require 'test/unit'
require 'rgen/ecore/ecore'
require 'rgen/template_language'
require 'rgen/metamodel_builder'
require 'rgen/model_builder'
require 'rgen/environment'
require 'rtext/instantiator'
require 'rtext/language'

class TemplateContainerTest < Test::Unit::TestCase
  
  TEMPLATES_DIR = File.dirname(__FILE__)+"/templates"
  OUTPUT_DIR = File.dirname(__FILE__)+"/"
  
  module StatemachineMM
    extend RGen::MetamodelBuilder::ModuleExtension
    class Statemachine < RGen::MetamodelBuilder::MMBase
      has_attr 'name'
    end
    class State < RGen::MetamodelBuilder::MMBase
      has_attr 'name'
    end
    class Transition < RGen::MetamodelBuilder::MMBase
      has_attr 'condition'
      has_one 'targetState', State
    end
    Statemachine.contains_many 'states', State, 'statemachine'
    State.contains_one 'transition', Transition, 'state'
  end

  def instantiate(text, mm, options={})
    env = RGen::Environment.new
    lang = RText::Language.new(mm.ecore, 
      :root_classes => mm.ecore.eAllClasses.select{|c| c.name == "Statemachine" },
      :unlabled_arguments => lambda {|c| ["name"] },
      :unquoted_arguments => lambda {|c| ["name"] },
      :line_number_attribute => "line_number",
      :fragment_ref_attribute => "fragment_ref"
    )
    inst = RText::Instantiator.new(lang)
    problems = []
    inst.instantiate(text, options.merge({:env => env, :problems => problems, :root_elements => options[:root_elements]}))
    return env, problems
  end

  def assert_no_problems(problems)
    assert problems.empty?, problems.collect{|p| "#{p.message}, line: #{p.line}"}.join("\n")
  end

  def test_with_model
    tc = RGen::TemplateLanguage::DirectoryTemplateContainer.new(StatemachineMM, OUTPUT_DIR)
    tc.load(TEMPLATES_DIR)
    File.delete(OUTPUT_DIR+"/fsm.c") if File.exists? OUTPUT_DIR+"/fsm.c"
    root_elements = []
    env, problems = instantiate(%Q(
      Statemachine Engine {
        states: [
          State On {
            Transition targetState: Off, condition: "onoff_pressed == 1"
          }
          State Off {
            Transition targetState: On, condition: "onoff_pressed == 1"
          }
        ]
      }
      ), StatemachineMM, :root_elements => root_elements)

    assert_no_problems(problems)
    tc.expand('root::Root', :for => root_elements)
    result = expected = ""
    File.open(OUTPUT_DIR+"/fsm.c") {|f| result = f.read}
    File.open(OUTPUT_DIR+"/fsm.c.expected") {|f| expected = f.read}
    assert_equal expected, result
  end
  
end
