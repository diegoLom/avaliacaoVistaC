package com.avaliacao.vista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avaliacao.vista.model.Contato;

public interface Contatos extends JpaRepository<Contato, Long> {

}
