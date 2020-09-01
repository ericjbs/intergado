package br.com.intergado.entrevista.ericsilva.persistence.models;

import java.util.List;

public class Animais {
	List<Animal> animais;

	public List<Animal> getAnimais() {
		return animais;
	}

	public void setAnimais(List<Animal> animais) {
		this.animais = animais;
	}
	
	public void add(Animal animal) {
		this.animais.add(animal);
	}

	@Override
	public String toString() {
		return String.format("Animais [animais=%s]", animais);
	}
	
}
