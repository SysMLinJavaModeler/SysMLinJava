package sysmlinjava.quantitykinds;

import java.util.List;
import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava representation of a collection of instances of SysML's
 * quantityKinds. {@code SysMLQuantityKinds} extension classes declare static
 * instances of the {@code SysMLQuantityKind} and a list of these instances.
 * <p>
 * Each {@code SysMLQuantityKind} instance is a static final instance in the
 * extended class of this abstract class. An example follows.
 * 
 * <pre>{@code
	&#64;QuantityKind
	public static final SysMLQuantityKind Acceleration = new SysMLQuantityKind("acceleration", "a", "Acceleration", "");
	&#64;QuantityKind
	public static final SysMLQuantityKind Area = new SysMLQuantityKind("area", "area", "Area", "");
	}</pre>
 * <p>
 * The quantityKinds can be referenced in other elements of the SysMLinJava
 * model code by simply referencing the class scope with the unit name. An
 * example follows.
 * 
 * <pre>{@code
	quantity = SysMLinJavaQuantityKinds.Acceleration;
	}</pre>
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLQuantityKinds extends SysMLClass
{
	/**
	 * List of the instances of {@code SysMLQuantityKind}s declared in extension of
	 * this {@code SysMLQuantityKinds} class. See the
	 * {@code SysMLinJavaQuantityKinds} initialization of this variable for an
	 * example.
	 * 
	 * @see sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds
	 */
	public static List<SysMLQuantityKind> instances;

}
