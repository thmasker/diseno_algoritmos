


public class P4 {

	static class Cono {
		double radio, altura;
		public Cono(double radio, double altura) {
			this.radio = radio;
			this.altura = altura;
		}
		
		// Volumen probabilista lanzando k puntos
		// V = casos favorables/casos posibles
		public double volumenNumericoP(int k) {
			int buenos = 0;
			for(int n = 0; n < k; n++) {
				double x = Math.random()*radio;
				double y = Math.random()*radio;
				double z = Math.random()*altura;
				if( (Math.sqrt(x*x + y*y) < radio)
						&& z < (altura - altura*Math.sqrt(x*x + y*y)/radio)
				) buenos++;
			}
			return 4*radio*radio*altura*(double)buenos/k;
		}
		
		public double volumenConocido() {
			return (Math.PI * radio * radio * altura)/3.0;
		}
	}
	
	public static void main(String[] args) {
		Cono c = new Cono(2,4);
		System.out.println("Real:" + c.volumenConocido());
		System.out.println("Probabilista:" + c.volumenNumericoP(100000));
	}
}
