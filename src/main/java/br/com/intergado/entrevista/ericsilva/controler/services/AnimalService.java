package br.com.intergado.entrevista.ericsilva.controler.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import br.com.intergado.entrevista.ericsilva.constants.AppConstants;
import br.com.intergado.entrevista.ericsilva.persistence.repository.AnimalRepository;

@RestController
@RequestMapping(AppConstants.BASE_PATH)
public class Animal {
	
	@Autowired
	private AnimalRepository animalRepository; 
	
	/**
	 * Busca uma animal pelo id
	 * @param animalId
	 * @return
	 * @throws ResourceAccessException
	 */
	@GetMapping("/animais/{id}")
	public ResponseEntity<Animal> getAnimalById(@PathVariable(value="id") Integer animalId) 
			throws ResourceAccessException {
		return null;
	}
	
	/**
	 * Cria ou atualiza um animal pelo id
	 * @param animal
	 * @return
	 */
	@PutMapping("/animais/{id}")
	public Animal createAnimal(@Validated @RequestBody Animal animal) {
		return null;
	}
	
	/**
	 * Cria ou atualiza animais a partir de uma lista
	 * @param animals
	 * @return
	 */
	@PostMapping("/animais")
	public Animal createAnimals(@Validated @RequestBody List<Animal> animals){
		return null;
	}
	
	/**
	 * Remove um animal
	 * @param animalId
	 * @return
	 * @throws ResourceAccessException
	 */
	@DeleteMapping("/animais/{id}")
	public ResponseEntity<Animal> deleteAnimal(@PathVariable(value="id") Integer animalId) 
			throws ResourceAccessException {
		return null;
	}
	
}
