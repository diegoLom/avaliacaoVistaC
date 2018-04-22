package com.avaliacao.vista.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.swing.text.MaskFormatter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.avaliacao.vista.services.UniqueKey;

@Entity
@Table(name="empresa")
@UniqueKey(columnNames={"cnpj"}, message="CNPJ já existente",  propertyId ="codigoEmpresa")
public class Empresa implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long codigoEmpresa;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dataregistro", nullable=false)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataRegistro;
	
	@NotBlank(message="O nome da Empresa obrigatório")
	@Column(name="nomeempresa", nullable=false)
	private String nomeEmpresa;
	
	
	@Column(name="cnpj", nullable=false)
	private Long cnpj;

	public Long getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(Long codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}
	
	@NotBlank(message="O cnpj é requerido")
	private String cnpjFormatado;
	
	public String getCnpjFormatado() {
		
		if(getCnpj() != null) {
			try {
				MaskFormatter format= new MaskFormatter("##.###.###/####-##");
				return format.valueToString(getCnpj());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
		}
		return cnpjFormatado;
	}

	public void setCnpjFormatado(String cnpjFormatado) {
		
		if(cnpjFormatado != null && !cnpjFormatado.isEmpty()) {
			setCnpj(new Long(cnpjFormatado.replaceAll("\\D", "")));
		}
			
		
		this.cnpjFormatado = cnpjFormatado;
	}
	

}
