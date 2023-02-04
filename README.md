# SysMLinJava - high precision MBSE
## Concept
The SysMLinJava API is A Java-based model development kit (MDK) for high-precision modeling as executable SysML models
Modeling in SysML has traditionally meant using a commercially available tool to draw the structural and behavioral elements of the model as SysML diagrams. This dependence on drawings has more often than not resulted in costly effort to learn and manipulate the drawing tool and imposed considerable frustration in modeling complex behaviors. In addition, the ability of the drawings-based tool to execute/test the model has been frustratingly limited and/or esoteric to the vendor product.

SysMLinJava was developed to make extensive, precise, and executable models relatively easy to develop, analyze, and test. Whereas SysML is based-on the object-oriented paradigm, it's a natural progession to use an object-oriented programming language to develop and execute SysML models. Rather than fumble with drawing tools to specify model objects, the systems engineer can quickly generate model objects as Java classes using any of the myriad powerful easy-to-use Java software development IDEs available today. And the "ultimate" SysML modeling capability - the executable model - is easily realized through Java's extensive and powerful execution capabilities.

SysMLinJava was designed/developed to comply with the OMG standard for SysML - a drawings-based language. As a Java-based implementation, SysMLinJava cannot technically comply with the SysML standard. Therefore, SysMLinJava does not claim to be in technical compliance with the SysML standard, but rather SysMLinJava is a representation of the SysML meta-model in the Java programming language so that the executable modeling capabilities of a programming language can leveraged for SysML models.

## SysML Elements in Java
The elements of SysML - blocks, state machines, value types, activities, etc. - are all mapped to SysMLinJava classes.  The SysMLinJava classes represent the properties of the SysML elements as Java variables and methods. The block values, for instance, are represented as Java variables that are specializations of the SysMLValueType.  Block operations and receptions are represented by Java methods.  The block's state machine is represented by a variable that is an extension of the SysMLStateMachine class.  And so forth.  An actual code example of a SysML block implementation is provided as follows.  The example demonstrates how SysML modeling with SysMLinJava is relatively easy and straightforward.

## Example Block
Of course the primary element-type of the SysML model is the block.  SysMLinJava reflects this primacy in its implementation as well.  Based on the SysMLBlock class, the Block in SysMLinJava is defined by its many properties - values, flows, state machine, ports, constraints, constraint blocks, comments, requirements, connectors, operations, receptions, activities, and many more. All of these properties map to Java fields/variables and Java methods.  This mapping can be best visualized by an example block shown as follows.

```java
public class TrafficSignalControlSystem extends SysMLBlock
{
	//non-relevant code and comments not shown for brevity
	
	@FullPort
	public TimeSynchReceivePort timeSynchReceiver;
	@FullPort
	public EVDetectionReceivePort evDetectionReceiver;
	@FullPort
	public SignalStatesTransmitPort systemStateTransmitter;
	
	@Value
	public SignalStatesEnum eastBound;
	@Value
	public SignalStatesEnum westBound;
	@Value
	public SignalStatesEnum northBound;
	@Value
	public SignalStatesEnum southBound;
	@Value
	public DurationSeconds EWRedNSGrnDuration;
	@Value
	public DurationSeconds YellowDuration;
	@Value
	public DurationSeconds EWGrnNSRedDuration;
	@Value
	public BBoolean emergencyVehiclePresent;
	@Value
	public DirectionDegrees emergencyVehicleApproach;
	
	@StateMachine
	public TrafficSignalSystemStateMachine stateMachine;

	@Problem
	public SysMLProblem addLeftTurnsIssue;
	
	@Requirement
	public SysMLRequirement rootRequirement;
	
	public TrafficSignalSystem(String name, Long id){...}

	@Reception
	public void onPhaseTime(){...}
	@Reception
	public void onTimeSynch(){...}
	@Reception
	public void onEmergencyVehicleApproach(){...}
	@Reception
	public void onEmergencyVehicleDepart(){...}

	@Operation
	public void setEWGrnNSRed(){...}
	@Operation
	public void setEWYelNSRed(){...}
	@Operation
	public void setEGrnWYelNSRed(){...}
	@Operation
	public void setEWRedNSGreen(){...}
	@Operation
	public void setEWRedNSYel(){...}
	@Operation
	public void setEGrnWRedNSRed(){...}
	@Operation
	public void setERedWGrnNSRed(){...}

	@Override
	protected void createValues(){...}
	@Override
	protected void createFullPorts(){...}
	@Override
	protected void createStateMachine()){...}
	@Override
	protected void createProblems(){...}
	@Override
	protected void createRequirements(){...}
}
```

The example shows a block representation of a simple traffic signal control system.  The system block is an extension of the `SysMLBlock`.  Its properties include:

- **ports** declared as fields annotated as full ports and of types extended from `SysMLFullPort`
- **values** declared as fields annotated as values and of types (all provided as part of the SysMLinJava MDK) extended from `SysMLValueType`
- **state machine** declared as a field in the `SysMLBlock` annotated as a state machine and of type extended from `SysMLStateMachine`
- **problem** declared as field annotated as a problem and of type `SysMLProblem`
- **requirement** declared as field annotated as a requirement and of type `SysMLRequirement`
- **operations** declared as methods annotated as operations and defining the activity performed (not shown)
- **receptions** declared as methods annotated as receptions and defining the activity performed (not shown)

The block also declares a number of overridden "create..." methods. While not properties of the block, per se, the methods perform all the creations/initializations of the block's properties, i.e. initialization of the values, creation of the ports, etc.  These creation/initialization methods are invoked automatically by the `SysMLBlock`'s constructor, so the modeler need only add the property initialization statements to the appropriate "create..." method and the properties will be initialized in the correct sequence as part of block construction.

