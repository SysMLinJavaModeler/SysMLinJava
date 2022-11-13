package sysmlinjava.valuetypes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava representation of the SysML enumerated value type. In SysML, the
 * common "enum" is the SysML Enumeration, which is an extension of the SysML
 * ValueType. This SysMLinJava adaptation enables the enumeration valueType to
 * be used in the SysMLinJava model as an observable parameter in
 * constraintBlocks and it enables use by the many SysML tools for document
 * auto-generation, graphical displays, etc.
 * <p>
 * Specializations of the {@code SysMLEnumeration} can be quickly and easily
 * generated by invocation of the provided {@code sourceCodeString()} method.
 * The {@code sourceCodeString()} method generates the source code for a
 * specialization of the {@code SysMLEnumeration} for a specified set of
 * enumerated values. Calling this method with the specified parameters enables
 * quick development of the enumerated value type for SysMLinJava models.
 * <p>
 * As an abstract class, enumeration value types must be declared as extensions
 * to this class. The generic parameter is the extension class itself, just as
 * it is declared (by the compiler) for the Java enum type. Each of the
 * enumerated values are then declared as static fields within the class and
 * initialized in accordance with the constructor declared for the class. An
 * example enumerated value type is as follows:
 * 
 * <pre>
 * public class CardinalDirectionsEnum extends SysMLEnumeration&lt;CardinalDirectionsEnum&gt;
 * {
 * 	&#64;Attribute
 * 	public static final CardinalDirectionsEnum north = new CardinalDirectionsEnum("north", 0, new DirectionRadians(0));
 * 	&#64;Attribute
 * 	public static final CardinalDirectionsEnum east = new CardinalDirectionsEnum("east", 1, new DirectionRadians(PI / 2));
 * 	&#64;Attribute
 * 	public static final CardinalDirectionsEnum south = new CardinalDirectionsEnum("south", 2, new DirectionRadians(PI));
 * 	&#64;Attribute
 * 	public static final CardinalDirectionsEnum west = new CardinalDirectionsEnum("west", 3, new DirectionRadians(PI * 1.5));
 * 
 * 	public static final CardinalDirectionsEnum[] values = {north, east, south, west};
 * 
 * 	public DirectionRadians direction;
 * 
 * 	private CardinalDirectionsEnum(String name, int ordinal, DirectionRadians direction)
 * 	{
 * 		super(name, ordinal);
 * 		this.direction = direction;
 * 	}
 * 
 * 	public static CardinalDirectionsEnum valueOf(String name)
 * 	{
 * 		return SysMLEnumeration.valueOf(name, values);
 * 	}
 * 
 * 	public static CardinalDirectionsEnum valueOf(int ordinal)
 * 	{
 * 		return SysMLEnumeration.valueOf(ordinal, values);
 * 	}
 * 
 * 	public static CardinalDirectionsEnum[] values()
 * 	{
 * 		return values;
 * 	}
 * }
 * </pre>
 * 
 * @author ModelerOne
 *
 * @param <E> The class that is extending SysMLEnumeration, i.e. the class
 *            containing the enumeration values
 */
public abstract class SysMLEnumeration<E extends SysMLEnumeration<E>> extends SysMLValueType implements Comparable<E>
{
	/**
	 * String name for the enumerated value
	 */
	@Attribute
	public String name;
	/**
	 * Ordinal value for this enumerated value
	 */
	@Attribute
	public int ordinal;

	/**
	 * Base constructor for basic name and ordinal values
	 * 
	 * @param name    string name of the enum value
	 * @param ordinal int ordinal of the enum value
	 */
	public SysMLEnumeration(String name, int ordinal)
	{
		super();
		this.name = name;
		this.ordinal = ordinal;
	}

	/**
	 * Base constructor for copy of a enumeration instance
	 * 
	 * @param e enumeration value to be copied
	 */
	public SysMLEnumeration(E e)
	{
		super();
		this.name = e.name;
		this.ordinal = e.ordinal;
	}

	/**
	 * Returns the enum value's string name
	 * 
	 * @return String name of the enumeration value
	 */
	public String name()
	{
		return name;
	}

	/**
	 * Returns the enum value's int ordinal
	 * 
	 * @return int ordinal of the enumberation value
	 */
	public int ordinal()
	{
		return ordinal;
	}

	/**
	 * Returns the Enumerated for the specified name and values of the Enumerated
	 * (analogous to the Java enum's {@code valueOf(String name)} operation.<br>
	 * <b>Note:</b> this method should be overriddent by extending classes to just
	 * require the name value.
	 * 
	 * @param <E>    Class that extends this Enumerated class
	 * @param name   string name of the enum value
	 * @param values array of enumerated values (typically declared in the extension
	 *               class as static field);
	 * 
	 * @return the instance of the Enumerated extension with the specified name
	 */
	@SuppressWarnings("unchecked")
	public static <E extends SysMLEnumeration<E>> E valueOf(String name, SysMLEnumeration<?>[] values)
	{
		E result = null;
		int i = 0;
		while (result == null && i < values.length)
		{
			if (values[i].name.equals(name))
				result = (E)values[i];
			i++;
		}
		return result;
	}

