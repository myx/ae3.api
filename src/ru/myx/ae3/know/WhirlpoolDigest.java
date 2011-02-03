package ru.myx.ae3.know;

import java.security.MessageDigest;

/**
 * TODO: GPL CODE, REPLACE WITH FREE CODE ASAP!!!
 * 
 * @author myx
 * 
 */
public final class WhirlpoolDigest extends MessageDigest implements Cloneable {
	
	// inner block size in bytes
	private static final int	BLOCK_SIZE	= 64;
	
	// default number of rounds
	private static final int	R			= 10;
	
	private static final int[]	SBOX		= {
			0x18,
			0x23,
			0xc6,
			0xe8,
			0x87,
			0xb8,
			0x01,
			0x4f,
			0x36,
			0xa6,
			0xd2,
			0xf5,
			0x79,
			0x6f,
			0x91,
			0x52,
			0x60,
			0xbc,
			0x9b,
			0x8e,
			0xa3,
			0x0c,
			0x7b,
			0x35,
			0x1d,
			0xe0,
			0xd7,
			0xc2,
			0x2e,
			0x4b,
			0xfe,
			0x57,
			0x15,
			0x77,
			0x37,
			0xe5,
			0x9f,
			0xf0,
			0x4a,
			0xda,
			0x58,
			0xc9,
			0x29,
			0x0a,
			0xb1,
			0xa0,
			0x6b,
			0x85,
			0xbd,
			0x5d,
			0x10,
			0xf4,
			0xcb,
			0x3e,
			0x05,
			0x67,
			0xe4,
			0x27,
			0x41,
			0x8b,
			0xa7,
			0x7d,
			0x95,
			0xd8,
			0xfb,
			0xee,
			0x7c,
			0x66,
			0xdd,
			0x17,
			0x47,
			0x9e,
			0xca,
			0x2d,
			0xbf,
			0x07,
			0xad,
			0x5a,
			0x83,
			0x33,
			0x63,
			0x02,
			0xaa,
			0x71,
			0xc8,
			0x19,
			0x49,
			0xd9,
			0xf2,
			0xe3,
			0x5b,
			0x88,
			0x9a,
			0x26,
			0x32,
			0xb0,
			0xe9,
			0x0f,
			0xd5,
			0x80,
			0xbe,
			0xcd,
			0x34,
			0x48,
			0xff,
			0x7a,
			0x90,
			0x5f,
			0x20,
			0x68,
			0x1a,
			0xae,
			0xb4,
			0x54,
			0x93,
			0x22,
			0x64,
			0xf1,
			0x73,
			0x12,
			0x40,
			0x08,
			0xc3,
			0xec,
			0xdb,
			0xa1,
			0x8d,
			0x3d,
			0x97,
			0x00,
			0xcf,
			0x2b,
			0x76,
			0x82,
			0xd6,
			0x1b,
			0xb5,
			0xaf,
			0x6a,
			0x50,
			0x45,
			0xf3,
			0x30,
			0xef,
			0x3f,
			0x55,
			0xa2,
			0xea,
			0x65,
			0xba,
			0x2f,
			0xc0,
			0xde,
			0x1c,
			0xfd,
			0x4d,
			0x92,
			0x75,
			0x06,
			0x8a,
			0xb2,
			0xe6,
			0x0e,
			0x1f,
			0x62,
			0xd4,
			0xa8,
			0x96,
			0xf9,
			0xc5,
			0x25,
			0x59,
			0x84,
			0x72,
			0x39,
			0x4c,
			0x5e,
			0x78,
			0x38,
			0x8c,
			0xd1,
			0xa5,
			0xe2,
			0x61,
			0xb3,
			0x21,
			0x9c,
			0x1e,
			0x43,
			0xc7,
			0xfc,
			0x04,
			0x51,
			0x99,
			0x6d,
			0x0d,
			0xfa,
			0xdf,
			0x7e,
			0x24,
			0x3b,
			0xab,
			0xce,
			0x11,
			0x8f,
			0x4e,
			0xb7,
			0xeb,
			0x3c,
			0x81,
			0x94,
			0xf7,
			0xb9,
			0x13,
			0x2c,
			0xd3,
			0xe7,
			0x6e,
			0xc4,
			0x03,
			0x56,
			0x44,
			0x7f,
			0xa9,
			0x2a,
			0xbb,
			0xc1,
			0x53,
			0xdc,
			0x0b,
			0x9d,
			0x6c,
			0x31,
			0x74,
			0xf6,
			0x46,
			0xac,
			0x89,
			0x14,
			0xe1,
			0x16,
			0x3a,
			0x69,
			0x09,
			0x70,
			0xb6,
			0xd0,
			0xed,
			0xcc,
			0x42,
			0x98,
			0xa4,
			0x28,
			0x5c,
			0xf8,
			0x86							};
	
