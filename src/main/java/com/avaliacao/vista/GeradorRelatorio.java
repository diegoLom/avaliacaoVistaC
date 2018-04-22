package com.avaliacao.vista;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class GeradorRelatorio implements Serializable {
	
//	public static void main(String[] args) {
//		gerarRelatorio(null);
//	}
	
//	public static void gerarRelatorio(List lista) {
//		
//		lista = new ArrayList<Aluno>();
//		for(int x = 0 ; x < 10 ; x++) {
//			lista.add(new Aluno("Diego"+x, "Diego"+(x+1)));
//		}
//		
//		InputStream in = GeradorRelatorio.class.getResourceAsStream("/relatorios/relListaContatos.jrxml");
//		
//		try {
//			JasperReport report = JasperCompileManager.compileReport(in);
//			JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista, false));
//			JasperViewer.viewReport(print, false);
//			
//			
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

}
