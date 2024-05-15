package ClasesJava;

import Enums.EstadoVehiculo;

import java.util.Objects;

public class Vehiculo {
	private String matricula;
	private String modelo;
	private int plazas;
	private double km;
	private int tipoMotos;
	private EstadoVehiculo estadoVehiculo;

	public Vehiculo() {
	}

	public Vehiculo(String matricula, String modelo, int plazas, double km, int tipoMotos) {
		this.matricula = matricula;
		this.modelo = modelo;
		this.plazas = plazas;
		this.km = km;
		this.tipoMotos = tipoMotos;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	public double getKm() {
		return km;
	}

	public void setKm(double km) {
		this.km = km;
	}

	public int getTipoMotos() {
		return tipoMotos;
	}

	public void setTipoMotos(int tipoMotos) {
		this.tipoMotos = tipoMotos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vehiculo vehiculo = (Vehiculo) o;
		return plazas == vehiculo.plazas && Double.compare(km, vehiculo.km) == 0 && tipoMotos == vehiculo.tipoMotos && Objects.equals(matricula, vehiculo.matricula) && Objects.equals(modelo, vehiculo.modelo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(matricula, modelo, plazas, km, tipoMotos);
	}

	@Override
	public String toString() {
		return "ClasesJava.Vehiculo{" +
				"matricula='" + matricula + '\'' +
				", modelo='" + modelo + '\'' +
				", plazas=" + plazas +
				", km=" + km +
				", tipoMotos=" + tipoMotos +
				'}';
	}
}
