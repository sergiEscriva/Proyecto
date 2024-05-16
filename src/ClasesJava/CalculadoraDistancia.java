package ClasesJava;

public class CalculadoraDistancia {
	private static final double TIERRA_RADIO = 6371.0;

	public static double convertirRadianes(double grados) {
		return grados * Math.PI / 180;
	}

	public static double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
		latitud1 = convertirRadianes(latitud1);
		latitud2 = convertirRadianes(latitud2);
		longitud1 = convertirRadianes(longitud1);
		longitud2 = convertirRadianes(longitud2);

		double diferenciaLatitud = latitud1 - latitud2;
		double diferenciaLongitud = longitud1 - longitud2;

		double a = Math.sin(diferenciaLatitud / 2) * Math.sin(diferenciaLatitud / 2) +
				   Math.cos(latitud1) * Math.cos(latitud2) *
				   Math.sin(diferenciaLongitud / 2) * Math.sin(diferenciaLongitud / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return TIERRA_RADIO * c;

	}
}
