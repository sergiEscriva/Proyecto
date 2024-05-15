package ClasesJava;

import Enums.TipoMotor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strava implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger();
	static final GuardarEstado SAVE = new GuardarEstado();

	private static List<Conductor> listaConductores = new ArrayList<>();
	private static List<Vehiculo> listaVehiculos = new ArrayList<>();
	private static List<Ruta> listaRutas = new ArrayList<>();
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_RESET = "\u001B[0m";

	public static void main(String[] args) {
		imprimirInicio();
		cargarDatos();
		Scanner sc = new Scanner(System.in);
		int opcion = 0;
		do {


			try {
				opcion = sc.nextInt();

				switch (opcion) {
					case 1:
						crearConductor(sc);
						break;
					case 2:
						crearVehiculo(sc);
						break;
					case 3:
						eliminarConductor(sc);
						break;
					default:
						System.out.println("Opcion no valida");
						opcion = 0;

				}
				SAVE.guardarDatos();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (opcion >= 1 && opcion <= 3);
	}


	private static void cargarDatos() {
		File file = new File("src\\FicherosGuardados\\EstadoStrava.dat");
		if (file.length() == 0) {
			System.err.println("No hay datos que cargar");
			return;
		}
		try {
			SAVE.cargarDatos();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error de entrada/salida al cargar los datos");
			LOGGER.error("Error de entrada/salida: ", e);
		}

		listaConductores = SAVE.obtenerConductores();
		listaVehiculos = SAVE.obtenerVehiculos();
		listaRutas = SAVE.obtenerRutas();
	}

	private static void imprimirInicio() {
		System.out.println("                                                         _________________________   \n" +
				"                    /\\\\      _____          _____       |   |     |     |    | |  \\  \n" +
				"     ,-----,       /  \\\\____/__|__\\_    ___/__|__\\___   |___|_____|_____|____|_|___\\ \n" +
				"  ,--'---:---`--, /  |  _     |     `| |      |      `| |                    | |    \\\n" +
				" ==(o)-----(o)==J    `(o)-------(o)=   `(o)------(o)'   `--(o)(o)--------------(o)--'  \n" +
				"`````````````````````````````````````````````````````````````````````````````````````\n");
		System.out.println("Bienvenido a Strava Automation\n" +
				"1. Crear Conductor\n" +
				"2. Crear Vechiculo\n" +
				"3. Crear Ruta");
	}

	private static void crearConductor(Scanner sc) {
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
			SAVE.agregarConductor(conductor);

		} catch (Exception e) {
			LOGGER.error("Error en la creacion del Conductor");
		} finally {
			System.out.println(ANSI_RESET);
		}
	}

	private static void crearVehiculo(Scanner sc) {
		sc.nextLine();
		String matricula;
		String marca;
		String modelo;


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
			listaVehiculos.add(vehiculo);
			SAVE.agregarVehiculo(vehiculo);

		} catch (Exception e) {
			LOGGER.error("Fallo al crear el vehiculo");
		}
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

	private static void eliminarConductor(Scanner sc){
		System.out.println("Inserte id del Conductor");
		System.err.println("SE ELIMINARAN LOS COCHES ASOCIADOS AL USUARIO");
		sc.nextLine();
		String id = sc.nextLine();
		Iterator<Conductor> iterator = SAVE..iterator();
		while (iterator.hasNext()) {
			Conductor conductor = iterator.next();
			if (conductor.getId().equalsIgnoreCase(id)){
				iterator.remove();
				listaConductores.remove(conductor);
				SAVE.eliminarConductor(conductor);
			}
		}
	}
}
