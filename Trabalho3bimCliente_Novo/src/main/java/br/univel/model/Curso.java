package br.univel.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Curso implements Serializable {
	//Alterado para commit
	private Long id;
	private String nome;
	private int version;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
}
