# SysMLinJava - high precision MBSE
## Concept
The SysMLinJava API is A Java-based Model Development Kit (MDK) for high-precision modeling as executable SysML models
Modeling in SysML has traditionally meant using a commercially available tool to draw the structural and behavioral elements of the model as SysML diagrams. This dependence on drawings has more often than not resulted in costly effort to learn and manipulate the drawing tool and imposed considerable frustration in modeling complex behaviors. In addition, the ability of the drawings-based tool to execute/test the model has been frustratingly limited and/or esoteric to the vendor product.

SysMLinJava was developed to make extensive, precise, and executable models relatively easy to develop, analyze, and test. Whereas SysML is based-on the object-oriented paradigm, it's a natural progession to use an object-oriented programming language to develop and execute SysML models. Rather than fumble with drawing tools to specify model objects, the systems engineer can quickly generate model objects as Java classes using any of the myriad powerful easy-to-use Java software development IDEs available today. And the "ultimate" SysML modeling capability - the executable model - is easily realized through Java's extensive and powerful execution capabilities.

SysMLinJava was designed/developed to comply with the OMG standard for SysML - a drawings-based language. As a Java-based implementation, SysMLinJava cannot technically comply with the SysML standard. Therefore, SysMLinJava does not claim to be in technical compliance with the SysML standard, but rather SysMLinJava is a representation of the SysML meta-model in the Java programming language so that the executable modeling capabilities of a programming language can leveraged for SysML models.

## SysML Elements in Java
The elements of SysML - blocks, state machines, value types, activities, etc. - are all mapped to SysMLinJava classes. The SysMLinJava classes represent the properties of the SysML elements as Java variables and methods. The block values, for instance, are represented as Java variables that are specializations of the SysMLValueType . Block operations and receptions are represented by Java methods. The block's state machine is represented by a variable that is an extension of the SysMLStateMachine class. And so forth. An actual code example of a SysML block implementation is provided as follows.  The example demonstrates how SysML modeling with SysMLinJava is relatively easy and straightforward.

## Example Block
Of course the primary element-type of the SysML model is the block. SysMLinJava reflects this primacy in its implementation as well. Based on the SysMLBlock class, the Block in SysMLinJava is defined by its many properties - values, flows, state machine, ports, constraints, constraint blocks, comments, requirements, connectors, operations, receptions, activities, and many more. All of these properties map to Java fields/variables and Java methods. This mapping can be best visualized by an example block shown as follows.

