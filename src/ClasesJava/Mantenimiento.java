package ClasesJava;

import Enums.TipoMantenimiento;

import java.util.Objects;

public class Mantenimiento {
	private double costo;
	private Vehiculo vehiculo;
	private TipoMantenimiento tipoMantenimiento;
	private String parte;

	public Mantenimiento(double costo, Vehiculo vehiculo, TipoMantenimiento tipoMantenimiento, String parte) {
		this.costo = costo;
		this.vehiculo = vehiculo;
		this.tipoMantenimiento = tipoMantenimiento;
		this.parte = parte;
	}

	public Mantenimiento() {
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public TipoMantenimiento getTipoMantenimiento() {
		return tipoMantenimiento;
	}

	public void setTipoMantenimiento(TipoMantenimiento tipoMantenimiento) {
		this.tipoMantenimiento = tipoMantenimiento;
	}

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Mantenimiento that = (Mantenimiento) o;
		return Double.compare(costo, that.costo) == 0 && Objects.equals(vehiculo, that.vehiculo) && tipoMantenimiento == that.tipoMantenimiento && Objects.equals(parte, that.parte);
	}

	@Override
	public int hashCode() {
		return Objects.hash(costo, vehiculo, tipoMantenimiento, parte);
	}

	@Override
	public String toString() {
		return "ClasesJava.Mantenimiento{" +
				"costo=" + costo +
				", vehiculo=" + vehiculo +
				", tipoMantenimiento=" + tipoMantenimiento +
				", parte='" + parte + '\'' +
				'}';
	}
}
