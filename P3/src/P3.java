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
	
	private static int[] forward() {
		Moneda m = best(_forward());
		return solution(m);
	}
	
	private static Moneda best(ArrayList<Moneda> monedas) {
		Moneda best = monedas.get(1);
		
		for(int i = 2; i < monedas.size(); i++) {
			if(monedas.get(i).cambio() < best.cambio() || (monedas.get(i).cambio() == best.cambio() && monedas.get(i).monedasTotales() < best.monedasTotales()))
				best = monedas.get(i);
		}
		
		return best;
	}
	
	private static int[] solution(Moneda best) {
		Moneda last = best;
		
		while(last.vengo() != null) {
			System.out.printf("Moneda: %d, cantidad: %d\n", MONEDAS[last.tipo()], last.cantidad());
			last = last.vengo();
		}
		
		return new int[0];
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
	
	private static void forwardMatrix() {
		int [][] F = new int[CAMBIO+1][MONEDAS.length+1];
		for(int f=0;f<F.length;f++) for(int c=0;c<F[0].length;c++) F[f][c]=-1;
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
		
		System.out.println(rutaF(0, caminoF));
	}
	
	private static String rutaF(int fila, int [][] caminoF) {
		String ruta = "";
		
		for(int col = caminoF[0].length-1; col > 0; col--) {
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
						if(c-MONEDAS[t]*cant >=0) {
							if(F[c-MONEDAS[t]*cant][t+1] < F[c][t] + MONEDAS[t]*cant) {
						
								F[c-MONEDAS[t]*cant][t+1] = F[c][t] + MONEDAS[t]*cant;
								caminoF[c-MONEDAS[t]*cant][t+1] = cant;
							}
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		//forward();
		forwardMatrix();
	}
}
