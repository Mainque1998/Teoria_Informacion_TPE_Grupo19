package Trabajo_Especial;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ejercicio3{

	public Ejercicio3() {}
		
		public void ejecutar(int [][]matrizWillOriginal) throws IOException{///AGREGADO PARA EL ARCH
			Formulas f= new Formulas();
			Nodo arbolHuffman = this.getArbolHuffman(f.getDistribucion(matrizWillOriginal));
			TreeMap<Integer, String> solucion=new TreeMap<Integer, String>();  
			this.getCodigos(arbolHuffman, "", solucion);
			
				///AGREGADO PARA EL ARCH
				byte[] b= this.ConvertByteListToPrimitives(this.codoficarMensajes(solucion));
			
				FileOutputStream fos = new FileOutputStream("output.bin");
				fos.write(b);
				fos.close();
				///CAPAZ SE PUEDE USAR OUTPUT, NO PROBE
		}
	
		public Nodo getArbolHuffman(TreeMap<Integer, Integer> probabilidades){
	        PriorityQueue<Nodo> q = new PriorityQueue<Nodo>(probabilidades.size(), new Comparador());
	        for (Integer p: probabilidades.keySet()){
	            Nodo nuevoNodo = new Nodo();
	            nuevoNodo.setColor(p); 
	            nuevoNodo.setProbabilidad(probabilidades.get(p)); 
	            nuevoNodo.setIzq(null);
	            nuevoNodo.setDer(null); 
	            q.add(nuevoNodo);
	        } 
	        Nodo raiz = null; 
	        while (q.size() > 1) { 
	            Nodo x = q.peek(); //OBTENGO EL PRIMERO
	            q.poll(); //REMUEVO EL PRIMERO
	            Nodo y = q.peek(); 
	            q.poll(); 
	            Nodo f = new Nodo(); //JUNTO LOS DOS NODOS DE MENOR PROBABILIDAD
	            f.setProbabilidad(x.getProbabilidad() + y.getProbabilidad()); 
	            f.setColor(-1);
	            f.setIzq(x); 
	            f.setDer(y); 
	            raiz = f; 
	            q.add(f); 
	        } 
	        
	        return raiz;
	    } 
		
		public void getCodigos(Nodo raiz, String s, TreeMap<Integer, String> solucion){
			if (raiz.izq == null && raiz.der == null) {// && raiz.getColor()>-1 
	            System.out.println(raiz.getColor() + ":" + s);
				solucion.put(raiz.color, s);
	        }
			else {
				getCodigos(raiz.getIzq(), s+"0", solucion);
				getCodigos(raiz.getDer(), s+"1", solucion);
			}
	    }
		
		public List<Byte> codoficarMensajes(TreeMap<Integer, String> codigos) {///EJEMPLO PARA PROBAR
			List<Byte> result = new ArrayList<Byte>();
			byte buffer = 0;
			int bufferPos = 0;			
			int mensaje[]= {255 , 221, 0 , 221};
			String aux;
	    	for(int i=0; i < mensaje.length; i++) {
				aux= codigos.get(mensaje[i]);
				///System.out.println(aux);///TEST
				for(int k=0; k<aux.length(); k++) {
					buffer = (byte) (buffer << 1);
					bufferPos++;
					buffer = (byte) (buffer | (byte) aux.charAt(k));
					if (bufferPos == 8 ) {
						////System.out.print(buffer);
						result.add(buffer);
						buffer = 0;
						bufferPos = 0;
					}
				}
			}
			return result;
		}
		
		/*public static List<Byte> codoficarMensajes(TreeMap<Integer, String> codigos, int [][]mensaje) {///CODIFICADOR
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
						buffer = (byte) (buffer | aux.charAt(k));
						if (bufferPos == 8 ) {
							result.add(buffer);
							buffer = 0;
							bufferPos = 0;
						}
					}
			}
			return result;
		}*/
		
		public byte[] ConvertByteListToPrimitives(List<Byte> input) {///AGREGADO PARA EL ARCH
			byte[] ret = new byte[input.size()];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = input.get(i);
			}

			return ret;
		}		
		
		/*public String decodificar(BitSet bits, Nodo raiz)///DECODIFICADOR ASÍ NOMAS
	    {
	        String decodificacion = "";
	        Nodo c = raiz;
	        for (int i = 0; i < bits.length(); i++) {
	            if (bits.get(i)) {    //VERDADERO -> 1, FALSO -> 0
	                c = c.getDer();
	            }
	            else {
	                c = c.getIzq();
	            }
	            if (c.getIzq() == null && c.getDer() == null) {
	                decodificacion += c.getColor();
	                c = raiz;
	            }
	        }
	        System.out.print(decodificacion);
	        return decodificacion;
	    }*/
		
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
