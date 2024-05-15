package ClasesJava;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * La clase GuardarEstado se utiliza para guardar y cargar el estado de la aplicación.
 * Esta clase implementa la interfaz Serializable para poder guardar y cargar los objetos en un archivo.
 *
 * @author sergiEscriva
 * @version 1.0
 */
public class GuardarEstado implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private static final String RUTA = "src\\FicherosGuardados\\EstadoStrava.dat";
	private List<Conductor> listaConductores;
	private List<Vehiculo> listaVehiculos;
	private List<Ruta> listaRutas;

	/**
	 * Constructor de la clase GuardarEstado.
	 * Inicializa las listas de conductores, vehículos y rutas.
	 */
	public GuardarEstado() {
		listaConductores = new ArrayList<>();
		listaVehiculos = new ArrayList<>();
		listaRutas = new ArrayList<>();
	}
	/**
	 * Guarda el estado actual de la aplicación en un archivo.
	 *
	 * @throws IOException Si ocurre un error de entrada/salida.
	 */
	public void guardarDatos() throws IOException {
		try (FileOutputStream fos = new FileOutputStream(RUTA);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(this);
		}
	}
	/**
	 * Carga el estado de la aplicación desde un archivo.
	 *
	 * @throws IOException Si ocurre un error de entrada/salida.
	 * @throws ClassNotFoundException Si no se puede encontrar la clase al cargar los datos.
	 */
	public void cargarDatos() throws IOException, ClassNotFoundException {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA))) {
			GuardarEstado estadoCargado = (GuardarEstado) ois.readObject();
			if (estadoCargado != null) {
				this.listaConductores = estadoCargado.obtenerConductores();
				this.listaVehiculos = estadoCargado.obtenerVehiculos();
				this.listaRutas = estadoCargado.obtenerRutas();
			}

		}
	}

	public void agregarConductor(Conductor conductor) {
		listaConductores.add(conductor);
	}

	public void eliminarConductor(Conductor conductor) {
		listaConductores.remove(conductor);
	}

	public List<Conductor> obtenerConductores() {
		return listaConductores;
	}

	public void agregarVehiculo(Vehiculo vehiculo) {
		listaVehiculos.add(vehiculo);
	}

	public void eliminarVehiculo(Vehiculo vehiculo) {
		listaVehiculos.remove(vehiculo);
	}

	public List<Vehiculo> obtenerVehiculos() {
		return listaVehiculos;
	}

	public void agregarRuta(Ruta ruta) {
		listaRutas.add(ruta);
	}

	public void eliminarRuta(Ruta ruta) {
		listaRutas.remove(ruta);
	}

	public List<Ruta> obtenerRutas() {
		return listaRutas;
	}


}
