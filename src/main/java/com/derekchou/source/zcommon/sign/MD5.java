package com.derekchou.source.zcommon.sign;

class MD5 {
	static final int S11 = 7;
	static final int S12 = 12;
	static final int S13 = 17;
	static final int S14 = 22;
	static final int S21 = 5;
	static final int S22 = 9;
	static final int S23 = 14;
	static final int S24 = 20;
	static final int S31 = 4;
	static final int S32 = 11;
	static final int S33 = 16;
	static final int S34 = 23;
	static final int S41 = 6;
	static final int S42 = 10;
	static final int S43 = 15;
	static final int S44 = 21;
	
	static class Context {
		int[] state = new int[4];	//ABCD
		long count;			//number of bits
		byte[] buffer = new byte[64];	//input buffer
	}
	
	static byte[] PADDING = new byte[] {
		(byte)0x80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		  		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		  		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
		};
	
	/* F, G, H and I are basic MD5 functions. */
	static int F(int x, int y, int z) {
		return ((x & y) | ((~x) & z));
	}
	static int G(int x, int y, int z) {
		return ((x & z) | (y & (~z)));
	}
	static int H(int x, int y, int z) {
		return (x ^ y ^ z);
	}
	static int I(int x, int y, int z) {
		return (y ^ (x | (~z)));
	}
	
	/* ROTATE_LEFT rotates x left n bits. */
	static int ROTATE_LEFT(int x, int n) {
		return ((x << n) | (x >>> (32-n)));
	}
	
	/* FF, GG, HH, and II transformations for rounds 1, 2, 3, and 4.
	 * Rotation is separate from addition to prevent recomputation.
	 */
	static int FF(int a, int b, int c, int d, int x, int s, int ac) {
		a += F(b, c, d) + x + (ac);
		a = ROTATE_LEFT(a, s);
		a += b;
		return a;
	}

	static int GG(int a, int b, int c, int d, int x, int s, int ac) {
		a += G(b, c, d) + x + (ac);
		a = ROTATE_LEFT(a, s);
		a += b;
		return a;
	}

	static int HH(int a, int b, int c, int d, int x, int s, int ac) {
		a += H(b, c, d) + x + (ac);
		a = ROTATE_LEFT(a, s);
		a += b;
		return a;
	}

	static int II(int a, int b, int c, int d, int x, int s, int ac) {
		a += I(b, c, d) + x + (ac);
		a = ROTATE_LEFT(a, s);
		a += b;
		return a;
	}
	
	static int[] toInts(byte[] input, int start, int len) {
		int[] output = new int[len/4];
		for (int i = 0, j = start; j < len; i++, j += 4) {
			output[i] = (input[j] & 0xFF) | ((input[j+1] & 0xFF) << 8)
					| ((input[j+2] & 0xFF) << 16) | ((input[j+3] & 0xFF) << 24);
		}
		return output;
	}
	
	static byte[] encode(int[] input) {
		byte[] out = new byte[input.length * 4];
		for (int i = 0, j = 0; j < input.length; i += 4, j++) {
			out[i] = (byte) (input[j] & 0xff);
			out[i + 1] = (byte) ((input[j] >> 8) & 0xff);
			out[i + 2] = (byte) ((input[j] >> 16) & 0xff);
			out[i + 3] = (byte) ((input[j] >> 24) & 0xff);
		}
		return out;
	}
	
	static byte[] encode(long num) {
		byte[] out = new byte[8];
		for (int i = 0; i < out.length; i++) {
			out[i] = (byte)((num >> (i*8)) & 0xff);
		}
		return out;
	}
	
	static Context init() {
		Context ctx = new Context();
		ctx.state[0] = 0x67452301;
		ctx.state[1] = 0xefcdab89;
		ctx.state[2] = 0x98badcfe;
		ctx.state[3] = 0x10325476;
		return ctx;
	}
	
