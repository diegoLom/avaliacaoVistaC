package com.avaliacao.vista.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.avaliacao.vista.util.DataUtil;

public class AreaFilter implements Serializable {
	
	
	@NotNull(message="{validation.dataInicial.notNull}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataInicial;
	
	@NotNull(message="{validation.dataFinal.notNull}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataFinal;
	
	private String descricaoArea;

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
		this.dataFinal = DataUtil.converterUltimaHoraDia(dataFinal);
	}

	public String getDescricaoArea() {
		return descricaoArea;
	}

	public void setDescricaoArea(String descricaoArea) {
		this.descricaoArea = descricaoArea;
	}
	
	
	
	
	
	

}
