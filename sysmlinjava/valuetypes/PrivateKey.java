package sysmlinjava.valuetypes;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a private encryption key and its associated public
 * key
 * 
 * @author ModelerOne
 */
public class PrivateKey extends SysMLValueType
{
	/**
	 * Attribute for the value of the key as a byte array
	 */
	@Attribute
	public byte[] value;

	/**
	 * Attribute for the public key that is associated with this private key
	 */
	@Attribute
	public PublicKey publicKey;

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
	public PrivateKey()
	{
		super();
		generate();
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
	 * Generates the private and public keys
	 */
	public void generate()
	{
		KeyPairGenerator keyPairGen;
		try
		{
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			value = keyPair.getPrivate().getEncoded();
			publicKey = new PublicKey(keyPair.getPublic().getEncoded());
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
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
}
