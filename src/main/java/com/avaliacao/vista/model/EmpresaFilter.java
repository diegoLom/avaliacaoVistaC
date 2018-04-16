package com.avaliacao.vista.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class EmpresaFilter extends Empresa{
	
	
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
		this.dataFinal = dataFinal;
	}

	@NotNull(message="{validation.dataInicial.notNull}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataInicial;
	
	@NotNull(message="{validation.dataFinal.notNull}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataFinal;

	
	
	
	
	
	
	

}
