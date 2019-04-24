public class P5 {
	public static void main(String [] args) {
		Leer leer = new Leer();
		
		System.out.println("-------------	BuscaBusca S.A. (¡encuentra el patrón!)	-------------\n");
		do {
			leer.pedirString("Introduce el patrón a buscar:");
			
			System.out.println("\n¿Desea buscar más patrones? (y/n)");
		} while(leer.pedirChar() == 'y');
		
		System.out.println("\nHecho por Alberto Velasco Mata y Diego Pedregal Hidalgo, 2019 (C)");
		System.out.println("Sin derechos reservados :(");
	}
}
