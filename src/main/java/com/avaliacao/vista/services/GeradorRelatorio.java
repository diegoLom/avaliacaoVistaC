package com.avaliacao.vista.services;

import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GeradorRelatorio {
	
	
	public static byte[] gerarRelatorio(String caminhoRelatorio, Map<String, Object> parametros, Collection dataSource ) throws Exception {
		
		InputStream inputStream = GeradorRelatorio.class
				.getResourceAsStream(caminhoRelatorio);
		
		try {
			if(inputStream != null) {
				JasperReport report = JasperCompileManager.compileReport(inputStream);
				JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(dataSource, false));
				return JasperExportManager.exportReportToPdf(jasperPrint);
			}else {
				return null;
			}
		} finally {
		
		}
	}

}
