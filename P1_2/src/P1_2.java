import java.util.Stack;

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
