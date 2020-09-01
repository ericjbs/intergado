package br.com.intergado.entrevista.ericsilva.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intergado.entrevista.ericsilva.persistence.models.Fazenda;

public interface FazendaRepository  
	extends JpaRepository<Fazenda, Integer> { }
