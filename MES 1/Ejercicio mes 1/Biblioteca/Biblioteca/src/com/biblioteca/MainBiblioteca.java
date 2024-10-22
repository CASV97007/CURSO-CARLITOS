package com.biblioteca;

import java.util.Date;

import com.biblioteca.service.BibliotecaService;

public class MainBiblioteca {

	public static void main(String[] args) {
		
		
		BibliotecaService bs = new BibliotecaService();
		bs.agregarLibro("Don Quijote de tu puta madre", "Quevedo (QUEEEEEEEEDATE)", true, new Date());
		bs.agregarLibro("MI LIBRO, Luna de Pluton", "Dross", true, new Date());
		bs.agregarLibro("Pocoyo", "Tu madre", true, new Date());
		bs.agregarUsuario("Christan");
		bs.agregarUsuario("Carlos");
		bs.agregarUsuario("Sara");
		bs.mostrarMenu();
	}

}
