package Trabajo_Especial;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.TreeMap;

public class Ejercicio2 extends Main {

	public Ejercicio2() {}

	public void ejecutar(int [][]matrizWillOriginal, int [][]matrizWillMasParecido, int [][]matrizWillej2) {
		PrintWriter out = null;
		try {out = new PrintWriter("ejercicio2.txt");}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		Formulas f = new Formulas();
		
        out.println("Distribucion de intensidades de WillOriginal");
        TreeMap<Integer, Integer> distOriginal= f.getDistribucion(matrizWillOriginal);
        for(Integer p: distOriginal.keySet())
        	out.println(p + ": " + distOriginal.get(p));
        double m= f.getMedia(matrizWillOriginal);
        	out.println("Media: " + m + ", Desvio: " + f.getDesvio(matrizWillOriginal, m));
        
        out.println("Distribucion de intensidades de WillMásParecido");
        TreeMap<Integer, Integer> distMasParecido= f.getDistribucion(matrizWillMasParecido);
        for(Integer p: distOriginal.keySet())
        	out.println(p + ": " + distMasParecido.get(p));
        m= f.getMedia(matrizWillMasParecido);
        out.println("Media: " + m + ", Desvio: " + f.getDesvio(matrizWillMasParecido, m));
        
        out.println("Distribucion de intensidades de WillPolicia");
        TreeMap<Integer, Integer> distej2= f.getDistribucion(matrizWillej2);
        for(Integer p: distOriginal.keySet())
        	out.println(p + ": " + distej2.get(p));
        m= f.getMedia(matrizWillej2);
        out.println("Media: " + m + ", Desvio: " + f.getDesvio(matrizWillej2, m));
        out.close();
	}
}
