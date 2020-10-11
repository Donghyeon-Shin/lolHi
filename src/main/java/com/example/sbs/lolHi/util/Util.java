package com.example.sbs.lolHi.util;

import java.math.BigInteger;

public class Util {

	public static int getAsInt(Object object) {
	
		if ( object instanceof BigInteger ) {
			return ((BigInteger)object).intValue();
		} else if ( object instanceof Long ) {
			return ((int)object);
		}
		
		return -1;
	}

}