	/**
	 * Returns the Enumerated for the specified ordinal and values of the Enumerated
	 * (analogous to the Java enum's {@code valueOf(int ordinal)} operation.<br>
	 * <b>Note:</b> this method should be overriddent by extending classes to just
	 * require the ordinal value.
	 * 
	 * @param <E>     Class that extends this Enumerated class
	 * @param ordinal integer ordinal of the enum value
	 * @param values  array of enumerated values (typically declared in the
	 *                extension class as static field);
	 * 
	 * @return the instance of the Enumerated extension with the specified name
	 */
	@SuppressWarnings("unchecked")
	public static <E extends SysMLEnumeration<E>> E valueOf(int ordinal, SysMLEnumeration<?>[] values)
	{
		E result = null;
		int i = 0;
		while (result == null && i < values.length)
		{
			if (values[i].ordinal == ordinal)
				result = (E)values[i];
			i++;
		}
		return result;
	}

	/**
	 * Sets this enumeration value to a specified enumeration value and notifies all
	 * {@code ValueChangeObserver}s, if any, of the change.
	 * 
	 * @param value enumeration value to set this enumeration value to
	 */
	public void setValue(E value)
	{
		this.name = value.name;
		this.ordinal = value.ordinal;
		notifyValueChangeObservers();
	}

	/**
	 * Sets this enumeration value to a specified enumeration value. Does NOT notify
	 * {@code ValueChangeObserver}s of the change.
	 * 
	 * @param value enumeration value to set this enumeration value to
	 */
	public void set(E value)
	{
		this.name = value.name;
		this.ordinal = value.ordinal;
	}

	/**
	 * Returns comparison value for this and the specified enumerated value type
	 * 
	 * @param o other value to compare
	 * @return negative number if other ordinal is less than this ordinal, positive
	 *         if greater, 0 if same
	 */
	@Override
	public final int compareTo(E o)
	{
		return this.ordinal - o.ordinal;
	}

	/**
	 * Override of Object.equals(Object) method for {@code SysMLEnumeration}
	 * instances.
	 * 
	 * @param obj other object to compare to
	 * @return true if equal (names are same), false otherwise
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		if (this.getClass().getSimpleName().equals(obj.getClass().getSimpleName()))
			if (obj instanceof SysMLEnumeration<?>)
				if (((SysMLEnumeration<?>)obj).name.equals(this.name))
					result = true;
		return result;
	}

	@Override
	public void createUnits()
	{
		units = SysMLinJavaUnits.Enumerated;
	}

	/**
	 * String format for auto-generation of enumeration code's package declaration
	 */
	private static String packageDec =
		"package %s;%n"+
		"%n"+
		"import sysmlinjava.annotations.Attribute;%n";
	/**
	 * String format for auto-generation of enumeration code's class declaration
	 */
	private static String classDec = "public class %s extends SysMLEnumeration<%s>%n{";
	/**
	 * String format for auto-generation of enumeration code's enumeration field declaration
	 */
	private static String enumFieldDec = "\t@Attribute public static final %s %s = new %s(\"%s\", %d);";
	/**
	 * String format for auto-generation of enumeration code's values declaration
	 */
	private static String valuesFieldDec = "\tpublic static final %s[] values = {%s};";
	/**
	 * String format for auto-generation of enumeration code's constructor declaration
	 */
	private static String constructorDec =
		"\tprivate %s(String name, int ordinal)%n"+
		"\t{%n"+
		"\t\tsuper(name, ordinal);%n"+
		"\t}%n";
	/**
	 * String format for auto-generation of enumeration code's method declaration
	 */
	private static String methodDecs =
		"\tpublic static %s valueOf(String name)%n"+
		"\t{%n"+
		"\t\treturn SysMLEnumeration.valueOf(name, values);%n"+
		"\t}%n"+
		"%n"+
		"\tpublic static %s valueOf(int ordinal)%n"+
		"\t{%n"+
		"\t\ttreturn SysMLEnumeration.valueOf(ordinal, values);%n"+
		"\t}%n"+
		"%n"+
		"\tpublic static %s[] values()%n"+
		"\t{%n"+
		"\t\treturn values;%n"+
		"\t}%n"+
		"}%n";
	
	/**
	 * Generates a string and (Optionally) a file containing the source code for a
	 * class that extends/specializes this class for a specified enumeration.
	 * 
	 * @param packagePathName name of the package to appear in the package path name
	 *                        declaration
	 * @param enumClassName   name for the enumeration class
	 * @param enumNames       list of the names of the enumerated values (ordinal
	 *                        will be position in list)
	 * @param javaFilePath    optional path of .java file in which to store the
	 *                        generated class source code
	 * @return string containing the generated source code
	 */
	public static String sourceCodeFor(String packagePathName, String enumClassName, List<String> enumNames, Optional<Path> javaFilePath)
	{
		StringJoiner joiner = new StringJoiner("\n");
		joiner.add(String.format(packageDec, packagePathName));
		joiner.add(String.format(classDec, enumClassName, enumClassName));
		for (int i = 0; i < enumNames.size(); i++)
			joiner.add(String.format(enumFieldDec, enumClassName, enumNames.get(i), enumClassName, enumNames.get(i), i));
		StringJoiner valuesJoiner = new StringJoiner(", ");
		for (int i = 0; i < enumNames.size(); i++)
			valuesJoiner.add(enumNames.get(i));
		joiner.add(String.format(valuesFieldDec, enumClassName, valuesJoiner.toString()));
		joiner.add(String.format(constructorDec, enumClassName));
		joiner.add(String.format(methodDecs, enumClassName, enumClassName, enumClassName));
		if (javaFilePath.isPresent())
			try
			{
				Files.writeString(javaFilePath.get(), joiner.toString(), StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		return joiner.toString();
	}

	@Override
	public String toString()
	{
		return name;
	}
}