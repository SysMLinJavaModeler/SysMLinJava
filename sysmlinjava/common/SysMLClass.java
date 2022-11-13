package sysmlinjava.common;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * SysMLinJava representation of the base class of many SysML classes.
 * <h2>Base class for most SysML classes</h2>The {@code SysMLClass} is an
 * abstract class that provides the basic SysMLinJava class attributes such as a
 * name and ID number as well as a logger. Virtually all of the other
 * SysMLinJava elements such as {@code SysMLBlock}, {@code SysMLValueType},
 * {@code SysMLSignal}, and all of the {@code SysMLStateMachine}-related
 * elements are extensions of the {@code SysMLClass}.
 * <h3>Create/initialize methods</h3> The {@code SysMLClass} provides a series
 * of overrideable method calls to create any attributes and comments properties
 * of the represented SysML class. The {@code SysMLClass} constructor
 * automatically invokes these methods to create the attributes and comments
 * (problems, rationalses, hyperlinks, etc) so that extensions to the
 * {@code SysMLClass} need only override the {@code createXxxx()} methods for
 * the applicable properties (fields) declared in the class to ensure the
 * properties are created/initialized in the correct and complete sequence
 * needed. The overridable {@code createXxxx()} methods provide a "reminder" to
 * create and initialize the class's attributes and comments as well as a
 * framework for their complete definition.
 *
 * @author ModelerOne
 *
 */
public abstract class SysMLClass
{
	/**
	 * Logger for this and inheriting classes
	 */
	protected Logger logger;
	/**
	 * Optional name for this SysMLinJava object to be used in displays, logs, etc.
	 * as desired
	 */
	public Optional<String> name;
	/**
	 * ID of this SysMLinJava object to be used to identify it as desired, e.g. as
	 * the index for this SysMLinJava object's location in an array of objects.
	 * Defaults to 0 if not specified or set.
	 */
	public Long id;

	/**
	 * Constructor for logger only initialization, i.e. no name nor id
	 */
	public SysMLClass()
	{
		super();
		logger = Logger.getLogger(this.getClass().getSimpleName());
		this.name = Optional.empty();
		this.id = 0L;

		createAttributes();
		createComments();
		createProblems();
		createRationales();
		createHyperlinks();
	}

	/**
	 * Constructor for logger and name only initialization, i.e. no id
	 * 
	 * @param name name for the class
	 */
	public SysMLClass(String name)
	{
		this();
		this.name = Optional.of(name);
	}

