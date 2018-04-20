package com.avaliacao.vista.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.type.descriptor.java.CalendarDateTypeDescriptor;
import org.springframework.format.annotation.DateTimeFormat;

import com.avaliacao.vista.util.DataUtil;

public class ContatoFilter implements Serializable{
	
	
	
	public ContatoFilter() {
		super();
		contato = new Contato();
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = DataUtil.converterUltimaHoraDia(dataFinal);;
	}

	@NotNull(message="{validation.dataInicial.notNull}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataInicial;
	
	@NotNull(message="{validation.dataFinal.notNull}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataFinal;

	private Contato contato;
	
	
	
	
	
	
	
	

}
