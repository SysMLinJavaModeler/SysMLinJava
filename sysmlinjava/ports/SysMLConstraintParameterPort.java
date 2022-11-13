package sysmlinjava.ports;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.ValueObserver;
import sysmlinjava.constraintblocks.SysMLConstraintBlock;
import sysmlinjava.valuetypes.SysMLValueType;

/**
 * SysMLinJava's representation of the SysML parameter port for constraint
 * parameters in SysML constraint blocks.
 * <h2>Ports for binding block values to constraint parameters</h2> The
 * {@code SysMLConstraintParameterPort} enables the "binding" of values in
 * blocks to constraint parameters in constraint blocks. The port essentially
 * just realizes the concept of SysML's "bound parameter" in the executable java
 * programming language. Whereas the bound parameter finds the constraint
 * parameter and the block value to which it is bound always being equivalent,
 * The {@code SysMLConstraintParameterPort} achieves this equivalence by
 * implementing the {@code ValueObserver} interface to be notified of a change
 * in the value of a connected {@code ObservableValue}. It adds itself to the
 * {@code ObservableValue}'s (bound parameter's) {@code ValueObserver}s list.
 * When the bound parameter's value is changed, the port is notified and it
 * retrieves the new value and adds it to a thread-safe queue in the port. The
 * new value is then retrieved from the queue by the constraint block that
 * contains the constraint parameter for the observed value via the
 * {@code getValue()} operation.
 * <h3>Bound values from multiple threads</h3>The
 * {@code SysMLConstraintParameterPort} provides for a thread-safe
 * implementation of the binding connector - a capability typically not
 * available in traditional diagram-based SysML modeling applications. It
 * performs this multiple thread connection by using a thread-safe queue for the
 * passing of updated values to the constraint block. Block values in a thread
 * invoke the port to queue their new values to this thread-safe queue and the
 * constraint block, likely in another thread, retreives the updated values from
 * this queue. In this way the {@code SysMLConstraintParameterPort} enables
 * parametric analysis of bound values located in virtually any thread
 * configuration of multi-threaded models.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLConstraintParameterPort extends SysMLClass implements ValueObserver
{
	/**
	 * Queue of changed parameter values. The queue is a thread-safe queue whereby a
	 * call of the {@code valueChanged()} operation by any thread causes a changed
	 * parameter value to be enqued, and a call of the {@code getValue()} operation
	 * by any Observer's thread causes the latest parameter value to be dequeud.
	 */
	public ArrayBlockingQueue<SysMLValueType> queuedParameterValues;
	/**
	 * The block of which the observed parameter value is a member, i.e. the context
	 * block accessed by the port to retrieve the changed value of the associated
	 * constraint parameter. This variable should be set by the connection function
	 * {@code SysMLBindingConnectorFunction} declared in the {@code SysMLBlock} that
	 * contains both this port's block, i.e. the {@code constraintBlock} and the
	 * {@code SysMLBlock} block that contains the parameter, i.e. the
	 * {@code parameterConstraintBlock}.
	 */
	public SysMLBlock parameterContextBlock;
	/**
	 * The constraint block in which this port resides.
	 */
	public SysMLConstraintBlock constraintBlock;
	/**
	 * The function that is performed upon notification that the observed value (the
	 * bound constraint parameter) has changed, i.e. an instance of a
	 * {@code SysMLConstraintParameterPortFunction}. The function typically will
	 * retrieve the new value and enqueue it to the {@code queuedParameterValues}
	 * queue and notify the {@code constraintBlock} of the value change.<br>
	 * Example:
	 * 
	 * <pre>
		alphaPortValueChangedFunction = (constraintParameterPort, contextBlock) ->
		{
		try
		{
			IInteger parameter = ((MyParameterBlock)contextBlock).paramValue;
			constraintParameterPort.queuedParameterValues.put(new IInteger(parameter.value));
			constraintParameterPort.constraintBlock.valueChanged();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		};
	 * </pre>
	 */
	public Optional<SysMLConstraintParameterPortFunction> onValueChangedFunction;

	/**
	 * Constructor (minimal) that specifies the constraint block in which this
	 * parameter port exists.
	 * 
	 * @param constraintBlock parent constraint block
	 */
	public SysMLConstraintParameterPort(SysMLConstraintBlock constraintBlock)
	{
		super();
		this.queuedParameterValues = new ArrayBlockingQueue<SysMLValueType>(10);
		this.constraintBlock = constraintBlock;
		this.onValueChangedFunction = Optional.empty();
	}

	/**
	 * Constructor that specifies the constraint block in which this parameter port
	 * exists and the functional interface that specifies the function to be
	 * performed upon value change.
	 * 
	 * @param constraintBlock      parent constraint block
	 * @param valueChangedFunction function to be performed upon value change. (see
	 *                             example for {@code onValueChangedFunction} field)
	 * @param name                 unique name of the port
	 */
	public SysMLConstraintParameterPort(SysMLConstraintBlock constraintBlock, SysMLConstraintParameterPortFunction valueChangedFunction, String name)
	{
		super(name);
		this.queuedParameterValues = new ArrayBlockingQueue<SysMLValueType>(10);
		this.constraintBlock = constraintBlock;
		this.onValueChangedFunction = Optional.of(valueChangedFunction);
	}

	/**
	 * Sets the context block in which the "bound" parameter for this port resides
	 * 
	 * @param parameterContextBlock context block for this port's bound parameter
	 */
	public void setParameterContextBlock(SysMLBlock parameterContextBlock)
	{
		this.parameterContextBlock = parameterContextBlock;
	}

	/**
	 * Updates the parameter value for the bound value by queuing the current bound
	 * value to the ports parameter values queue and then calling the constraint
	 * block's {@code valueChanged()} operation which submits a
	 * {@code SysMLParameterChangeEvent} to the constrain block's state machine.
	 * 
	 * @param value updated bound value for the parameter updated by this port
	 */
	public void updateParameterValue(SysMLValueType value)
	{
		try
		{
			queuedParameterValues.put(value);
			constraintBlock.valueChanged(name.isPresent() ? name.get() : "");
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void valueChanged()
	{
		if (onValueChangedFunction.isPresent())
			onValueChangedFunction.get().onValueChange(this, parameterContextBlock);
	}

	/**
	 * Retrieves the latest changed constraint parameter value from the port's
	 * parameter values queue. This operation is typically called by the parent
	 * constraint block after being notified of the value change by the
	 * {@code valueChanged()} operation.
	 * 
	 * @return an extended {@code SysMLValueType} object that represents the
	 *         latest/changed value of the constraint parameter represented by
	 *         ("bound" to) this port.
	 */
	public SysMLValueType getValue()
	{
		SysMLValueType result = null;
		if (!queuedParameterValues.isEmpty())
		{
			if (queuedParameterValues.size() > 2)
				logger.info("size > 2");
			while (queuedParameterValues.size() > 1)
			{
				try
				{
					result = queuedParameterValues.take();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			result = queuedParameterValues.peek();
		}
		else
			logger.warning("unexpected empty parameters queue");
		return result;
	}
}
