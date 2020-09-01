package br.com.intergado.entrevista.ericsilva.controler.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import br.com.intergado.entrevista.ericsilva.model.StatusMessageResponse;
import br.com.intergado.entrevista.ericsilva.persistence.models.Animais;
import br.com.intergado.entrevista.ericsilva.persistence.models.Animal;
import br.com.intergado.entrevista.ericsilva.persistence.repository.AnimalRepository;

@RestController
@RequestMapping(AppConstants.BASE_PATH)
public class AnimalService {
	
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
		
		Animal animal = animalRepository
				.findById(animalId)
				.orElseThrow(() -> new ResourceAccessException(String.format("Animal %d não encontrado.", animalId)));
		
		return ResponseEntity.ok().body(animal);
		
	}
	
	/**
	 * Atualiza um animal pelo id
	 * @param animalDetails
	 * @return
	 */
	@PutMapping("/animais/{id}")
	public Animal updateAnimal(@PathVariable(value="id") int animalId, @Validated @RequestBody Animal animalDetails) {
		
		Animal animal = animalRepository
				.findById(animalId)
				.orElseThrow(() -> new ResourceAccessException(String.format("Animal %d não encontrado.", animalId)));
		
		animal.setFazenda(animalDetails.getFazenda());
		animal.setTag(animalDetails.getTag());
		
		return animalRepository.save(animal);
	}
	
	/**
	 * Cria animais a partir de uma lista de forma transacional
	 * @param animals
	 * @return
	 */
	@PostMapping("/animais/lista")
	@Transactional
	public ResponseEntity<Object> createAnimais(@Validated @RequestBody Animais animais){	
		try {
			animalRepository.saveAll(animais.getAnimais());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao processar a lista. Nenhum registro persistido.");
		}
		StatusMessageResponse retorno = new StatusMessageResponse();
		retorno.setMensagem("Inserido com sucesso");
		
		return ResponseEntity.ok(animais);
	}
	
	/**
	 * Cria um animal
	 * @param animals
	 * @return
	 */
	@PostMapping("/animais")
	public Animal createAnimal(@Validated @RequestBody Animal animal){
		return animalRepository.save(animal);
	}
	
	/**
	 * Remove um animal
	 * @param animalId
	 * @return
	 * @throws ResourceAccessException
	 */
	@DeleteMapping("/animais/{id}")
	public Map<String, Boolean> deleteAnimal(@PathVariable(value="id") Integer animalId) 
			throws ResourceAccessException {
		Animal animal = animalRepository
				.findById(animalId)
				.orElseThrow(() -> new ResourceAccessException(String.format("Animal não encontrado: %s", animalId)));
		animalRepository.delete(animal);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	}
	
}
