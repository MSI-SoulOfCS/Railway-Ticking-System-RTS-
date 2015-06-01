package com.mercury.demand.security;

import java.security.Key;
import java.security.MessageDigest;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

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
	
	public String httpGetStringConvert(String token) {
		StringBuilder s = new StringBuilder(token.length());

		CharacterIterator it = new StringCharacterIterator(token);
		for (char ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
		    switch (ch) {
		        case '&':
		            s.append("%26");
		            break;
		        case '+':
		            s.append("%2B");
		            break;
		        case ',':
		            s.append("%2C");
		            break;
		        case '/':
		        	s.append("%2F");
		        	break;
		        case ':':
		        	s.append("%3A");
		        	break;
		        case ';':
		        	s.append("%3B");
		        	break;
		        case '=':
		        	s.append("%3D");
		        	break;
		        case '?':
		        	s.append("%3F");
		        	break;
		        case '@':
		        	s.append("%40");
		        	break;
		        case ' ':
		        	s.append("%20");
		        	break;
		        case '\t':
		        	s.append("%09");
		        	break;
		        case '#':
		        	s.append("%23");
		        	break;
		        case '<':
		        	s.append("%3C");
		        	break;
		        case '>':
		        	s.append("%3E");
		        	break;
		        case '\\':
		        	s.append("%22");
		        	break;
		        case '\n':
		        	s.append("%0A");
		        	break;
		        default:
		            s.append(ch);
		            break;
		    }
		}

		return s.toString();		
	}
	
	public String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    	e.printStackTrace();
		    }
		    return null;
	}
}
 