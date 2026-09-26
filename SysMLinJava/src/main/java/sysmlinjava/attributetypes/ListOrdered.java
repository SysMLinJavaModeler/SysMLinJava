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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;

import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.javaannotations.attributes.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava representation of an ordered list as a SysML value type. It
 * uses/wraps the jave {@code ArrayList} the {@code SysMLAttributeType} to
 * provide a familiar list construct that can be used as an attribute of a model
 * element.
 * <p>
 * As an extension of the {@code SysMLAttributeType}, the {@code ListOrdered}
 * can be used for parameters in parametric analysis. It can also by used as
 * attributes of parts, ports, items, etc. SysMLinJava includes other attributes
 * types for collections such as the {@code KeyValueMap} and {@code QueueFIFO}.
 * <p>
 * Note that comments for most of the actions/operations on the list are not
 * provided as they are rdundant to those provied by the javadoc for the
 * standard {@code ArrayList}, See those standard javadocs for details.
 * 
 * @author ModelerOne
 * @param <T> type of object in the list
 */
@SuppressWarnings("javadoc")
public class ListOrdered<T> extends SysMLAttributeType
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

	/**
	 * Constructor for specified initial values
	 * 
	 * @param initials set of initial values
	 */
	@SafeVarargs
	public ListOrdered(T... initials)
	{
		super();
		list = new ArrayList<>(Arrays.asList(initials));
	}

	public void setValue(List<T> value)
	{
		list = new ArrayList<>(value);
		notifyAttributeObservers();
	}

	@Operation
	public int size()
	{
		return list.size();
	}

	@Operation
	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	@Operation
	public boolean contains(T t)
	{
		return list.contains(t);
	}

	@Operation
	public Iterator<T> iterator()
	{
		return list.iterator();
	}

	@Operation
	public boolean add(T t)
	{
		return list.add(t);
	}

	@Operation
	public boolean remove(T t)
	{
		return list.remove(t);
	}

	@Operation
	public boolean addAll(ListOrdered<? extends T> listOrdered)
	{
		return list.addAll(listOrdered.list);
	}

	@Operation
	public boolean addAll(int index, ListOrdered<? extends T> listOrdered)
	{
		return list.addAll(listOrdered.list);
	}

	@Operation
	public boolean removeAll(ListOrdered<? extends T> listOrdered)
	{
		return list.removeAll(listOrdered.list);
	}

	@Operation
	public boolean retainAll(ListOrdered<? extends T> listOrdered)
	{
		return list.retainAll(listOrdered.list);
	}

	@Operation
	public void clear()
	{
		list.clear();
	}

	@Operation
	public void forEach(Consumer<T> action)
	{
		Objects.requireNonNull(action);
		list.forEach(element -> action.accept(element));
	}

	@Operation
	public T get(int index)
	{
		return list.get(index);
	}

	@Operation
	public T set(int index, T element)
	{
		return list.set(index, element);
	}

	@Operation
	public void add(int index, T element)
	{
		list.add(index, element);
	}

	@Operation
	public T remove(int index)
	{
		return list.remove(index);
	}

	@Operation
	public int indexOf(T o)
	{
		return list.indexOf(o);
	}

	@Operation
	public int lastIndexOf(T o)
	{
		return list.lastIndexOf(o);
	}

	@Operation
	public ListIterator<T> listIterator()
	{
		return list.listIterator();
	}

	@Operation
	public ListIterator<T> listIterator(int index)
	{
		return list.listIterator(index);
	}

	@Operation
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

	@SuppressWarnings("unchecked")
	@Operation
	public T[] toArray()
	{
		return (T[]) list.toArray();
	}

	@Operation
	public <Q> Q[] toArray(Q[] a)
	{
		return list.toArray(a);
	}

	@Operation
	public boolean containsAll(ListOrdered<? extends T> listOrdered)
	{
		return list.containsAll(listOrdered.list);
	}

	/**
	 * Returns "shallow" copy of this ordered list, i.e. individual elements of this
	 * list's {@code list} are not copied
	 */
	@Override
	public SysMLAttributeType copy()
	{
		return new ListOrdered<>(list);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Object;
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
