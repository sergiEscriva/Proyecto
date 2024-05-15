package ClasesJava;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Conductor implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private String id;
	private ArrayList<Vehiculo> listaVehiculos;
	private boolean disponible;

	public Conductor() {
	}

	public Conductor(String nombre, String id) {
		this.nombre = nombre;
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Vehiculo> getListaVehiculos() {
		return listaVehiculos;
	}

	public void setListaVehiculos(ArrayList<Vehiculo> listaVehiculos) {
		this.listaVehiculos = listaVehiculos;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Conductor conductor = (Conductor) o;
		return id == conductor.id && disponible == conductor.disponible && Objects.equals(nombre, conductor.nombre) && Objects.equals(listaVehiculos, conductor.listaVehiculos);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, id, listaVehiculos, disponible);
	}



	@Override
	public String toString() {
		return "ClasesJava.Conductor{" +
				"nombre='" + nombre + '\'' +
				", id=" + id +
				", listaVehiculos=" + listaVehiculos +
				", disponible=" + disponible +
				'}';
	}
}