	private static final long[]	T0			= new long[256];
	
	private static final long[]	T1			= new long[256];
	
	private static final long[]	T2			= new long[256];
	
	private static final long[]	T3			= new long[256];
	
	private static final long[]	T4			= new long[256];
	
	private static final long[]	T5			= new long[256];
	
	private static final long[]	T6			= new long[256];
	
	private static final long[]	T7			= new long[256];
	
	private static final long[]	rc			= new long[WhirlpoolDigest.R];
	
	static {
		// long time = System.currentTimeMillis();
		
		// 2^8 + 2^4 + 2^3 + 2 + 1;
		final int ROOT = 0x11d; // paragraph 2.1 [WHIRLPOOL]
		int i, r, j;
		
		// !!! s3 UNUSED - kinda error maybe???????
		long s, s2, s4, s5, s8, s9, t;
		char c;
		final byte[] S = new byte[256];
		for (i = 0; i < 256; i++) {
			c = (char) WhirlpoolDigest.SBOX[i];
			
			s = c;
			s2 = s << 1;
			if (s2 > 0xFFL) {
				s2 ^= ROOT;
			}
			s4 = s2 << 1;
			if (s4 > 0xFFL) {
				s4 ^= ROOT;
			}
			s5 = s4 ^ s;
			s8 = s4 << 1;
			if (s8 > 0xFFL) {
				s8 ^= ROOT;
			}
			s9 = s8 ^ s;
			
			S[i] = (byte) s;
			WhirlpoolDigest.T0[i] = t = s << 56 | s << 48 | s4 << 40 | s << 32 | s8 << 24 | s5 << 16 | s2 << 8 | s9;
			WhirlpoolDigest.T1[i] = t >>> 8 | t << 56;
			WhirlpoolDigest.T2[i] = t >>> 16 | t << 48;
			WhirlpoolDigest.T3[i] = t >>> 24 | t << 40;
			WhirlpoolDigest.T4[i] = t >>> 32 | t << 32;
			WhirlpoolDigest.T5[i] = t >>> 40 | t << 24;
			WhirlpoolDigest.T6[i] = t >>> 48 | t << 16;
			WhirlpoolDigest.T7[i] = t >>> 56 | t << 8;
		}
		
		for (r = 1, i = 0, j = 0; r < WhirlpoolDigest.R + 1; r++) {
			WhirlpoolDigest.rc[i++] = (S[j++] & 0xFFL) << 56
					| (S[j++] & 0xFFL) << 48
					| (S[j++] & 0xFFL) << 40
					| (S[j++] & 0xFFL) << 32
					| (S[j++] & 0xFFL) << 24
					| (S[j++] & 0xFFL) << 16
					| (S[j++] & 0xFFL) << 8
					| S[j++]
					& 0xFFL;
		}
	}
	
	/** Number of bytes processed so far. */
	private long				count;
	
	/** Temporary input buffer. */
	private final byte[]		buffer;
	
	/** The 512-bit context as 8 longs. */
	private long				H0, H1, H2, H3, H4, H5, H6, H7;
	
	/** Work area for computing the round key schedule. */
	private long				k00, k01, k02, k03, k04, k05, k06, k07;
	
	private long				Kr0, Kr1, Kr2, Kr3, Kr4, Kr5, Kr6, Kr7;
	
	/** work area for transforming the 512-bit buffer. */
	private long				n0, n1, n2, n3, n4, n5, n6, n7;
	
