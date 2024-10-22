package com.biblioteca.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.biblioteca.model.Libro;
import com.biblioteca.model.Usuario;

public class BibliotecaService {

	private Scanner scanner;
	private Map<Long, Libro> mapLibros;
	private Map<Long, Usuario> mapUsuarios;
	private static int countIdLibros = 0;
	private static int countIdUsuarios = 0;
	private SimpleDateFormat sdf;

	public BibliotecaService() {
		super();
		scanner = new Scanner(System.in);
		mapLibros = new HashMap<>();
		mapUsuarios = new HashMap<>();
		sdf = new SimpleDateFormat("dd/MM/yyyy");
	}

	public void mostrarMenu() {

		String continuar;

		do {
			System.out.println("¿Qué operacion deseas realizar?");
			System.out.println("1- Mostrar Libros");
			System.out.println("2- Agregar Libro");
			System.out.println("3- Buscar Libro");
			System.out.println("4- Registrar Usuario");
			System.out.println("5- Buscar Usuario");
			System.out.println("6- Mostrar Usuarios");
			System.out.println("7- Prestar Libro");
			System.out.println("8- Devolver Libro");

			int opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {
			case 1:
				mostrarLibros();
				break;
			case 2:
				agregarLibros();
				break;
			case 3:
				buscarLibros(0);
				break;
			case 4:
				registrarUsuarios();
				break;
			case 5:
				buscarUsuarios(0);
				break;
			case 6:
				mostrarUsuarios(false);
				break;
			case 7:
				prestarLibro();
				break;
			case 8:
				devolverLibro();
				break;
			default:
				System.out.println("Opción no válida. Seleccione de nuevo.");
				mostrarMenu();
			}

			System.out.print("¿Deseas realizar otra operación? (s/n)");
			continuar = scanner.next();
			scanner.nextLine();

		} while (continuar.equalsIgnoreCase("s"));

	}

	public void agregarLibros() {

		System.out.println("¿Cuántos libros tiene la Biblioteca?");
		int nLibros = scanner.nextInt();
		scanner.nextLine();

		for (int i = 0; i < nLibros; i++) {

			System.out.println("Introduce el titulo del libro " + (countIdLibros + 1) + ":");
			String titulo = scanner.nextLine();
			System.out.println("Disponibilidad del libro " + (countIdLibros + 1) + " TRUE/FALSE:");
			boolean disponibilidad = scanner.nextLine().equalsIgnoreCase("true") ? true : false;
			Date fecha = preguntarFecha();
			agregarLibro(titulo, null, disponibilidad, fecha);
		}

	}

	public void agregarLibro(String titulo, String autor, boolean disponibilidad, Date fechaPublicacion) {

		Libro libro = new Libro();
		countIdLibros++;
		libro.setId(countIdLibros);
		libro.setTitulo(titulo);
		libro.setAutor(autor);
		libro.setDisponible(disponibilidad);
		libro.setFechaPublicacion(fechaPublicacion);
		mapLibros.put(libro.getId(), libro);

	}

	public void agregarUsuario(String nombre) {

		Usuario usuario = new Usuario();
		countIdUsuarios++;
		usuario.setId(countIdUsuarios);
		usuario.setNombre(nombre);
		mapUsuarios.put(usuario.getId(), usuario);

	}

	public Date preguntarFecha() {

		Date fecha = null;
		System.out.println("Introduce la fecha de publicacion en formato: dd-MM-yyyy");
		try {
			fecha = sdf.parse(scanner.nextLine());
		} catch (ParseException e) {
			System.err.println("El formato de la fecha es incorrecto.");
			preguntarFecha();
		}
		return fecha;
	}

	private void registrarUsuarios() {

		System.out.println("¿Cuántos usuarios hay?");
		int nUsuarios = scanner.nextInt();
		scanner.nextLine();

		for (int i = 0; i < nUsuarios; i++) {

			System.out.println("Introduce el nombre del usuario " + countIdUsuarios + ":");
			agregarUsuario(scanner.nextLine());

		}

	}

	public void mostrarLibros() {

		for (Libro i : mapLibros.values()) {
			System.out.println(i + "\n--------------------------");
		}

	}

