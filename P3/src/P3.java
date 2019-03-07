import java.util.ArrayList;

public class P3 {
	static int[] MONEDAS = new int[] {5,2,1};
	static int CAMBIO = 13;
	
	private static class Moneda {
		int tipo;	// Índice en MONEDAS del valor de esta moneda
		int cantidad;
		int cambioRestante;
		Moneda vengo;
		
		public int tipo() {
			return tipo;
		}
		public void setTipo(int tipo) {
			this.tipo = tipo;
		}
		public int cantidad() {
			return cantidad;
		}
		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		public int cambio() {
			return cambioRestante;
		}
		public void setCambioRestante(int cambioRestante) {
			this.cambioRestante = cambioRestante;
		}
		public Moneda vengo() {
			return vengo;
		}
		public void setVengo(Moneda vengo) {
			this.vengo = vengo;
		}
		public Moneda(int tipo, int cantidad, int cambioRestante) {
			this.tipo = tipo;
			this.cantidad = cantidad;
			this.cambioRestante = cambioRestante;
		}
		
		public boolean equals(Object m) {
			return m instanceof Moneda &&
					tipo() == ((Moneda)m).tipo() &&
					cambio() == ((Moneda)m).cambio();
		}
		@Override
		public String toString() {
			return "Moneda [tipo=" + tipo + ", cantidad=" + cantidad + ", cambioRestante=" + cambioRestante + "]";
		}
		
		
	}
	
	private static int[] forward() {
		Moneda m = best(_forward());
		return solution(m);
	}
	
	private static Moneda best(ArrayList<Moneda> monedas) {
		Moneda best = monedas.get(1);
		for(int i = 2; i < monedas.size(); i++) {
			if(monedas.get(i).cambio() < best.cambio() || (monedas.get(i).cambio() == best.cambio() && monedas.get(i).cantidad() < best.cantidad()))
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
		monedas.add(new Moneda(-1,0,CAMBIO));
		int pos = 0;
		int tipo_moneda = -1;
		while(pos < monedas.size()) {
			Moneda actual = monedas.get(pos);
			tipo_moneda = actual.tipo()+1;
			if(tipo_moneda < MONEDAS.length) {
				System.out.println("Moneda actual: " + actual.toString());
				for(int cant = 0; cant <= actual.cambio() / MONEDAS[tipo_moneda]; cant++) {
					int nuevo_cambio = actual.cambio() - cant * MONEDAS[tipo_moneda];
					if((CAMBIO - nuevo_cambio) > MONEDAS[MONEDAS.length-1]) {
						Moneda nueva = new Moneda(tipo_moneda, cant, nuevo_cambio);
						nueva.setVengo(actual);
						if(!monedas.contains(nueva)) {
							monedas.add(nueva);
							System.out.println("\tAñadiendo: " + nueva.toString());
						} else {
							Moneda existente = monedas.get(monedas.indexOf(nueva));
							if(existente.cantidad() * MONEDAS[existente.tipo()] < nueva.cantidad() * MONEDAS[nueva.tipo()]) {
								existente.setCantidad(nueva.cantidad());
								existente.setVengo(nueva.vengo());
								System.out.println("\tCambiando: " + existente.toString());
								System.out.println("\t\tPor: " + nueva.toString());
							}
						}
					}
					
				}
			}
			pos++;
		}
		
		return monedas;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		forward();
	}

}
