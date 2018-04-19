package com.avaliacao.vista.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.swing.text.MaskFormatter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.avaliacao.vista.services.UniqueKey;


@Entity
@Table(name="contato")
@UniqueKey(columnNames = {"cpf", "nomeContato", "empresa.codigoEmpresa", "area.codigoArea" }, propertyId ="codigoContato") 
public class Contato implements Serializable {
	

	
	@Id
	@GeneratedValue
	private Long codigoContato;
	
	@NotBlank(message="O nome do contato é requerido")
	@Column(name="nomecontato")
	private String nomeContato;
	
	@Column(name="cpf", unique=true)
	private Long cpf; 
	
	@Column(name="telefoneresidencial")
	private Integer telefoneResidencial;
	
	@Column(name="telefonecelular")
	private Integer telefoneCelular;
	
	@Column(name="email", unique=true)
	private String email; 
	
	@ManyToOne
	@JoinColumn(name="codigoarea")
	private Area area;
	
	@ManyToOne
	@JoinColumn(name="codigoempresa")
	private Empresa empresa;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dataregistro", nullable=false)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dataRegistro;
	

	public Long getCodigoContato() {
		return codigoContato;
	}

	public void setCodigoContato(Long codigoContato) {
		this.codigoContato = codigoContato;
	}

	public String getNomeContato() {
		return nomeContato;
	}

	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Integer getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(Integer telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public Integer getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(Integer telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	
	@NotBlank(message="O cpf é requerido")
	private String cpfFormatado;
	
	public String getCpfFormatado() {
		
		if(getCpf() != null) {
			try {
				MaskFormatter format= new MaskFormatter("###.###.###-##");
				return format.valueToString(getCpf());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cpfFormatado;
	}

	public void setCpfFormatado(String cpfFormatado) {
		
		if(cpfFormatado != null && !cpfFormatado.isEmpty()) {
			setCpf(new Long(cpfFormatado.replaceAll("\\D", "")));
		}
			
		
		this.cpfFormatado = cpfFormatado;
	}
	
	
	private String telefoneResidencialFormatado;
	
	private String telefoneCelularFormatado;
	
	public String getTelefoneResidencialFormatado() {
		
		if(getTelefoneResidencial() != null) {
			try {
				MaskFormatter format= new MaskFormatter("####-####");
				return format.valueToString(getTelefoneResidencial());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return telefoneResidencialFormatado;
	}

	public void setTelefoneResidencialFormatado(String telefoneResidencialFormatado) {
		
		if(telefoneResidencialFormatado != null && !telefoneResidencialFormatado.isEmpty()) {
			setTelefoneResidencial(new Integer(telefoneResidencialFormatado.replaceAll("\\D", "")));
		}
		
		this.telefoneResidencialFormatado = telefoneResidencialFormatado;
	}

	public String getTelefoneCelularFormatado() {
		
		if(getTelefoneCelular() != null) {
			try {
				MaskFormatter format= new MaskFormatter("####-####");
				return format.valueToString(getTelefoneCelular());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return telefoneCelularFormatado;
	}

	public void setTelefoneCelularFormatado(String telefoneCelularFormatado) {
		
		if(telefoneCelularFormatado != null && !telefoneCelularFormatado.isEmpty()) {
			setTelefoneCelular(new Integer(telefoneCelularFormatado.replaceAll("\\D", "")));
		}
		
		this.telefoneCelularFormatado = telefoneCelularFormatado;
	}


}
