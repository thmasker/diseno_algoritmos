import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Algoritmos{
	private String patron;
	private String archivo;
	private double porcentaje;
	private int lineas;
	
	public Algoritmos(String patron, String archivo, double porcentaje, int lineas) {
		this.setPatron(patron);
		this.setArchivo(archivo);
		this.setPorcentaje(porcentaje);
		this.setLineas(lineas);
	}

	public String patron() {
		return patron;
	}

	public void setPatron(String patron) {
		this.patron = patron;
	}

	public String archivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public double porcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public int lineas() {
		return lineas;
	}

	public void setLineas(int lineas) {
		this.lineas = lineas;
	}
	
	//	Calcula número de líneas que hay que leer
	private int calcularPorcentaje() {
		return (int) (lineas * porcentaje / 100.0);
	}
	
	//	Elige la línea a comparar aleatoriamente
	private String elegirLinea(ArrayList<Integer> elegidas) throws IOException {
		int lineaLeer;
		String nuevaLinea;
		
		do {
			lineaLeer = ThreadLocalRandom.current().nextInt(1, lineas + 1);
		} while(elegidas.contains(lineaLeer));
		
		try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
		    for (int i = 0; i < lineaLeer - 1; i++)
		        br.readLine();
		    nuevaLinea = br.readLine();
		}
		
		elegidas.add(lineaLeer);
		
		return nuevaLinea;
	}
	
	//	Estima el resultado según las ocurrencias obtenidas en el porcentaje comparado
	private int estimarOcurrencias(int ocurrencias) {
		try {
			return lineas * ocurrencias / calcularPorcentaje();	
		} catch(ArithmeticException e) {
			return 0;
		}
	}
	
	public int fuerzaBruta() throws IOException{
		int lineasRestantes = calcularPorcentaje();
		ArrayList<Integer> elegidas = new ArrayList<Integer>();
		ArrayList<Integer> ocurrencias = new ArrayList<Integer>();
		String texto;
		
		while(lineasRestantes > 0) {
			texto = elegirLinea(elegidas);
			ocurrencias = naive(ocurrencias, texto);
			lineasRestantes--;
		}
		
		return estimarOcurrencias(ocurrencias.size());
	}
	
	private ArrayList<Integer> naive(ArrayList<Integer> ocurrencias, String texto){
		if(patron().length() > 0 && texto.length() >= patron.length()) {
			int t = 0;
			int p = 0;
			
			while(texto.length() - t >= patron.length()) {
				if(texto.charAt(t) == patron.charAt(p)) {
					int T = t + 1;
					int P = 1;
					
					while(P < patron.length() && texto.charAt(T) == patron.charAt(P)) {
						T++;
						P++;
					}
					
					if(P == patron.length()) ocurrencias.add(t);
				}
				
				t++;
			}
		}
		
		return ocurrencias;
	}
}