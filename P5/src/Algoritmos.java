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
	
	public int boyerMoore() throws IOException {
		int lineasRestantes = calcularPorcentaje();
		ArrayList<Integer> elegidas = new ArrayList<Integer>();
		ArrayList<Integer> ocurrencias = new ArrayList<Integer>();
		String texto;
		
		while(lineasRestantes > 0) {
			texto = elegirLinea(elegidas);
			ocurrencias = boyerMoore(ocurrencias, patron, texto);
			lineasRestantes--;
		}
		
		return estimarOcurrencias(ocurrencias.size());
	}
	
	private ArrayList<Integer> boyerMoore(ArrayList<Integer> ocurrencias, String patron, String texto){
		if(patron.length() > 0 && texto.length() >= patron.length()) {
			ArrayList<Character> occChar = new ArrayList<Character>();
			ArrayList<Integer> occPos = new ArrayList<Integer>();
			
			iniciaOcc(occChar, occPos, patron);
			
			int [] s = new int[patron.length()+1];
			int [] f = new int[patron.length()+1];
			
			preproceso1(s, f, patron);
			preproceso2(s, f, patron);
			
			boyerMoore(patron, texto, s, ocurrencias, occChar, occPos);
		}
		
		return ocurrencias;
	}
	
	private void boyerMoore(String patron, String texto, int [] s, ArrayList<Integer> ocurrencias,
			ArrayList<Character> occChar, ArrayList<Integer> occPos) {
		int i = 0, j;
		
		while(i <= texto.length() - patron.length()) {
			j = patron.length() - 1;
			
			while(j >= 0 && patron.charAt(j) == texto.charAt(i+j))
				j--;
			
			if(j < 0) {
				ocurrencias.add(i);
				i += s[0];
			} else {
				int aux = -1;
				
				if(occChar.contains(texto.charAt(i+j)))
					aux = occPos.get(occChar.indexOf(texto.charAt(i+j)));
				
				i += Math.max(s[j+1], j - aux);
			}
		}
	}
	
	private void iniciaOcc(ArrayList<Character> occChar, ArrayList<Integer> occPos, String patron) {
		for(int n = patron.length() - 1; n >= 0; n--) {
			if(!occChar.contains(patron.charAt(n))) {
				occChar.add(patron.charAt(n));
				occPos.add(n);
			}
		}
	}
	
	private void preproceso1(int [] s, int [] f, String patron) {
		int i = patron.length(), j = i + 1;
		
		f[i] = j;
		
		while(i > 0) {
			while(j <= patron.length() && patron.charAt(i - 1) != patron.charAt(j - 1)) {
				if(s[j] == 0)
					s[j] = j - i;
				
				j = f[j];
			}
			
			i--;
			j--;
			
			f[i] = j;
		}
	}
	
	private void preproceso2(int [] s, int [] f, String patron) {
		int j = f[0];
		
		for (int i = 0; i <= patron.length(); i++) {
			if(s[i] == 0)
				s[i] = j;
			
			if(i == j)
				j = f[j];
		}
	}
	
	public int karpRabin() throws IOException {
		int lineasRestantes = calcularPorcentaje();
		ArrayList<Integer> elegidas = new ArrayList<Integer>();
		ArrayList<Integer> ocurrencias = new ArrayList<Integer>();
		String texto;
		
		while(lineasRestantes > 0) {
			texto = elegirLinea(elegidas);
			ocurrencias = karpRabin(ocurrencias, patron, texto);
			lineasRestantes--;
		}
		
		return estimarOcurrencias(ocurrencias.size());
	}
	
	private ArrayList<Integer> karpRabin(ArrayList<Integer> ocurrencias, String patron, String texto){
		if(patron.length() > 0 && texto.length() >= patron.length()) {
			int m = patron.length();
			for(int n = 0; n <= texto.length()-m; n++) {
				String aux = texto.substring(n,  n+m);
				if(aux.hashCode() == patron.hashCode() && aux.equals(patron))
					ocurrencias.add(n);
			}
		}
		
		return ocurrencias;
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