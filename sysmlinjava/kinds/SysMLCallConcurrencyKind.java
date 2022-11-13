package sysmlinjava.kinds;

/**
 * SysMLinJava representation of the SysML enumeration of the kinds of object
 * call concurrencies available under SysML, i.e. the SysML
 * {@code CallConcurrencyKind}
 * 
 * @author ModelerOne
 *
 */
public enum SysMLCallConcurrencyKind
{
	/**
	 * Call is sequential (executes in same thread of execution)
	 */
	sequential,
	/**
	 * Call is guarded (executes sequentially, single instance at a time, caller
	 * blocked until available)
	 */
	guarded,
	/**
	 * Call is concurrent (executes in different thread of execution)
	 */
	concurrent;
}