	private long				nn0, nn1, nn2, nn3, nn4, nn5, nn6, nn7;
	
	// Static code - to initialize lookup tables
	// --------------------------------
	
	/** work area for holding block cipher's intermediate values. */
	private long				w0, w1, w2, w3, w4, w5, w6, w7;
	
	// Constructor(s)
	// -------------------------------------------------------------------------
	
	/** Trivial 0-arguments constructor. */
	public WhirlpoolDigest() {
		super( "WHIRLPOOL" );
		this.buffer = new byte[WhirlpoolDigest.BLOCK_SIZE];
	}
	
	/**
	 * <p>
	 * Private constructor for cloning purposes.
	 * </p>
	 * 
	 * @param md
	 *            the instance to clone.
	 */
	private WhirlpoolDigest(final WhirlpoolDigest md) {
		super( "WHIRLPOOL" );
		this.H0 = md.H0;
		this.H1 = md.H1;
		this.H2 = md.H2;
		this.H3 = md.H3;
		this.H4 = md.H4;
		this.H5 = md.H5;
		this.H6 = md.H6;
		this.H7 = md.H7;
		this.count = md.count;
		this.buffer = md.buffer.clone();
	}
	
	// Class methods
	// -------------------------------------------------------------------------
	
	// Instance methods
	// -------------------------------------------------------------------------
	
	// java.lang.Cloneable interface implementation ----------------------------
	
	@Override
	public MessageDigest clone() {
		return new WhirlpoolDigest( this );
	}
	
	// Implementation of concrete methods in BaseHash --------------------------
	
	@Override
	protected byte[] engineDigest() {
		final byte[] tail = this.padBuffer(); // pad remaining bytes in buffer
		this.update( tail, 0, tail.length ); // last transform of a message
		final byte[] result = this.getResult(); // make a result out of context
		
		this.reset(); // reset this instance for future re-use
		
		return result;
	}
	
	@Override
	protected void engineReset() {
		this.H0 = this.H1 = this.H2 = this.H3 = this.H4 = this.H5 = this.H6 = this.H7 = 0L;
	}
	
	@Override
	protected void engineUpdate(final byte b) {
		final int i = (int) (this.count % WhirlpoolDigest.BLOCK_SIZE);
		this.count++;
		this.buffer[i] = b;
		if (i == WhirlpoolDigest.BLOCK_SIZE - 1) {
			this.transform( this.buffer, 0 );
		}
	}
	
	@Override
	protected void engineUpdate(final byte[] b, final int offset, final int len) {
		int n = (int) (this.count % WhirlpoolDigest.BLOCK_SIZE);
		this.count += len;
		final int partLen = WhirlpoolDigest.BLOCK_SIZE - n;
		int i = 0;
		
		if (len >= partLen) {
			System.arraycopy( b, offset, this.buffer, n, partLen );
			this.transform( this.buffer, 0 );
			for (i = partLen; i + WhirlpoolDigest.BLOCK_SIZE - 1 < len; i += WhirlpoolDigest.BLOCK_SIZE) {
				this.transform( b, offset + i );
			}
			n = 0;
		}
		
		if (i < len) {
			System.arraycopy( b, offset + i, this.buffer, n, len - i );
		}
	}
	
