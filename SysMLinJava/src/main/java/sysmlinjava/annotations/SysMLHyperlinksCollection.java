/*
 * Copyright (C) 2026 SysMLinJava, LLC. This file is part of the SysMLinJava
 * framework. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ListIterator;
import java.util.StringJoiner;
import java.util.logging.Logger;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.annotations.Comment;
import sysmlinjava.javaannotations.annotations.Documentation;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.annotations.TextualRepresentation;
import sysmlinjava.javaannotations.dependencies.Refine;
import sysmlinjava.javaannotations.metadata.Causation;
import sysmlinjava.javaannotations.metadata.ElementFilter;
import sysmlinjava.javaannotations.metadata.Icon;
import sysmlinjava.javaannotations.metadata.Image;
import sysmlinjava.javaannotations.metadata.Issue;
import sysmlinjava.javaannotations.metadata.Rationale;
import sysmlinjava.javaannotations.metadata.Risk;
import sysmlinjava.javaannotations.metadata.StatusInfo;
import sysmlinjava.metadata.SysMLCausation;
import sysmlinjava.metadata.SysMLElementGroup;
import sysmlinjava.metadata.SysMLIcon;
import sysmlinjava.metadata.SysMLImage;
import sysmlinjava.metadata.SysMLIssue;
import sysmlinjava.metadata.SysMLRationale;
import sysmlinjava.metadata.SysMLRefinement;
import sysmlinjava.metadata.SysMLRisk;
import sysmlinjava.metadata.SysMLStatusInfo;

/**
 * SysMLinJava representation of a collection of SysML supporting information
 * links (hyperlinks). The collection is declared as public static (class-level)
 * instances of {@code SysMLHyperlink}s that are created/initialized in their
 * variable declarations. Optionally, the instances can be created/initialized
 * in overrides of the {@code createHyperlinks()} methods. The Hyperlinks are
 * optionally collected into a list of hyperlinks. The list is created in an
 * override of the {@code createHyperlinksList()} methods.
 * <p>
 * The type of the field variable must be {@code SysMLHyperlink}. The field
 * should include the &#64;{@code Hyperlink} annotation.
 * <p>
 * An example of how the hyperlink elements are declared and used in the
 * hyperlinks collection declaration is as follows.
 * 
 * <pre>{@code
	public class MyHyperlinksCollection extends SysMLHyperlinksCollection
	{
		&#64;Hyperlink
		public static SysMLHyperlink c2SubsystemSpec = new SysMLHyperlink("C2 Subsystem Hyperlinks Specification", "https://SpecServer.com/HyperlinkSpecs/C2 Subsystem Hyperlinks Specification.pdf");
		&#64;Hyperlink
		public static SysMLHyperlink deployedSubsystemSpec = new SysMLHyperlink("Deployed Subsystem Hyperlinks Specification", "https://SpecServer.com/HyperlinkSpecs/Deployed Subsystem Hyperlinks Specification.pdf");
			:
	}}</pre>
 * 
 * @author ModelerOne
 */
public abstract class SysMLHyperlinksCollection extends SysMLAnything
{
	/**
	 * Validates that inheriting hyperlinks collection declares only recognized
	 * types. If used, this method should be invoked in a {@code static} block of
	 * the inheriting class with the inheriting class as the argument. An example
	 * follows:
	 * 
	 * <pre>
		public class MyHyperlinksCollection extends SysMLHyperlinksCollection
		{
				:
				:
				
			static
			{
				validate(MyRequirementsCollection.class);
			}
		}
	 * </pre>
	 */
	public static void validate(Class<? extends SysMLHyperlinksCollection> subClass)
	{
		Logger logger = Logger.getLogger(SysMLHyperlinksCollection.class.getSimpleName());

		final List<String> recognizedAnnoNames = List.of(Hyperlink.class.getSimpleName(), Comment.class.getSimpleName(), Documentation.class.getSimpleName(), Issue.class.getSimpleName(), Risk.class.getSimpleName(), Image.class.getSimpleName(), Icon.class.getSimpleName(), StatusInfo.class.getSimpleName(), Causation.class.getSimpleName(), Refine.class.getSimpleName(), Rationale.class.getSimpleName(), ElementFilter.class.getSimpleName(), TextualRepresentation.class.getSimpleName());
		final List<String> recognizedTypeNames = List.of(SysMLHyperlink.class.getSimpleName(), SysMLComment.class.getSimpleName(), SysMLDocumentation.class.getSimpleName(), SysMLIssue.class.getSimpleName(), SysMLRisk.class.getSimpleName(), SysMLImage.class.getSimpleName(), SysMLIcon.class.getSimpleName(), SysMLStatusInfo.class.getSimpleName(), SysMLCausation.class.getSimpleName(), SysMLRefinement.class.getSimpleName(), SysMLRationale.class.getSimpleName(), SysMLElementGroup.class.getSimpleName(), SysMLTextualRepresentation.class.getSimpleName());

		ListIterator<Field> fields = List.of(subClass.getDeclaredFields()).listIterator();
		while (fields.hasNext())
		{
			Field nextType = fields.next();
			if (!recognizedTypeNames.contains(nextType.getType().getSimpleName()))
			{
				StringJoiner joiner = new StringJoiner(", ");
				recognizedTypeNames.forEach(name -> joiner.add(name));
				logger.warning("unrecognized type for hyperlinks collection: %s , i.e. not %s".formatted(nextType.getType().getSimpleName(), joiner.toString()));
			}
		}
		ListIterator<Annotation> annos = List.of(subClass.getAnnotations()).listIterator();
		while (annos.hasNext())
		{
			Annotation nextAnno = annos.next();
			if (!recognizedAnnoNames.contains(nextAnno.annotationType().getSimpleName()))
			{
				StringJoiner joiner = new StringJoiner(", @", "@", "");
				recognizedAnnoNames.forEach(name -> joiner.add(name));
				logger.warning("unrecognized annotation for hyperlinks collection: %s , i.e. not %s".formatted(nextAnno.annotationType().getSimpleName(), joiner.toString()));
			}
		}
	}
}
