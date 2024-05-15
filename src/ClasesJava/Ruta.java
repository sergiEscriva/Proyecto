package ClasesJava;

import java.util.Objects;

public class Ruta {
	private String origen;
	private String destino;
	private double duracion;
	private double distancia;
	private Vehiculo vehiculo;
	private Conductor conductor;

	public Ruta() {
	}

	public Ruta(String origen, String destino, double duracion, double distancia, Vehiculo vehiculo, Conductor conductor) {
		this.origen = origen;
		this.destino = destino;
		this.duracion = duracion;
		this.distancia = distancia;
		this.vehiculo = vehiculo;
		this.conductor = conductor;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ruta ruta = (Ruta) o;
		return Double.compare(duracion, ruta.duracion) == 0 && Double.compare(distancia, ruta.distancia) == 0 && Objects.equals(origen, ruta.origen) && Objects.equals(destino, ruta.destino) && Objects.equals(vehiculo, ruta.vehiculo) && Objects.equals(conductor, ruta.conductor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(origen, destino, duracion, distancia, vehiculo, conductor);
	}

	@Override
	public String toString() {
		return "ClasesJava.Ruta{" +
				"origen='" + origen + '\'' +
				", destino='" + destino + '\'' +
				", duracion=" + duracion +
				", distancia=" + distancia +
				", vehiculo=" + vehiculo +
				", conductor=" + conductor +
				'}';
	}
}
