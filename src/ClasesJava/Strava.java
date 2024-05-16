package ClasesJava;

import Enums.Ciudades;
import Enums.TipoMotor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strava implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger();
	private static final String RUTA = "src\\FicherosGuardados\\EstadoStrava.dat";
	private static List<Conductor> listaConductores = new ArrayList<>();
	private static List<Vehiculo> listaVehiculos = new ArrayList<>();
	private static List<Ruta> listaRutas = new ArrayList<>();
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_RESET = "\u001B[0m";

	private static HashMap<Conductor, PriorityQueue<Vehiculo>> listaAsignada = new HashMap<>();

	public static void main(String[] args) {
		imprimirDibujo();
		Scanner sc = new Scanner(System.in);
		cargarEstado();
		int opcion = 0;
		do {
			try {
				System.out.println("Bienvenido a Strava Automation\n" +
						"1. Crear Conductor\n" +
						"2. Crear Vechiculo\n" +
						"3. Eliminar Conductor\n" +
						"4. Lista Conuctores\n" +
						"5. Crear Ruta");

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
							System.out.println("Todos los vehiculos creados correctamente");
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
						crearRuta(sc);
						break;

					default:
						System.out.println("Opcion no valida");
						opcion = 0;

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (opcion >= 1 && opcion <= 7);
		guardarEstado();

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
			} while (comprobarId(id) || idUsado(id));
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

	private static boolean idUsado(String id) {
		for (Conductor conductor : listaConductores) {
			if (conductor.getId().equalsIgnoreCase(id)) {
				System.out.println("id ya en uso");
				return true;
			}
		}
		return false;
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
				System.out.println("Vehiculo insertado correctamente" + ANSI_RESET);

				listaVehiculos.add(vehiculo);

			} catch (Exception e) {
				LOGGER.error("Fallo al crear el vehiculo" + ANSI_RESET);
			}
		}
		listaAsignada.put(conductor, vehiculos);
		return Boolean.TRUE;
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
				sc.next();
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

			System.out.print(" | " + conductor + " |\n");
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

	private static void crearRuta(Scanner sc) {
		if (listaVehiculos.isEmpty() || listaConductores.isEmpty()) {
			System.out.println("Debe haber vehiculos y conductores para crear una ruta");
			return;
		}
		Ciudades[] ciudades = Ciudades.values();
		System.out.println(ANSI_BLUE + Arrays.toString(ciudades) + ANSI_RESET + "\n" + "Escriba sin '_'");
		System.out.println("Inserte ubicacion de origen");
		Ciudades origen = obtenerUbicacion(sc, ciudades);

		System.out.println("Inserte ubicacion de destino");
		Ciudades destino = obtenerUbicacion(sc, ciudades);

		double distancia = CalculadoraDistancia.calcularDistancia(origen.getLatitud(), origen.getLongitud(), destino.getLatitud(), destino.getLongitud());

		System.out.print("Seleccione conductor:");
		Conductor conductor = obtenerConductorVinculado(sc);

		System.out.println("Seleccione el vehiculo del conductor");
		Vehiculo vehiculo = obtenerVehiculoVinculado(sc, conductor);

		Ruta ruta = new Ruta(origen, destino, distancia, vehiculo, conductor);
		listaRutas.add(ruta);
	}

	private static Ciudades obtenerUbicacion(Scanner sc, Ciudades[] ciudades) {

		Ciudades ciudadEncontrada = null;

		while (ciudadEncontrada == null) {
			String nombreCiudad = sc.nextLine();
			for (Ciudades ciudad : ciudades) {
				if (ciudad.getNombreCiudad().equalsIgnoreCase(nombreCiudad)) {
					ciudadEncontrada = ciudad;
					break;
				}
			}
			if (ciudadEncontrada == null) {
				System.out.println("la ciudad no se ha encontrado\n" +
						"Vuelva a intentarlo");

			}

		}
		return ciudadEncontrada;
	}

	private static Conductor obtenerConductorVinculado(Scanner sc) {
		sc.nextLine();

		Conductor conductor = null;
		while (conductor == null) {
			System.out.println("Inserte el id del conductor");
			mostrarConductores();
			String id = sc.nextLine();
			for (Map.Entry<Conductor, PriorityQueue<Vehiculo>> lista : listaAsignada.entrySet()) {
				if (lista.getKey().getId().equalsIgnoreCase(id)) {
					System.out.println("Conductor seleccionado: " + lista.getKey().getNombre());
					conductor = lista.getKey();
					return conductor;
				}
			}
			if (conductor == null) {
				System.out.println("Conductor no encontrado vuelva a intentarlo");
			}
		}
		return null;
	}

	private static Vehiculo obtenerVehiculoVinculado(Scanner sc, Conductor conductor) {
		Vehiculo vehiculo = null;
		while (vehiculo == null) {
			for (Map.Entry<Conductor, PriorityQueue<Vehiculo>> lista : listaAsignada.entrySet()) {

				if (lista.getKey().equals(conductor)) {

					System.out.println(" | " + lista.getValue().toString() + " | ");
					System.out.println("Seleccione el vehiculo por la matricula");
					String matricula = sc.nextLine();

					for (Vehiculo vehiculoABuscar : lista.getValue()) {
						if (vehiculoABuscar.getMatricula().equalsIgnoreCase(matricula)) {
							vehiculo = vehiculoABuscar;
							break;
						}
					}
					if (vehiculo == null) {
						System.out.println("Vehiculo no encontrado, por favor, vuelva a intentarlo.");
					} else {
						break;
					}

				} else {
					System.out.println("Conductor no valido, reinicie la aplicacion");
				}
			}
		}
		return vehiculo;
	}

	public static void guardarEstado() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA))) {
			oos.writeObject(listaConductores);
			oos.writeObject(listaVehiculos);
			oos.writeObject(listaRutas);
			oos.writeObject(listaAsignada);
			System.out.println("El estado de la aplicación se ha guardado en EstadoStrava.dat");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static void cargarEstado() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(RUTA))) {

			listaConductores = (List<Conductor>) in.readObject();
			listaVehiculos = (List<Vehiculo>) in.readObject();
			listaRutas = (List<Ruta>) in.readObject();
			listaAsignada = (HashMap<Conductor, PriorityQueue<Vehiculo>>) in.readObject();
		} catch (IOException | ClassNotFoundException i) {
			i.printStackTrace();
			return;
		}
	}
}
