import java.util.ArrayList;

public class P3 {
	static int[] MONEDAS = new int[] {5, 2, 1};
	static int CAMBIO = 13;
	
	private static class Moneda {
		int tipo;		// Índice en MONEDAS del valor de esta moneda
		int cantidad;	// Cantidad de monedas de este tipo
		int monedasTotales;	// Cantidad total de monedas totales que se devolverían
		int cambioRestante;	// Cambio restante que faltaría por devolver
		Moneda vengo;		// Moneda de la que vengo o a la que voy
		
		public int tipo() {
			return tipo;
		}
		
		public int cantidad() {
			return cantidad;
		}
		
		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		
		public int monedasTotales() {
			return monedasTotales;
		}
		
		public void setMonedasTotales(int monedasTotales) {
			this.monedasTotales = monedasTotales;
		}
		
		public int cambio() {
			return cambioRestante;
		}
		
		public Moneda vengo() {
			return vengo;
		}
		
		public void setVengo(Moneda vengo) {
			this.vengo = vengo;
		}
		
		public Moneda(int tipo, int cantidad, int cambioRestante, int monedasTotales) {
			this.tipo = tipo;
			this.cantidad = cantidad;
			this.cambioRestante = cambioRestante;
			this.monedasTotales = monedasTotales;
		}
		
		public boolean equals(Object m) {
			return m instanceof Moneda &&
					tipo() == ((Moneda)m).tipo() &&
					cambio() == ((Moneda)m).cambio();
		}
		
		@Override
		public String toString() {
			return "Moneda [tipo=" + tipo 
					+ ", cantidad=" + cantidad 
					+ ", monedasTotales=" + monedasTotales 
					+ ", cambioRestante=" + cambioRestante + "]";
		}
	}
	
	private static void forward() {
		Moneda m = bestForward(_forward());
		solutionForward(m);
	}
	
	private static Moneda bestForward(ArrayList<Moneda> monedas) {
		Moneda best = monedas.get(1);
		
		for(int i = 2; i < monedas.size(); i++) {
			if(monedas.get(i).cambio() < best.cambio() || (monedas.get(i).cambio() == best.cambio() && monedas.get(i).monedasTotales() < best.monedasTotales()))
				best = monedas.get(i);
		}
		
		return best;
	}
	
	private static void solutionForward(Moneda best) {
		Moneda last = best;
		
		while(last.vengo() != null) {
			System.out.printf("Moneda: %d, cantidad: %d\n", MONEDAS[last.tipo()], last.cantidad());
			last = last.vengo();
		}
	}
	
	private static ArrayList<Moneda> _forward() {
		ArrayList<Moneda> monedas = new ArrayList<Moneda>();
		monedas.add(new Moneda(-1,0,CAMBIO,0));
		int pos = 0;
		int tipo_moneda = -1;
		
		while(pos < monedas.size()) {
			Moneda actual = monedas.get(pos);
			tipo_moneda = actual.tipo()+1;
			
			if(tipo_moneda < MONEDAS.length) {
				System.out.println("Moneda actual: " + actual.toString());
				
				for(int cant = 0; cant <= actual.cambio() / MONEDAS[tipo_moneda]; cant++) {
					
					int nuevo_cambio = actual.cambio() - cant * MONEDAS[tipo_moneda];
					int nuevo_monedasTotales = actual.monedasTotales() + cant;
					
					Moneda nueva = new Moneda(tipo_moneda, cant, nuevo_cambio, nuevo_monedasTotales);
					nueva.setVengo(actual);
						
					if(!monedas.contains(nueva)) {
						monedas.add(nueva);
						System.out.println("\tAÃ±adiendo: " + nueva.toString());
					} else {
						Moneda existente = monedas.get(monedas.indexOf(nueva));
						
						if(nueva.monedasTotales() < existente.monedasTotales()) {
							System.out.println("\tCambiando: " + existente.toString());
							System.out.println("\t\tPor: " + nueva.toString());
							existente.setCantidad(nueva.cantidad());
							existente.setVengo(nueva.vengo());
							existente.setMonedasTotales(nueva.monedasTotales());
						}
					}	
				}
			}
			
			pos++;
		}
		
		return monedas;
	}
	
	private static void backward() {
		Moneda first = _backward(new Moneda(-1, 0, CAMBIO, 0), new ArrayList<Moneda>());
		solutionBackward(first);
	}
	
	private static void solutionBackward(Moneda first) {
		while(first.vengo() != null) {
			System.out.printf("Moneda: %d, cantidad: %d\n", MONEDAS[first.tipo()], first.cantidad());
			first = first.vengo();
		}
	}
	
