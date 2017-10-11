package ds1;

import java.nio.ByteBuffer;

public final class Util {
	/*
	 * Converts a 8-byte byte array to long
	 */
	static long convertByteArrayToLong(byte[] arr) {
	    ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
	    b.put(arr);
	    b.flip();
	    return b.getLong();		
	}
	
	static byte[] convertLongToByteArray(long n) {
	    ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
	    b.putLong(n);
	    return b.array();
	}
}