	/**
	 * Constructor for logger, name, and id initialization.
	 * 
	 * @param name name for the class
	 * @param id   unique identifier of the class
	 */
	public SysMLClass(String name, Long id)
	{
		this(name);
		this.id = id;
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance this instance is to be copy of
	 */
	public SysMLClass(SysMLClass copied)
	{
		super();
		logger = Logger.getLogger(this.getClass().getSimpleName());
		this.name = copied.name;
		this.id = copied.id;
	}

	/**
	 * Sets the name field that may or may not be unique, as needed
	 * 
	 * @param name name of the class
	 */
	public void setName(String name)
	{
		this.name = Optional.of(name);
	}

	/**
	 * Sets the id field
	 * 
	 * @param id Long number that may or may not be unique, as needed
	 */
	public void setID(Long id)
	{
		this.id = id;
	}

	/**
	 * Overridable operation for the identity String for this object for use in
	 * logging and other display outputs. This default implementation returns the
	 * object's {@code name}, if present, or the class's name if not. If the
	 * {@code id} is set to a value greater than 0, then the id is concatenated. (If
	 * the {@code id} is to be concatenated to the identity string, then set the
	 * {@code id} to an integer value greater than 0). If an array {@code index}
	 * value is present, the index (in square brackets) is concatenated to the
	 * string.
	 * <p>
	 * Extensions of the {@code SysMLClass} can override this operation to provide
	 * an arbitrarily formatted identity string as desired.
	 * 
	 * @return the identity string for this {@code SysMLClass} object
	 */
	public String identityString()
	{
		String result;
		if (name.isPresent())
		{
			if (id > 0)
				result = String.format("%s%d", name.get(), id);
			else
				result = String.format("%s", name.get());
		}
		else
		{
			if (id > 0)
				result = String.format("%s%d", getClass().getSimpleName(), id);
			else
				result = String.format("%s", getClass().getSimpleName());
		}
		return result;
	}

	/**
	 * Overridable operation that creates and initializes the attributes of the class.
	 * Extensions of the {@code SysMLClass} should override this operation to
	 * create/initialize any attributes as follows:
	 * 
	 * <pre>
	    anAttribute = new AnAttribute(&lt;params&gt;, &lt;params&gt;, ...);
	    nextAttribute = new NextAttribute(&lt;params&gt;, &lt;params&gt;, ...);
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field
	 * annotated with the {@code &#64;Attribute} annotation and the assigned value
	 * corresponds to a constructor for that field variable.
	 */
	protected void createAttributes()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's comments. An
	 * example follows:
	 * 
	 * <pre>
	   myFirstComment = new SysMLComment(<i>comment text</i>);
	   mySeondComment = new SysMLComment(<i>comment text</i>);
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field
	 * annotated with the {@code &#64;Comment} annotation and of type
	 * {@code SysMLComment}. The assigned value is the {@code SysMLComment}'s
	 * constructor with initialization parameters as appropriate.
	 */
	protected void createComments()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's problem
	 * comments. An example follows:
	 * 
	 * <pre>
	   myFirstProblem = new SysMLProblem(<i>problem text</i>);
	   mySeondProblem = new SysMLProblem(<i>problem text</i>);
	   etc.}
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field
	 * annotated with the {@code &#64;Problem} annotation and of type
	 * {@code SysMLProblem}. The assigned value is the {@code SysMLProblem}'s
	 * constructor with initialization parameters as appropriate.
	 */
	protected void createProblems()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's rationale
	 * comments. An example follows:
	 * 
	 * <pre>
	   myFirstRationale = new SysMLRationale(<i>rationale text</i>);
	   mySeondRationale = new SysMLRationale(<i>rationale text</i>);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field
	 * annotated with the {@code &#64;Rationale} annotation and of type
	 * {@code SysMLRationale}. The assigned value is the {@code SysMLRationale}'s
	 * constructor with initialization parameters as appropriate.
	 */
	protected void createRationales()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's hyperlink
	 * comments. An example follows:
	 * 
	 * <pre>
	   myFirstHyperlink = new SysMLHyperlink(<i>hyperlink args</i>);
	   mySeondHyperlink = new SysMLHyperlink(<i>hyperlink args</i>);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field
	 * annotated with the {@code &#64;Hyperlink} annotation and of type
	 * {@code SysMLHyperlink}. The assigned value is the {@code SysMLHyperlink}'s
	 * constructor with initialization parameters as appropriate.
	 */
	protected void createHyperlinks()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's element groups
	 * comments. An example follows:
	 * 
	 * <pre>
	   myElementGroup = new SysMLElementGroup(<i>element group args</i>);
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field
	 * annotated with the {@code &#64;ElementGroup} annotation and of type
	 * {@code SysMLElementGroup}. The assigned value is the
	 * {@code SysMLElementGroup}'s constructor with initialization parameters as
	 * appropriate.
	 * 
	 */
	protected void createElementGroups()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's constraint
	 * notes comments. An example follows:
	 * 
	 * <pre>
	   myFirstConstraintNote = new SysMLConstraintNote(<i>constraint lambda expression</i>);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field
	 * annotated with the {@code &#64;ConstraintNote} annotation and of type
	 * {@code SysMLConstraintNote}. The assigned value is the
	 * {@code SysMLConstraintNote}'s constructor with initialization parameters as
	 * appropriate.
	 */
	protected void createConstraintNotes()
	{
	}

	/**
	 * Name of method to create attributes, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createAttributesMethodName = "createAttributes";
	/**
	 * Name of method to create comments, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createCommentsMethodName = "createComments";
	/**
	 * Name of method to create problems, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createProblemsMethodName = "createProblems";
	/**
	 * Name of method to create rationales, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createRationalesMethodName = "createRationales";
	/**
	 * Name of method to create hyperlinks, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createHyperlinksMethodName = "createHyperlinks";
	/**
	 * Name of method to create element groups, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createElementGroupsMethodName = "createElementGroups";
	/**
	 * Name of method to create constraint notes, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintNotesMethodName = "createConstraintNotes";

	@Override
	public String toString()
	{
		return String.format("%s [name=%s, id=%s]", getClass().getSimpleName(), name, id);
	}
}
