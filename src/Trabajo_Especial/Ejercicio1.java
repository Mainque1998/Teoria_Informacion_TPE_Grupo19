package Trabajo_Especial;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.TreeMap;

public class Ejercicio1{

	public Ejercicio1() {}
	
	public void ejecutar(int [][]matrizWillOriginal, int [][]matrizWill1, int [][]matrizWill2, int [][]matrizWill3, int [][]matrizWill4, int [][]matrizWill5) {
		///ABRO ARCH
		PrintWriter out = null;
		try {out = new PrintWriter("Ejercicio1.txt");}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		///OBTENGO LOS COEFICIENTES
        double p1=  this.getPearson(matrizWillOriginal, matrizWill1);
        double p2=  this.getPearson(matrizWillOriginal, matrizWill2);
        double p3=  this.getPearson(matrizWillOriginal, matrizWill3);
        double p4=  this.getPearson(matrizWillOriginal, matrizWill4);
        double p5=  this.getPearson(matrizWillOriginal, matrizWill5);
        
        ///GENERO LA LISTA ORD POR COEFICIENTES (MENOS A MAYOR)
        TreeMap<Double, String> coeficientes= new TreeMap<Double, String>();//treemap mantiene en orden de menor a mayor por clave
        coeficientes.put(p1, "imagen1");
        coeficientes.put(p2, "imagen2");
        coeficientes.put(p3, "imagen3");
        coeficientes.put(p4, "imagen4");
        coeficientes.put(p5, "imagen5");
        ///IMPRIMO EN ARCH
        out.println("Lista de imagenes de menos a más parecidas");
        for(Double p: coeficientes.keySet())
        	out.println(coeficientes.get(p)+" su coeficiente con la original es " + p);
        ///CIERRO ARCH
        out.close();
	}
	
    private double getPearson(int [][]img1, int[][]img2) {
    	Formulas f= new Formulas();
    	double media1= f.getMedia(img1);
    	double media2= f.getMedia(img2);
    	double acum1=0;
    	double acum2=0;
    	double acum3=0;
    	for(int i=0; i < img1.length; i++)//dimensiones iguales, solo tomo la de una
    		for(int j=0; j < img1[0].length; j++) {
    			acum1+=(img1[i][j]-media1)*(img2[i][j]-media2); 
    			acum2+= Math.pow((img1[i][j]-media1) , 2);
    			acum3+= Math.pow((img2[i][j]-media2) , 2);
    		}
    	return (double)acum1/ (double)Math.sqrt(acum2*acum3);
    }
}