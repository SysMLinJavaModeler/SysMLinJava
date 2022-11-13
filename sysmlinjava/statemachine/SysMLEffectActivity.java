package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLInterface;
import sysmlinjava.events.SysMLEvent;

/**
 * Functional interface specifying the activity performed as the effect of a
 * state transition. That is, the activity to be performed by the transition's
 * {@code SysMLEffect} element.<br>
 * The {@code SysMLEffectActivity} should be declared as a field in the extended
 * {@code SysMLStateMachine} class. The field should be annotated with the
 * {@code EffectActivity} annotation. It should then be implemented as an
 * instance of a Lambda function in the override of the
 * {@code SysMLStateMachine}'s {@code createStateEffectActivities()} operation
 * to provide the function with access to the state machine's properties. An
 * example follows.
 * 
 * <pre>
		:
	&#64;EffectActivity
	private SysMLEffectActivity onEthernetIPPacketEventEffectActivity;
	&#64;EffectActivity
	private SysMLEffectActivity onDataLinkFrameEventEffectActivity;
	&#64;EffectActivity
	private SysMLEffectActivity onGPSMessageEventEffectActivity;
		:
	&#64;Override
	protected void createEffectActivities()
	{
		super.createEffectActivities();
		onEthernetIPPacketEventEffectActivity = (event, contextBlock) ->
		{
			IPPacket ipPacket = ((IPPacketEvent)event.get()).getPacket();
			ModemRadio modemRadio = (ModemRadio)contextBlock.get();
			modemRadio.processIPPacketFromEthernet(ipPacket);
		};
		onDataLinkFrameEventEffectActivity = (event, contextBlock) ->
		{
			DataLinkFrame datalinkFrame = ((DataLinkFrameEvent)event.get()).getFrame();
			ModemRadio modemRadio = (ModemRadio)contextBlock.get();
			modemRadio.processIPPacketFromDataLink(datalinkFrame);
		};

		onGPSMessageEventEffectActivity = (event, contextBlock) ->
		{
			GPSMessage gpsMessage = ((GPSMessageEvent)event.get()).getMessage();
			ModemRadio modemRadio = (ModemRadio)contextBlock.get();
			modemRadio.processTDMASlotTimeFromGPS(gpsMessage);
		};
	}
		:
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLEffectActivity extends SysMLInterface
{
	/**
	 * Specification of the activity to be performed as the effect of an associated
	 * state transition, i.e. the activity to be performed by the transition's
	 * {@code SysMLEffect} element. This function must be realized by an instance of
	 * a lambda expression.
	 * 
	 * @param event        The SysMLEvent that invoked the state transition
	 * @param contextBlock The block in whose context the state transtion's
	 *                     associated state machine executes.
	 */
	void perform(Optional<? extends SysMLEvent> event, Optional<? extends SysMLBlock> contextBlock);
}