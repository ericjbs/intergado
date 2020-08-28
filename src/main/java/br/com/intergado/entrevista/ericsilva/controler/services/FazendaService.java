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
import br.com.intergado.entrevista.ericsilva.persistence.repository.FazendaRepository;

@RestController
@RequestMapping(AppConstants.BASE_PATH)
public class Fazenda {
	
	@Autowired
	private FazendaRepository FazendaRepository; 
	
	/**
	 * Busca uma Fazenda pelo id
	 * @param FazendaId
	 * @return
	 * @throws ResourceAccessException
	 */
	@GetMapping("/fazendas/{id}")
	public ResponseEntity<Fazenda> getFazendaById(@PathVariable(value="id") Integer FazendaId) 
			throws ResourceAccessException {
		return null;
	}
	
	/**
	 * Cria ou atualiza uma Fazenda pelo id
	 * @param Fazenda
	 * @return
	 */
	@PutMapping("/fazendas/{id}")
	public Fazenda createFazenda(@Validated @RequestBody Fazenda Fazenda) {
		return null;
	}
	
	/**
	 * Remove uma Fazenda
	 * @param FazendaId
	 * @return
	 * @throws ResourceAccessException
	 */
	@DeleteMapping("/fazendas/{id}")
	public ResponseEntity<Fazenda> deleteFazenda(@PathVariable(value="id") Integer FazendaId) 
			throws ResourceAccessException {
		return null;
	}
	
}
