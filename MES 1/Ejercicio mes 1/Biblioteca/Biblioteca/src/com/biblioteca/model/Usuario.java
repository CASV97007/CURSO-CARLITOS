package com.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

	private long id;
	private String nombre;
	private List<Libro> librosPrestados;

	public Usuario() {
		super();
		librosPrestados = new ArrayList<Libro>();
	}

	public Libro obtenerLibroPorId(Long id) {

		for (Libro i : getLibrosPrestados()) {

			if (i.getId() == id) {
				return i;
			}

		}
		return null;

	}

	public Usuario(long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		librosPrestados = new ArrayList<Libro>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Libro> getLibrosPrestados() {
		return librosPrestados;
	}

	public void setLibrosPrestados(List<Libro> librosPrestados) {
		this.librosPrestados = librosPrestados;
	}

	public Usuario(long id, String nombre, List<Libro> librosPrestados) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.librosPrestados = librosPrestados;
	}

	@Override
	public String toString() {
		return "Usuario id= " + id + "\nNombre= " + nombre;
	}

}
