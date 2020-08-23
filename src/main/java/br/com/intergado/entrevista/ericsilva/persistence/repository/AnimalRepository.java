package br.com.intergado.entrevista.ericsilva.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.intergado.entrevista.ericsilva.controler.services.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Integer> { }
