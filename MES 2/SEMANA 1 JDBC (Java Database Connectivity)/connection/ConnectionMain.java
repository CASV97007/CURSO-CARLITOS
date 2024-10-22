package com.carlitos.baseDeDatos.connection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnectionMain {

	public static void main(String[] args) {
		String dbName = "carlitosdb";
		String user = "carlitos";
		String password = "Prueba.123";
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://localhost:1433;databaseName=" + dbName
				+ ";encrypt=true;trustServerCertificate=true;";

//		String DB_URL = "jdbc:sqlserver://[tu_servidor];databaseName=[nombre_de_tu_base_de_datos];user=[tu_usuario];password=[tu_contraseña]";
		Connection conn = null;
		try (Scanner scanner = new Scanner(System.in)) {
			// Carga el driver
			Class.forName(driver);

			// Establece la conexión
			conn = DriverManager.getConnection(url, user, password);

			if (conn != null) {
				System.out.println("Conexión establecida con éxito");

				mostrarMenu(conn, scanner);

			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

	}

	private static void mostrarMenu(Connection conn, Scanner scanner) throws SQLException {
		String continuar;
		do {
			System.out.println("Que operación deseas realizar:");
			System.out.println("1 - Mostrar Datos Clientes");
			System.out.println("2 - Dar de alta nuevo Cliente");
			System.out.println("3 - Modificar Cliente");
			System.out.println("4 - Dar de baja a un Cliente");
			System.out.print("Selecciona una opción: ");

			int opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {
			case 1:
				mostrarDatosClientes(conn);
				break;
			case 2:
				insertarCliente(conn, scanner);
				break;
			case 3:
				updateCliente(conn, scanner);
				break;
			case 4:
				deleteCliente(conn, scanner);
				break;
			default:
				System.out.println("Opción no válida.");
				mostrarMenu(conn, scanner);
			}

			System.out.print("¿Deseas realizar otra operación? (s/n): ");
			continuar = scanner.next();
			scanner.nextLine();
		} while (continuar.equalsIgnoreCase("s"));

	}

	private static void deleteCliente(Connection conn, Scanner scanner) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("delete from cliente where id=?");
		System.out.println("Que cliente quieres eliminar?");
		int id = scanner.nextInt();
		ps.setInt(1, id);
		System.out.println("Se ha eliminado el cliente  nº: " + scanner);

	}

	private static void updateCliente(Connection conn, Scanner scanner) throws SQLException {
		PreparedStatement ps = conn
				.prepareStatement("UPDATE cliente set nombre=?,apellido=?,telefono=?,direccion=? where id =? ");
		Cliente c = pedirDatosCliente(scanner);
		System.out.println("Que cliente quieres editar?");
		mostrarDatosClientes(conn);

		int id = scanner.nextInt();
		scanner.nextLine();
		ps.setInt(5, id);
		ps.setString(1, c.getNombre());
		ps.setString(2, c.getApellido());
		ps.setString(3, c.getTelefono());
		ps.setString(5, c.getDireccion());
		ps.execute();
		System.out.println("Se ha actualizado el cliente  Nº " + c.getId());

	}

	private static void insertarCliente(Connection conn, Scanner scanner) throws SQLException {
		PreparedStatement ps = conn
				.prepareStatement("insert into cliente(nombre,apellido,telefono,direccion) values(?,?,?,?) ");
		Cliente c = pedirDatosCliente(scanner);

		ps.setString(1, c.getNombre());
		ps.setString(2, c.getApellido());
		ps.setString(3, c.getTelefono());
		ps.setString(4, c.getDireccion());
		ps.execute();
		System.out.println("Se han insertado el nuevo cliente: " + c);
	}

	private static Cliente pedirDatosCliente(Scanner scanner) {
		System.out.print("Introduce el Nombre del cliente: ");
		String nombre = scanner.nextLine();
		System.out.print("Introduce el Apellido del cliente: ");
		String apellido = scanner.nextLine();
		System.out.print("Introduce el Telefono del cliente: ");
		String telefono = scanner.nextLine();
		System.out.print("Introduce la Direccion del cliente: ");
		String direccion = scanner.nextLine();
	
		return new Cliente(null, nombre, apellido, telefono, direccion);
	}

	private static void mostrarDatosClientes(Connection conn) {
		try {
			PreparedStatement ps = conn.prepareStatement("select * from cliente");
			ResultSet rs = ps.executeQuery();
			System.out.println("Info Clientes");
			while (rs.next()) {
				Cliente c = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));

				System.out.println(c);
			}

		} catch (SQLException sqlEx) {
			System.out.println("Notificando error en sql");
			sqlEx.printStackTrace();
			return;
		}

	}

}
