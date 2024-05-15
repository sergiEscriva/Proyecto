package ClasesJava;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Strava {
	private static List<Conductor> listaConductores = new ArrayList<>();
	private static List<Vehiculo> listaVehiculos = new ArrayList<>();
	private static List<Ruta> listaRutas = new ArrayList<>();

	public static void main(String[] args) {
		imprimirInicio();
		Scanner sc = new Scanner(System.in);
		int opcion = 1;

		switch (opcion){
			case 1:
		}


	}
	private static void imprimirInicio(){
		System.out.println("                                                         _________________________   \n" +
				"                    /\\\\      _____          _____       |   |     |     |    | |  \\  \n" +
				"     ,-----,       /  \\\\____/__|__\\_    ___/__|__\\___   |___|_____|_____|____|_|___\\ \n" +
				"  ,--'---:---`--, /  |  _     |     `| |      |      `| |                    | |    \\\n" +
				" ==(o)-----(o)==J    `(o)-------(o)=   `(o)------(o)'   `--(o)(o)--------------(o)--'  \n" +
				"`````````````````````````````````````````````````````````````````````````````````````\n");
		System.out.println("Bienvenido a ClasesJava.Strava Automation\n" +
				"1. Crear ClasesJava.Conductor\n" +
				"2. Crear Vechiculo\n" +
				"3. Crear ClasesJava.Ruta");
	}
}
