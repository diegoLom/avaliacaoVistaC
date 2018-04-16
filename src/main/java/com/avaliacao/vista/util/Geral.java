package com.avaliacao.vista.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Geral {
	
	
	
	public static Object resultadoOuNulo(Object arg0, String agregacao) {
		Object resultado = null;
		
		if(arg0 != null) {
			if(agregacao != null && !agregacao.isEmpty()) {
				Collection separandoString = new ArrayList(Arrays.asList(agregacao.split("[.]")));
				
				for(int x =0; x < separandoString.size(); x++) {
					
					String separadas = (String) separandoString.toArray()[x];
					//result.getClass().getDeclaredMethod("get"+set.substring(0,1).toUpperCase()+set.substring(1, set.length()), null).invoke(result, null);
					Method metodo = null;
					try {
						metodo = arg0.getClass().getDeclaredMethod("get"+separadas.substring(0,1).toUpperCase()+separadas.substring(1, separadas.length()), null);
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(metodo != null) {
						try {
							resultado = metodo.invoke(arg0, null);
							if(x + 1 < separandoString.size()) {
								separandoString.remove(separadas);
								resultado = resultadoOuNulo(resultado, agregacao.replace(separadas+".", ""));
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}
					
				}
			}
		}
		
		
		
		return resultado;
	}

}
