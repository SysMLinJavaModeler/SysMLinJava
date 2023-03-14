package sysmlinjava.valuetypes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a public encryption key
 * 
 * @author ModelerOne
 */
public class PublicKey extends SysMLValueType
{
	/**
	 * Attribute for the value of the key as a byte array
	 */
	@Attribute
	public byte[] value;

	/**
	 * Map of the string/character value to the byte value
	 */
	final Map<String, Byte> stringByteMap;
	/**
	 * Constant value of the hexadecimal characters
	 */
	static final char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	/**
	 * Constructor - initialize attributes
	 */
	public PublicKey()
	{
		super();
		value = new byte[0];
		stringByteMap = new HashMap<>();
		for (int i = 0; i < hexChars.length; i++)
			for (int j = 0; j < hexChars.length; j++)
			{
				char[] chars = {hexChars[i], hexChars[j]};
				byte rightNib = (byte)j;
				byte leftNib = (byte)i;
				byte byteValue = (byte)((leftNib << 4) | rightNib);
				stringByteMap.put(String.valueOf(chars), byteValue);
			}
	}

	/**
	 * Constructor
	 * 
	 * @param value byte array value to ve used as the key initial value
	 */
	public PublicKey(byte[] value)
	{
		this();
		this.value = value;
	}

	/**
	 * Returns new instance with value from the specified string version of the key
	 * 
	 * @param string string to be converted into the key
	 * @return new instance with value from the specified string version of the key
	 */
	public static PublicKey valueOf(String string)
	{
		PublicKey result = new PublicKey();
		if (!string.isBlank() && string.length() > 20)
			result.fromHexString(string);
		return result;
	}

	/**
	 * Returns whether the key value is present in this instance
	 * 
	 * @return whether the key value is present
	 */
	public boolean isPresent()
	{
		return value != null && value.length > 10;
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Bytes;
	}

	/**
	 * Returns a string representation of the key
	 * 
	 * @return string representation of the key
	 */
	public String toHexString()
	{
		StringBuilder bldr = new StringBuilder();
		for (int i = 0; i < value.length; i++)
		{
			int leftNib = value[i] >> 4 & 0x0F;
			int rightNib = value[i] & 0x0F;
			bldr.append(hexChars[leftNib]);
			bldr.append(hexChars[rightNib]);
		}
		return bldr.toString();
	}

	/**
	 * Sets this key from a conversion of the specified hex string value
	 * 
	 * @param hexString hex string representation of the key
	 */
	public void fromHexString(String hexString)
	{
		int numBytes = hexString.length() / 2;
		value = new byte[numBytes];
		for (int i = 0; i < numBytes; i++)
		{
			int j = i * 2;
			value[i] = stringByteMap.get(hexString.substring(j, j + 2));
		}
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("PublicKey [value=");
		builder.append(Arrays.toString(value));
		builder.append("]");
		return builder.toString();
	}
}