	public void mostrarUsuarios(boolean imprimirLibro) {

		for (Usuario i : mapUsuarios.values()) {
			System.out.println(i + "\n");
			if (imprimirLibro == true) {
				mostrarLibrosDeUsuarioPrestados(i);
			}
			System.out.println("\n--------------------------\n");
		}

	}

	public void buscarLibros(long clave) {

		System.out.println("¿Que numero de libro quieres buscar?");
		clave = scanner.nextLong();
		scanner.nextLine();
		Libro libro = mapLibros.get(clave);
		System.out.println(libro.toString());

	}

	public Libro buscarLibroBiblioteca() {

		System.out.println("Selecciona ID de LIBRO\n");
		mostrarLibros();
		Long clave = scanner.nextLong();
		scanner.nextLine();
		Libro libro = mapLibros.get(clave);
		return libro;
	}

	private Libro buscarLibroPrestado(Usuario usuario) {

		System.out.println("Selecciona ID de LIBRO prestado\n");
		mostrarLibrosDeUsuarioPrestados(usuario);
		Long clave = scanner.nextLong();
		scanner.nextLine();
		Libro libro = usuario.obtenerLibroPorId(clave);
		
		if(libro == null) {
			System.err.println("Error libro como null");
			buscarLibroPrestado(usuario);
		}
		return libro;

	}

	public void buscarUsuarios(long clave) {

		System.out.println("¿Que número de usuario quieres buscar?");
		clave = scanner.nextLong();
		scanner.nextLine();
		Usuario usuario = mapUsuarios.get(clave);
		System.out.println(usuario.toString());

	}

	public Usuario buscarUsuarioBiblioteca() {

		System.out.println("Selecciona ID de USUARIO:\n");
		mostrarUsuarios(true);
		Long clave = scanner.nextLong();
		scanner.nextLine();
		Usuario usuario = mapUsuarios.get(clave);
		return usuario;
	}

	public void prestarLibro() {

		// Se busca el libro a prestar
		Libro libroPrestado = buscarLibroBiblioteca();
		// Se cambia el estado del libro
		libroPrestado.setDisponible(false);
		// Se actualiza en la coleccion el libro prestado pasando la clave del libro
		// obtenido y libro modificado
		mapLibros.put(libroPrestado.getId(), libroPrestado);
		// Se busca el usuario que presta el libro
		Usuario usuario = buscarUsuarioBiblioteca();
		// Se añade el libro a prestar a la lista de libros prestados por el usuario
		usuario.getLibrosPrestados().add(libroPrestado);
		// Se actualiza en la coleccion de usuarios de la bibliota el usuario
		mapUsuarios.put(usuario.getId(), usuario);

	}

	public void mostrarLibrosDeUsuarioPrestados(Usuario usuario) {
		for (Libro i : usuario.getLibrosPrestados()) {
			System.out.println(i);
		}
	}

	public void devolverLibro() {

		try {

			// Se pregunta que usuario quiere devolver un libro y lo almacena en la variable
			// local 'usuario'
			Usuario usuario = buscarUsuarioBiblioteca();
			// Se pregunta que libro quiere devolver el usuario y lo almacena en la variable
			// local 'libro'
			Libro libro = buscarLibroPrestado(usuario);
			// Se elimina el libro prestado del usuario seleccionado
			usuario.getLibrosPrestados().remove(libro);
			// Se actualiza el los libros del usuario
			mapUsuarios.put(usuario.getId(), usuario);
			// Se cambia la disponibilidad a 'true' del libro
			libro.setDisponible(true);
			// Se actualiza el libro en la biblioteca
			mapLibros.put(libro.getId(), libro);
			
		} catch (Exception error) {
			error.printStackTrace();
			System.err.println(
					"Ha ocurrido un error en el metodo 'devolverLibro' intentalo de nuevo. Comprueba que el Usuario o el libro exista");
			System.out.println("¿Quieres continuar devolviendo un libro? s/n");

			String continuar = scanner.next();

			if (continuar.equalsIgnoreCase("s")) {
				devolverLibro();
			}

		}

	}

	public void registrarUsuarios(Usuario u1) {
		mapUsuarios.put(u1.getId(), u1);

	}
}