	private byte[] getResult() {
		// apply inverse mu to the context
		final long H0 = this.H0;
		final long H1 = this.H1;
		final long H2 = this.H2;
		final long H3 = this.H3;
		final long H4 = this.H4;
		final long H5 = this.H5;
		final long H6 = this.H6;
		final long H7 = this.H7;
		final byte[] result = new byte[] {
				(byte) (H0 >>> 56),
				(byte) (H0 >>> 48),
				(byte) (H0 >>> 40),
				(byte) (H0 >>> 32),
				(byte) (H0 >>> 24),
				(byte) (H0 >>> 16),
				(byte) (H0 >>> 8),
				(byte) H0,
				(byte) (H1 >>> 56),
				(byte) (H1 >>> 48),
				(byte) (H1 >>> 40),
				(byte) (H1 >>> 32),
				(byte) (H1 >>> 24),
				(byte) (H1 >>> 16),
				(byte) (H1 >>> 8),
				(byte) H1,
				(byte) (H2 >>> 56),
				(byte) (H2 >>> 48),
				(byte) (H2 >>> 40),
				(byte) (H2 >>> 32),
				(byte) (H2 >>> 24),
				(byte) (H2 >>> 16),
				(byte) (H2 >>> 8),
				(byte) H2,
				(byte) (H3 >>> 56),
				(byte) (H3 >>> 48),
				(byte) (H3 >>> 40),
				(byte) (H3 >>> 32),
				(byte) (H3 >>> 24),
				(byte) (H3 >>> 16),
				(byte) (H3 >>> 8),
				(byte) H3,
				(byte) (H4 >>> 56),
				(byte) (H4 >>> 48),
				(byte) (H4 >>> 40),
				(byte) (H4 >>> 32),
				(byte) (H4 >>> 24),
				(byte) (H4 >>> 16),
				(byte) (H4 >>> 8),
				(byte) H4,
				(byte) (H5 >>> 56),
				(byte) (H5 >>> 48),
				(byte) (H5 >>> 40),
				(byte) (H5 >>> 32),
				(byte) (H5 >>> 24),
				(byte) (H5 >>> 16),
				(byte) (H5 >>> 8),
				(byte) H5,
				(byte) (H6 >>> 56),
				(byte) (H6 >>> 48),
				(byte) (H6 >>> 40),
				(byte) (H6 >>> 32),
				(byte) (H6 >>> 24),
				(byte) (H6 >>> 16),
				(byte) (H6 >>> 8),
				(byte) H6,
				(byte) (H7 >>> 56),
				(byte) (H7 >>> 48),
				(byte) (H7 >>> 40),
				(byte) (H7 >>> 32),
				(byte) (H7 >>> 24),
				(byte) (H7 >>> 16),
				(byte) (H7 >>> 8),
				(byte) H7 };
		
		return result;
	}
	
	final void guidSet48(final byte[] bytes) {
		final byte[] tail = this.padBuffer(); // pad remaining bytes in buffer
		this.update( tail, 0, tail.length ); // last transform of a message
		{
			final long H0 = this.H0;
			bytes[1] = (byte) (H0 >>> 56);
			bytes[2] = (byte) (H0 >>> 48);
			bytes[3] = (byte) (H0 >>> 40);
			bytes[4] = (byte) (H0 >>> 32);
			bytes[5] = (byte) (H0 >>> 24);
			bytes[6] = (byte) (H0 >>> 16);
			bytes[7] = (byte) (H0 >>> 8);
			bytes[8] = (byte) H0;
		}
		{
			final long H1 = this.H1;
			bytes[9] = (byte) (H1 >>> 56);
			bytes[10] = (byte) (H1 >>> 48);
			bytes[11] = (byte) (H1 >>> 40);
			bytes[12] = (byte) (H1 >>> 32);
			bytes[13] = (byte) (H1 >>> 24);
			bytes[14] = (byte) (H1 >>> 16);
			bytes[15] = (byte) (H1 >>> 8);
			bytes[16] = (byte) H1;
		}
		{
			final long H2 = this.H2;
			bytes[17] = (byte) (H2 >>> 56);
			bytes[18] = (byte) (H2 >>> 48);
			bytes[19] = (byte) (H2 >>> 40);
			bytes[20] = (byte) (H2 >>> 32);
			bytes[21] = (byte) (H2 >>> 24);
			bytes[22] = (byte) (H2 >>> 16);
			bytes[23] = (byte) (H2 >>> 8);
			bytes[24] = (byte) H2;
		}
		{
			final long H3 = this.H3;
			bytes[25] = (byte) (H3 >>> 56);
			bytes[26] = (byte) (H3 >>> 48);
			bytes[27] = (byte) (H3 >>> 40);
			bytes[28] = (byte) (H3 >>> 32);
			bytes[29] = (byte) (H3 >>> 24);
			bytes[30] = (byte) (H3 >>> 16);
			bytes[31] = (byte) (H3 >>> 8);
			bytes[32] = (byte) H3;
		}
		{
			final long H4 = this.H4;
			bytes[33] = (byte) (H4 >>> 56);
			bytes[34] = (byte) (H4 >>> 48);
			bytes[35] = (byte) (H4 >>> 40);
			bytes[36] = (byte) (H4 >>> 32);
			bytes[37] = (byte) (H4 >>> 24);
			bytes[38] = (byte) (H4 >>> 16);
			bytes[39] = (byte) (H4 >>> 8);
			bytes[40] = (byte) H4;
		}
		{
			final long H5 = this.H5;
			bytes[41] = (byte) (H5 >>> 56);
			bytes[42] = (byte) (H5 >>> 48);
			bytes[43] = (byte) (H5 >>> 40);
			bytes[44] = (byte) (H5 >>> 32);
			bytes[45] = (byte) (H5 >>> 24);
			bytes[46] = (byte) (H5 >>> 16);
			bytes[47] = (byte) (H5 >>> 8);
			bytes[48] = (byte) H5;
		}
	}
	