	static void transform(int state[], byte block[], int blockStart) {
		int a = state[0], b = state[1], c = state[2], d = state[3];
		int[] x = toInts(block, blockStart, 64);

		/* Round 1 */
		a = FF(a, b, c, d, x[ 0], S11, 0xd76aa478); /* 1 */
		d = FF(d, a, b, c, x[ 1], S12, 0xe8c7b756); /* 2 */
		c = FF(c, d, a, b, x[ 2], S13, 0x242070db); /* 3 */
		b = FF(b, c, d, a, x[ 3], S14, 0xc1bdceee); /* 4 */
		a = FF(a, b, c, d, x[ 4], S11, 0xf57c0faf); /* 5 */
		d = FF(d, a, b, c, x[ 5], S12, 0x4787c62a); /* 6 */
		c = FF(c, d, a, b, x[ 6], S13, 0xa8304613); /* 7 */
		b = FF(b, c, d, a, x[ 7], S14, 0xfd469501); /* 8 */
		a = FF(a, b, c, d, x[ 8], S11, 0x698098d8); /* 9 */
		d = FF(d, a, b, c, x[ 9], S12, 0x8b44f7af); /* 10 */
		c = FF(c, d, a, b, x[10], S13, 0xffff5bb1); /* 11 */
		b = FF(b, c, d, a, x[11], S14, 0x895cd7be); /* 12 */
		a = FF(a, b, c, d, x[12], S11, 0x6b901122); /* 13 */
		d = FF(d, a, b, c, x[13], S12, 0xfd987193); /* 14 */
		c = FF(c, d, a, b, x[14], S13, 0xa679438e); /* 15 */
		b = FF(b, c, d, a, x[15], S14, 0x49b40821); /* 16 */

		/* Round 2 */
		a = GG(a, b, c, d, x[ 1], S21, 0xf61e2562); /* 17 */
		d = GG(d, a, b, c, x[ 6], S22, 0xc040b340); /* 18 */
		c = GG(c, d, a, b, x[11], S23, 0x265e5a51); /* 19 */
		b = GG(b, c, d, a, x[ 0], S24, 0xe9b6c7aa); /* 20 */
		a = GG(a, b, c, d, x[ 5], S21, 0xd62f105d); /* 21 */
		d = GG(d, a, b, c, x[10], S22, 0x2441453); /* 22 */
		c = GG(c, d, a, b, x[15], S23, 0xd8a1e681); /* 23 */
		b = GG(b, c, d, a, x[ 4], S24, 0xe7d3fbc8); /* 24 */
		a = GG(a, b, c, d, x[ 9], S21, 0x21e1cde6); /* 25 */
		d = GG(d, a, b, c, x[14], S22, 0xc33707d6); /* 26 */
		c = GG(c, d, a, b, x[ 3], S23, 0xf4d50d87); /* 27 */
		b = GG(b, c, d, a, x[ 8], S24, 0x455a14ed); /* 28 */
		a = GG(a, b, c, d, x[13], S21, 0xa9e3e905); /* 29 */
		d = GG(d, a, b, c, x[ 2], S22, 0xfcefa3f8); /* 30 */
		c = GG(c, d, a, b, x[ 7], S23, 0x676f02d9); /* 31 */
		b = GG(b, c, d, a, x[12], S24, 0x8d2a4c8a); /* 32 */

		/* Round 3 */
		a = HH(a, b, c, d, x[ 5], S31, 0xfffa3942); /* 33 */
		d = HH(d, a, b, c, x[ 8], S32, 0x8771f681); /* 34 */
		c = HH(c, d, a, b, x[11], S33, 0x6d9d6122); /* 35 */
		b = HH(b, c, d, a, x[14], S34, 0xfde5380c); /* 36 */
		a = HH(a, b, c, d, x[ 1], S31, 0xa4beea44); /* 37 */
		d = HH(d, a, b, c, x[ 4], S32, 0x4bdecfa9); /* 38 */
		c = HH(c, d, a, b, x[ 7], S33, 0xf6bb4b60); /* 39 */
		b = HH(b, c, d, a, x[10], S34, 0xbebfbc70); /* 40 */
		a = HH(a, b, c, d, x[13], S31, 0x289b7ec6); /* 41 */
		d = HH(d, a, b, c, x[ 0], S32, 0xeaa127fa); /* 42 */
		c = HH(c, d, a, b, x[ 3], S33, 0xd4ef3085); /* 43 */
		b = HH(b, c, d, a, x[ 6], S34, 0x4881d05); /* 44 */
		a = HH(a, b, c, d, x[ 9], S31, 0xd9d4d039); /* 45 */
		d = HH(d, a, b, c, x[12], S32, 0xe6db99e5); /* 46 */
		c = HH(c, d, a, b, x[15], S33, 0x1fa27cf8); /* 47 */
		b = HH(b, c, d, a, x[ 2], S34, 0xc4ac5665); /* 48 */

		/* Round 4 */
		a = II(a, b, c, d, x[ 0], S41, 0xf4292244); /* 49 */
		d = II(d, a, b, c, x[ 7], S42, 0x432aff97); /* 50 */
		c = II(c, d, a, b, x[14], S43, 0xab9423a7); /* 51 */
		b = II(b, c, d, a, x[ 5], S44, 0xfc93a039); /* 52 */
		a = II(a, b, c, d, x[12], S41, 0x655b59c3); /* 53 */
		d = II(d, a, b, c, x[ 3], S42, 0x8f0ccc92); /* 54 */
		c = II(c, d, a, b, x[10], S43, 0xffeff47d); /* 55 */
		b = II(b, c, d, a, x[ 1], S44, 0x85845dd1); /* 56 */
		a = II(a, b, c, d, x[ 8], S41, 0x6fa87e4f); /* 57 */
		d = II(d, a, b, c, x[15], S42, 0xfe2ce6e0); /* 58 */
		c = II(c, d, a, b, x[ 6], S43, 0xa3014314); /* 59 */
		b = II(b, c, d, a, x[13], S44, 0x4e0811a1); /* 60 */
		a = II(a, b, c, d, x[ 4], S41, 0xf7537e82); /* 61 */
		d = II(d, a, b, c, x[11], S42, 0xbd3af235); /* 62 */
		c = II(c, d, a, b, x[ 2], S43, 0x2ad7d2bb); /* 63 */
		b = II(b, c, d, a, x[ 9], S44, 0xeb86d391); /* 64 */

		state[0] += a;
		state[1] += b;
		state[2] += c;
		state[3] += d;
	}
	
