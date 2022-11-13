package sysmlinjava.valuetypes;

import java.net.InetAddress;
import java.net.UnknownHostException;
import sysmlinjava.annotations.Attribute;
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
public class InternetAddress extends SysMLValueType
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
