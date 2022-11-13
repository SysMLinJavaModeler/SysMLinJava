package sysmlinjava.analysis.common;

import java.util.Optional;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.analysis.common.StackedProtocolObject;

/**
 * Interface specification for an object that is encapsulated in another object
 * which is part of a protocol "stack". The interface requires services on the
 * implementing object to include methods to return a "stack" of names of the
 * objects in the nested encapsulations of the protocol objects. The stack of
 * names serves to display the protocol objects as nested layers of information
 * corresponding to their position in the "stack". For instance, a typical
 * world-wide web protocol stack would show the nested encapsulations of the
 * protocols in the stack and might appear as follows:
 * 
 * <pre>
        EthernetPacket[IPPacket[TCPPacket[HTTPResponse[HTMLDoc]]]]
 * </pre>
 * 
 * @author ModelerOne
 *
 */
public interface StackedProtocolObject
{
	/**
	 * Overridable default implementation of method to return the "stack" of names.
	 * Overriding implementations should invoke the default method below. Default is
	 * to return the name of this interface.
	 * 
	 * @return String representation of the object stack names
	 */
	default String stackNamesString()
	{
		return getClass().getSimpleName();
	}

	/**
	 * Default implementation of method to be called by required method above to
	 * produce a stack names string (instead of a simple class name string). This
	 * method will call the stackNamesString() operation for the "next" object in
	 * the stack to produce its stackNamesString, thereby using reentrancy to build
	 * the entire stack of names of objects in the protocol stack.
	 * 
	 * @param namedClass object that is the current point in the "stack" of objects.
	 *                   If this object (in the stack of protocol objects) has a
	 *                   name, the name is used, but if not, the object's class name
	 *                   is used.
	 * @param next       StackedProtocolObject that is the "next" object in the
	 *                   stack.
	 * @return String representation of the protocol stack's object names.
	 */
	default String stackNamesString(SysMLClass namedClass, Optional<StackedProtocolObject> next)
	{
		String name = namedClass.name.isPresent() ? namedClass.name.get() : namedClass.getClass().getSimpleName();
		if (next.isPresent())
			return String.format("%s[%s]", name, next.get().stackNamesString());
		else
			return String.format("%s", name);
	}
}