package com.avaliacao.vista;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.avaliacao.vista.model.Area;
import com.avaliacao.vista.model.Contato;
import com.avaliacao.vista.model.Empresa;
import com.avaliacao.vista.repository.Areas;
import com.avaliacao.vista.util.Geral;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvaliacaoVistaApplicationTests {
	
	@Autowired
	static
	Areas areas;
	
	@Test
	public void contextLoads() {
	}

	public static void main(String[] args) {
	Contato contato = new Contato();
	contato.setEmpresa(new Empresa());
	
	
	contato.getEmpresa().setCnpj(328734923L);
	
	new Geral().resultadoOuNulo(contato, "empresa.cnpj");
	
	
	}

}