	static void update(Context ctx, byte[] input, int start, int len) {

		/* Compute number of bytes mod 64 */
		int index = (int) ((ctx.count >> 3) & 0x3F);
		int partLen = 64 - index;

		/* Update number of bits */
		ctx.count += (len << 3);

		int i = start;
		/* Transform as many times as possible. */
		if (len >= partLen) {
			System.arraycopy(input, i, ctx.buffer, index, partLen);
			transform(ctx.state, ctx.buffer, 0);

			for (i = partLen; i + 63 < len; i += 64) {
				transform(ctx.state, input, i);
			}
			index = 0;
		}

		/* Buffer remaining input */
		System.arraycopy(input, i, ctx.buffer, index, len - i);
	}
	
	static void end(Context ctx) {
		/* Save number of bits */
		byte[] bits = encode(ctx.count);	//8 bytes length

		/* Pad out to 56 mod 64. */
		int index = (int) ((ctx.count >> 3) & 0x3f);
		int padLen = (index < 56) ? (56 - index) : (120 - index);
		update(ctx, PADDING, 0, padLen);

		/* Append length (before padding) */
		update(ctx, bits, 0, bits.length);
	}
	
	public static byte[] digest(byte[] input, int start, int len) {
		Context ctx = init();
		update(ctx, input, start, len);
		end(ctx);

		/* Store state in digest */
		byte[] digest = encode(ctx.state);
		return digest;
	}
	
	public static byte[] digest(byte[] input) {
		return digest(input, 0, input.length);
	}
}
