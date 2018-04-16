package com.avaliacao.vista.viewResolver;

import com.avaliacao.vista.view.ExcelView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * Created by aboullaite on 2017-02-24.
 */
public class ExcelViewResolver implements ViewResolver {
	
	
    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
    	
    	if(s.equals("ExcellView")) {
    		ExcelView view = new ExcelView();
    		return view;
    	}	
      
    	 return null;
    }
}
