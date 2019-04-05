import java.util.Stack;

public class P1_2 {
	public static int ITERATIONS;
	public static int N;
	public static int K = N/2;
	
	private static long factorial(int n) {
		long n_factorial = 1;
		
		for(int i = n; i > 1; i--) {
			n_factorial *= i;
		}
		
		return n_factorial;
	}
	
	private static long combinatorio_iterativo(int n, int k) {
		return factorial(n)/(factorial(k) * factorial(n-k));
	}
	
	private static long combinatorio_recursivo(int n, int k) {
		if(k == 0 || n==k)
			return 1;
		else
			return combinatorio_recursivo(n-1, k-1) + combinatorio_recursivo(n-1, k);
	}
	
	private static long combinatorio_recursivo_pilas(int n, int k) {
		Stack<Integer> pN = new Stack<Integer>();
		Stack<Integer> pK = new Stack<Integer>();
		Stack<Integer> pL = new Stack<Integer>();
		Stack<Integer> pS = new Stack<Integer>();
		
		int sol = 0;
		pN.push(n);
		pK.push(k);
		pL.push(1);
		pS.push(0);
		
		while(!pN.empty()) {
			while(pN.peek() != pK.peek() && pK.peek() != 0 && pL.peek() <= 2) {
				switch(pL.peek()) {
				case 1:
					pN.push(pN.peek()-1);
					pK.push(pK.peek()-1);
					break;
				case 2:
					pN.push(pN.peek()-1);
					pK.push(pK.peek());
					break;
				}
				pL.push(1);
				if(pN.peek() == pK.peek() || pK.peek() == 0)
					pS.push(1);
				else
					pS.push(0);
			}
			pN.pop();
			pK.pop();
			pL.pop();
			sol=pS.pop();
			if(!pN.empty()) {
				pL.push(pL.pop()+1);
				pS.push(pS.pop()+sol);
			}
		}
		if(sol == 0)
			sol = 1;
		
		return sol;
	}
	
	private static void menuEntrada() {
		Leer leer = new Leer();
		
		System.out.println("Inserte el número de elementos a combinar (n): ");
		N = leer.pedirIntPositivo();
		System.out.println("\nNúmero de elementos a combinar: " + N);
		
		System.out.println("\nInserte el número de elementos por grupo (k): ");
		do {
			K = leer.pedirIntRango(0, N);	
		} while(K > N || K < 0);
		System.out.println("\nNúmero de elementos por grupo: " + K);
		
		System.out.println("\nInserte el número de iteraciones que quiere analizar: ");
		ITERATIONS = leer.pedirIntPositivo();
		System.out.println("\nNúmero de iteraciones a analizar: " + ITERATIONS);
		System.out.println();
	}

	public static void main(String[] args) {
		long sum, min, max, mean, start, elapsed;
		long result = 0;
		
		Leer leer = new Leer();
		
		System.out.println("-------------	COMBINACIONES S.A. (¡encuentra el número combinatorio!)	-------------\n");
		do {
			menuEntrada();
			
			/*
			 * ITERATIVO
			 */
			System.out.println("[ ITERATIVO ]");
			sum = 0; min = Long.MAX_VALUE; max = 0;
			for(int i = 0; i < ITERATIONS; i++) {
				start = System.nanoTime();
				result = combinatorio_iterativo(N,K);
				elapsed = System.nanoTime() - start;
				sum += elapsed;
				if(elapsed < min) min = elapsed;
				if(elapsed > max) max = elapsed;
			}
			mean = sum/ITERATIONS;
			System.out.printf("Result: %d; Elapsed: %d ns (min: %d ns, max: %d ns)\n", result, mean, min, max);
			
			
			/*
			 * RECURSIVO
			 */
			System.out.println("[ RECURSIVO ]");
			sum = 0; min = Long.MAX_VALUE; max = 0;
			for(int i = 0; i < ITERATIONS; i++) {
				start = System.nanoTime();
				result = combinatorio_recursivo(N,K);
				elapsed = System.nanoTime() - start;
				sum += elapsed;
				if(elapsed < min) min = elapsed;
				if(elapsed > max) max = elapsed;
			}
			mean = sum/ITERATIONS;
			System.out.printf("Result: %d; Elapsed: %d ns (min: %d ns, max: %d ns)\n", result, mean, min, max);
			
			
			/*
			 * RECURSIVO CON PILAS
			 */
			System.out.println("[ RECURSIVO PILAS ]");
			sum = 0; min = Long.MAX_VALUE; max = 0;
			for(int i = 0; i < ITERATIONS; i++) {
				start = System.nanoTime();
				result = combinatorio_recursivo_pilas(N,K);
				elapsed = System.nanoTime() - start;
				sum += elapsed;
				if(elapsed < min) min = elapsed;
				if(elapsed > max) max = elapsed;
			}
			mean = sum/ITERATIONS;
			
			System.out.printf("Result: %d; Elapsed: %d ns (min: %d ns, max: %d ns)\n", result, mean, min, max);
			
			System.out.println("\n¿Desea calcular más números combinatorios? (y/n)");
		} while(leer.pedirChar() == 'y');
		
		System.out.println("\nHecho por Alberto Velasco Mata y Diego Pedregal Hidalgo, 2019 (C)");
		System.out.println("Sin derechos reservados :(");
	}
}
