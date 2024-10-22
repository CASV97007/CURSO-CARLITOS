package com.biblioteca.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Libro {

	private long id;
	private String titulo;
	private String autor;
	private Date fechaPublicacion;
	private boolean disponible;
	static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	public Libro() {
		super();
	}

	public Libro(long id, String titulo, String autor, Date fechaPublicacion, boolean disponible) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.fechaPublicacion = fechaPublicacion;
		this.disponible = disponible;
	}

	@Override
	public String toString() {
		return "Libro id= " + id + "\nTitulo= " + titulo + "\nAutor= " + autor + "\nFecha Publicacion= "
				+ formato.format(fechaPublicacion) + "\nDisponible= [" + disponible + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

}
