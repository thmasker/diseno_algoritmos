import java.util.Scanner;

public class P4 {

	static class Cono {
		double radio, altura;
		
		public Cono(double radio, double altura) {
			this.radio = radio;
			this.altura = altura;
		}
		
		/**
		 * CÁLCULO DEL VOLUMEN CON PROPORCIONES
		 */
		double[] intervaloP = new double[2];
		public double intervaloInfP() { return 4*radio*radio*altura*intervaloP[0]; }
		public double intervaloSupP() { return 4*radio*radio*altura*intervaloP[1]; }
		public String intervaloConfP() { return "(" + intervaloInfP() + ", " + intervaloSupP() + ")"; }
		
		private void setIntervaloConfP(double p, int n) {
			intervaloP[0] = p - 1.96*Math.sqrt(p*(1-p)/n);
			intervaloP[1] = p + 1.96*Math.sqrt(p*(1-p)/n);
		}
		
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
			setIntervaloConfP((double)buenos/k, k);
			return 4*radio*radio*altura*(double)buenos/k;
		}
		
		/**
		 * CÁLCULO DEL VOLUMEN CON EL TEOREMA DEL VALOR MEDIO
		 */
		double[] intervaloVM = new double[2];
		public double intervaloInfVM() { return intervaloVM[0]; }
		public double intervaloSupVM() { return intervaloVM[1]; }
		public String intervaloConfVM() { return "(" + intervaloInfVM() + ", " + intervaloSupVM() + ")"; }
		
		private void setIntervaloConfVM(double[] valores) {
			double media = media(valores);
			double S = cuasiV(valores, media);
			intervaloVM[0] = media - 1.96*S/Math.sqrt(valores.length);
			intervaloVM[1] = media + 1.96*S/Math.sqrt(valores.length);
		}
		private double media(double valores[]) {
			double media = 0.0d;
			for(int i = 0; i < valores.length; i++)
				media = media + valores[i];
			return media/valores.length;
		}
		private double cuasiV(double[] valores, double media) {
			double S = 0;
			for(int i = 0; i < valores.length; i++)
				S = S + Math.pow(valores[i] - media, 2);
			return Math.sqrt(S/(valores.length-1));
		}
		public double volumenNumericoVM(int k) {
			double[] valores = new double[k];
			double suma = 0.0d;
			for(int n = 0; n < k; n++) {
				double x, y;
				do {
					x = Math.random()*radio;
					y = Math.random()*radio;
				} while((Math.sqrt(x*x + y*y) > radio));
				
				double z = altura - altura*Math.sqrt(x*x + y*y)/radio;
				valores[n] = Math.PI*radio*radio*z;
				suma = suma+valores[n];
			}
			setIntervaloConfVM(valores);
			return suma/k;
		}
		
		/**
		 * CÁLCULO DEL VOLUMEN USANDO LA FÓRMULA TEÓRICA
		 */
		public double volumenConocido() {
			return (Math.PI * radio * radio * altura)/3.0;
		}
	}
	
	public static void main(String[] args) {
		int radio, altura, puntosP, medidasVM;
		
		System.out.println("-------------	Tamañito S.A. (¡Calcula el volumen!)	-------------\n");
		
		Leer leer = new Leer();
		radio = leer.pedirIntPositivo("Introduce el radio del cono: ");
		altura = leer.pedirIntPositivo("Introduce la altura del cono: ");
		puntosP = leer.pedirIntPositivo("Introduce la cantidad de puntos para la estimación por proporciones: ");
		medidasVM = leer.pedirIntPositivo("Introduce la cantidad de medidas para la estimación por valor medio: ");
		
		
		Cono c = new Cono(radio, altura);
		System.out.println("Real: " + c.volumenConocido());
		System.out.println("Proporciones (" + puntosP + " puntos): " + c.volumenNumericoP(puntosP));
		System.out.println("\tIntervalo de confianza: " + c.intervaloConfP());
		
		System.out.println("Valor medio (" + medidasVM + " medidas): " + c.volumenNumericoVM(medidasVM));
		System.out.println("\tIntervalo de confianza: " + c.intervaloConfVM());
		
		System.out.println("\nHecho por Alberto Velasco Mata y Diego Pedregal Hidalgo, 2019 (C)");
		System.out.println("Sin derechos reservados :(");
	}
}