	final void guidSet64(final byte[] bytes) {
		final byte[] tail = this.padBuffer(); // pad remaining bytes in buffer
		this.update( tail, 0, tail.length ); // last transform of a message
		{
			final long H0 = this.H0;
			bytes[1] = (byte) (H0 >>> 56);
			bytes[2] = (byte) (H0 >>> 48);
			bytes[3] = (byte) (H0 >>> 40);
			bytes[4] = (byte) (H0 >>> 32);
			bytes[5] = (byte) (H0 >>> 24);
			bytes[6] = (byte) (H0 >>> 16);
			bytes[7] = (byte) (H0 >>> 8);
			bytes[8] = (byte) H0;
		}
		{
			final long H1 = this.H1;
			bytes[9] = (byte) (H1 >>> 56);
			bytes[10] = (byte) (H1 >>> 48);
			bytes[11] = (byte) (H1 >>> 40);
			bytes[12] = (byte) (H1 >>> 32);
			bytes[13] = (byte) (H1 >>> 24);
			bytes[14] = (byte) (H1 >>> 16);
			bytes[15] = (byte) (H1 >>> 8);
			bytes[16] = (byte) H1;
		}
		{
			final long H2 = this.H2;
			bytes[17] = (byte) (H2 >>> 56);
			bytes[18] = (byte) (H2 >>> 48);
			bytes[19] = (byte) (H2 >>> 40);
			bytes[20] = (byte) (H2 >>> 32);
			bytes[21] = (byte) (H2 >>> 24);
			bytes[22] = (byte) (H2 >>> 16);
			bytes[23] = (byte) (H2 >>> 8);
			bytes[24] = (byte) H2;
		}
		{
			final long H3 = this.H3;
			bytes[25] = (byte) (H3 >>> 56);
			bytes[26] = (byte) (H3 >>> 48);
			bytes[27] = (byte) (H3 >>> 40);
			bytes[28] = (byte) (H3 >>> 32);
			bytes[29] = (byte) (H3 >>> 24);
			bytes[30] = (byte) (H3 >>> 16);
			bytes[31] = (byte) (H3 >>> 8);
			bytes[32] = (byte) H3;
		}
		{
			final long H4 = this.H4;
			bytes[33] = (byte) (H4 >>> 56);
			bytes[34] = (byte) (H4 >>> 48);
			bytes[35] = (byte) (H4 >>> 40);
			bytes[36] = (byte) (H4 >>> 32);
			bytes[37] = (byte) (H4 >>> 24);
			bytes[38] = (byte) (H4 >>> 16);
			bytes[39] = (byte) (H4 >>> 8);
			bytes[40] = (byte) H4;
		}
		{
			final long H5 = this.H5;
			bytes[41] = (byte) (H5 >>> 56);
			bytes[42] = (byte) (H5 >>> 48);
			bytes[43] = (byte) (H5 >>> 40);
			bytes[44] = (byte) (H5 >>> 32);
			bytes[45] = (byte) (H5 >>> 24);
			bytes[46] = (byte) (H5 >>> 16);
			bytes[47] = (byte) (H5 >>> 8);
			bytes[48] = (byte) H5;
		}
		{
			final long H6 = this.H6;
			bytes[49] = (byte) (H6 >>> 56);
			bytes[50] = (byte) (H6 >>> 48);
			bytes[51] = (byte) (H6 >>> 40);
			bytes[52] = (byte) (H6 >>> 32);
			bytes[53] = (byte) (H6 >>> 24);
			bytes[54] = (byte) (H6 >>> 16);
			bytes[55] = (byte) (H6 >>> 8);
			bytes[56] = (byte) H6;
		}
		{
			final long H7 = this.H7;
			bytes[57] = (byte) (H7 >>> 56);
			bytes[58] = (byte) (H7 >>> 48);
			bytes[59] = (byte) (H7 >>> 40);
			bytes[60] = (byte) (H7 >>> 32);
			bytes[61] = (byte) (H7 >>> 24);
			bytes[62] = (byte) (H7 >>> 16);
			bytes[63] = (byte) (H7 >>> 8);
			bytes[64] = (byte) H7;
		}
	}
	
