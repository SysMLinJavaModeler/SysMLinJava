package sysmlinjava.kinds;

/**
 * SysMLinJava representation of the enumeration of the kinds of feature
 * direction available under SysML, i.e. SysMLinJava's version of SysML's
 * {@code FeatureDirectionKind}
 * 
 * @author ModelerOne
 *
 */
public enum SysMLFeatureDirectionKind
{
	/**
	 * Feature is provided (can be invoked by others)
	 */
	provided,
	/**
	 * Feature is required (will be invoked by this)
	 */
	required,
	/**
	 * Feature is both provided and required
	 */
	providedrequired;
}
