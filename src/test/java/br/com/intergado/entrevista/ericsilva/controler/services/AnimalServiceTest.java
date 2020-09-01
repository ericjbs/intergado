package br.com.intergado.entrevista.ericsilva.controler.services;

import java.util.ArrayList;
import java.util.List;

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
import br.com.intergado.entrevista.ericsilva.persistence.models.Animais;
import br.com.intergado.entrevista.ericsilva.persistence.models.Animal;
import br.com.intergado.entrevista.ericsilva.persistence.models.Fazenda;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EricsilvaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class AnimalServiceTest {

	@Autowired
	private TestRestTemplate restTemplate;

	private String endpointAnimais;
	private Animal animalEsperado;
	private HttpHeaders headers;

	@BeforeAll
	public void init() {

		headers = new HttpHeaders();
		this.endpointAnimais = String.format("http://localhost:8081%s/animais", AppConstants.BASE_PATH);
		
		Fazenda fazenda = new Fazenda();
		Animal animal = new Animal();
		fazenda.setNome("fazenda_1");
		animal.setTag("tag_1");
		animal.setFazenda(fazenda);

		animalEsperado = restTemplate.postForEntity(endpointAnimais, animal, Animal.class).getBody();

	}

	/**
	 * Ao buscar um animal de id n no caminho /animais/n deve retornar um JSON de id
	 * n, tag tag_1, fazenda com nome fazenda_1 e Http Status Code 200 (Sucesso)
	 */
	@Test
	final void testGetAnimalByIdSucesso() {

		HttpEntity<String> entity;
		entity = new HttpEntity<String>(null, headers);

		ResponseEntity<Animal> response = restTemplate.exchange(endpointAnimais + "/" + animalEsperado.getId(),
				HttpMethod.GET, entity, Animal.class);

		/*
		 * Testa somente o nome da fazenda, a tag e status code 200, pois os ids são
		 * autogerados
		 */
		Assert.assertEquals(animalEsperado.getFazenda().getNome(), response.getBody().getFazenda().getNome());
		Assert.assertEquals(animalEsperado.getTag(), response.getBody().getTag());
		Assert.assertEquals(200, response.getStatusCodeValue());

	}

	/**
	 * Ao buscar um animal de id 2 no caminho /animais/2 deve retornar uma mensagem
	 * de erro e o Http Status Code 500 (Internal Server Error), já que o animal não
	 * existe
	 */
	@Test
	final void testGetAnimalByIdFalha() {

		HttpEntity<String> entity;
		entity = new HttpEntity<String>(null, headers);

		ResponseEntity<Animal> response = restTemplate.exchange(endpointAnimais + "/2", HttpMethod.GET, entity,
				Animal.class);

		Assert.assertEquals(500, response.getStatusCodeValue());
	}

	/**
	 * Ao inserir um animal no caminho /animais, deve retornar HTTP Status Code 200
	 * (Sucesso).
	 */
	@Test
	final void testCreateAnimalSucesso() {

		Fazenda fazenda = new Fazenda();
		Animal animal = new Animal();
		fazenda.setNome("fazenda_2");
		animal.setTag("tag_2");
		animal.setFazenda(fazenda);

		ResponseEntity<Animal> response = restTemplate.postForEntity(endpointAnimais, animal, Animal.class);

		Assert.assertEquals(fazenda.getNome(), response.getBody().getFazenda().getNome());
		Assert.assertEquals(animal.getTag(), response.getBody().getTag());
		Assert.assertEquals(200, response.getStatusCodeValue());
	}

	/**
	 * Ao criar varios animais atraves do caminho /animais, retorna Http Status Code
	 * 200.
	 */
	@Test
	final void testCreateAnimaisSucesso() {

		List<Animal> listaAnimais = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Fazenda fazenda = new Fazenda();
			Animal animal = new Animal();
			fazenda.setNome("fazenda_" + (i + 1));
			animal.setTag("tag_" + (i + 1));
			animal.setFazenda(fazenda);
			listaAnimais.add(animal);
		}

		Animais animais = new Animais();
		animais.setAnimais(listaAnimais);

		ResponseEntity<Animais> response = restTemplate.postForEntity(endpointAnimais + "/lista", animais,
				Animais.class);

		Assert.assertEquals(200, response.getStatusCodeValue());
	}

	/**
	 * Ao criar varios animais através de um POST no caminho /animais, destes um com
	 * id errado, não grava nenhum e retorna Http Status Code 400 (Bad Request).
	 */
	@Test
	final void testCreateAnimaisFalha() {

		List<Animal> listaAnimais = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			Fazenda fazenda = new Fazenda();
			Animal animal = new Animal();
			fazenda.setNome("fazenda_" + i + 1);
			animal.setTag("tag_" + i + 1);
			animal.setFazenda(fazenda);
			listaAnimais.add(animal);
		}

		listaAnimais.add(new Animal());

		Animais animais = new Animais();
		animais.setAnimais(listaAnimais);

		ResponseEntity<Animais> response = restTemplate.postForEntity(endpointAnimais + "/lista", animais,
				Animais.class);

		Assert.assertEquals(500, response.getStatusCodeValue());
	}

	/**
	 * Ao recuperar um animal existente e o atualizar, o mesmo deve reter as
	 * propriedades atualizadas
	 */
	@Test
	final void testUpdateAnimalPorId() {
		int id = 3;

		Animal animal = restTemplate.getForObject(endpointAnimais + "/" + id, Animal.class);
		animal.setTag("tag_x");

		HttpEntity<Animal> entity;
		entity = new HttpEntity<>(animal, headers);
		ResponseEntity<Animal> response = null;

		try {
			response = restTemplate.exchange(endpointAnimais + "/" + id, HttpMethod.PUT, entity, Animal.class);
		} catch (RestClientException e) {
			Assert.fail(String.format("Put falhou: %s", e.toString()));
		}
		
		Assert.assertEquals(animal, response.getBody());
	}

	/**
	 * Ao deletar um animal através de um DELETE no caminho /animais/1 retorna Http
	 * Status Code 200 (Sucesso)
	 */
	@Test
	final void testDeleteAnimalSucesso() {

		int id = 1;
		Animal animal = restTemplate.getForObject(endpointAnimais + "/" + id, Animal.class);

		Assert.assertNotNull(animal);

		restTemplate.delete(endpointAnimais + "/" + id);

		try {
			animal = restTemplate.getForObject(endpointAnimais + "/" + id, Animal.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
