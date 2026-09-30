# SysMLinJava - high precision MBSE
## Concept
The SysMLinJava API is a Java-based model development kit (MDK) for high-precision modeling as executable SysML models.  Modeling in SysML has traditionally meant using a commercially available tool to draw the structural and behavioral elements of the model as SysML diagrams OR, with the advent of SysMLv2, use a customized declaratory code language to specify the model as definitions (classes) and usages (objects). The dependence on drawings has more often than not resulted in costly effort to learn and manipulate the drawing tool.  The use of a custom code language has met with similar costs and frustrations.  And in both approaches to modeling, model execution, simulation, and testing has been frustratingly limited and/or esoteric to the vendor product.

SysMLinJava was developed to make extensive, precise, and executable models relatively easy and inexpensive to develop, analyze, and test.  Whereas SysML is based-on the object-oriented paradigm, it's a natural progession to use an object-oriented programming language to develop and execute SysML models.  Rather than fumble with drawing tools or custom coding language to specify model objects, the systems engineer can quickly generate model objects as Java classes using any of the myriad powerful easy-to-use Java software development IDEs available today.  And the "ultimate" SysML modeling capability - the executable model - is easily realized through Java's extensive and powerful execution capabilities.

SysMLinJava was designed/developed to comply with the OMG standard for SysML - a custom code-based language.  As a Java-based implementation, SysMLinJava cannot technically comply with the SysML standard.  Therefore, SysMLinJava does not claim to be in technical compliance with the SysML standard, but rather SysMLinJava is a representation of the SysML meta-model in the Java programming language so that the executable modeling capabilities of the programming language can be leveraged for SysML models.

## SysML Elements in Java
The elements of SysML - parts, ports, items, states, attribute types, actions, etc. - are all mapped to SysMLinJava classes.  The SysMLinJava classes represent the properties of the SysML elements as Java variables and methods.  The part attributes, for instance, are represented as Java variables that are specializations of the SysMLAttributeType.  Part actions are represented by Java methods and/or functions.  The part's states are represented by a variable that is an extension of the SysMLStateMachine class.  And so forth.  An actual code example of a SysML part implementation is provided as follows.  The example demonstrates how SysML modeling with SysMLinJava is relatively easy and straightforward.

## Example Part
Of course the primary element-type of the SysML model is the part.  SysMLinJava reflects this primacy in its implementation as well.  Based on the SysMLPart class, the part in SysMLinJava is defined by its many properties - attributes, items, state machine, ports, constraints, parametrics, comments, requirements, connectors, actions, and many more. All of these properties map to Java fields/variables and Java methods.  This mapping can be best visualized by an example part shown as follows.

```java
public class TrafficSignalControlSystem extends SysMLPart
{
	/**
	 * Part that represents the intersection signal system at main at 1st streets
	 */
	@Part
	public IntersectionSignalSystem mainAt1st;
	/**
	 * Part that represents the intersection signal system at main at 2nd streets
	 */
	@Part
	public IntersectionSignalSystem mainAt2nd;
	/**
	 * Part that represents the intersection signal system at main at 3rd streets
	 */
	@Part
	public IntersectionSignalSystem mainAt3rd;
	/**
	 * Part that represents the intersection signal system at main at 4th streets
	 */
	@Part
	public IntersectionSignalSystem mainAt4th;

	/**
	 * Attribute that indicates the presence of an emergency vehicle
	 */
	@Attribute
	public BBoolean emergencyVehiclePresent;
	/**
	 * Attribute that indicates the approaching direction of the emergency vehicle, if
	 * present
	 */
	@Attribute
	public Optional<DirectionDegrees> emergencyVehicleDirection;
	/**
	 * Root requirement for system
	 */
	@Requirement
	public SysMLRequirement rootRequirement;
	/**
	 * Capabilities issue for system modeling
	 */
	@Issue
	public SysMLIssue capabilitiesIssue;

/**
	 * Constructor
	 */
	public TrafficSignalControlSystem()
	{
		super("TrafficControlSystem", 0L);
	}

	@Override
	protected void createAttributes()
	{
		emergencyVehiclePresent = BBoolean.False;
		emergencyVehicleDirection = Optional.empty();
	}

	@Override
	protected void createParts()
	{
		mainAt1st = new IntersectionSignalSystem(this, "MainAt1st", 1L);
		mainAt2nd = new IntersectionSignalSystem(this, "MainAt2nd", 2L);
		mainAt3rd = new IntersectionSignalSystem(this, "MainAt3rd", 3L);
		mainAt4th = new IntersectionSignalSystem(this, "MainAt4th", 4L);
	}

	@Override
	protected void createRequirements()
	{
		rootRequirement = SystemRequirements.id1;
	}

	@Override
	protected void createIssues()
	{
		capabilitiesIssue = SystemIssues.capabilities;
	}
	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new TrafficSignalControlSystemStateMachine(this));
	}
}

```

