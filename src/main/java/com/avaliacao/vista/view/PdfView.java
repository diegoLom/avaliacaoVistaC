package com.avaliacao.vista.view;

import com.avaliacao.vista.util.Geral;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
//import me.aboullaite.model.User;
import org.apache.poi.ss.usermodel.Row;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aboullaite on 2017-02-25.
 */
public class PdfView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");

        List users = (List) model.get("results");
        document.add(new Paragraph("Generated Users " + LocalDate.now()));

       PdfPTable table = new PdfPTable(users.size());
        table.setWidthPercentage(100.0f);
        table.setSpacingBefore(10);

        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(BaseColor.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setPadding(5);
        
        LinkedHashMap<String, String> columnsTitle = (LinkedHashMap<String, String>) model.get("columnsTitle");
        
        for(String colunaTitulo : columnsTitle.values()) {
        
    	  cell.setPhrase(new Phrase(colunaTitulo, font));
          table.addCell(cell);

        }
        
        
        for(Object result : users) {
            for(String set : columnsTitle.keySet()) {
            	 Object valor = Geral.resultadoOuNulo(result, set);
            	 table.addCell(valor.toString());
	       	}
        }
        
        
        document.add(table); 
    }
}
