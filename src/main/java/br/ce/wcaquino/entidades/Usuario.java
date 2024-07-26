package br.ce.wcaquino.entidades;

public class Usuario {

	private String nome;
	
	public Usuario() {}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Usuario other = (Usuario) obj;
		if ((nome == null && other.nome != null) || !nome.equals(other.nome)) {
			return false;
		}
		return true;
	}
	
	
	
	public Usuario(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}