	private byte[] padBuffer() {
		// [WHIRLPOOL] p. 6:
		// "...padded with a 1-bit, then with as few 0-bits as necessary to
		// obtain a bit string whose length is an odd multiple of 256, and
		// finally with the 256-bit right-justified binary representation of L."
		// in this implementation we use 'count' as the number of bytes hashed
		// so far. hence the minimal number of bytes added to the message proper
		// are 33 (1 for the 1-bit followed by the 0-bits and the encoding of
		// the count framed in a 256-bit block). our formula is then:
		// count + 33 + padding = 0 (mod BLOCK_SIZE)
		final int n = (int) ((this.count + 33) % WhirlpoolDigest.BLOCK_SIZE);
		final int padding = n == 0
				? 33
				: WhirlpoolDigest.BLOCK_SIZE - n + 33;
		
		final byte[] result = new byte[padding];
		
		// padding is always binary 1 followed by binary 0s
		result[0] = (byte) 0x80;
		
		// save (right justified) the number of bits hashed
		final long bits = this.count * 8;
		int i = padding - 8;
		result[i++] = (byte) (bits >>> 56);
		result[i++] = (byte) (bits >>> 48);
		result[i++] = (byte) (bits >>> 40);
		result[i++] = (byte) (bits >>> 32);
		result[i++] = (byte) (bits >>> 24);
		result[i++] = (byte) (bits >>> 16);
		result[i++] = (byte) (bits >>> 8);
		result[i] = (byte) bits;
		
		return result;
	}
	
