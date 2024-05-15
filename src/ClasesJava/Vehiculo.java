package ClasesJava;

import Enums.EstadoVehiculo;
import Enums.TipoMotor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Vehiculo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private String matricula;
	private String marca;
	private String modelo;
	private int plazas;
	private double km;
	private TipoMotor tipoMotor;
	private EstadoVehiculo estadoVehiculo;

	public Vehiculo() {
	}

	public Vehiculo(String matricula, String marca, String modelo, int plazas, double km, TipoMotor tipoMotor) {
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.plazas = plazas;
		this.km = km;
		this.tipoMotor = tipoMotor;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public EstadoVehiculo getEstadoVehiculo() {
		return estadoVehiculo;
	}

	public void setEstadoVehiculo(EstadoVehiculo estadoVehiculo) {
		this.estadoVehiculo = estadoVehiculo;
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

	public TipoMotor getTipoMotor() {
		return tipoMotor;
	}

	public void setTipoMotor(TipoMotor tipoMotor) {
		this.tipoMotor = tipoMotor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vehiculo vehiculo = (Vehiculo) o;
		return plazas == vehiculo.plazas && Double.compare(km, vehiculo.km) == 0 && tipoMotor == vehiculo.tipoMotor && Objects.equals(matricula, vehiculo.matricula) && Objects.equals(modelo, vehiculo.modelo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(matricula, modelo, plazas, km, tipoMotor);
	}

	@Override
	public String toString() {
		return "ClasesJava.Vehiculo{" +
				"matricula='" + matricula + '\'' +
				", modelo='" + modelo + '\'' +
				", plazas=" + plazas +
				", km=" + km +
				", tipoMotos=" + tipoMotor +
				'}';
	}
}