The example shows a part representation of a simple traffic signal control system.  The system part is an extension of the `SysMLPart`.  Its properties include:

- **part** declared as fields annotated as parts and of types extended from `SysMLPart`
- **attributes** declared as fields annotated as attributes and of types (all provided as part of the SysMLinJava MDK) extended from `SysMLAttributeType`
- **state machine** declared as a field in the `SysMLPart` annotated as a state machine and of type extended from `SysMLStateMachine`
- **issue** declared as field annotated as a problem and of type `SysMLProblem`
- **requirement** declared as field annotated as a requirement and of type `SysMLRequirement`
- **actions** declared as methods annotated as actions and defining the activity performed (not shown)

The part also declares a number of overridden "create..." methods. While not properties of the part, per se, the methods perform all the creations/initializations of the part's elements, i.e. initialization of the attributes, creation of the parts, etc.  These creation/initialization methods are invoked automatically by the `SysMLPart`'s constructor, so the modeler need only add the property initialization statements to the appropriate "create..." method and the properties will be initialized in the correct sequence as part of part construction.
Although it is not explicitly shown in the example, the system model part is executable.  Higher-level model elements, e.g. domain objects, can invoke the system part's "start" operation which executes the part's state machine behavior in a dedicated Java thread.  This state machine then receives and responds to events that might be submitted to it by its ports, by other parts, and/or by actions of the part itself.  In any case, this structural and behavioral model of a system can be made as complex and precise as is needed while being fully executable and in accordance with the SysML standard.

In addition to the part, SysMLinJava provides explicit support for other commonly used SysML elements to include:
- State (machine) that is fully executable
- Action, Calculation, Case
- AttributeType
- Port, Proxy port
- Connector, Binding connector
- Comment, Issue, Rationale
- Hyperlink, ElementGroup
- Signal, Event
- Dependency
- Parametric Analysis Case (old constraintBlock)
- Requirement, Concern
- VerificationCase, UseCase
- View, ViewPoint, Stakeholder

SysMLinJava also provides supporting classes for extensive executable models to include:
- Threaded state machine for asychronous behavior across multiple threads
- Action as lambda expression
- Part thread pools for multi-threaded part behavior
- Part container for asychronous behaviors across multiple OS processes
- Asychronous parametric analysis case for analysis of parameters across multiple threads.

SysMLinJava supports virtually all of the features of SysMLv2.  You can see the complete version of the above example model as well as other examples of complex SysMLinJava models in the SysMLinJavaExampleModels repository.  All the examples can be downloaded for review and imported into your IDE, and are fully executable.

## How it works
### The SysMLinJava module
SysMLinJava is a java API that can be added as a project in an IDE.  It is a java module that can be used by java modules in other IDE projects to develop SysMLinJava models.  Typically, the modeler will create a project in the IDE with the SysMLinJava module as its sole content.  Another project will be created/used for the SysMLinJava model with a "requires transitive" dependency on the SysMLinJava module.

