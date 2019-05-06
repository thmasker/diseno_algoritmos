import java.io.IOException;

public class P5 {
	public static void main(String [] args) throws IOException {
		long inicio, tiempoFuerzaBruta, tiempoKarpRabin, tiempoBoyerMoore;
		
		Leer leer = new Leer();
		String patron, archivo = "quijote1.txt";
		double porcentaje=0.0;
		do {
		
		System.out.println("-------------	BuscaBusca S.A. (�encuentra el patr�n!)	-------------\n");
		
		patron = leer.pedirString("Introduce el patr�n a buscar:");
		
		porcentaje = leer.pedirDoubleRango(0.0, 100.0, "Introduce el porcentaje de texto para estimar:");
		leer.pedirString();

		Algoritmos algoritmo = new Algoritmos(patron, archivo, porcentaje, 187);
		
		System.out.println("\n---------- Algoritmo Fuerza Bruta ----------");
		inicio = System.nanoTime();
		int fuerzaBruta = algoritmo.fuerzaBruta();
		tiempoFuerzaBruta = System.nanoTime() - inicio;
		System.out.println("\tOcurrencias en el texto: " + fuerzaBruta);
		System.out.printf("\tTiempo de ejecuci�n: %d ns\n", tiempoFuerzaBruta);
		
		System.out.println("\n---------- Algoritmo Karp-Rabin ----------");
		inicio = System.nanoTime();
		int karpRabin = algoritmo.karpRabin();
		tiempoKarpRabin = System.nanoTime() - inicio;
		System.out.println("\tOcurrencias en el texto: " + karpRabin);
		System.out.printf("\tTiempo de ejecuci�n: %d ns\n", tiempoKarpRabin);
		
		System.out.println("\n---------- Algoritmo Boyer-Moore ----------");
		inicio = System.nanoTime();
		int boyerMoore = algoritmo.boyerMoore();
		tiempoBoyerMoore = System.nanoTime() - inicio;
		System.out.println("\tOcurrencias en el texto: " + boyerMoore);
		System.out.printf("\tTiempo de ejecuci�n: %d ns\n", tiempoBoyerMoore);
		}while(porcentaje>0);
		System.out.println("\nHecho por Alberto Velasco Mata y Diego Pedregal Hidalgo, 2019 (C)");
		System.out.println("Sin derechos reservados :(");
	}
}
