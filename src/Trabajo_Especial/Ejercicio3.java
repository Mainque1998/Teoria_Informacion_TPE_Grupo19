package Trabajo_Especial;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
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
		//OBTIENE CODIFICACION DE LA ORIGINAL
		TreeMap<Integer, Integer> distribucionA= f.getDistribucion(matrizWillOriginal);
		Nodo arbolHuffmanA = this.getArbolHuffman(distribucionA);
		TreeMap<Integer, String> codigosA=new TreeMap<Integer, String>();  
		this.getCodigos(arbolHuffmanA, "", codigosA);
		
		///GENERA OUTPUT Y CABACERAS CON LAS DISTRIBUCIONES PARA ARMAR EL ARBOL
		byte[] mensajeComprimido= this.ConvertByteListToPrimitives(this.codificarMensaje(codigosA, matrizWillOriginal));
		this.generarArchivoComprimido("Ejercicio3_outputA.bin", mensajeComprimido, distribucionA);
		
		imagen.generarImagen(this.decodificarMensaje("Ejercicio3_outputA.bin"), "Ejercicio3_A.bmp");
		///FIN INCISO DE A
		
		//INCISO B
		///GENERA OUTPUT Y CABACERAS CON LAS DISTRIBUCIONES PARA ARMAR EL ARBOL
		mensajeComprimido= this.ConvertByteListToPrimitives(this.codificarMensaje(codigosA, matrizWill1));
		this.generarArchivoComprimido("Ejercicio3_outputB.bin", mensajeComprimido, distribucionA);
		
		imagen.generarImagen(this.decodificarMensaje("Ejercicio3_outputB.bin"), "Ejercicio3_B.bmp");
		///FIN INCISO DE B
		
		//INCISO C
		///GENERA OUTPUT Y CABACERAS CON LAS DISTRIBUCIONES PARA ARMAR EL ARBOL
		mensajeComprimido= this.ConvertByteListToPrimitives(this.codificarMensaje(codigosA, matrizWillej2));
		this.generarArchivoComprimido("Ejercicio3_outputC.bin", mensajeComprimido, distribucionA);
		
		imagen.generarImagen(this.decodificarMensaje("Ejercicio3_outputC.bin"), "Ejercicio3_C.bmp");
		///FIN INCISO DE C
		
		//INCISO D
		//OBTIENE LA CODIFICACION DE LA DEL POLICIA
		TreeMap<Integer, Integer> distribucionD= f.getDistribucion(matrizWillej2);
		Nodo arbolHuffmanD = this.getArbolHuffman(distribucionD);
		TreeMap<Integer, String> codigosD=new TreeMap<Integer, String>();  
		this.getCodigos(arbolHuffmanD, "", codigosD);
		
		///GENERA OUTPUT Y CABACERAS CON LAS DISTRIBUCIONES PARA ARMAR EL ARBOL
		mensajeComprimido= this.ConvertByteListToPrimitives(this.codificarMensaje(codigosD, matrizWillej2));
		this.generarArchivoComprimido("Ejercicio3_outputD.bin", mensajeComprimido, distribucionD);
		
		imagen.generarImagen(this.decodificarMensaje("Ejercicio3_outputD.bin"), "Ejercicio3_D.bmp");
		///FIN INCISO DE D
		
		///INCISO E
		PrintWriter out = null;
		try {out = new PrintWriter("Ejercicio3_E.txt");}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		File file = new File("Ejercicio3_outputA.bin");
		long tamComprimido = file.length();
		file = new File("ImagenesWill"+ File.separator +"Will(Original).bmp");
		long tamOriginal = file.length();
		out.println("Tasa compresion para inciso A es "+((float)tamOriginal/tamComprimido));
		
		file = new File("Ejercicio3_outputB.bin");
		tamComprimido = file.length();
		file = new File("ImagenesWill"+ File.separator +"Will_1.bmp");
		tamOriginal = file.length();
		out.println("Tasa compresion para inciso B es "+((float)tamOriginal/tamComprimido));
		
		file = new File("Ejercicio3_outputC.bin");
		tamComprimido = file.length();
		file = new File("ImagenesWill"+ File.separator +"Will_ej2.bmp");
		tamOriginal = file.length();
		out.println("Tasa compresion para inciso C es "+((float)tamOriginal/tamComprimido));
		
		file = new File("Ejercicio3_outputD.bin");
		tamComprimido = file.length();
		file = new File("ImagenesWill"+ File.separator +"Will_ej2.bmp");
		tamOriginal = file.length();
		out.println("Tasa compresion para inciso D es "+((float)tamOriginal/tamComprimido));
		
		out.close();
		///FIN INCISO E
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
	
	public void generarArchivoComprimido(String path, byte[] mensajeComprimido, TreeMap<Integer, Integer> distribucionA) throws IOException {
		FileOutputStream fosA = new FileOutputStream(path);
		fosA.write(distribucionA.size()*4);///Primer byte contiene longitud del header
		for(int color: distribucionA.keySet()) {///siempre se escribe en [-128,127]
			fosA.write(color);///escribe el color (1 byte, si es negativo entonces hay que sumarle 256)
			String frecuenciaBin= Integer.toBinaryString(distribucionA.get(color));
			///divido la frecuencia en 3 bytes
			byte buffer1 = 0;
			byte buffer2= 0;
			byte buffer3= 0;
			byte mask = (byte) (1 << 7); // mask: 10000000
			for(int i=0; i<frecuenciaBin.length(); i++) {
				if(i<frecuenciaBin.length()) {
					buffer3 = (byte) (buffer3 << 1);
					if ((buffer2 & mask) == mask)
						buffer3 = (byte) (buffer3 | 1);
					buffer2 = (byte) (buffer2 << 1);
					if ((buffer1 & mask) == mask)
						buffer2 = (byte) (buffer2 | 1);
				}
				buffer1 = (byte) (buffer1 << 1);
				if (frecuenciaBin.charAt(i) == '1')
					buffer1 = (byte) (buffer1 | 1);
			}
			fosA.write(buffer3);
			fosA.write(buffer2);
			fosA.write(buffer1);
		}//(4 bytes por cada color)
		fosA.write(mensajeComprimido);///el resto de los bytes son la imagen codificada
		fosA.close();
	}
	
	public int[][] decodificarMensaje(String dirPathCompact) {///DECODIFICADOR
		int[][] mensaje= new int[1700][1310];//inicializacion de la matriz resultado
		
		try {
			byte[] inputSequence = Files.readAllBytes(new File(dirPathCompact).toPath());///obtiene un arreglo de bytes del .bin
			Nodo raizArbolHuffman = this.getArbolHuffman(this.leerCabecera(inputSequence));///genera el arbol con la cabecera del arch
			
			///comienza el decode
			byte mask = (byte) (1 << 7); // mask: 10000000
			int bufferPos = 0;
			
			Nodo puntAux= raizArbolHuffman;
			int indiceMensaje=inputSequence[0]+1;///comienzo del mensaje codificado
			byte buffer = inputSequence[indiceMensaje];
			for(int i=0;i<mensaje.length; i++)///i arranca desde longitud del header +1
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
	
	public TreeMap<Integer, Integer> leerCabecera(byte[] inputSequence) {
		TreeMap<Integer, Integer> distribucion= new TreeMap<Integer, Integer>();
		for(int i=1; i<inputSequence[0]+1;i+=4) {///i color, de i+1 a i+3 frecuencia
			String s1 = String.format("%8s", Integer.toBinaryString(inputSequence[i+1] & 0xFF)).replace(' ', '0');
			String s2 = String.format("%8s", Integer.toBinaryString(inputSequence[i+2] & 0xFF)).replace(' ', '0');
			String s3 = String.format("%8s", Integer.toBinaryString(inputSequence[i+3] & 0xFF)).replace(' ', '0');
			if(inputSequence[i]<0)
				distribucion.put(inputSequence[i]+256, Integer.parseInt(s1+s2+s3,2));
			else
				distribucion.put((int)inputSequence[i], Integer.parseInt(s1+s2+s3,2));
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
