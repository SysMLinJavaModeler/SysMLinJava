package sysmlinjava.views;

import java.util.Optional;

import sysmlinjava.parts.SysMLPart;
import sysmlinjava.states.StateBehaviorContext;

/**
 * SysMLinJava representation of the abstract SysML "rendering". Renderings must
 * be declared as extensions of this {@code SysMLRendering} which override the
 * {@code create...} methods to create the rendering of the view as a "part".
 * 
 * @author ModelerOne
 */
public abstract class SysMLRendering extends SysMLPart
{
	/**
	 * Constructor, basic
	 */
	protected SysMLRendering()
	{
		super();
	}

	/**
	 * Constructor for behaviored (executable) rendering
	 * 
	 * @param context state behavior context in which the rendering is to be
	 *                executed
	 * @param name    unique name of the rendering
	 * @param id      unique identifier of the rendering
	 */
	protected SysMLRendering(Optional<StateBehaviorContext> context, String name, Long id)
	{
		super(context, name, id);
	}

	/**
	 * Constructor for named/id'ed rendering
	 * 
	 * @param name unique name of the rendering
	 * @param id   unique identifier of the rendering
	 */
	protected SysMLRendering(String name, Long id)
	{
		super(name, id);
	}

	/**
	 * Creates a list of the instances of the sub-renderings, if any, of this
	 * rendering. An example follows.
	 * 
	 * <pre>{@code
			public class MyViewRendering extends SysMLRendering
			{
					:
				SubRenderAlpha alphaRender;
				SubRenderBravo bravoRender;
				SubRenderCharlie charlieRender;
					:
			
				&#64;Override
				protected void createSubRenderings()
				{
					alphaRender = new SubRenderAlpha();
					bravoRender = new SubRenderBravo();
					charlieRender = new SubRenderCharlie();
				}
					:
		
				public static class SubRenderAlpha extends SysMLRendering
				{
					:
				}
				public static class SubRenderBravo extends SysMLRendering
				{
					:
				}
				public static class SubRenderCharlie extends SysMLRendering
				{
					:
				}
			}}}</pre>
	 */
	protected void createSubRenderings()
	{
	}

}
