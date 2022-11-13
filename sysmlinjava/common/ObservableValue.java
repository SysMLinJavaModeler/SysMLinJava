package sysmlinjava.common;

/**
 * Interface specification for an observable value, i.e. a valueType that is
 * observable by {@code ValueObserver} implementations. Similar to the familiar
 * Observer/Observable pattern, the {@code ObservableValue} implementation
 * maintains a list of {@code ValueObserver}s and notifies them when the
 * observed value changes. All {@code SysMLValueType}s implement this interface
 * making them observable for use as end values in "binding connectors" as well
 * as other change-notification applications.
 * 
 * @author ModelerOne
 * @see sysmlinjava.common.ValueObserver
 */
public interface ObservableValue extends SysMLInterface
{
	/**
	 * Adds the specified {@code ValueObserver} to list of observers of this value
	 * which are to be notified of a value change.
	 * 
	 * @param observer value observer to be added
	 */
	public void addValueChangeObserver(ValueObserver observer);

	/**
	 * Causes all {@code ValueObserver}s in the list of observers to be notified of
	 * the change in value, i.e. it invokes each of the {@code ValueObserver}s
	 * {@code valueChanged()} method.
	 */
	public void notifyValueChangeObservers();
}
