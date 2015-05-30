package com.mercury.demand.security;

import java.security.Key;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.jersey.core.util.Base64;
 
// AES Encrypt & Decrypt
public class MagicCrypt {
 
	private static MagicCrypt instance;
	
	//Initialization Vector is 16 bits' 0
	private static final IvParameterSpec DEFAULT_IV = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
	//Algorithm is AES
	private static final String ALGORITHM = "AES";
	//Set transformation
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	
	private Key key;
	private IvParameterSpec iv;
	private Cipher cipher;
 
	
	//Static Method
	public static MagicCrypt getInstance() {
		if(instance == null) {
			synchronized(MagicCrypt.class) {
				if(instance == null) instance = new MagicCrypt("MercurySystems");
			}
		}
		
		return instance;
	}
	
	//Constructor
	private MagicCrypt() {}
	private MagicCrypt(final String key) {
		this(key, 128);
	}
 
	private MagicCrypt(final String key, final int bit) {
		this(key, bit, null);
	}
 
	private MagicCrypt(final String key, final int bit, final String iv) {
		if (bit == 256) {
			this.key = new SecretKeySpec(getHash("SHA-256", key), ALGORITHM);
		} else {
			this.key = new SecretKeySpec(getHash("MD5", key), ALGORITHM);
		}
		if (iv != null) {
			this.iv = new IvParameterSpec(getHash("MD5", iv));
		} else {
			this.iv = DEFAULT_IV;
		}
 
		init();
	}
 

	private static byte[] getHash(final String algorithm, final String text) {
		try {
			return getHash(algorithm, text.getBytes("UTF-8"));
		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
 
	private static byte[] getHash(final String algorithm, final byte[] data) {
		try {
			final MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.update(data);
			return digest.digest();
		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
 

	private void init() {
		try {
			cipher = Cipher.getInstance(TRANSFORMATION);
		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
 

	public String encrypt(final String str) {
		try {
			return encrypt(str.getBytes("UTF-8"));
		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
 

	public String encrypt(final byte[] data) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			final byte[] encryptData = cipher.doFinal(data);
			return new String(Base64.encode(encryptData), "UTF-8");
		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
 

	public String decrypt(final String str) {
		try {
			return decrypt(Base64.decode(str));
		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
 

	public String decrypt(final byte[] data) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			final byte[] decryptData = cipher.doFinal(data);
			return new String(decryptData, "UTF-8");
		} catch (final Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
}
 