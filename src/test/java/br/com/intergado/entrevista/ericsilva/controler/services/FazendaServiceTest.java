package br.com.intergado.entrevista.ericsilva.controler.services;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import br.com.intergado.entrevista.ericsilva.EricsilvaApplication;
import br.com.intergado.entrevista.ericsilva.constants.AppConstants;
import br.com.intergado.entrevista.ericsilva.persistence.models.Fazenda;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EricsilvaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class FazendaServiceTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	private String endpointFazendas;
	private Fazenda fazendaEsperada;
	private Fazenda fazendaEsperada2;
	private HttpHeaders headers;

	@BeforeAll
	public void init() {

		headers = new HttpHeaders();
		this.endpointFazendas = String.format("http://localhost:8081%s/fazendas", AppConstants.BASE_PATH);
		
		Fazenda fazenda = new Fazenda();
		fazenda.setNome("fazenda_1");

		fazendaEsperada = restTemplate.postForEntity(endpointFazendas, fazenda, Fazenda.class).getBody();
		fazendaEsperada2 = restTemplate.postForEntity(endpointFazendas, fazenda, Fazenda.class).getBody();

	}

	@Test
	final void testGetFazendaById() {
		
		HttpEntity<String> entity;
		entity = new HttpEntity<String>(null, headers);

		ResponseEntity<Fazenda> response = restTemplate.exchange(endpointFazendas + "/" + fazendaEsperada.getId(),
				HttpMethod.GET, entity, Fazenda.class);

		Assert.assertEquals(fazendaEsperada, response.getBody());
		Assert.assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	final void testCreateFazenda() {
		Fazenda fazenda = new Fazenda();
		fazenda.setNome("fazenda_2");

		ResponseEntity<Fazenda> response = restTemplate.postForEntity(endpointFazendas, fazenda, Fazenda.class);
		
		Assert.assertEquals(fazenda.getNome(), response.getBody().getNome());
		Assert.assertEquals(200, response.getStatusCodeValue());
		
	}

	@Test
	final void testDeleteFazenda() {
		
		int id = fazendaEsperada.getId();
		Fazenda fazenda = restTemplate.getForObject(endpointFazendas + "/" + id, Fazenda.class);

		Assert.assertNotNull(fazenda);

		restTemplate.delete(endpointFazendas + "/" + id);

		try {
			fazenda = restTemplate.getForObject(endpointFazendas + "/" + id, Fazenda.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

	@Test
	final void testUpdateFazendaPorId() {
		int id = fazendaEsperada2.getId();

		Fazenda fazenda = restTemplate.getForObject(endpointFazendas + "/" + id, Fazenda.class);
		fazenda.setNome("Teste");

		HttpEntity<Fazenda> entity;
		entity = new HttpEntity<>(fazenda, headers);
		ResponseEntity<Fazenda> response = null;

		try {
			response = restTemplate.exchange(endpointFazendas + "/" + id, HttpMethod.PUT, entity, Fazenda.class);
		} catch (RestClientException e) {
			Assert.fail(String.format("Put falhou: %s", e.toString()));
		}
		
		Assert.assertEquals(fazenda, response.getBody());
	}
	
}
