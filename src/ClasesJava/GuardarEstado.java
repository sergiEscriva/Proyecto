package ClasesJava;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GuardarEstado implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private static final String RUTA = "src\\FicherosGuardados\\EstadoStrava.dat";
	private List<Conductor> listaConductores;
	private List<Vehiculo> listaVehiculos;
	private List<Ruta> listaRutas;

	public GuardarEstado() {
		listaConductores = new ArrayList<>();
		listaVehiculos = new ArrayList<>();
		listaRutas = new ArrayList<>();
	}

	public void guardarDatos() throws IOException {
		try (FileOutputStream fos = new FileOutputStream(RUTA);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			 oos.writeObject(this);
		}
	}
	public void cargarDatos() throws IOException, ClassNotFoundException {
		try (FileInputStream fis = new FileInputStream(RUTA);
			 ObjectInputStream ois = new ObjectInputStream(fis)) {
			GuardarEstado estadoCargado = (GuardarEstado) ois.readObject();
			this.listaConductores = estadoCargado.obtenerConductores();
			this.listaVehiculos = estadoCargado.obtenerVehiculos();
			this.listaRutas = estadoCargado.obtenerRutas();
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
