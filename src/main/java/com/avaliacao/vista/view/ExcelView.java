package com.avaliacao.vista.view;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.avaliacao.vista.util.Geral;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by aboullaite on 2017-02-23.
 */
public class ExcelView extends AbstractXlsView{

	 @SuppressWarnings("unchecked")
	    protected void buildExcelDocument(Map<String, Object> model,
	            HSSFWorkbook workbook,
	            HttpServletRequest request,
	            HttpServletResponse response)
	    {
	        //VARIABLES REQUIRED IN MODEL
	        String sheetName = (String)model.get("sheetname");
	        List<String> headers = (List<String>)model.get("headers");
	        List<List<String>> results = (List<List<String>>)model.get("results");
	        List<String> numericColumns = new ArrayList<String>();
	        if (model.containsKey("numericcolumns"))
	            numericColumns = (List<String>)model.get("numericcolumns");
	        //BUILD DOC
	        HSSFSheet sheet = workbook.createSheet(sheetName);
	        sheet.setDefaultColumnWidth((short) 12);
	        int currentRow = 0;
	        short currentColumn = 0;
	        //CREATE STYLE FOR HEADER
	        HSSFCellStyle headerStyle = workbook.createCellStyle();
	        HSSFFont headerFont = workbook.createFont();
	        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headerStyle.setFont(headerFont); 
	        //POPULATE HEADER COLUMNS
	        HSSFRow headerRow = sheet.createRow(currentRow);
	        for(String header:headers){
	            HSSFRichTextString text = new HSSFRichTextString(header);
	            HSSFCell cell = headerRow.createCell(currentColumn); 
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(text);            
	            currentColumn++;
	        }
	        //POPULATE VALUE ROWS/COLUMNS
	        currentRow++;//exclude header
	        for(List<String> result: results){
	            currentColumn = 0;
	            HSSFRow row = sheet.createRow(currentRow);
	            for(String value : result){//used to count number of columns
	                HSSFCell cell = row.createCell(currentColumn);
	                if (numericColumns.contains(headers.get(currentColumn))){
	                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	                    cell.setCellValue(value);
	                } else {
	                    HSSFRichTextString text = new HSSFRichTextString(value);                
	                    cell.setCellValue(text);                    
	                }
	                currentColumn++;
	            }
	            currentRow++;
	        }
	    }

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		 // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

        @SuppressWarnings("unchecked")
        List results = (List) model.get("results");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("User Detail");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);


        // create header row
        Row header = sheet.createRow(0);
        
        LinkedHashMap<String, String> columnsTitle = (LinkedHashMap<String, String>) model.get("columnsTitle");
        
        int cont = 0;
        for(String colunaTitulo : columnsTitle.values()) {
        	header.createCell(cont).setCellValue(colunaTitulo);
        	header.getCell(cont).setCellStyle(style);
        	cont++;
        }
        
        int rowCount = 1;
        
        for(Object result : results) {
        	Row userRow =  sheet.createRow(rowCount++);
	        cont = 0;
	        for(String set : columnsTitle.keySet()) {
	        	 Object valor = Geral.resultadoOuNulo(result, set);
	        	 if(valor instanceof Integer)
	        		 userRow.createCell(cont).setCellValue((Integer)valor);
	        	 
	        	 if(valor instanceof Long)
	        		 userRow.createCell(cont).setCellValue((Long)valor);
	        	 
	        	 if(valor instanceof String)
	        		 userRow.createCell(cont).setCellValue((String)valor);
	        	 
	        	 if(valor instanceof java.util.Date)
        		 userRow.createCell(cont).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format((java.util.Date)valor));
	        	 
	        	 
	        	 
	        	 cont++;
	             
	        }
        }
     

     /*   for(User user : users){
            Row userRow =  sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(user.getFirstName());
            userRow.createCell(1).setCellValue(user.getLastName());
            userRow.createCell(2).setCellValue(user.getAge());
            userRow.createCell(3).setCellValue(user.getJobTitle());
            userRow.createCell(4).setCellValue(user.getCompany());
            userRow.createCell(5).setCellValue(user.getAddress());
            userRow.createCell(6).setCellValue(user.getCity());
            userRow.createCell(7).setCellValue(user.getCountry());
            userRow.createCell(8).setCellValue(user.getPhoneNumber());

            } **/

		
	}

}
