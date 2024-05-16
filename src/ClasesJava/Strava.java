package ClasesJava;

import Enums.TipoMotor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strava implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger();

	private static List<Conductor> listaConductores = new ArrayList<>();
	private static List<Vehiculo> listaVehiculos = new ArrayList<>();
	private static List<Ruta> listaRutas = new ArrayList<>();
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_RESET = "\u001B[0m";

	private static HashMap<Conductor, PriorityQueue<Vehiculo>> listaAsignada = new HashMap<>();

	public static void main(String[] args) {
		imprimirDibujo();
		Scanner sc = new Scanner(System.in);

		int opcion = 0;
		do {
			try {
				System.out.println("Bienvenido a Strava Automation\n" +
						"1. Crear Conductor\n" +
						"2. Crear Vechiculo\n" +
						"3. Eliminar Conductor\n" +
						"4. Lista Conuctores");

				opcion = sc.nextInt();

				switch (opcion) {
					case 1:
						if (crearConductor(sc)) {
							System.out.println("Conductor creado correctamene");
						} else {
							System.out.println("No se ha podido crear el concuctor");
						}
						break;
					case 2:
						if (crearVehiculo(sc)) {
							System.out.println("Vehiculo creado correctamente");
						} else {
							System.out.println("No se ha podido crear el vehiculo");
						}
						break;
					case 3:
						eliminarConductor(sc);
						break;
					case 4:
						mostrarConductores();
						break;
					case 5:
						mostrarVehiculos();
						break;
					case 6:
						crearRuta();
						break;
					default:
						System.out.println("Opcion no valida");
						opcion = 0;

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (opcion >= 1 && opcion <= 6);

	}

	private static void imprimirDibujo() {
		System.out.println("                                                         _________________________   \n" +
				"                    /\\\\      _____          _____       |   |     |     |    | |  \\  \n" +
				"     ,-----,       /  \\\\____/__|__\\_    ___/__|__\\___   |___|_____|_____|____|_|___\\ \n" +
				"  ,--'---:---`--, /  |  _     |     `| |      |      `| |                    | |    \\\n" +
				" ==(o)-----(o)==J    `(o)-------(o)=   `(o)------(o)'   `--(o)(o)--------------(o)--'  \n" +
				"`````````````````````````````````````````````````````````````````````````````````````\n");

	}

	private static boolean crearConductor(Scanner sc) {
		sc.nextLine();
		String nombre;
		String id;
		try {
			System.out.print(ANSI_BLUE + "Inserte el nombre: ");
			nombre = sc.nextLine();
			do {
				System.out.print("Inserte el id\n" +
						"Formato: 111A");
				id = sc.nextLine();
			} while (comprobarId(id));
			Conductor conductor = new Conductor(nombre, id);
			listaConductores.add(conductor);
			return Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error en la creacion del Conductor");
		} finally {
			System.out.println(ANSI_RESET);
		}
		return Boolean.FALSE;
	}

	private static boolean crearVehiculo(Scanner sc) {
		if (listaConductores.isEmpty()) {
			System.out.println("Deber haber almenos 1 conductor registrado");
			return Boolean.FALSE;
		}

		sc.nextLine();
		String matricula;
		String marca;
		String modelo;
		Conductor conductor = obtenerConductor(sc);
		PriorityQueue<Vehiculo> vehiculos = new PriorityQueue<>();

		if (conductor == null) {
			System.out.println("Conductor no encontrado saliendo de la creacion del Vehiculo");
		}

		System.out.println("Cuantos vehiculos desea añadir al conductor " + conductor.getId());
		int numVechiculos = sc.nextInt();
		for (int i = 0; i < numVechiculos; i++) {
			sc.nextLine();

			try {
				do {
					System.out.println(ANSI_BLUE + "Inserte la matricula: \n" +
							"Formato: 1111AAA");
					matricula = sc.nextLine();
				} while (!comprobarMatricula(matricula));

				System.out.print("Inserte la marca: ");
				marca = sc.nextLine();

				System.out.print("Inserte el modelo: ");
				modelo = sc.nextLine();

				int numPlazas = obtenerNumPlazas(sc);

				double km = obtenerKm(sc);

				TipoMotor tipoMotor = seleccionarMotor(sc);
				Vehiculo vehiculo = new Vehiculo(matricula, marca, modelo, numPlazas, km, tipoMotor);
				vehiculos.add(vehiculo);
				System.out.println("Vehiculo insertado correctamente");

				listaVehiculos.add(vehiculo);

			} catch (Exception e) {
				LOGGER.error("Fallo al crear el vehiculo");
			}
		}
		listaAsignada.put(conductor, vehiculos);
		return Boolean.FALSE;
	}

	private static boolean comprobarId(String palabra) {

		Pattern pattern = Pattern.compile("^[0-9]{3}[A-Z]]$");
		Matcher matcher = pattern.matcher(palabra);
		return matcher.matches();
	}

	private static boolean comprobarMatricula(String palabra) {
		Pattern pattern = Pattern.compile("^[0-9]{4}[A-Z]{3}$");
		Matcher matcher = pattern.matcher(palabra);
		return matcher.matches();
	}

	private static TipoMotor seleccionarMotor(Scanner sc) {
		TipoMotor tipoMotorAElegir = null;
		System.out.println("Seleccione tipo de motor\n" +
				"1. Diesel\n" +
				"2. Gasolina\n" +
				"3. Electrico\n" +
				"4. Hibrido");
		int opcion = sc.nextInt();

		switch (opcion) {
			case 1:
				tipoMotorAElegir = TipoMotor.DIESEL;
				break;
			case 2:
				tipoMotorAElegir = TipoMotor.GASOLINA;
				break;
			case 3:
				tipoMotorAElegir = TipoMotor.ELECTRICO;
				break;
			case 4:
				tipoMotorAElegir = TipoMotor.HIBRIDO;
				break;
			default:
				System.out.println("Numero no valido");
		}

		return tipoMotorAElegir;

	}

	private static double obtenerKm(Scanner sc) {
		double km = 0;
		boolean validKm;
		do {
			System.out.println("Inserte Kilometros totales");
			if (sc.hasNextDouble()) {
				km = sc.nextDouble();
				validKm = true;
			} else {
				System.out.println("Por favor, introduzca un número válido para los kilómetros.");
				validKm = false;
				sc.next(); // Descarta la entrada incorrecta
			}
		} while (!validKm);
		return km;
	}

	private static int obtenerNumPlazas(Scanner sc) {
		int numPlazas = 0;
		boolean validNumPlazas;
		do {
			System.out.print("Inserte numero de plazas: ");
			if (sc.hasNextInt()) {
				numPlazas = sc.nextInt();
				validNumPlazas = true;
			} else {
				System.out.println("Por favor, introduzca un número válido para las plazas.");
				validNumPlazas = false;
				sc.next();
			}
		} while (!validNumPlazas);
		return numPlazas;
	}

	private static void eliminarConductor(Scanner sc) {
		int contador = 0;
		System.out.println("Inserte id del Conductor");
		System.err.println("SE ELIMINARAN LOS COCHES ASOCIADOS AL USUARIO");
		sc.nextLine();
		String id = sc.nextLine();
		for (int i = 0; i < listaConductores.size(); i++) {
			if (listaConductores.get(i).getId().equalsIgnoreCase(id)) {
				listaConductores.remove(listaConductores.get(i));
				contador++;
			}
		}
		if (contador != 0) {
			System.out.println("Se han eliminado " + contador + " conductor/es");
		}
	}

	private static void mostrarConductores() {
		if (listaConductores.isEmpty()) {
			System.out.println("No existen conductores\n");
			return;
		}
		for (Conductor conductor : listaConductores) {

			System.out.print(" | " + conductor + " | ");
		}
	}

	private static void mostrarVehiculos() {
		if (listaVehiculos.isEmpty()) {
			System.out.println("So existen vehiculos\n");
			return;
		}
		for (Vehiculo vehiculo : listaVehiculos) {
			System.out.println(" | " + vehiculo + " | ");
		}


	}

	private static Conductor obtenerConductor(Scanner sc) {
		System.out.print("Inserte el id del conductor: ");
		String id = sc.nextLine();
		for (int i = 0; i < listaConductores.size(); i++) {
			if (listaConductores.get(i).getId().equalsIgnoreCase(id)) {
				return listaConductores.get(i);
			}
		}
		return null;
	}

	private static void crearRuta(Scanner sc){

	}
}
