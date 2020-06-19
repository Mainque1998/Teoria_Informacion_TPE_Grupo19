package Trabajo_Especial;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ejercicio3{

	public Ejercicio3() {}
		
	public void ejecutar(int [][]matrizWillOriginal, int [][]matrizWill1, int [][] matrizWillej2) throws IOException{///AGREGADO PARA EL ARCH
		Formulas f= new Formulas();
		Imagen imagen= new Imagen();
		//INCISO A
		TreeMap<Integer, Integer> distribucionA= f.getDistribucion(matrizWillOriginal);
		Nodo arbolHuffmanA = this.getArbolHuffman(distribucionA);
		TreeMap<Integer, String> codigosA=new TreeMap<Integer, String>();  
		this.getCodigos(arbolHuffmanA, "", codigosA);
		
		///GENERA OUTPUT Y CABACERAS CON LAS DISTRIBUCIONES PARA ARMAR EL ARBOL
		byte[] mensajeComprimido= this.ConvertByteListToPrimitives(this.codificarMensaje(codigosA, matrizWillOriginal));
		FileOutputStream fosA = new FileOutputStream("Ejercicio3_outputA.bin");
		fosA.write(mensajeComprimido);
		fosA.close();//LISTO .BIN
		
		PrintWriter outA = null;
		try {outA = new PrintWriter("Ejercicio3_outputACabecera.txt");}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		for(Integer i: distribucionA.keySet())
        	outA.print(i + "," + distribucionA.get(i) + " ");
		outA.print(".");
		outA.close();//LISTO .TXT
		imagen.generarImagen(this.decodoficarMensaje("Ejercicio3_outputA.bin", "Ejercicio3_outputACabecera.txt"), "Ejercicio3_A.bmp");
		///FIN INCISO DE A
		
		//INCISO B
		///GENERA OUTPUT Y CABACERAS CON LAS DISTRIBUCIONES PARA ARMAR EL ARBOL
		mensajeComprimido= this.ConvertByteListToPrimitives(this.codificarMensaje(codigosA, matrizWill1));
		FileOutputStream fosB = new FileOutputStream("Ejercicio3_outputB.bin");
		fosB.write(mensajeComprimido);
		fosB.close();//LISTO .BIN
		
		PrintWriter outB = null;
		try {outB = new PrintWriter("Ejercicio3_outputBCabecera.txt");}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		for(Integer i: distribucionA.keySet())
        	outB.print(i + "," + distribucionA.get(i) + " ");
		outB.print(".");
		outB.close();//LISTO .TXT
		imagen.generarImagen(this.decodoficarMensaje("Ejercicio3_outputB.bin", "Ejercicio3_outputBCabecera.txt"), "Ejercicio3_B.bmp");
		///FIN INCISO DE B
		
		//INCISO C
		///GENERA OUTPUT Y CABACERAS CON LAS DISTRIBUCIONES PARA ARMAR EL ARBOL
		mensajeComprimido= this.ConvertByteListToPrimitives(this.codificarMensaje(codigosA, matrizWillej2));
		FileOutputStream fosC = new FileOutputStream("Ejercicio3_outputC.bin");
		fosC.write(mensajeComprimido);
		fosC.close();//LISTO .BIN
		
		PrintWriter outC = null;
		try {outC = new PrintWriter("Ejercicio3_outputCCabecera.txt");}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		for(Integer i: distribucionA.keySet())
        	outC.print(i + "," + distribucionA.get(i) + " ");
		outC.print(".");
		outC.close();//LISTO .TXT
		imagen.generarImagen(this.decodoficarMensaje("Ejercicio3_outputC.bin", "Ejercicio3_outputCCabecera.txt"), "Ejercicio3_C.bmp");
		///FIN INCISO DE C
		
		//INCISO D
		TreeMap<Integer, Integer> distribucionD= f.getDistribucion(matrizWillej2);
		Nodo arbolHuffmanD = this.getArbolHuffman(distribucionD);
		TreeMap<Integer, String> codigosD=new TreeMap<Integer, String>();  
		this.getCodigos(arbolHuffmanD, "", codigosD);
		
		///GENERA OUTPUT Y CABACERAS CON LAS DISTRIBUCIONES PARA ARMAR EL ARBOL
		mensajeComprimido= this.ConvertByteListToPrimitives(this.codificarMensaje(codigosD, matrizWillej2));
		FileOutputStream fosD = new FileOutputStream("Ejercicio3_outputD.bin");
		fosD.write(mensajeComprimido);
		fosD.close();//LISTO .BIN
		
		PrintWriter outD = null;
		try {outD = new PrintWriter("Ejercicio3_outputDCabecera.txt");}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		for(Integer i: distribucionD.keySet())
        	outD.print(i + "," + distribucionD.get(i) + " ");
		outD.print(".");
		outD.close();//LISTO .TXT
		imagen.generarImagen(this.decodoficarMensaje("Ejercicio3_outputD.bin", "Ejercicio3_outputDCabecera.txt"), "Ejercicio3_D.bmp");
		///FIN INCISO DE D
		
	}

	public Nodo getArbolHuffman(TreeMap<Integer, Integer> probabilidades){
        PriorityQueue<Nodo> cola = new PriorityQueue<Nodo>(probabilidades.size(), new Comparador());
        for (Integer p: probabilidades.keySet()){
            Nodo nuevoNodo = new Nodo();
            nuevoNodo.setColor(p); 
            nuevoNodo.setProbabilidad(probabilidades.get(p)); 
            nuevoNodo.setIzq(null);
            nuevoNodo.setDer(null); 
            cola.add(nuevoNodo);
        } 
        Nodo raiz = null; 
        while (cola.size() > 1) { 
            Nodo primero = cola.peek(); //OBTENGO EL PRIMERO
            cola.poll(); //REMUEVO EL PRIMERO
            Nodo segundo = cola.peek(); 
            cola.poll(); 
            Nodo n = new Nodo(); //JUNTO LOS DOS NODOS DE MENOR PROBABILIDAD
            n.setProbabilidad(primero.getProbabilidad() + segundo.getProbabilidad()); 
            n.setColor(-1);
            n.setIzq(primero); 
            n.setDer(segundo); 
            raiz = n; 
            cola.add(n); 
        } 
        
        return raiz;
    } 
	
	public void getCodigos(Nodo raiz, String s, TreeMap<Integer, String> solucion){///OBTIENE LOS CODIGOS DEL ARBOL
		if (raiz.izq == null && raiz.der == null) {
			solucion.put(raiz.color, s);
        }
		else {
			getCodigos(raiz.getIzq(), s+"0", solucion);
			getCodigos(raiz.getDer(), s+"1", solucion);
		}
    }
	
	public List<Byte> codificarMensaje(TreeMap<Integer, String> codigos, int [][]mensaje) {///CODIFICADOR
		List<Byte> result = new ArrayList<Byte>();
		byte buffer = 0;
		int bufferPos = 0;			
		String aux;
    	for(int i=0; i < mensaje.length; i++)
    		for(int j=0; j < mensaje[0].length; j++) {
				aux= codigos.get(mensaje[i][j]);
				for(int k=0; k<aux.length(); k++) {
					buffer = (byte) (buffer << 1);
					bufferPos++;
					if (aux.charAt(k) == '1')
						buffer = (byte) (buffer | 1);
					if (bufferPos == 8 ) {
						result.add(buffer);
						buffer = 0;
						bufferPos = 0;
					}
				}
    		}
    	if (bufferPos != 0) {
    		buffer = (byte) (buffer << 8-bufferPos);
			result.add(buffer);///por si el ultimo buffer no se llego a completar
    	}
		return result;
	}
	
	public byte[] ConvertByteListToPrimitives(List<Byte> input) {///AGREGADO PARA EL ARCH
		byte[] ret = new byte[input.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = input.get(i);
		}

		return ret;
	}
	
	public int[][] decodoficarMensaje(String dirPathCompact, String dirCabecera) {///DECODIFICADOR
		int[][] mensaje= new int[1700][1310];//inicializacion de la matriz resultado
		
		try {
			Nodo raizArbolHuffman = this.getArbolHuffman(this.leerCabecera(dirCabecera));
			byte[] inputSequence = Files.readAllBytes(new File(dirPathCompact).toPath());
			byte mask = (byte) (1 << 7); // mask: 10000000
			int bufferPos = 0;
			
			Nodo puntAux= raizArbolHuffman;
			int indiceMensaje=0;
			byte buffer = inputSequence[0];
			for(int i=0;i<mensaje.length; i++)
				for(int j=0; j<mensaje[0].length; j++){
					boolean cargoColor=false;
					while (!cargoColor) {
						
						if ((buffer & mask) == mask)//si es 1
			                puntAux = puntAux.getDer();
			            else
			            	puntAux = puntAux.getIzq();
						
			            if (puntAux.getIzq() == null && puntAux.getDer() == null) {
			                mensaje[i][j] = puntAux.getColor();
			                puntAux = raizArbolHuffman;
			                cargoColor= true;
			            }
						
						buffer = (byte) (buffer << 1);
						bufferPos++;
						if(bufferPos==8) {
							indiceMensaje++;
							bufferPos = 0;
							if(indiceMensaje<inputSequence.length)
								buffer = inputSequence[indiceMensaje];
							else System.out.println("esto no deberia pasar");
						}
					}
					
				}
			
		} catch (IOException e1) {e1.printStackTrace();}
		
		return mensaje;
	}

	
	public TreeMap<Integer, Integer> leerCabecera(String path) throws IOException{
		List<String> s= Files.readAllLines( new File(path).toPath() ); //s.get(0) es el unico string
		TreeMap<Integer, Integer> distribucion= new TreeMap<Integer, Integer>();
		int i=0;
		while (s.get(0).charAt(i) != '.') {//pregunta si no termino
			int j=i;
			while(s.get(0).charAt(j) != ',')//a izq color, a der frecuencia
				j++;
			Integer color = Integer.valueOf(s.get(0).substring(i, j));
			i=j+1;
			while(s.get(0).charAt(j) != ' ')
				j++;
			Integer frecuencia = Integer.valueOf(s.get(0).substring(i, j));
			distribucion.put(color, frecuencia);
			i=j+1;
		}
		return distribucion;
	}
}

////////////////CLASES NODO Y COMPARADOR
class Nodo { 
	
    int probabilidad; 
    Integer color; 
    Nodo izq; 
    Nodo der; 
    
    public int getProbabilidad() {
    	return this.probabilidad;
    }
    
    public Integer getColor() {
    	return this.color;
    }
    
    public Nodo getIzq() {
    	return this.izq;
    }
    
    public Nodo getDer() {
    	return this.der;
    }
    
    public void setProbabilidad(int p) {
    	this.probabilidad=p;
    }
    
    public void setColor(Integer c) {
    	this.color=c;
    }
    
    public void setIzq(Nodo n) {
    	this.izq=n;
    }
    
    public void setDer(Nodo n) {
    	this.der=n;
    }
} 

class Comparador implements Comparator<Nodo> { 
    public int compare(Nodo x, Nodo y) 
    { 
  
        return x.probabilidad - y.probabilidad; 
    } 
}