	private void transform(final byte[] in, int offset) {
		// apply mu to the input
		this.n0 = (in[offset++] & 0xFFL) << 56
				| (in[offset++] & 0xFFL) << 48
				| (in[offset++] & 0xFFL) << 40
				| (in[offset++] & 0xFFL) << 32
				| (in[offset++] & 0xFFL) << 24
				| (in[offset++] & 0xFFL) << 16
				| (in[offset++] & 0xFFL) << 8
				| in[offset++]
				& 0xFFL;
		this.n1 = (in[offset++] & 0xFFL) << 56
				| (in[offset++] & 0xFFL) << 48
				| (in[offset++] & 0xFFL) << 40
				| (in[offset++] & 0xFFL) << 32
				| (in[offset++] & 0xFFL) << 24
				| (in[offset++] & 0xFFL) << 16
				| (in[offset++] & 0xFFL) << 8
				| in[offset++]
				& 0xFFL;
		this.n2 = (in[offset++] & 0xFFL) << 56
				| (in[offset++] & 0xFFL) << 48
				| (in[offset++] & 0xFFL) << 40
				| (in[offset++] & 0xFFL) << 32
				| (in[offset++] & 0xFFL) << 24
				| (in[offset++] & 0xFFL) << 16
				| (in[offset++] & 0xFFL) << 8
				| in[offset++]
				& 0xFFL;
		this.n3 = (in[offset++] & 0xFFL) << 56
				| (in[offset++] & 0xFFL) << 48
				| (in[offset++] & 0xFFL) << 40
				| (in[offset++] & 0xFFL) << 32
				| (in[offset++] & 0xFFL) << 24
				| (in[offset++] & 0xFFL) << 16
				| (in[offset++] & 0xFFL) << 8
				| in[offset++]
				& 0xFFL;
		this.n4 = (in[offset++] & 0xFFL) << 56
				| (in[offset++] & 0xFFL) << 48
				| (in[offset++] & 0xFFL) << 40
				| (in[offset++] & 0xFFL) << 32
				| (in[offset++] & 0xFFL) << 24
				| (in[offset++] & 0xFFL) << 16
				| (in[offset++] & 0xFFL) << 8
				| in[offset++]
				& 0xFFL;
		this.n5 = (in[offset++] & 0xFFL) << 56
				| (in[offset++] & 0xFFL) << 48
				| (in[offset++] & 0xFFL) << 40
				| (in[offset++] & 0xFFL) << 32
				| (in[offset++] & 0xFFL) << 24
				| (in[offset++] & 0xFFL) << 16
				| (in[offset++] & 0xFFL) << 8
				| in[offset++]
				& 0xFFL;
		this.n6 = (in[offset++] & 0xFFL) << 56
				| (in[offset++] & 0xFFL) << 48
				| (in[offset++] & 0xFFL) << 40
				| (in[offset++] & 0xFFL) << 32
				| (in[offset++] & 0xFFL) << 24
				| (in[offset++] & 0xFFL) << 16
				| (in[offset++] & 0xFFL) << 8
				| in[offset++]
				& 0xFFL;
		this.n7 = (in[offset++] & 0xFFL) << 56
				| (in[offset++] & 0xFFL) << 48
				| (in[offset++] & 0xFFL) << 40
				| (in[offset++] & 0xFFL) << 32
				| (in[offset++] & 0xFFL) << 24
				| (in[offset++] & 0xFFL) << 16
				| (in[offset++] & 0xFFL) << 8
				| in[offset++]
				& 0xFFL;
		
		// transform K into the key schedule Kr; 0 <= r <= R
		this.k00 = this.H0;
		this.k01 = this.H1;
		this.k02 = this.H2;
		this.k03 = this.H3;
		this.k04 = this.H4;
		this.k05 = this.H5;
		this.k06 = this.H6;
		this.k07 = this.H7;
		
		this.nn0 = this.n0 ^ this.k00;
		this.nn1 = this.n1 ^ this.k01;
		this.nn2 = this.n2 ^ this.k02;
		this.nn3 = this.n3 ^ this.k03;
		this.nn4 = this.n4 ^ this.k04;
		this.nn5 = this.n5 ^ this.k05;
		this.nn6 = this.n6 ^ this.k06;
		this.nn7 = this.n7 ^ this.k07;
		
		// intermediate cipher output
		this.w0 = this.w1 = this.w2 = this.w3 = this.w4 = this.w5 = this.w6 = this.w7 = 0L;
		
		for (int r = 0; r < WhirlpoolDigest.R; r++) {
			// 1. compute intermediate round key schedule by applying ro[rc]
			// to the previous round key schedule --rc being the round constant
			this.Kr0 = WhirlpoolDigest.T0[(int) (this.k00 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.k07 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.k06 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.k05 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.k04 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.k03 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.k02 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.k01 & 0xFFL)]
					^ WhirlpoolDigest.rc[r];
			
			this.Kr1 = WhirlpoolDigest.T0[(int) (this.k01 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.k00 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.k07 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.k06 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.k05 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.k04 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.k03 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.k02 & 0xFFL)];
			
			this.Kr2 = WhirlpoolDigest.T0[(int) (this.k02 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.k01 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.k00 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.k07 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.k06 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.k05 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.k04 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.k03 & 0xFFL)];
			
			this.Kr3 = WhirlpoolDigest.T0[(int) (this.k03 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.k02 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.k01 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.k00 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.k07 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.k06 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.k05 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.k04 & 0xFFL)];
			
			this.Kr4 = WhirlpoolDigest.T0[(int) (this.k04 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.k03 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.k02 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.k01 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.k00 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.k07 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.k06 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.k05 & 0xFFL)];
			
			this.Kr5 = WhirlpoolDigest.T0[(int) (this.k05 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.k04 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.k03 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.k02 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.k01 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.k00 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.k07 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.k06 & 0xFFL)];
			
			this.Kr6 = WhirlpoolDigest.T0[(int) (this.k06 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.k05 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.k04 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.k03 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.k02 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.k01 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.k00 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.k07 & 0xFFL)];
			
			this.Kr7 = WhirlpoolDigest.T0[(int) (this.k07 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.k06 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.k05 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.k04 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.k03 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.k02 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.k01 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.k00 & 0xFFL)];
			
			this.k00 = this.Kr0;
			this.k01 = this.Kr1;
			this.k02 = this.Kr2;
			this.k03 = this.Kr3;
			this.k04 = this.Kr4;
			this.k05 = this.Kr5;
			this.k06 = this.Kr6;
			this.k07 = this.Kr7;
			
			// 2. incrementally compute the cipher output
			this.w0 = WhirlpoolDigest.T0[(int) (this.nn0 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.nn7 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.nn6 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.nn5 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.nn4 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.nn3 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.nn2 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.nn1 & 0xFFL)]
					^ this.Kr0;
			this.w1 = WhirlpoolDigest.T0[(int) (this.nn1 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.nn0 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.nn7 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.nn6 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.nn5 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.nn4 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.nn3 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.nn2 & 0xFFL)]
					^ this.Kr1;
			this.w2 = WhirlpoolDigest.T0[(int) (this.nn2 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.nn1 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.nn0 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.nn7 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.nn6 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.nn5 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.nn4 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.nn3 & 0xFFL)]
					^ this.Kr2;
			this.w3 = WhirlpoolDigest.T0[(int) (this.nn3 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.nn2 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.nn1 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.nn0 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.nn7 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.nn6 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.nn5 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.nn4 & 0xFFL)]
					^ this.Kr3;
			this.w4 = WhirlpoolDigest.T0[(int) (this.nn4 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.nn3 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.nn2 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.nn1 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.nn0 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.nn7 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.nn6 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.nn5 & 0xFFL)]
					^ this.Kr4;
			this.w5 = WhirlpoolDigest.T0[(int) (this.nn5 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.nn4 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.nn3 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.nn2 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.nn1 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.nn0 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.nn7 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.nn6 & 0xFFL)]
					^ this.Kr5;
			this.w6 = WhirlpoolDigest.T0[(int) (this.nn6 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.nn5 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.nn4 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.nn3 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.nn2 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.nn1 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.nn0 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.nn7 & 0xFFL)]
					^ this.Kr6;
			this.w7 = WhirlpoolDigest.T0[(int) (this.nn7 >> 56 & 0xFFL)]
					^ WhirlpoolDigest.T1[(int) (this.nn6 >> 48 & 0xFFL)]
					^ WhirlpoolDigest.T2[(int) (this.nn5 >> 40 & 0xFFL)]
					^ WhirlpoolDigest.T3[(int) (this.nn4 >> 32 & 0xFFL)]
					^ WhirlpoolDigest.T4[(int) (this.nn3 >> 24 & 0xFFL)]
					^ WhirlpoolDigest.T5[(int) (this.nn2 >> 16 & 0xFFL)]
					^ WhirlpoolDigest.T6[(int) (this.nn1 >> 8 & 0xFFL)]
					^ WhirlpoolDigest.T7[(int) (this.nn0 & 0xFFL)]
					^ this.Kr7;
			
			this.nn0 = this.w0;
			this.nn1 = this.w1;
			this.nn2 = this.w2;
			this.nn3 = this.w3;
			this.nn4 = this.w4;
			this.nn5 = this.w5;
			this.nn6 = this.w6;
			this.nn7 = this.w7;
		}
		
		// apply the Miyaguchi-Preneel hash scheme
		this.H0 ^= this.w0 ^ this.n0;
		this.H1 ^= this.w1 ^ this.n1;
		this.H2 ^= this.w2 ^ this.n2;
		this.H3 ^= this.w3 ^ this.n3;
		this.H4 ^= this.w4 ^ this.n4;
		this.H5 ^= this.w5 ^ this.n5;
		this.H6 ^= this.w6 ^ this.n6;
		this.H7 ^= this.w7 ^ this.n7;
	}
	
}
