package sysmlinjava.valuetypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava representation of an ordered list as a SysML value type. It
 * uses/implements the jave {@code List} interface and extends the
 * {@code SysMLValueType} to provide a familiar list construct that can be used
 * as a value in a block (extension of {@code SysMLBlock}) class.
 * <p>
 * As an extension of the {@code SysMLValueType}, the {@code ListOrdered} can be
 * used for constraint parameters in parametric analysis. It can alse by used as
 * values and flows in blocks. SysMLinJava includes other value types for
 * collections such as the {@code KeyValueMap} and {@code Queue}.
 * 
 * @author ModelerOne
 *
 * @param <T> type of object in the list
 */
public class ListOrdered<T> extends SysMLValueType implements List<T>
{
	/**
	 * List that is "wrapped" by this ordered list value type
	 */
	@Attribute
	public ArrayList<T> list;

	/**
	 * Constructor
	 */
	public ListOrdered()
	{
		super();
		list = new ArrayList<>();
	}

	/**
	 * Constructor for specified initial capacity
	 * 
	 * @param initialCapacity initial capacity (space for {@code T} instances) of
	 *                        the list
	 */
	public ListOrdered(int initialCapacity)
	{
		super();
		list = new ArrayList<>(initialCapacity);
	}

	/**
	 * Constructor for specified initial values
	 * 
	 * @param initial list of initial values
	 */
	public ListOrdered(List<T> initial)
	{
		super();
		list = new ArrayList<>(initial);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Object;
	}

	@Override
	public int size()
	{
		return list.size();
	}

	@Override
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{
		return list.contains(o);
	}

	@Override
	public Iterator<T> iterator()
	{
		return list.iterator();
	}

	@Override
	public boolean add(T e)
	{
		return list.add(e);
	}

	@Override
	public boolean remove(Object o)
	{
		return list.remove(o);
	}

	@Override
	public boolean addAll(Collection<? extends T> c)
	{
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c)
	{
		return list.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return list.retainAll(c);
	}

	@Override
	public void clear()
	{
		list.clear();
	}

	@Override
	public T get(int index)
	{
		return list.get(index);
	}

	@Override
	public T set(int index, T element)
	{
		return list.set(index, element);
	}

	@Override
	public void add(int index, T element)
	{
		list.add(index, element);
	}

	@Override
	public T remove(int index)
	{
		return list.remove(index);
	}

	@Override
	public int indexOf(Object o)
	{
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o)
	{
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator()
	{
		return list.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index)
	{
		return list.listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex)
	{
		return list.subList(fromIndex, toIndex);
	}

	/**
	 * Returns an instance of the list containing the specified elements in the
	 * order specified
	 * 
	 * @param <E>      type of the elements in the list
	 * @param elements set of elements to be in the list
	 * @return instance of the list containing the specified elements in the order
	 *         specified
	 */
	@SafeVarargs
	public static <E> ListOrdered<E> of(E... elements)
	{
		ListOrdered<E> result = new ListOrdered<>();
		for (E e : elements)
			result.list.add(e);
		return result;
	}

	/**
	 * Performs the given action for each element of the {@code list} until all
	 * elements have been processed or the action throws an exception. Actions are
	 * performed in the order of iteration, if that order is specified. Exceptions
	 * thrown by the action are relayed to the caller.
	 * <p>
	 * The behavior of this method is unspecified if the action performs
	 * side-effects that modify the underlying source of elements, unless an
	 * overriding class has specified a concurrent modification policy.
	 *
	 * @param action The action to be performed for each element
	 * @throws NullPointerException if the specified action is null
	 */
	public void forEach(Consumer<? super T> action)
	{
		if (action != null)
			for (T t : list)
				action.accept(t);
		else
			throw new NullPointerException();
	}

	@Override
	public Object[] toArray()
	{
		return list.toArray();
	}

	@Override
	public <Q> Q[] toArray(Q[] a)
	{
		return list.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return list.containsAll(c);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ListOrdered [list=");
		builder.append(list);
		builder.append("]");
		return builder.toString();
	}
}
