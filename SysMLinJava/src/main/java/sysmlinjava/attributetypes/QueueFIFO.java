/*
 * Copyright (C) 2026 SysMLinJava, LLC. This file is part of the SysMLinJava
 * framework. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.attributetypes;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;

import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.javaannotations.attributes.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava representation of a first-in-first-out queue as a SysML value
 * type. It uses/implements the jave {@code Deque} interface and extends the
 * {@code SysMLAttributeType} to provide a familiar queue construct that can be
 * used as an attribute in an item or part.
 * <p>
 * As an extension of the {@code SysMLAttributeType}, the {@code QueueFIFO} can
 * be used for constraint parameters in parametric analysis. It can alse by used
 * as attributes in items and parts. SysMLinJava includes other value types for
 * collections such as the {@code KeyValueMap} and {@code List}
 * 
 * @author ModelerOne
 * @param <T> type of T in the queue
 */
@SuppressWarnings("javadoc")
public class QueueFIFO<T> extends SysMLAttributeType
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

	@Operation
	public boolean isEmpty()
	{
		return que.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public T[] toArray()
	{
		return (T[]) que.toArray();
	}

	public <Q> Q[] toArray(Q[] a)
	{
		return que.toArray(a);
	}

	@Operation
	public boolean containsAll(Collection<?> c)
	{
		return que.containsAll(c);
	}

	@Operation
	public boolean removeAll(Collection<?> c)
	{
		return que.removeAll(c);
	}

	@Operation
	public boolean retainAll(Collection<?> c)
	{
		return que.retainAll(c);
	}

	@Operation
	public void clear()
	{
		que.clear();
	}

	@Operation
	public void addFirst(T e)
	{
		que.addFirst(e);
	}

	@Operation
	public void addLast(T e)
	{
		que.addLast(e);
	}

	@Operation
	public boolean offerFirst(T e)
	{
		return que.offerFirst(e);
	}

	@Operation
	public boolean offerLast(T e)
	{
		return que.offerLast(e);
	}

	@Operation
	public T removeFirst()
	{
		return que.removeFirst();
	}

	@Operation
	public T removeLast()
	{
		return que.removeLast();
	}

	@Operation
	public T pollFirst()
	{
		return que.pollFirst();
	}

	@Operation
	public T pollLast()
	{
		return que.pollLast();
	}

	@Operation
	public T getFirst()
	{
		return que.getFirst();
	}

	@Operation
	public T getLast()
	{
		return que.getLast();
	}

	@Operation
	public T peekFirst()
	{
		return que.peekFirst();
	}

	@Operation
	public T peekLast()
	{
		return que.peekLast();
	}

	@Operation
	public boolean removeFirstOccurrence(T o)
	{
		return que.removeFirstOccurrence(o);
	}

	@Operation
	public boolean removeLastOccurrence(T o)
	{
		return que.removeLastOccurrence(o);
	}

	@Operation
	public boolean add(T e)
	{
		return que.add(e);
	}

	@Operation
	public boolean offer(T e)
	{
		return que.offer(e);
	}

	@Operation
	public T remove()
	{
		return que.remove();
	}

	@Operation
	public T poll()
	{
		return que.poll();
	}

	@Operation
	public T element()
	{
		return que.element();
	}

	@Operation
	public T peek()
	{
		return que.peek();
	}

	@Operation
	public boolean addAll(Collection<? extends T> c)
	{
		return que.addAll(c);
	}

	@Operation
	public void push(T e)
	{
		que.push(e);
	}

	@Operation
	public T pop()
	{
		return que.pop();
	}

	@Operation
	public boolean remove(T o)
	{
		return que.remove(o);
	}

	@Operation
	public boolean contains(T o)
	{
		return que.contains(o);
	}

	@Operation
	public int size()
	{
		return que.size();
	}

	@Operation
	public Iterator<T> iterator()
	{
		return que.iterator();
	}

	@Operation
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
