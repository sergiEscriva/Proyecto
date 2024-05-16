package Enums;

public enum EstadoVehiculo {
	DISPONIBLE("Disponible","El vehiculo esta disponible"),
	//EN_RUTA("En ruta", "El vehiculo se encuentra en una ruta"),
	MANTENIMIENTO("En mantenimiento", "El vehiculo se encuentra en revision o reparandose");


	private String estado;
	private String descripcion;

	EstadoVehiculo(String estado, String descripcion) {
		this.estado = estado;
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
