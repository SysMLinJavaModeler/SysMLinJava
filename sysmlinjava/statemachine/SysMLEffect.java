package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.events.SysMLEvent;

/**
 * SysMLinJava's representation of the SysML effect that specifies the optional
 * behavior to be performed during transition between states or within a state.
 * The {@code SysMLEffect} is performed within the context of the state machine
 * in which it is declared, but has parameteric access to a specified
 * contextBlock, which is typically the block for which the state machine
 * operates.
 * <p>
 * Note the {@code SysMLEffect} cannot be extended and must be used as-is to
 * specify a SysML effect for a transition. An example follows.
 * 
 * <pre>
	&#64;Effect
	private SysMLEffect onEthernetIPPacketEffect;
	&#64;Effect
	private SysMLEffect onDataLinkFrameEffect;
	&#64;Effect
	private SysMLEffect onGPSMessageEffect;

		:
	&#64;Override	
	protected void createEffects()
	{
		super.createEffects();
		onGPSMessageEffect = new SysMLEffect(contextBlock, onGPSMessageEffectActivity, "onGPSMessage");
		onEthernetIPPacketEffect = new SysMLEffect(contextBlock, onEthernetIPPacketEffectActivity, "onEthernetIPPacket");
		onDataLinkFrameEffect = new SysMLEffect(contextBlock, onDataLinkFrameEffectActivity, "onDataLinkFrame");
	}
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 */
public final class SysMLEffect extends SysMLClass
{
	/**
	 * Optional contextBlock within whose context this effect is to be performed.
	 */
	public Optional<? extends SysMLBlock> contextBlock;
	/**
	 * Optional activity to be performed for this effect.
	 */
	public Optional<SysMLEffectActivity> activity;

	/**
	 * Constructor for specifying contextBlock, activity to be performed, and object
	 * name
	 * 
	 * @param contextBlock block in whose context this effect's activity is to be
	 *                     performed
	 * @param activity     activity (behavior) to be performed for this effect
	 * @param name         unique name
	 */
	public SysMLEffect(Optional<? extends SysMLBlock> contextBlock, SysMLEffectActivity activity, String name)
	{
		super();
		this.contextBlock = contextBlock;
		this.name = Optional.of(name);
		this.activity = Optional.of(activity);
	}

	/**
	 * Invokes performance of the activity specified for this effect, i.e. invokes
	 * the {@code perform()} operation of the functional interface
	 * {@code SysMLEffectActivity}
	 * 
	 * @param event        Event that invoked the transition with which this effect
	 *                     is associated
	 * @param contextBlock Block in whose context this effect's activity is to be
	 *                     performed
	 */
	public void perform(Optional<? extends SysMLEvent> event, Optional<? extends SysMLBlock> contextBlock)
	{
		if (activity.isPresent())
			activity.get().perform(event, contextBlock);
	}

	@Override
	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}
}
