package com.avaliacao.vista.util;

import java.util.Calendar;

public class DataUtil {
	
	 static Calendar c = Calendar.getInstance();
		
	
	 public static java.util.Date converterUltimaHoraDia(java.util.Date dataFinal) {
		 
		 if(dataFinal != null) {
				
			 c.setTime(dataFinal);
			 c.set(Calendar.HOUR_OF_DAY, 23);
			 c.set(Calendar.MINUTE, 59);
			 
			dataFinal = c.getTime();
		}
		 
		 return dataFinal;
	 }
	 
	

}
