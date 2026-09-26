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
 * Indicates the annotated field declares a part that is to appear as a line
 * item in a bill-of-materials (BOM). The line item will be entitled by the type
 * name of the field variable. The multiplicity of the variable will be used to
 * specify the quantity of the line item in the BOM. The part class will be
 * used to identify the other information that will be used for the line item,
 * e.g. values for the line item, e.g. size, weigth, etc. and comments
 * containing defintion of other columns of information for the line item,
 * e.g.description, sourcing, version, etc. An example declaration is as
 * follows.
 * 
 * <pre>
 * {@code

	&#64;BOMLineItem
	&#64;Part
	VoltageSource voltageSource;

		:

	&#64;Override
	protected void createParts()
	{
		voltageSource = new VoltageSource("VoltageSource");
	}

  }
 * </pre>
 * 
 * @author ModelerOne
 *
 * @see BOMLineItemValue
 * @see BOMLineItemComment
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface BOMLineItem
{

}
