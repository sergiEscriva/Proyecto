package ClasesJava;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GuardarEstado implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String RUTA = "src\\ficheros\\EstadoStrava.txt";
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

}
