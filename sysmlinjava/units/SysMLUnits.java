package sysmlinjava.units;

import java.util.ArrayList;
import java.util.List;
import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava's representation of a collection (library) of {@code SysMLUnit}s
 * which are SysMLinJava's representations of the SysML unit. Whereas units in
 * SysMLinJava are instances of the {@code SysMLUnit}, this {@code SysMLUnits}
 * class provides a base library of {@code SysMLUnit} instances.
 * <p>
 * Each {@code SysMLUnit} instance must be declared as a static final instance,
 * i.e. each unit is declared as a unique object within the scope of the
 * containing class. This {@code SysMLUnits} class provides the base for classes
 * that contain {@code SysMLUnit} instances. Extensions of the
 * {@code SysMLUnits} class should exclusively contain unit declarations, i.e.
 * {@code SysMLUnit} instances, and not other objects or operations. An example
 * of a use of this class to declare units is as follows.
 * 
 * <pre>
    public class SysMLinJavaUnits extends SysMLUnits
    {
        &#64;Unit
        public static final SysMLUnit FeetCubic = new SysMLUnit("feet-cubic", "ft3", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
        &#64;Unit
        public static final SysMLUnit MilesCubic = new SysMLUnit("miles-cubic", "mi3", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
        &#64;Unit
        public static final SysMLUnit Liters = new SysMLUnit("liters", "ltr", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
        &#64;Unit
        public static final SysMLUnit Gallons = new SysMLUnit("gallons", "gal", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
        &#64;Unit
        public static final SysMLUnit Nanoseconds = new SysMLUnit("nanoseconds", "ns", "", "", Optional.of(SysMLinJavaQuantityKinds.Time));
        
        static {instances.addAll(List.of(FeetCubic, MilesCubic, Liters, Gallons, Nanoseconds));}  
    }
 * </pre>
 * 
 * The units can be referenced in the SysMLinJava model code by simply
 * referencing the containing class scope with the unit name. An example from a
 * units declaration for a {@code SysMLValueType} is as follows:
 * 
 * <pre>
    &#64;Override
    protected void createUnits()
    {
        units = SysMLinJavaUnits.FeetCubic;
    }
 * </pre>
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLUnits extends SysMLClass
{
	/**
	 * List (library) of all SysMLUnit instances. This list can be used to
	 * programmatically search for units or otherwise use the library of units.
	 * SysMLinJava tools use this list for generating model reports, requirements
	 * generation, etc. so it should be created for these purposes.
	 * <p>
	 * Extensions to this class that create/instantiate SysMLUnit instances should
	 * invoke a static statement to add the instances to this list. An example of
	 * adding the {@code SysMLUnit}s to this "library" of units in an extension to
	 * the {@code SysMLUnits} class is as follows:
	 * 
	 * <pre>
    static
    {
        instances.addAll(List.of(FeetCubic, MilesCubic, Liters, Gallons, Nanoseconds));
    }		  
	 * </pre>
	 */
	public static List<SysMLUnit> instances = new ArrayList<>();

	/**
	 * Constructor - default, no initializations
	 */
	public SysMLUnits()
	{
		super();
	}
}