package Trabajo_Especial;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) {
    	Main hola = new Main();
    	
    	///GENERAR ARCHIVOS
    	PrintWriter out1 = null;
    	PrintWriter out2 = null;
		try {
			out1 = new PrintWriter("ejercicio1.txt");
			out2 = new PrintWriter("ejercicio2.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	///FIN DE GENERAR ARCHIVOS
    	
    	///CARGA DE MATRICES
    	
        String willOriginal =  "ImagenesWill"+ File.separator +"Will(Original).bmp";
    	BufferedImage img = hola.cargarImagen(willOriginal);
        int[][] matrizWillOriginal= hola.cargarMatriz(img);
        
        String will1 = "ImagenesWill"+ File.separator +"Will_1.bmp";
        BufferedImage img1 = hola.cargarImagen(will1);
        int[][] matrizWill1= hola.cargarMatriz(img1);
        
        String will2 = "ImagenesWill"+ File.separator+"Will_2.bmp";
        BufferedImage img2 = hola.cargarImagen(will2);
        int[][] matrizWill2= hola.cargarMatriz(img2);
        
        String will3 = "ImagenesWill"+ File.separator +"Will_3.bmp";
        BufferedImage img3 = hola.cargarImagen(will3);
        int[][] matrizWill3= hola.cargarMatriz(img3);
        
        String will4 = "ImagenesWill"+ File.separator+"Will_4.bmp";
        BufferedImage img4 = hola.cargarImagen(will4);
        int[][] matrizWill4= hola.cargarMatriz(img4);
        
        String will5 = "ImagenesWill"+ File.separator+"Will_5.bmp";
        BufferedImage img5 = hola.cargarImagen(will5);
        int[][] matrizWill5= hola.cargarMatriz(img5);
        
        String willej2 = "ImagenesWill"+ File.separator+"Will_ej2.bmp";
        BufferedImage imgej2 = hola.cargarImagen(willej2);
        int[][] matrizWillej2= hola.cargarMatriz(imgej2);
        
        /*String willC2 = "C:/Users/usuario/Downloads/ImagenesWill/Will_Canal2.bmp";///revisar luego el separeitor
        BufferedImage imgC2 = hola.cargarImagen(willC2);
        int[][] matrizWillC2= hola.cargarMatriz(imgC2);
        
        String willC10 = "C:/Users/usuario/Downloads/ImagenesWill/Will_Canal10.bmp";///revisar luego el separeitor
        BufferedImage imgC10 = hola.cargarImagen(willC10);
        int[][] matrizWillC10= hola.cargarMatriz(imgC10);
        
        String willC8 = "C:/Users/usuario/Downloads/ImagenesWill/Will_Canal8.bmp";///revisar luego el separeitor
        BufferedImage imgC8 = hola.cargarImagen(willC8);
        int[][] matrizWillC8= hola.cargarMatriz(imgC8);*/
        
        ///FIN DE CARGA DE MATRIZ
        
        ///COEFICIENTES EJERCICIO1
        double p1=  hola.getPearson(matrizWillOriginal, matrizWill1);
        double p2=  hola.getPearson(matrizWillOriginal, matrizWill2);
        double p3=  hola.getPearson(matrizWillOriginal, matrizWill3);
        double p4=  hola.getPearson(matrizWillOriginal, matrizWill4);
        double p5=  hola.getPearson(matrizWillOriginal, matrizWill5);
        
        TreeMap<Double, String> coeficientes= new TreeMap<Double, String>();///treemap mantiene en orden de menor a mayor por clave
        coeficientes.put(p1, "imagen1");
        coeficientes.put(p2, "imagen2");
        coeficientes.put(p3, "imagen3");
        coeficientes.put(p4, "imagen4");
        coeficientes.put(p5, "imagen5");
        out1.println("Lista de imagenes de menos a más parecidas");
        for(Double p: coeficientes.keySet())
        	out1.println(coeficientes.get(p)+" su coeficiente con la original es " + p);
        ///FIN DE COEFICIENTES
        
        ///DISTRIBUCION DE DENSIDADES, EJERCICIO2
        out2.println("Distribucion de intensidades de WillOriginal");
        TreeMap<Integer, Integer> distOriginal= hola.getDistribucion(matrizWillOriginal);
        for(Integer p: distOriginal.keySet())
        	out2.println(p + ": " + distOriginal.get(p));
        double m= hola.getMedia(matrizWillOriginal);
        	out2.println("Media: " + m + ", Desvio: " + hola.getDesvio(matrizWillOriginal, m));
        
        out2.println("Distribucion de intensidades de WillMásParecido");
        TreeMap<Integer, Integer> dist1= hola.getDistribucion(matrizWill1);
        for(Integer p: distOriginal.keySet())
        	out2.println(p + ": " + dist1.get(p));
        m= hola.getMedia(matrizWill1);
        out2.println("Media: " + m + ", Desvio: " + hola.getDesvio(matrizWill1, m));
        
        out2.println("Distribucion de intensidades de WillPolicia");
        TreeMap<Integer, Integer> distej2= hola.getDistribucion(matrizWillej2);
        for(Integer p: distOriginal.keySet())
        	out2.println(p + ": " + distej2.get(p));
        m= hola.getMedia(matrizWillej2);
        out2.println("Media: " + m + ", Desvio: " + hola.getDesvio(matrizWillej2, m));
        ///FIN DE DISTRIBUCION
        
        ///CIERRE DE ARCHIVOS
    	out1.close();
        out2.close();
        ///FIN DE CIERRE DE ARCHIVOS
        
    }

    public BufferedImage cargarImagen(String directorioImagen) {
        try {
            BufferedImage img = ImageIO.read(new File(directorioImagen));
            return img;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int[][] cargarMatriz(BufferedImage img) {
    	int[][] m= new int[img.getHeight()][img.getWidth()];
        for (int i = 0; i < img.getWidth(); i++)
            for (int j = 0; j < img.getHeight(); j++) {
            	int rgb = img.getRGB(i, j);
                Color color = new Color(rgb, true);
                m[j][i] = color.getBlue();
            }
        return m;
    }
    
    
    ///MEDIA Y PEARSON
    public double getMedia(int [][] imagen) {
    	double acum=0;
    	
    	for(int i=0; i < imagen.length; i++)
    		for(int j=0; j < imagen[0].length; j++)
    			acum+= imagen[i][j];
    	return acum / (double) (imagen.length * imagen[0].length);
    }
    
    public double getPearson(int [][]img1, int[][]img2) {
    	double media1= this.getMedia(img1);
    	double media2= this.getMedia(img2);
    	double acum1=0;
    	double acum2=0;
    	double acum3=0;
    	for(int i=0; i < img1.length; i++)///dimensiones iguales, solo tomo las dim de una
    		for(int j=0; j < img1[0].length; j++) {
    			acum1+=(img1[i][j]-media1)*(img2[i][j]-media2); 
    			acum2+= Math.pow((img1[i][j]-media1) , 2);
    			acum3+= Math.pow((img2[i][j]-media2) , 2);
    		}
    	return (double)acum1/ (double)Math.sqrt(acum2*acum3);
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    ///DISTRIBUCION Y DESVIO
    public TreeMap<Integer, Integer> getDistribucion(int[][] matriz){
    	TreeMap<Integer, Integer> distribucion= new TreeMap<Integer, Integer>();
        for (int i = 0; i < matriz.length; i++)
            for (int j = 0; j < matriz[0].length; j++){
            	if(distribucion.containsKey(matriz[i][j]))
            		distribucion.put(matriz[i][j], distribucion.get(matriz[i][j])+1);///put asigna o remplaza el viejo elemento de la clave si tenia
            	else
            		distribucion.put(matriz[i][j],1);
            }
        return distribucion;
    }
    
    public double getDesvio(int [][]img, double media) {
        	double acum=0;
        	for(int i=0; i < img.length; i++)
        		for(int j=0; j < img[0].length; j++)
        			acum+= Math.pow((img[i][j]-media) , 2);
        	return (double)Math.sqrt(acum / (double)(img.length *img[0].length));
    }
    
    ///FIN DE DISTRIBUCION Y DESVIO
    
    ///CIERRE DE ARCHIVOS
	
    /*
    public void escribirPixeles(BufferedImage img) {
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int r = 0, g = 0, b = 0;  //Inicializados en 0 para que compile
                //Hacer algo
                Color color = new Color(r, g, b);
                img.setRGB(x, y, color.getRGB());
            }
        }
    }
    
    public void imprimirMapaDeBits (BufferedImage img) {
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgb = img.getRGB(x, y);
                Color color = new Color(rgb, true);
                System.out.print(color.getGreen() + "  ") ;
            }
            System.out.println();
        }
    }
    */
}
