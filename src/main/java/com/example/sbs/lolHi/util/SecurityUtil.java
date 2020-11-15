package com.example.sbs.lolHi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {
	
	static public String encryptSHA256(String str) {
		String sha = "";
		
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteDate[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for ( int i = 0; i < byteDate.length; i++ ) {
				sb.append(Integer.toString((byteDate[i]&0xff) + 0x100, 16).substring(1));
			}
			sha = sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Encrypt Error - NoSuchAlgorithmException");
			
			sha = null;
		}

		return sha;
	}

}
