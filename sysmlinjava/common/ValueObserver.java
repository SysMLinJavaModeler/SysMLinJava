package sysmlinjava.common;

/**
 * The {@code ValueObserver} is a functional interface for a value observer,
 * i.e. an object that wants to be notified of a change in the value of
 * {@code ObservableValue} implementations. Similar to the deprecated
 * {@code Observer} in the Java API, the {@code ValueObserver} implementation
 * provides an implementation of a {@code valueChanged()} operation that is
 * called by the {@code ObservableValue} whose {@code addValueObserver()}
 * operation was previously invoked. All {@code SysMLValueTypes} are
 * {@code ObservableValue}s thereby enabling their use as ends of the
 * {@code SysMLBindingConnector} in constraint blocks.
 * 
 * @author ModelerOne
 * @see sysmlinjava.common.ObservableValue
 * @see sysmlinjava.connectors.SysMLBindingConnector
 */
@FunctionalInterface
public interface ValueObserver extends SysMLInterface
{
	/**
	 * Invoked by an {@code ObservableValue} to notify this {@code ValueObserver} of
	 * a change in the observed value.
	 */
	public void valueChanged();
}
