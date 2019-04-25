import java.util.ArrayList;


public class P3_backward {
	private static class Moneda {
		int tipo;		// Índice en MONEDAS del valor de esta moneda
		int cantidad;	// Cantidad de monedas de este tipo
		int monedasTotales;	// Cantidad total de monedas totales que se devolverían
		int cambioRestante;	// Cambio restante que faltaría por devolver
		Moneda vengo;		// Moneda de la que vengo o a la que voy
		
		public int tipo() {
			return tipo;
		}
		
		public int valorMoneda(int[] monedas) {
			if(this.tipo < 0)
				return 0;
			return monedas[this.tipo];
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
		
		public void setCambio(int cambio) {
			this.cambioRestante = cambio;
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
	
	public static class Cambio {
		int cambio;
		int[] monedas;
		
		public Cambio(int cambio, int[] monedas) {
			this.cambio = cambio;
			this.monedas = monedas;
		}
		
		public int[] backward() {
			//Moneda first = _backward(new Moneda(-1, 0, cambio, -1), new ArrayList<Moneda>(), 0);
			Moneda first = _backward(new Moneda(-1, 0, 0, -1), new ArrayList<Moneda>(), 0);
			
			int[] solution = new int[monedas.length];
			while(first != null) {
				System.out.println(first.toString());
				if(first.tipo() >= 0)
					solution[first.tipo()] = first.cantidad();
				
				first = first.vengo();
			}
			return solution;
		}
		
		private Moneda _backward(Moneda actual, ArrayList<Moneda> calculadas, int indent) {
			final String INDENT = "   ";

			if(actual.tipo() == monedas.length - 1) {
				actual.setMonedasTotales(actual.cantidad());
				actual.setCambio(actual.valorMoneda(monedas) * actual.cantidad());
			}
			if(calculadas.contains(actual)) {
				actual = calculadas.get(calculadas.indexOf(actual));
			} else {
				int nuevo_tipo = actual.tipo() + 1;
				if(nuevo_tipo < monedas.length) {
					for(int cant = 0; cant <= (cambio - actual.valorMoneda(monedas) * actual.cantidad()) / monedas[nuevo_tipo]; cant++) {
						for(int ind = 0; ind < indent; ind++) System.out.print(INDENT);
						System.out.println(cant);
						
						Moneda nueva = new Moneda(nuevo_tipo, cant, 0, 0);
						
						nueva = _backward(nueva, calculadas, indent+1);
						
						int nuevo_totales = actual.cantidad() + nueva.monedasTotales();
						int nuevo_cambio = actual.valorMoneda(monedas) * actual.cantidad() + nueva.cambio();

						if(	(nuevo_cambio > actual.cambio() && nuevo_cambio <= cambio)
							|| (nuevo_cambio == actual.cambio() && nuevo_totales < actual.monedasTotales())
						) {
							actual.setVengo(nueva);
							actual.setMonedasTotales(nuevo_totales);
							actual.setCambio(nuevo_cambio);
						}

					}
				}
				calculadas.add(actual);
			}

			return actual;
		}
	}
		
		
		
		/**** WORKS IN ASCENDING ORDER ONLY ****/
//		private Moneda _backward(Moneda actual, ArrayList<Moneda> calculadas, int indent) {
//			final String INDENT = "   ";
//
//			if(actual.tipo() == monedas.length - 1) {
//				actual.setMonedasTotales(actual.cantidad());
//			}
//			if(calculadas.contains(actual)) {
//				actual = calculadas.get(calculadas.indexOf(actual));
//			} else {
//				int nuevo_tipo = actual.tipo() + 1;
//				if(nuevo_tipo < monedas.length) {
//					for(int ind = 0; ind < indent; ind++) System.out.print(INDENT);
//					System.out.println("Cambio: " + actual.cambio() + ", tipo: " + actual.tipo());
//					
//					int best_cambio = actual.cambio();
//					for(int cant = 0; cant <= actual.cambio() / monedas[nuevo_tipo]; cant++) {
//						for(int ind = 0; ind < indent; ind++) System.out.print(INDENT);
//						System.out.println(cant);
//						
//						Moneda nueva = new Moneda(nuevo_tipo, cant, actual.cambio() - monedas[nuevo_tipo] * cant, -1);
//						System.out.println(nueva.toString());
//						nueva = _backward(nueva, calculadas, indent+1);
//						System.out.println("after: " + nueva.toString());
//						
//						int nuevo_totales = nueva.monedasTotales() + actual.cantidad();
//
//						if(		(actual.vengo() == null)
//								|| nuevo_totales < actual.monedasTotales()
//								|| nueva.cambio() < best_cambio
//						/*		|| actual.monedasTotales() == -1
//								|| nueva.cambio() < actual.vengo().cambio() && actual.monedasTotales() == 0
//								|| (nuevo_totales < actual.monedasTotales() && nueva.cambio() == actual.vengo().cambio())*/
//								)
//						{
//							System.out.println("actualizando");
//							best_cambio = nueva.cambio();
//							actual.setVengo(nueva);
//							actual.setMonedasTotales(nuevo_totales);
//						}
//
//					}
//					actual.setCambio(best_cambio);
//				}
//				calculadas.add(actual);
//			}
//
//			return actual;
//		}
//	}
		
//		private Moneda _backward(Moneda actual, ArrayList<Moneda> calculadas, int indent) {
//			String INDENT = "   ";
//			
//			if(actual.tipo() == monedas.length - 1) {
//				actual.setCambio(actual.valorMoneda(monedas) * actual.cantidad());
//				actual.setMonedasTotales(actual.cantidad());
//				
//				/* DEBUG */
//				//for(int _i = 0; _i < indent; _i++) System.out.print(INDENT);
//				//System.out.println("Moneda terminal (caso base): " + actual.toString());
//				/** DEBUG **/
//			}
//			if(calculadas.contains(actual)) {
//				/* DEBUG */
//				//for(int _i = 0; _i < indent; _i++) System.out.print(INDENT);
//				//System.out.println("Ya calculada: " + actual.toString());
//				/** DEBUG **/
//				
//				actual = calculadas.get(calculadas.indexOf(actual));
//			} else {
//				/* DEBUG */
//				//for(int _i = 0; _i < indent; _i++) System.out.print(INDENT);
//				//System.out.println("Calculando: " + actual.toString());
//				/** DEBUG **/
//				
//				int nuevo_tipo = actual.tipo() + 1;
//				if(nuevo_tipo < monedas.length) { 
//				for(int cant = 0; cant <= (cambio - actual.valorMoneda(monedas) * actual.cantidad()) / monedas[nuevo_tipo]; cant++) {
//					
//					Moneda nueva = _backward(new Moneda(nuevo_tipo, cant, 0, 0), calculadas, indent+1);
//					
//					/* DEBUG */
//					//for(int _i = 0; _i < indent; _i++) System.out.print(INDENT);
//					//System.out.println("Resultado backward: " + nueva.toString());
//					/** DEBUG **/
//					
//					int nuevo_totales = nueva.monedasTotales() + actual.cantidad();
//					int nuevo_cambio = nueva.cambio() + actual.valorMoneda(monedas) * actual.cantidad();
//					
//					if(		(actual.vengo() == null)
//							|| (nuevo_cambio > actual.cambio() && nuevo_cambio <= cambio)
//							|| (nuevo_cambio == cambio && nuevo_totales < actual.monedasTotales()))
//					{
//						/* DEBUG */
//						//for(int _i = 0; _i < indent; _i++) System.out.print(INDENT);
//						//System.out.println("Cambiando .voy de " + actual.toString());
//						//for(int _i = 0; _i < indent; _i++) System.out.print(INDENT);
//						//System.out.println("Por " + nueva.toString());
//						/** DEBUG **/
//						
//						
//						actual.setVengo(nueva);
//						actual.setCambio(nuevo_cambio);
//						actual.setMonedasTotales(nuevo_totales);
//					}
//					
//				}
//				}
//				calculadas.add(actual);
//			}
//			
//			return actual;
//		}
//	}
	
	public static void main(String[] args) {
		int CAMBIO = 20;
		//int[] MONEDAS = new int[]{3,2,1};
		int[] MONEDAS = new int[]{9,7,3};

		Cambio c = new Cambio(CAMBIO, MONEDAS);
		int[] solution = c.backward();
		for(int i = 0; i < MONEDAS.length; i++) {
			System.out.printf("Moneda: %d, cantidad: %d\n", MONEDAS[i], solution[i]);
		}
	}
}
