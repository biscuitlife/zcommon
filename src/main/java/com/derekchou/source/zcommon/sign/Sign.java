package com.derekchou.source.zcommon.sign;

/**
 * get low 64 bits from 128 bits md5 and return a long
 *
 */
public class Sign {
	
	private static final String CHAR_ENCODING = "GB18030";

	public static long getSignEx(String word) {
		try {
			byte[] bytes = word.getBytes("GBK");
			if (bytes.length <= 8) {
				return bytes2Long(bytes);
			} else {
				return getSign64(bytes);
			}
		} catch (Exception e) { // impossible
			e.printStackTrace();
		}

		return getSign64(word);
	}
	
	public static long getSign64(byte[] input) {
		byte[] md5 = MD5.digest(input, 0, input.length);
		assert md5.length == 16;
		return bytes2Long(md5);
	}
	
	public static long getSign64(String text) {
		return getSign64(text, CHAR_ENCODING);
	}
	
	public static long getSign64(String text, String encoding) {
		byte[] bytes = null;
		try {
			bytes = text.getBytes(encoding);
		} catch (Exception e) {
			e.printStackTrace();
			bytes = text.getBytes();
		}
		return getSign64(bytes);
	}
	
	public static long bytes2Long(byte[] bytes) {
		long sign = 0;
		int count = bytes.length < 8 ? bytes.length : 8;
		for (int i = 0; i < count; i++) {
			sign |= (long)(bytes[i] & 0xFF) << (i*8);
		}
		return sign;
	}
	
	public static byte[] getSign(byte[] input) {
		byte[] md5 = MD5.digest(input, 0, input.length);
		return md5;
	}
	
	public static byte[] getSign(String text) {
		return getSign(text, CHAR_ENCODING);
	}
	
	public static byte[] getSign(String text, String encoding) {
		byte[] bytes = null;
		try {
			bytes = text.getBytes(encoding);
		} catch (Exception e) {
			e.printStackTrace();
			bytes = text.getBytes();
		}
		return getSign(bytes);
	}
}
