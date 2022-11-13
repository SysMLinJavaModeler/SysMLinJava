package sysmlinjava.views;

import java.util.ArrayList;
import java.util.List;
import sysmlinjava.annotations.views.Stakeholder;
import sysmlinjava.annotations.views.View;
import sysmlinjava.annotations.views.Viewpoint;
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
        &#64;Purpose public static final String purposeA = "This is purpose A";
        &#64;Purpose public static final String purposeB = "This is purpose B";
        &#64;Purpose public static final String purposeC = "This is purpose C";
    
        &#64;Concern public static final String concernA = "This is concern A";
        &#64;Concern public static final String concernB = "This is concern B";
        &#64;Concern public static final String concernC = "This is concern C";
    
        &#64;Language public static final String languageA = "This is language A";
        &#64;Language public static final String languageB = "This is language B";
        &#64;Language public static final String languageC = "This is language C";
    
        &#64;Method public static final String methodA = "This is method A";
        &#64;Method public static final String methodB = "This is method B";
        &#64;Method public static final String methodC = "This is method C";
    
        &#64;Presentation public static final String presentationA = "This is presentation A";
        &#64;Presentation public static final String presentationB = "This is presentation B";
        &#64;Presentation public static final String presentationC = "This is presentation C";
    
        &#64;Stakeholder
        public static final SysMLStakeholder stakeholderA = new SysMLStakeholder("StakeholderA", List.of(concernA));
        &#64;Stakeholder
        public static final SysMLStakeholder stakeholderB = new SysMLStakeholder("StakeholderB", List.of(concernB));
        &#64;Stakeholder
        public static final SysMLStakeholder stakeholderC = new SysMLStakeholder("StakeholderC", List.of(concernB, concernC));
    
        &#64;Viewpoint
        public static final SysMLViewpoint viewpointA = new SysMLViewpoint("ViewpointA", List.of(stakeholderA), purposeA, List.of(concernA), List.of(languageA), List.of(presentationA), List.of(methodA));
        &#64;Viewpoint
        public static final SysMLViewpoint viewpointB = new SysMLViewpoint("ViewpointB", List.of(stakeholderB), purposeB, List.of(concernB), List.of(languageB), List.of(presentationB), List.of(methodB));
        &#64;Viewpoint
        public static final SysMLViewpoint viewpointC = new SysMLViewpoint("ViewpointC", List.of(stakeholderB, stakeholderC), purposeC, List.of(concernB, concernC), List.of(languageB, languageC), List.of(presentationB, presentationC), List.of(methodB, methodC));
    
        &#64;View
        public static final SysMLView viewA = new SysMLView("ViewA", viewpointA, List.of());
        &#64;View
        public static final SysMLView viewB = new SysMLView("ViewB", viewpointB, List.of());
        &#64;View
        public static final SysMLView viewC = new SysMLView("ViewC", viewpointC, List.of(viewA, viewB));
    }
 * </pre>
 * <p>
 * As the example shows, the elements of the stakeholder and viewpoints are
 * declared as annotated fields of {@code String} types. The stakeholders and
 * viewpoints are initialized with these elements as arguments to their
 * constructors. Finally, the views are constructed with their specified
 * viewpoints and included views, as shown. The extended {@code SysMLViews}
 * class then stands alone in the model as its prescribed set of views,
 * viewpoints, and stakeholders.
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
	 * List of all {@code SysMLStakeholder} instances. Extensions to this class should
	 * declare all {@code SysMLStakeholder} instances as static final fields in this
	 * class and then declare a static statement to add those instances to this
	 * list.
	 */
	@Stakeholder
	public static List<SysMLStakeholder> stakeholderInstances = new ArrayList<>();

	/**
	 * List of all {@code SysMLViewpoint} instances. Extensions to this class should
	 * declare all {@code SysMLViewpoint} instances as static final fields in this
	 * class and then declare a static statement to add those instances to this
	 * list.
	 */
	@Viewpoint
	public static List<SysMLViewpoint> viewpointInstances = new ArrayList<>();

	/**
	 * List of all {@code SysMLView} instances. Extensions to this class should
	 * declare all {@code SysMLView} instances as static final fields in this class
	 * and then declare a static statement to add those instances to this list.
	 */
	@View
	public static List<SysMLView> viewInstances = new ArrayList<>();

}