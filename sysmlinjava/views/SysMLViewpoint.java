package sysmlinjava.views;

import java.util.ArrayList;
import java.util.List;
import sysmlinjava.annotations.views.Concern;
import sysmlinjava.annotations.views.Language;
import sysmlinjava.annotations.views.Method;
import sysmlinjava.annotations.views.Presentation;
import sysmlinjava.annotations.views.Purpose;
import sysmlinjava.annotations.views.Stakeholder;
import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava's representation of the SysML viewpoint. The
 * {@code SysMLViewpoint} specifies the standard SysML viewpoint attributes.
 * Instances of the {@code SysMLViewpoint} should be declared in an extension of
 * the {@code SysMLViews} class. See the {@code SysMLViews} class for more info
 * on declaring instances of this class.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLViewpoint extends SysMLClass
{
	/**
	 * List of the stakeholders for this viewpoint
	 */
	@Stakeholder
	public List<SysMLStakeholder> stakeholders;
	/**
	 * String representation of the purpose of the viewpoint
	 */
	@Purpose
	public String purpose;
	/**
	 * List of string representations of the languages used for this viewpoint
	 */
	@Language
	public List<String> language;
	/**
	 * List of string representations of the presentation of this viewpoint
	 */
	@Presentation
	public List<String> presentation;
	/**
	 * List of string representations of the concerns addressed by this viewpoint
	 */
	@Concern
	public List<String> concerns;
	/**
	 * List of string representations of the methods by which this viewpoint is
	 * created
	 */
	@Method
	public List<String> methods;

	/**
	 * Constructor with no attribute initializations
	 */
	public SysMLViewpoint()
	{
		super();
		this.stakeholders = new ArrayList<>();
		this.purpose = "TBD";
		this.language = new ArrayList<>();
		this.presentation = new ArrayList<>();

		this.concerns = new ArrayList<>();
		this.methods = new ArrayList<>();
	}

	/**
	 * Constructor with all attributes initialized
	 * 
	 * @param name         name of the viewpoint
	 * @param stakeholders stakeholders targetted by the viewpoint
	 * @param purpose      purpose(s) of the viewpoint
	 * @param concerns     concerns(s) addressed by the viewpoint
	 * @param language     language(s) used by the viewpoint
	 * @param presentation presentation(s) of the viewpoint
	 * @param methods      methods(s) of creation of the viewpoint
	 */
	public SysMLViewpoint(String name, List<SysMLStakeholder> stakeholders, String purpose, List<String> concerns, List<String> language, List<String> presentation, List<String> methods)
	{
		super(name);
		this.stakeholders = stakeholders;
		this.purpose = purpose;
		this.language = language;
		this.presentation = presentation;
		this.concerns = concerns;
		this.methods = methods;
	}
}
