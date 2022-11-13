package sysmlinjava.analysis.bom.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a comment for a
 * bill-of-materials (BOM) item, i.e. an instance of a {@code SysMLComment} with
 * a formatted string containing information to be used in a BOM
 * {@code LineItem}. The formatted string is assumed to use the java properties
 * format to specify line item fields. An example declaration is as follows.
 * 
 * <pre>
 * {@code 
	&#64;BOMLineItemComment
	&#64;Comment
	SysMLComment bomComment;

		:

	&#64;Override
	protected void createComments()
	{
		bomComment = new SysMLComment("""
			Description:Voltage source/power supply for circuit
			Source:AcePowerSupplies.com
			Comment:Must be compliant with IEEE 802.11g for network comms
			""");
	}

 }
 * </pre>
 * 
 * Note that each row the comment is a &lt;name&gt;:&lt;value&gt; pair. The
 * value will be mapped to a corresponding column having the &lt;name&gt; in the
 * column header in the line item table and the &lt;value&gt; string will appear
 * in that column for the line item. An exampe line item with the values column
 * is as follows:
 * 
 * <pre>
 * {@code

 SysMLinJava Bill-of-Materials
 No.  Title          Qty  Values                    Description                  Source                Comment
   1  VoltageSource    1  amplitude (Volt) = 220.0  Voltage source/power supply  AcePowerSupplies.com  Must be compliant with IEEE
                          maxCurrent (Amps) = 20.0  for circuit                                        802.11g for network comms
 }
 * </pre>
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface BOMLineItemComment
{

}