### SysMLinJava modeling
The modeler will typically develop the system model in an IDE project.  Model elements will be constructed as java classes that inherit/extend one of the SysMLinJava classes, e.g. `SysMLPart`, `StateMachine`, `SysMLAttributeType`, `SysMLPort`, etc.  The model elements will be aggregated in a "domain" class that contains all elements as SysML "parts" of the domain such as the system of interest as well as all the other systems with which it interfaces.  Alternatively, the domain class could be replaced by a `SysMLVerificationCase` class that constructs and executes the model as a SysML test/test case.  In any case, the SysMLinJava model classes are compiled and linked into an executable process or processes, each potentiallly executing as multiple threads representing asynchronously behaving objects in the system and its domain.

### Parametric Analysis
Extensive capabilities for parametric analysis are also supported by the SysMLinJava API.  The `SysMLParametricAnalysis` provides a base class for all parametric analysis modeling in SysMLinJava.  The parametric analysis case supports "bound" parameters as well as analysis heirarchies.  In addition, the parametric analysis case can be configured to operate asynchronously enabling extensive parameteric analysis with parameters that update asychrounously from other bound parameters and from the parametric analysis case.

## Documentation
The SysMLinJava code includes full javadoc comments, which you can view in the code.  And a directory of the full javadocs is provided at the base of the master branch.  You can download this directory into your IDE for ready reference.

## Dependencies and License
SysMLinJava uses the Apache license as shown above.  It has no dependencies beyond the modules of the Java SDK itself.  This version of SysMLinJava has been successfully tested and used on OpenJDK 27.

## Skills Needed
As a java-based modeling language, SysMLinJava necessarily requires the modeler also be capable of Java software development.  While most model-base systems engineers have software development skills, many do not.  SysMLinJava is based on the more commonly used syntax of the java language with no need for modelers to use the more advanced and essoteric constructs of Java.  In fact, the most advanced element of Java used for SysMLinJava modeling is the lambda expression used for activity specification.  Of course, for the more highly complex, multi-threaded/multi-process models, the SysMLinJava modeler will need to be familiar with the concurrency  aspects of Java.  SysMLinJava incorporates many of java's concurrency constructs in such a way that their use in modeling is relatively easy and straightforward.

While some may find modeling in the Java language to be "a bridge too far", there is the alternative of obtaining the skills of a java-developer as a "co-modeler".  Oftentimes, systems engineers leverage the skills of java developers to code engineering analyses, reports, calculations, and experiments during traditional model development.  SysMLinJava affords the opportunity to leverage these java developers to assist in actual model development to achieve a more complete and precise executable system model.

There is the option of the SysML modeler learning to program in java.  There are a myriad of free java training websites available and popular java IDE's provide extensive assistance and help in developing, building, and executing java programs.  In any case, there are numerous options for the SysML modeler to be able to leverage the power of SysMLinJava for high precision model-based systems engineering.

Finally, there is the option of obtaining the assistance of the SysMLinJava developers themselves to actually develop the models for the system engineer.  SyMLinJava LLC staff are experts in SysML modeling and can deliver complete, correct, and easy to understand fully executable system models at relatively low cost. This assistance can be evaluated and obtained at SysMLinJava.com.

## Future Work
SysMLinJava is quite capabile now, but there are plans extend it in terms of its support for java-based SysML modeling.  Planned extensions include more attributeTypes, more support for distributed processes across the internet, and more tool support for faster/cheaper development of the various model elements.  In the near term, a free tool is be available that automates model execution and provides a set of graphical displays (state charts, sequence diagrams, timing diagrams, line charts, animations, etc) that can be accessed by the model to display model execution parameters and behaviors.  The tool also provides capabilities to export the model to XMI, generate system requirements from the system model, generate reports on the model's contents, and generate SysMLinJava code from modeler-provided element "forms".  Also, a SysMLinJava web site that provides more information and support for the SysMLinJava API will be available soon.

## Contact for Comments, Questions, Requests for Assistance or Training
Comments, questions, or requests for assistance or training can be sent to sysmlinjava@earthlink.net.
