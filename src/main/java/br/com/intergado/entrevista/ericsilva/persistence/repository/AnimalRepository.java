package br.com.intergado.entrevista.ericsilva.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intergado.entrevista.ericsilva.persistence.models.Animal;

public interface AnimalRepository 
	extends JpaRepository<Animal, Integer> { }