Although it is not explicitly shown in the example, the system model block is executable. Higher-level model elements, e.g. domain objects, can invoke the system block's "start" operation which executes the block's state machine behavior in a dedicated Java thread.  This state machine then receives and responds to events that might be submitted to it by its ports, by other blocks, and/or by operations/receptions of the block itself.  In any case, this structural and behavioral model of a system can be made as complex and precise as is needed while being fully executable and in accordance with the SysML standard.

In addition to the block, SysMLinJava provides explicit support for other commonly used SysML elements to include:
- state machine
- valueType
- full port, proxy port
- associationBlock, connector
- comment, problem, rationale
- hyperlink, constraintNote, elementGroup
- signal, event
- dependency
- constraintBlock. constraintParam, constraint
- requirement
- testCase, test
- view, viewPoint, stakeholder

SysMLinJava also provides supporting classes for extensive executable models to include:
- Threaded state machine for asychronous behavior across multiple threads
- Activity (as lambda expression)
- Block thread pools for multi-threaded block behavior
- Block container for asychronous behaviors across multiple OS processes
- Asychronous constraint block for constraint parameters across multiple threads.

SysMLinJava also supports virtually all of the features of SysMLv2.  You can see the complete version of the above example model as well as other examples of complex SysMLinJava models in the SysMLinJavaExampleModels repository.  All the examples can be downloaded for review and import into your IDE, and are fully executable.

## How it works
### The SysMLinJava module
SysMLinJava is a java API that can be added as a project in an IDE.  It is a java module that can be used by java modules in other IDE projects to develop SysMLinJava models.  Typically, the modeler will create a project in the IDE with the SysMLinJava module as its sole content.  Another project will be created/used for the SysMLinJava model with a "requires transitive" dependency on the SysMLinJava module.

### SysMLinJava modeling
The modeler will typically develop the system model in an IDE project.  Model elements will be constructed as java classes that inherit/extend one of the SysMLinJava classes, e.g. `SysMLBlock`, `StateMachine`, `SysMLValueType`, `SysMLFullPort`, etc.  The model elements will be aggregated in a "domain" class that contains all elements as SysML "parts" of the domain such as the system of interest as well as all the other systems with which it interfaces.  Alternatively, the domain class could be replaced by a `SysMLTest` and/or `SysMLTestCase` classes that construct and execute the model as a SysML test/test case.  In either case, the SysMLinJava model classes are compiled and linked into an executable process or processes, each potentiallly executing as multiple threads representing asynchronously behaving objects in the system and its domain.

### Parametric Analysis
Extensive capabilities for parametric analysis are also supported by the SysMLinJava API.  The `SysMLConstraintBlock` provides a base class for all constraint block modeling in SysMLinJava.  The constraint block supports "bound" constraint parameters as well as contraint block heirarchies.  In addition, the constraint block can be configured to operate asynchronously enabling extensive parameteric analysis with parameters that update asychrounously from other bound parameters and from the constraint block.

## Documentation
The SysMLinJava code includes full javadoc comments, which you can view in the code.  And a zip file of the full javadocs is provided at the base of the master branch.  Of course, you can execute the javadoc program against the code (e.g. via your IDE) to generate the javadoc as a directory of web pages, if desired.

## Dependencies and License
SysMLinJava uses the Apache license as shown above.  It has no dependencies beyond the modules of the Java SDK itself.  This version of SysMLinJava has been successfully tested and used on OpenJDK 18.

## Skills Needed
As a java-based modeling language, SysMLinJava necessarily requires the modeler also be capable of Java software development.  While most model-base systems engineers have software development skills, many do not.  SysMLinJava is based on the more commonly used syntax of the java language with no need for modelers to use the more advanced and essoteric constructs of Java.  In fact, the most advanced element of Java used for SysMLinJava modeling is the lambda expression used for activity specification.  Of course, for the more highly complex, multi-threaded/multi-process models, the SysMLinJava modeler will need to be familiar with the concurrency  aspects of Java.  SysMLinJava incorporates many of java's concurrency constructs in such a way that their use in modeling is relatively easy and straightforward.

While some may find modeling in the Java language to be "a bridge too far", there is the alternative of obtaining the skills of a java-developer as a "co-modeler".  Oftentimes, systems engineers leverage the skills of java developers to code engineering analyses, reports, calculations, and experiments during traditional model development.  SysMLinJava affords the opportunity to leverage these java developers to assist in actual model development to achieve a more complete and precise executable system model.

Finally, there is the option of the SysML modeler learhing to program in java.  There are a myriad of free java training websites available and popular java IDE's provide extensive assistance and help in developing, building, and executing java programs.  In any case, there are numerous options for the SysML modeler to be able to leverage the power of SysMLinJava for high precision model-based systems engineering.

## Future Work
SysMLinJava is quite capabile now, but there are plans extend it in terms of its support for java-based SysML modeling.  Planned extensions include more valueTypes, more support for distributed blocks/processes across the internet, and more tool support for faster/cheaper development of the various model elements.  In the near term, a tool will be available (for a modest fee) that automates model execution and provides a set of graphical displays (state charts, sequence diagrams, timing diagrams, line charts, animations, etc) that can be accessed by the model to display model execution parameters and behaviors.  The tool will also provide capabilities to export the model to XMI, generate system requirements from the system model, generate reports on the model's contents, and generate SysMLinJava code from modeler-provided element "forms".  Also, a SysMLinJava web site that provides more information and support for the SysMLinJava API will be available soon.  The tool and web-sites's availability (anticipated December 2022) will be noted on this site as soon as they are known.

## Contact for Comments, Questions, Requests for Assistance or Training
Comments, questions, or requests for assistance or training can be sent via sysmlinjava@earthlink.net.
