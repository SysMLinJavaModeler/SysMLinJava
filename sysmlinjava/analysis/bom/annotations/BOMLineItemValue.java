package sysmlinjava.analysis.bom.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates the annotated field declares a value property of a line item in a
 * bill-of-materials (BOM). The {@code &#64;BOMLineItem} annotation must be on a
 * field that is also annotated as a {@code &#64;Value}. The field variable must
 * an extension of the {@code SysMLValueType} and the variable must be
 * created/initialized in a {@code createValues()} opertaion. The value type
 * name, units, and initial value will be displayed in the "Values" column of
 * the line item. Example values might be the line item's size, weight, power,
 * voltage, etc. An example declaration is as follows.
 * 
 * <pre>
 * {@code

	&#64;BOMLineItemValue
	&#64;Value
	Voltage amplitude;
	
	&#64;BOMLineItemValue
	&#64;Value
	Current maxCurrent;

		:

	&#64;Override
	protected void createParts()
	{
		amplitude = new Voltage(220.0);
		maxCurrent = new Current(20.0);
	}

  }
 * </pre>
 * 
 * Note that each value will be listed in the "Values" column of the line item
 * row. An exampe line item with the values column is as follows:
 * 
 * <pre>
 * {@code

 SysMLinJava Bill-of-Materials
 No.  Title          Qty  Values                    Description                  Source                Comment
   1  VoltageSource    1  amplitude (Volt) = 220.0  Voltage source/power supply  AcePowerSupplies.com  Must be compliant with IEEE
                          maxCurrent (Amps) = 20.0  for circuit                                        802.11g for network comms
 }</pre>
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface BOMLineItemValue
{

}
