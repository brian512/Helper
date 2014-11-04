package cn.edu.wit.withelper.util;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	private static final String HEX = "0123456789ABCDEF";
	private static final String SEED = "@wit.edu.cn#!*&";

	private static void appendHex(StringBuffer paramStringBuffer, byte paramByte) {
		paramStringBuffer.append(HEX.charAt(0xF & paramByte >> 4)).append(
				HEX.charAt(paramByte & 0xF));
	}

	/**
	 * 解密
	 * @param paramString
	 * @return
	 */
	public static String decrypt(String paramString) {
		
		String decryptResult = null;
		try {
			decryptResult = new String(decrypt(getRawKey(SEED.getBytes()),
					toByte(paramString)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return decryptResult;
	}

	
	/**
	 * 加密
	 * @param paramString
	 * @return
	 */
	public static String encrypt(String paramString) {
		
		String encryptResult = null;
		
		try {
			encryptResult = toHex(encrypt(getRawKey(SEED.getBytes()), paramString.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encryptResult;
	}

	private static byte[] decrypt(byte[] paramArrayOfByte1,
			byte[] paramArrayOfByte2) throws Exception {
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte1,
				"AES");
		Cipher localCipher = Cipher.getInstance("AES");
		localCipher.init(2, localSecretKeySpec);
		return localCipher.doFinal(paramArrayOfByte2);
	}

	private static byte[] encrypt(byte[] paramArrayOfByte1,
			byte[] paramArrayOfByte2) throws Exception {
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte1,
				"AES");
		Cipher localCipher = Cipher.getInstance("AES");
		localCipher.init(1, localSecretKeySpec);
		return localCipher.doFinal(paramArrayOfByte2);
	}

	public static String fromHex(String paramString) {
		return new String(toByte(paramString));
	}

	private static byte[] getRawKey(byte[] paramArrayOfByte) throws Exception {
		KeyGenerator localKeyGenerator = KeyGenerator.getInstance("AES");
		SecureRandom localSecureRandom = SecureRandom.getInstance("SHA1PRNG");
		localSecureRandom.setSeed(paramArrayOfByte);
		localKeyGenerator.init(128, localSecureRandom);
		return localKeyGenerator.generateKey().getEncoded();
	}

	public static byte[] toByte(String paramString) {
		int i = paramString.length() / 2;
		byte[] arrayOfByte = new byte[i];
		for (int j = 0;; ++j) {
			if (j >= i)
				return arrayOfByte;
			arrayOfByte[j] = Integer.valueOf(
					paramString.substring(j * 2, 2 + j * 2), 16).byteValue();
		}
	}

	public static String toHex(String paramString) {
		return toHex(paramString.getBytes());
	}

	public static String toHex(byte[] paramArrayOfByte) {
		if (paramArrayOfByte == null)
			return "";
		StringBuffer localStringBuffer = new StringBuffer(
				2 * paramArrayOfByte.length);
		for (int i = 0;; ++i) {
			if (i >= paramArrayOfByte.length)
				return localStringBuffer.toString();
			appendHex(localStringBuffer, paramArrayOfByte[i]);
		}
	}
}
