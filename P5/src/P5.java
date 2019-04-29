import java.io.IOException;

public class P5 {
	public static void main(String [] args) throws IOException {
		Leer leer = new Leer();
		String patron, archivo = "quijote1.txt";
		double porcentaje;
		
		System.out.println("-------------	BuscaBusca S.A. (¡encuentra el patrón!)	-------------\n");
		do {
			patron = leer.pedirString("Introduce el patrón a buscar:");
			
			porcentaje = leer.pedirDoubleRango(0.0, 100.0, "Introduce el porcentaje de texto para estimar:");

			Algoritmos algoritmo = new Algoritmos(patron, archivo, porcentaje, 187);
			
			System.out.println("Ocurrencias en el texto: " + algoritmo.fuerzaBruta());
			
			System.out.println("\n¿Desea buscar más patrones? (y/n)");
		} while(leer.pedirChar() == 'y');

		System.out.println("\nHecho por Alberto Velasco Mata y Diego Pedregal Hidalgo, 2019 (C)");
		System.out.println("Sin derechos reservados :(");
	}
}
