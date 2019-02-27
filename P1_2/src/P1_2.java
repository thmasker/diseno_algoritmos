
public class P1_2 {
	
	static long combinatorio_iterativo(int n, int k) {
		
		long n_factorial = 1;
		for(int i = n; i > 1; i--)
			n_factorial *= i;
		
		long k_factorial = 1;
		for(int i = k; i > 1; i--)
			k_factorial *= i;
		
		long n_k_factorial = 1;
		for(int i = n-k; i > 1; i--)
			n_k_factorial *= i;
		
		return n_factorial/(k_factorial * n_k_factorial);
	}
	
	static long combinatorio_recursivo(int n, int k) {
		if(k == 0 || n==k)
			return 1;
		else
			return combinatorio_recursivo(n-1, k-1) + combinatorio_recursivo(n-1, k);
	}
	
	static long combinatorio_recursivo_pilas(int n, int k) {
		
		return 0;
	}

	public static void main(String[] args) {
		long start, elapsed;
		
		System.out.println("[ ITERATIVO ]");
		start = System.nanoTime();
		long result = combinatorio_iterativo(20,3);
		elapsed = System.nanoTime() - start;
		System.out.printf("Result: %d; Elapsed: %d ns\n", result, elapsed);
		
		System.out.println("[ RECURSIVO ]");
		start = System.nanoTime();
		result = combinatorio_recursivo(20,3);
		elapsed = System.nanoTime() - start;
		System.out.printf("Result: %d; Elapsed: %d ns\n", result, elapsed);
		
		System.out.println("[ RECURSIVO PILAS ]");
		start = System.nanoTime();
		result = combinatorio_recursivo_pilas(20,3);
		elapsed = System.nanoTime() - start;
		System.out.printf("Result: %d; Elapsed: %d ns\n", result, elapsed);
		
	}

}
