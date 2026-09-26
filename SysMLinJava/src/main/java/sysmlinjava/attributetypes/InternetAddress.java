/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.attributetypes;

import java.net.InetAddress;
import java.net.UnknownHostException;
import sysmlinjava.javaannotations.attributes.Operation;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for the internet address as an array of bytes. It
 * corresponds to the raw byte array representation of an IP address. Operations
 * are provided to translate this raw byte array into and from the Java standard
 * {@code InetAddress}.
 * 
 * @author ModelerOne
 *
 */
public class InternetAddress extends SysMLAttributeType
{
	/**
	 * Attribute for the byte array for the address
	 */
	@Attribute
	public byte[] value;

	/**
	 * Constructor
	 * 
	 * @param value array of bytes for the initial address
	 */
	public InternetAddress(byte[] value)
	{
		super();
		this.value = value;
	}

	/**
	 * Returns instance that is converted value of specified java.net.InetAddress to
	 * 
	 * @param javaInetAddress address to be converted
	 * @return instance of address converted
	 */
	public static InternetAddress of(InetAddress javaInetAddress)
	{
		return new InternetAddress(javaInetAddress.getAddress());
	}

	/**
	 * Returns instance of java.net.InetAddress converted from this address
	 * 
	 * @return java.net.InetAddress converted from this address
	 */
	public InetAddress toInetAddress()
	{
		InetAddress result = null;
		try
		{
			result = InetAddress.getByAddress(value);
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns address of the local host
	 * 
	 * @return address of the local host
	 */
	@Operation
	public static InternetAddress ofLocalHost()
	{
		InternetAddress result = null;
		try
		{
			result = InternetAddress.of(InetAddress.getLocalHost());
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns address of the specifed named host
	 * 
	 * @param hostName name of the specified host
	 * @return address of the specifed named host
	 */
	@Operation
	public static InternetAddress ofHostName(String hostName)
	{
		InternetAddress result = null;
		try
		{
			result = InternetAddress.of(InetAddress.getByName(hostName));
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void createUnits()
	{
		this.units = SysMLinJavaUnits.Bytes;
	}
}
