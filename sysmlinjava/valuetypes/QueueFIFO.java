package sysmlinjava.valuetypes;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava representation of a first-in-first-out queue as a SysML value
 * type. It uses/implements the jave {@code Deque} interface and extends the
 * {@code SysMLValueType} to provide a familiar queue construct that can be used
 * as a value in a block ({@code SysMLBlock}-extended) class.
 * <p>
 * As an extension of the {@code SysMLValueType}, the {@code QueueFIFO} can be
 * used for constraint parameters in parametric analysis. It can alse by used as
 * values in blocks. SysMLinJava includes other value types for collections such
 * as the {@code KeyValueMap} and {@code List}
 * 
 * @author ModelerOne
 *
 * @param <T> type of object in the queue
 */
public class QueueFIFO<T> extends SysMLValueType implements Deque<T>
{
	/**
	 * Queue that is "wrapped" by this queue value type
	 */
	@Attribute
	public ArrayDeque<T> que;

	/**
	 * Constructor
	 */
	public QueueFIFO()
	{
		super();
		que = new ArrayDeque<>();
	}

	/**
	 * Constructor for specified initial capacity
	 * 
	 * @param initialCapacity initial capacity (space for {@code T} instances) of
	 *                        the queue
	 */
	public QueueFIFO(int initialCapacity)
	{
		super();
		que = new ArrayDeque<>(initialCapacity);
	}

	@Override
	public boolean isEmpty()
	{
		return que.isEmpty();
	}

	@Override
	public Object[] toArray()
	{
		return que.toArray();
	}

	@Override
	public <Q> Q[] toArray(Q[] a)
	{
		return que.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return que.containsAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return que.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return que.retainAll(c);
	}

	@Override
	public void clear()
	{
		que.clear();
	}

	@Override
	public void addFirst(T e)
	{
		que.addFirst(e);
	}

	@Override
	public void addLast(T e)
	{
		que.addLast(e);
	}

	@Override
	public boolean offerFirst(T e)
	{
		return que.offerFirst(e);
	}

	@Override
	public boolean offerLast(T e)
	{
		return que.offerLast(e);
	}

	@Override
	public T removeFirst()
	{
		return que.removeFirst();
	}

	@Override
	public T removeLast()
	{
		return que.removeLast();
	}

	@Override
	public T pollFirst()
	{
		return que.pollFirst();
	}

	@Override
	public T pollLast()
	{
		return que.pollLast();
	}

	@Override
	public T getFirst()
	{
		return que.getFirst();
	}

	@Override
	public T getLast()
	{
		return que.getLast();
	}

	@Override
	public T peekFirst()
	{
		return que.peekFirst();
	}

	@Override
	public T peekLast()
	{
		return que.peekLast();
	}

	@Override
	public boolean removeFirstOccurrence(Object o)
	{
		return que.removeFirstOccurrence(o);
	}

	@Override
	public boolean removeLastOccurrence(Object o)
	{
		return que.removeLastOccurrence(o);
	}

	@Override
	public boolean add(T e)
	{
		return que.add(e);
	}

	@Override
	public boolean offer(T e)
	{
		return que.offer(e);
	}

	@Override
	public T remove()
	{
		return que.remove();
	}

	@Override
	public T poll()
	{
		return que.poll();
	}

	@Override
	public T element()
	{
		return que.element();
	}

	@Override
	public T peek()
	{
		return que.peek();
	}

	@Override
	public boolean addAll(Collection<? extends T> c)
	{
		return que.addAll(c);
	}

	@Override
	public void push(T e)
	{
		que.push(e);
	}

	@Override
	public T pop()
	{
		return que.pop();
	}

	@Override
	public boolean remove(Object o)
	{
		return que.remove(o);
	}

	@Override
	public boolean contains(Object o)
	{
		return que.contains(o);
	}

	@Override
	public int size()
	{
		return que.size();
	}

	@Override
	public Iterator<T> iterator()
	{
		return que.iterator();
	}

	@Override
	public Iterator<T> descendingIterator()
	{
		return que.descendingIterator();
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Object;
	}
}
