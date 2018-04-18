package com.avaliacao.vista.model;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.context.annotation.Description;
import org.springframework.format.annotation.DateTimeFormat;

import com.avaliacao.vista.services.UniqueKey;
import org.springframework.stereotype.Component; 

@Entity
@Table(name="area")
@UniqueKey(columnNames={"descricaoArea"}, message="Descrição já existente", propertyId ="codigoArea" )
@Embeddable
public class Area implements Serializable {
	
	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	@Column(name="descricaoarea", unique=true, nullable=false)
	private String descricaoArea;
	
	private Long areaId;
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long codigoArea;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dataregistro", nullable=false)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dataRegistro;

	@Description(value="Descrição")
	public String getDescricaoArea() {
		return descricaoArea;
	}

	public void setDescricaoArea(String descricaoArea) {
		this.descricaoArea = descricaoArea;
	}
	
	@Transient
	@Description(value="Código")
	public Long getCodigoArea() {
		return codigoArea;
	}

	public void setCodigoArea(Long codigoArea) {
		this.codigoArea = codigoArea;
	}

	@Description(value="Data Cadastro")
	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}
	
	

}
