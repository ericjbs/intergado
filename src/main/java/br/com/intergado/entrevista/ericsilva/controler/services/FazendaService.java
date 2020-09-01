package br.com.intergado.entrevista.ericsilva.controler.services;

import java.util.HashMap;
import java.util.Map;

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
import br.com.intergado.entrevista.ericsilva.persistence.models.Animal;
import br.com.intergado.entrevista.ericsilva.persistence.models.Fazenda;
import br.com.intergado.entrevista.ericsilva.persistence.repository.FazendaRepository;

@RestController
@RequestMapping(AppConstants.BASE_PATH)
public class FazendaService {
	
	@Autowired
	private FazendaRepository fazendaRepository; 
	
	/**
	 * Busca uma Fazenda pelo id
	 * @param FazendaId
	 * @return
	 * @throws ResourceAccessException
	 */
	@GetMapping("/fazendas/{id}")
	public ResponseEntity<Fazenda> getFazendaById(@PathVariable(value="id") Integer fazendaId) 
			throws ResourceAccessException {
		
		Fazenda fazenda  = fazendaRepository
				.findById(fazendaId)
				.orElseThrow(() -> new ResourceAccessException(String.format("Fazenda %d não encontrado.", fazendaId)));
		
		return ResponseEntity.ok().body(fazenda);
	}
	
	/**
	 * Cria ou atualiza uma Fazenda pelo id
	 * @param Fazenda
	 * @return
	 */
	@PostMapping("/fazendas")
	public Fazenda createFazenda(@Validated @RequestBody Fazenda fazenda) {
		return fazendaRepository.save(fazenda);
	}
	
	/**
	 * Remove uma Fazenda
	 * @param FazendaId
	 * @return
	 * @throws ResourceAccessException
	 */
	@DeleteMapping("/fazendas/{id}")
	public Map<String, Boolean> deleteFazenda(@PathVariable(value="id") Integer fazendaId) 
			throws ResourceAccessException {
		
		Fazenda fazenda = fazendaRepository
				.findById(fazendaId)
				.orElseThrow(() -> new ResourceAccessException(String.format("Fazenda não encontrada: %s", fazendaId)));
		fazendaRepository.delete(fazenda);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    
	    return response;
	}
	
	@PutMapping("/fazendas/{id}")
	public Fazenda updateFazenda(@PathVariable(value="id") int fazendaId, @Validated @RequestBody Fazenda fazendaDetails) {
		
		Fazenda fazenda = fazendaRepository
				.findById(fazendaId)
				.orElseThrow(() -> new ResourceAccessException(String.format("Fazenda %d não encontrado.", fazendaId)));
		
		fazenda.setNome(fazendaDetails.getNome());
		
		return fazendaRepository.save(fazenda);
	}
}
