package com.avaliacao.vista.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.avaliacao.vista.model.Area;

public interface Areas extends JpaRepository<Area, Long> {
	



}
