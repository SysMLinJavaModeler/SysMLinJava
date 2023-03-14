package sysmlinjava.views;

import sysmlinjava.common.SysMLClass;

/**
 * {@code SysMLViews} is SysMLinJava's abstract representation of a collection
 * of SysML's {@code View} elements. Whereas views in SysML are instances of the
 * SysML View, this {@code SysMLViews} class provides a base for a container of
 * View instances. Although typically not needed, the views in the container can
 * be referenced in blocks and other model elements of the SysMLinJava model by
 * simply referencing the class scope with the view name, e.g.
 * {@code aView = MyViews.ArchitectsView}.
 * <p>
 * Individual instances of {@code SysMLView} are self described by their
 * individual field values, i.e. name, viewpoint, and included views.
 * <p>
 * Each {@code SysMLView} instance in the extended {@code SysMLViews} class is a
 * {@code public static final} instance, i.e. the views declared in extensions
 * to this class must be used as is.
 * <p>
 * An example of how specializations of the {@code SysMLViews} should be
 * declared is as follows.
 * 
 * <pre>
    public class MyViews extends SysMLViews
    {
		&#64;Purpose public String purposeA;
		&#64;Purpose public String purposeB;
		&#64;Purpose public String purposeC;
		&#64;Concern public String concernA;
		&#64;Concern public String concernB;
		&#64;Concern public String concernC;
		&#64;Language public String languageA;
		&#64;Language public String languageB;
		&#64;Language public String languageC;
		&#64;Method public String methodA;
		&#64;Method public String methodB;
		&#64;Method public String methodC;
		&#64;Presentation public String presentationA;
		&#64;Presentation public String presentationB;
		&#64;Presentation public String presentationC;
		&#64;Stakeholder public SysMLStakeholder stakeholderA;
		&#64;Stakeholder public SysMLStakeholder stakeholderB;
		&#64;Stakeholder public SysMLStakeholder stakeholderC;
		&#64;Viewpoint public SysMLViewpoint viewpointA;
		&#64;Viewpoint public SysMLViewpoint viewpointB;
		&#64;Viewpoint public SysMLViewpoint viewpointC;
		&#64;View public SysMLView viewA;
		&#64;View public SysMLView viewB;
		&#64;View public SysMLView viewC;
    }
 * </pre>
 * <p>
 * As the example shows, the elements of the stakeholder and viewpoints are
 * declared as annotated fields of {@code String} types.  Their values are 
 * initialized in the applicable {@code create...()} operations.  The 
 * stakeholders and viewpoints themselves are declared as shown and initialized
 * with the appropriate elements in the {@code createStakeholders()} and
 * {@code createViewpoints()}, respectively.
 * <p>
 * Finally, the views are declared as shown and initialized in the 
 * {@code createViews()} method.  Initialization includes specification of the
 * viewpoints and included views that constitute the view.
 * <p>
 * It should be noted that while there are variations in the way the view
 * elements could be declared/coded that are different from the example,
 * SysMLinJava and SysMLinJava tools currently only supports this form.
 * Deviations from this format are likely to result in failures by these tools
 * to perform as desired.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLViews extends SysMLClass
{
	/**
	 * Constructor for unique name and ID
	 * @param name unique name
	 * @param id unique ID
	 */
	public SysMLViews(String name, Long id)
	{
		super(name, id);
		createPurposes();
		createConcerns();
		createLanguages();
		createMethods();
		createPresentations();
		createStakeholders();
		createViewpoints();
		createViews();
	}

	/**
	 * Creates the purposes.  An example of purpose declarations and initializations is as follows:
	 * <pre>
	 * {@code
		&#64;Purpose
		public String purposeA;
		&#64;Purpose
		public String purposeB;
		&#64;Purpose
		public String purposeC;

		protected void createPurposes()
		{
			purposeA = "This is purpose A";
			purposeB = "This is purpose B";
			purposeC = "This is purpose C";
		}
	 * }
	 * </pre>
	 */
	protected abstract void createPurposes();
	
	/**
	 * Creates the concerns.  An example of concern declarations and initializations is as follows:
	 * <pre>
	 * {@code
		&#64;Concern
		public String concernA;
		&#64;Concern
		public String concernB;
		&#64;Concern
		public String concernC;

		protected void createConcerns()
		{
			concernA = "This is concern A";
			concernB = "This is concern B";
			concernC = "This is concern C";
		}
	 * }
	 * </pre>
	 */
	protected abstract void createConcerns();
	
	/**
	 * Creates the languages.  An example of language declarations and initializations is as follows:
	 * <pre>
	 * {@code
		&#64;Language
		public String languageA;
		&#64;Language
		public String languageB;
		&#64;Language
		public String languageC;

		protected void createLanguages()
		{
			languageA = "This is language A";
			languageB = "This is language B";
			languageC = "This is language C";
		}
	 * }
	 * </pre>
	 */
	protected abstract void createLanguages();
	
	/**
	 * Creates the methods.  An example of method declarations and initializations is as follows:
	 * <pre>
	 * {@code
		&#64;Concern
		public String methodA;
		&#64;Concern
		public String methodB;
		&#64;Concern
		public String methodC;

		protected void createMethods()
		{
			methodA = "This is method A";
			methodB = "This is method B";
			methodC = "This is method C";
		}
	 * }
	 * </pre>
	 */
	protected abstract void createMethods();
	
	/**
	 * Creates the presentations.  An example of presentation declarations and initializations is as follows:
	 * <pre>
	 * {@code
		&#64;Presentation
		public String presentationA;
		&#64;Presentation
		public String presentationB;
		&#64;Presentation
		public String presentationC;

		protected void createPresentations()
		{
			presentationA = "This is presentation A";
			presentationB = "This is presentation B";
			presentationC = "This is presentation C";
		}
	 * }
	 * </pre>
	 */
	protected abstract void createPresentations();
	
	/**
	 * Creates the stakeholders.  An example of stakeholder declarations and initializations is as follows:
	 * <pre>
	 * {@code
		&#64;Stakeholder
		public SysMLStakeholder stakeholderA;
		&#64;Stakeholder
		public SysMLStakeholder stakeholderB;
		&#64;Stakeholder
		public SysMLStakeholder stakeholderC;
		
		protected void createStakeholders()
		{
			stakeholderA = new SysMLStakeholder("StakeholderA", List.of(concernA)),
			stakeholderB = new SysMLStakeholder("StakeholderB", List.of(concernB, concernA)),
			stakeholderC = new SysMLStakeholder("StakeholderC", List.of(concernA, concernC));
		}
	 * }
	 * </pre>
	 */
	protected abstract void createStakeholders();
	
	/**
	 * Creates the viewpoints.  An example of viewpoint declarations and initializations is as follows:
	 * <pre>
	 * {@code
		&#64;Viewpoint
		public SysMLViewpoint viewpointA;
		&#64;Viewpoint
		public SysMLViewpoint viewpointB;
		&#64;Viewpoint
		public SysMLViewpoint viewpointC;

		protected void createViewpoints()
		{
			viewpointA = new SysMLViewpoint("ViewpointA", List.of(stakeholderA), purposeA, List.of(concernA), List.of(languageA), List.of(presentationA), List.of(methodA));
			viewpointB = new SysMLViewpoint("ViewpointB", List.of(stakeholderB), purposeB, List.of(concernB), List.of(languageB), List.of(presentationB), List.of(methodB));
			viewpointC = new SysMLViewpoint("ViewpointC", List.of(stakeholderC), purposeC, List.of(concernC), List.of(languageC), List.of(presentationC), List.of(methodC));
		}
	 * }
	 * </pre>
	 */
	protected abstract void createViewpoints();
	
	/**
	 * Creates the views.  An example of view declarations and initializations is as follows:
	 * <pre>
	 * {@code
		&#64;View
		public SysMLView viewA;
		&#64;View
		public SysMLView viewB;
		&#64;View
		public SysMLView viewC;

		protected void createViews()
		{
			viewA = new SysMLView("ViewA", viewpointA, List.of()),
			viewB = new SysMLView("ViewB", viewpointB, List.of()),
			viewC = new SysMLView("ViewC", viewpointC, List.of(viewA, viewB)));
		}
	 * }
	 * </pre>
	 */
	protected abstract void createViews();

	/**
	 * Name of method to create purposes, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createPurposesMethodName = "createPurposes";
	/**
	 * Name of method to create concerns, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createConcernsMethodName = "createConcerns";
	/**
	 * Name of method to create languages, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createLanguagesMethodName = "createLanguages";
	/**
	 * Name of method to create methods, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createMethodsMethodName = "createMethods";
	/**
	 * Name of method to create presentations, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createPresentationsMethodName = "createPresentations";
	/**
	 * Name of method to create stakeholders, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createStakeholdersMethodName = "createStakeholders";
	/**
	 * Name of method to create viewpoints, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createViewpointsMethodName = "createViewpoints";
	/**
	 * Name of method to create views, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createViewsMethodName = "createViews";
}