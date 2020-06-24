package Trabajo_Especial;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.TreeMap;

public class Ejercicio2 {

	public Ejercicio2() {}

	public void ejecutar(int [][]matrizWillOriginal, int [][]matrizWillMasParecido, int [][]matrizWillej2) {
		///ABRO ARCHIVO
		PrintWriter out = null;
		try {out = new PrintWriter("Ejercicio2.txt");}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		Formulas f = new Formulas();
		
		///ORIGINAL
        out.println("Distribucion de intensidades de WillOriginal");
        TreeMap<Integer, Integer> distOriginal= f.getDistribucion(matrizWillOriginal);
        for(Integer p: distOriginal.keySet())
        	out.println(p + ": " + distOriginal.get(p));
        double m= f.getMedia(matrizWillOriginal);
        	out.println("Media: " + m + ", Desvio: " + f.getDesvio(matrizWillOriginal, m));
        
        ///MÁS PARECIDA
        out.println("Distribucion de intensidades de WillMásParecido");
        TreeMap<Integer, Integer> distMasParecido= f.getDistribucion(matrizWillMasParecido);
        for(Integer p: distOriginal.keySet())
        	out.println(p + ": " + distMasParecido.get(p));
        m= f.getMedia(matrizWillMasParecido);
        out.println("Media: " + m + ", Desvio: " + f.getDesvio(matrizWillMasParecido, m));
        
        ///DEL POLICIA
        out.println("Distribucion de intensidades de WillPolicia");
        TreeMap<Integer, Integer> distej2= f.getDistribucion(matrizWillej2);
        for(Integer p: distOriginal.keySet())
        	out.println(p + ": " + distej2.get(p));
        m= f.getMedia(matrizWillej2);
        out.println("Media: " + m + ", Desvio: " + f.getDesvio(matrizWillej2, m));
        
        ///CIERRTO ARCHVO
        out.close();
	}
}