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
 * SysMLinJava representation of a set of SysML annotations that may be
 * referenced (reused) by multiple model elements. The set is declared as public
 * static instances of annotation types {@code SysMLComment},
 * {@code SysMLDocumentation}, {@code SysMLTextualRepresentation}, and
 * {@code SysMLHyperlink}. Other metadata types, e.g. {@code SysMLIssue},
 * {@code SysMLRationale}, {@code SysMLImage}, etc. may also be included. An
 * example of how annotations are declared as static instances of annotation
 * types is as follows.
 * 
 * <pre>
	{@code
	public class MyAnnotationsCollection extends SysMLAnnotationsCollection
	{
			:
		&#64;Comment
		public static final SysMLComment popularComment = new SysMLComment("This comment is referenced by many model elements");
		&#64;Comment
		public static final SysMLComment unpopularComment = new SysMLComment("This comment is referenced by few model elements");
		&#64;Documentation
		public static final SysMLDocumentation commonDocumentation = new SysMLDocumentation("Common description of something about some model elements");
		&#64;TextualRepresentation
		public static final SysMLTextualRepresentation javaLinear = new SysMLTextualRepresentation("Java", "float y, x, m; y = m * x + b");
		&#64;Hyperlink
		public static final SysMLHyperlink specLink = new SysMLHyperlink("System Specification", "https://AlphaCo.com/SpecServer/SystemSpec.pdf");
			:
	}}
 * </pre>
 * 
 * The {@code SysMLAnnotationsCollection} extended class may also include
 * instances of the other metadata classes ({@code SysMLIssue},
 * {@code SysMLRationale}, {@code SysMLIcon}, etc. These metadata are declared
 * and initialized in the same way the annotations are as shown in the example
 * above.
 * 
 * @author ModelerOne
 */
public abstract class SysMLAnnotationsCollection extends SysMLAnything
{
	/**
	 * Validates that inheriting annotations collection declares only recognized
	 * types. If used, this method should be invoked in a {@code static} block of
	 * the inheriting class with the inheriting class as the argument. An example
	 * follows:
	 * 
	 * <pre>
		public class MyAnnotationsCollection extends SysMLAnnotationsCollection
		{
				:
				:
				
			static
			{
				validate(MyAnnotationsCollection.class);
			}
		}
	 * </pre>
	 */
	public static void validate(Class<? extends SysMLAnnotationsCollection> subClass)
	{
		Logger logger = Logger.getLogger(SysMLAnnotationsCollection.class.getSimpleName());

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
				logger.warning("unrecognized type for annotations collection: %s , i.e. not %s".formatted(nextType.getType().getSimpleName(), joiner.toString()));
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
				logger.warning("unrecognized annotation for annotations collection: %s , i.e. not %s".formatted(nextAnno.annotationType().getSimpleName(), joiner.toString()));
			}
		}
	}
}
