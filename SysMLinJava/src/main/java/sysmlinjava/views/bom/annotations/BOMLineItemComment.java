/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.views.bom.annotations;

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