	private static Moneda _backward(Moneda actual, ArrayList<Moneda> calculadas){
//		Moneda existente = null;
//		int tipo_moneda = -1;
//		
//		if(calculadas.contains(actual)) {
//			existente = calculadas.get(calculadas.indexOf(actual));
//		} else {
//			tipo_moneda = actual.tipo() + 1;
//			
//			if(tipo_moneda < MONEDAS.length) {
//				System.out.println("Moneda actual: " + actual.toString());
//				
//				for(int cant = 0; cant <= actual.cambio() / MONEDAS[tipo_moneda]; cant++) {
//					
//					int nuevo_cambio = actual.cambio() - cant * MONEDAS[tipo_moneda];
//					int nuevo_monedasTotales = actual.monedasTotales() + cant;
//					
//					Moneda nueva = new Moneda(tipo_moneda, cant, nuevo_cambio, nuevo_monedasTotales);
//					
//					existente = _backward(nueva, calculadas);
//					
//					if(existente.monedasTotales() < actual.monedasTotales()) {
//						System.out.println("\tCambiando: " + actual.toString());
//						System.out.println("\t\tPor: " + existente.toString());
//						existente.setCantidad(existente.cantidad());
//						existente.setVengo(existente.vengo());
//						existente.setMonedasTotales(existente.monedasTotales());
//					}
//					
//					if(monedas.get(i).cambio() < best.cambio() || (monedas.get(i).cambio() == best.cambio() && monedas.get(i).monedasTotales() < best.monedasTotales())) {
//						
//					}
//				}
//			}
//			
//			existente = actual;
//			calculadas.add(actual);
//		}
//		
//		return existente;
		return null;
	}
	
	private static void forwardMatrix() {
		int [][] F = new int[CAMBIO+1][MONEDAS.length+1];
		for(int f = 0; f < F.length; f++) for(int c = 0; c < F[0].length; c++) F[f][c] = -1;
		int [][] caminoF = new int[CAMBIO+1][MONEDAS.length+1];
		
		_forwardMatrix(F, caminoF);
		
		for(int i = 0; i < F.length; i++) {
			for(int j = 0; j < F[0].length; j++) {
				System.out.printf("\t%d", F[i][j]);
			}
			
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
		
		for(int i = 0; i < caminoF.length; i++) {
			for(int j = 0; j < caminoF[0].length; j++) {
				System.out.printf("\t%d", caminoF[i][j]);
			}
			
			System.out.println();
		}
		
		System.out.println(rutaF(F, caminoF));
	}
	
	private static String rutaF(int [][] F, int [][] caminoF) {
		String ruta = "";
		int fila = 0;
		
		for(int f = 0; f < F.length; f++) {
			if(F[f][F[0].length-1] > F[fila][F[0].length-1]) {
				fila = f;
			}
		}
		
		for(int col = F[0].length-1; col > 0; col--) {
			int cantidad = caminoF[fila][col];
			ruta = "Monedas de " + MONEDAS[col-1] + ": " + cantidad + "\n" + ruta;
			fila += cantidad * MONEDAS[col-1];
		}
		
		return ruta;
	}
	
	private static void _forwardMatrix(int [][] F, int [][] caminoF) {
		int cambios = F.length - 1;
		int tipos = F[0].length - 1;
		
		F[F.length-1][0]=0;
		for(int t = 0; t < tipos; t++) {
			for(int c = 0; c <= cambios; c++) {
				if(F[c][t] >= 0) {
					for(int cant = 0; cant <= c / MONEDAS[t]; cant++) {
						if((F[c-MONEDAS[t]*cant][t+1] == -1) || (F[c-MONEDAS[t]*cant][t+1] > F[c][t] + cant)) {
							F[c-MONEDAS[t]*cant][t+1] = F[c][t] + cant;
							caminoF[c-MONEDAS[t]*cant][t+1] = cant;
						}
					}
				}
			}
		}
	}
	
	private static void backwardMatrix() {
		int [][] B = new int[CAMBIO+1][MONEDAS.length+1];
		int [][] caminoB = new int[CAMBIO+1][MONEDAS.length+1];
		
		_backwardMatrix(B, caminoB, CAMBIO, 0);
		
		System.out.println(rutaB(caminoB));
	}
	
	private static String rutaB(int[][] ruta) {
		String sol = "";
		int fila = CAMBIO;
		for(int m=0; m < MONEDAS.length; m++) {
			sol += "Monedas de " + MONEDAS[m] + ": " + ruta[fila][m] + "\n";
			fila = fila - ruta[fila][m] * MONEDAS[m];
		}
		return sol;
	}
	
	private static void _backwardMatrix(int[][] B, int[][] caminoB, int cambioRestante, int monedaIndex) {
		// Solo si no estamos en la última columna y el valor no ha sido calculado previamente
		if(monedaIndex < B[0].length - 1 && B[cambioRestante][monedaIndex] == 0) {
			int n_monedaIndex = monedaIndex + 1;
			// Sucesores: Cantidad de monedas que podemos devolver de este tipo
			for(int cant = 0; cant <= cambioRestante / MONEDAS[monedaIndex]; cant++) {
				// Calculamos el nuevo cambio restante con esta cantidad de monedas de este tipo
				int n_cambioRestante = cambioRestante - cant * MONEDAS[monedaIndex];
				// Solo si el nuevo cambio no es negativo
				// Esta comparación no debería hacer falta porque ya se tiene en cuenta en los valores del cant definidos en el for
				if(n_cambioRestante >= 0) {
					// Calculamos la nueva celda (si no estaba calculada ya)
					_backwardMatrix(B, caminoB, n_cambioRestante, n_monedaIndex);
					// Cambio la celda actual si no estaba calculada o si la mejoram
					if(B[cambioRestante][monedaIndex] == 0 || B[n_cambioRestante][n_monedaIndex] == 0 ||  B[n_cambioRestante][n_monedaIndex] + cant < B[cambioRestante][monedaIndex]) {
						B[cambioRestante][monedaIndex] = B[n_cambioRestante][n_monedaIndex] + cant;
						caminoB[cambioRestante][monedaIndex] = cant;
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		//forward();
		//backward();
		//forwardMatrix();
		backwardMatrix();
	}
}