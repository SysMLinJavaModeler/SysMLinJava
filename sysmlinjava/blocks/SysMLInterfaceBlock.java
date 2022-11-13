package sysmlinjava.blocks;

import sysmlinjava.common.SysMLInterface;

/**
 * <h2></h2>
 * <h3>SysMLinJava's represention of the SysML interface block</h3>
 * {@code SysMLInterfaceBlock} is an extension of the {@code SysMLInterface} and
 * serves only to declare any extending interface to be a SysML interface block.
 * As such, it declares no methods/operations to be implemented by the
 * interface. Instead, this interface must be extended with the
 * method/operation/reception implementations required for a particular
 * application of the interface block to a {@code SysMLBlock} or
 * {@code SysMLProxyPort}
 * <h3>Interface properties</h3> Extensions of the {@code SysMLInterfaceBlock}
 * can include valueType, flow, comment, and requirement declarations. These
 * declarations are, by default, {@code public static final} fields in
 * accordance with standard Java interface grammer. Each of the declared fields
 * should be annotated with the appropriate annotation, e.g. {@code &#64;Value},
 * {@code &#64;Flow}, {@code &#64;Comment}, and {@code &#64;Requirement}
 * annotations. Operation and reception declarations should likewise be
 * annotated with {@code &#64;Operation} and (@code &#64;Reception} annotations,
 * respectively. Some Java editors will not be able to locate interface
 * annotations in the modulepath or classpath, in which case it will be
 * necessary to manually add the annotation import statements as needed. An
 * example is as follows:
 * 
 * <pre>
		:
	import sysmlinjava.annotations.Constraint;
	import sysmlinjava.annotations.Flow;
	import sysmlinjava.annotations.Reception;
	import sysmlinjava.annotations.Value;
		:

	public static interface PowerInterface extends SysMLInterfaceBlock
	{
		&#64;Value
		public static final PowerWatts maxPower = new PowerWatts(20.0);
		&#64;Flow
		public static final PowerWatts power = new PowerWatts(0);
		&#64;Constraint
		public SysMLConstraint constraint = () -> power.lessThan(maxPower);
		
		&#64;Reception
		public void receive(PowerWatts power);
	}
 * </pre>
 * 
 * @author ModelerOne
 *
 */
public interface SysMLInterfaceBlock extends SysMLInterface
{
}