```java
public class TrafficSignalSystem extends SysMLBlock
{
	//non-relevant code and comments not shown for brevity
	
	@FullPort
	public TimeSynchReceivePort timeSynchReceiver
	@FullPort
	public EVDetectionReceivePort evDetectionReceiver;
	@FullPort
	public SystemStatusTransmitPort systemStatusTransmitter;
	
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

The example shows a block representation of a simple traffic signal system. The system block is an extension of the SysMLBlock. Its properties include:

- ports declared as fields annotated as full ports and of types extended from SysMLFullPort
- values declared as fields annotated as values and of types (all provided as part of the SysMLinJava MDK) extended from SysMLValueType
- state machine declared as a field in the SysMLBlock annotated as a state machine and of type extended from SysMLStateMachine
- problem declared as field annotated as a problem and of type SysMLProblem
- requirement declared as field annotated as a requirement and of type SysMLRequirement
- operations declared as methods annotated as operations and defining the activity performed

The SysMLBlock also declares a number of overridden "create..." methods. While not properties of the block, per se, the methods perform all the creations/initializations of the block's properties, i.e. initialization of the values, creation of the ports, etc. These creation/initialization methods are invoked automatically by the SysMLBlock's constructor, so the modeler need only add the property initialization statements to the appropriate "create..." method and the properties will be initialized in the correct sequence as part of block construction.

Although it is not explicitly shown in the example, the system model block is executable. Higher-level model elements, e.g. domain objects, can invoke the system block's "start" operation which executes the block's state machine behavior in a dedicated Java thread. This state machine then receives and responds to events that might be submitted to it by its ports, by other blocks, and/or by operations/receptions of the block itself. In any case, this structural and behavioral model of a system can be made as complex and precise as is needed while being fully executable and in accordance with the SysML standard.

In addition to the block, SysMLinJava provides explicit support for other commonly used SysML elements to include:
- state machine
- valueType
- full port/proxy port
- associationBlock/connector
- comment/problem/rationale
- hyperlink/constraintNote/elementGroup
- signal/event
- dependency
- constraintBlock/constraintParam/constraint
- requirement
- testCase/test
- view/viewPoint/stakeholder

SysMLinJava also provides supporting classes for extensive executable models to include:
- Threaded state machine for asychronous behavior across multiple threads
- Activity (as lambda expression)
- Block thread pools for multi-threaded block behavior
- Block container for asychronous behaviors across multiple OS processes
- Asychronous constraint block for constraint parameters across multiple threads.

SysMLinJava also supports virtually all of the features of SysMLv2.  You can see the complete version of the above as well as other examples of complex SysMLinJava models in the SysMLinJavaExampleModels repository.  All the examples can be downloaded for review and are fully executable.

## How it works
### The SysMLinJava module
SysMLinJava is a java API that can be added as a project or library in an IDE used to develop a SysMLinJava model.  Typically, the modeler will create a project in the IDE with the SysMLinJava module as its sole content.  Another project will be created/used for the SysMLinJava model with a dependency/build path on the SysMLinJava module.

### SysMLinJava modeling
The modeler will typically develop the system model in an IDE project.  Model elements will be constructed as java classes that inherit/extend one of the SysMLinJava classes, e.g. SysMLBlock, StateMachine, SysMLValueType, SysMLFullPort, etc.  The model elements will be aggregated in a "domain" class that contains all elements of the domain such as the system of interest as well as all the other systems with which it interfaces.  Alternatively, the domain class could be replaced by a SysMLTest and/or SysMLTestCase class that constructs and executes the model as a system test/test case.  In either case, the SysMLinJava model classes are compiled and linked into an executable process or processes, each potentiallly executing as multiple threads representing asynchronously behaviing objects in the system and its domain.

### Parametric Analysis
Extensive capabilities for parametric analysis are also supported by the SysMLinJava API.  The SysMLConstraintBlock provides a base class for all constraint block modeling in SysMLinJava.  The constraint block supports "bound" constraint parameters as well as contraint block heirarchies.  In addition, the constraint block can be configured to operate asynchronously enabling extensive parameteric analysis of parameters that update asychrounously from other bound parameters and from the constrain block.

## Documentation
The SysMLinJava code includes full javadoc comments, which you can view in the code.  You can execute the javadoc program against the code (via your IDE) to generate the javadoc as web pages, if desired.

## Dependencies and License
SysMLinJava uses the Apache license as shown above.  It has no dependencies beyond the modules of the Java SDK itself.  SysMLinJava has been successfully tested and used on OpenJDK 18.

## Future Work
SysMLinJava is planned to be extended in terms of more valueTypes, more support for distributed blocks/processes across the internet, and more tool support for faster/cheaper development of the various model elements.  In the near term, a tool will be availabel (for a modest fee) that automates model execution and provides a set of graphical displays (state charts, sequence diagrams, timing diagrams, line charts, animations, etc) that can be accessed by the model to display model execution parameters and behaviors.  The tool will also provide capabilities to export the model to XMI, generate system requirements from the system model, generate reports on the model's contents, and generate SysMLinJava code from modeler-provided element "forms".  Also, a SysMLinJava web site that provides more information and support for the SysMLinJava API will be available soon.  The tool and web-sites's availability will be noted on this site as soon as they are available.

## Contact
Comments, questions, or requests for consulting or training can be made via sysmlinjava@earthlink.net.

© 2022 SysMLinJava.com. All Rights Reserved.
