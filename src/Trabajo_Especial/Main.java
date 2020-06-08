package Trabajo_Especial;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
    	
    	///CARGA DE MATRICES
    	Imagen imagen = new Imagen();
    	
        String willOriginal =  "ImagenesWill"+ File.separator +"Will(Original).bmp";
    	BufferedImage img = imagen.cargarImagen(willOriginal);
        int[][] matrizWillOriginal= imagen.cargarMatriz(img);
        
        String will1 = "ImagenesWill"+ File.separator +"Will_1.bmp";
        BufferedImage img1 = imagen.cargarImagen(will1);
        int[][] matrizWill1= imagen.cargarMatriz(img1);
        
        String will2 = "ImagenesWill"+ File.separator+"Will_2.bmp";
        BufferedImage img2 = imagen.cargarImagen(will2);
        int[][] matrizWill2= imagen.cargarMatriz(img2);
        
        String will3 = "ImagenesWill"+ File.separator +"Will_3.bmp";
        BufferedImage img3 = imagen.cargarImagen(will3);
        int[][] matrizWill3= imagen.cargarMatriz(img3);
        
        String will4 = "ImagenesWill"+ File.separator+"Will_4.bmp";
        BufferedImage img4 = imagen.cargarImagen(will4);
        int[][] matrizWill4= imagen.cargarMatriz(img4);
        
        String will5 = "ImagenesWill"+ File.separator+"Will_5.bmp";
        BufferedImage img5 = imagen.cargarImagen(will5);
        int[][] matrizWill5= imagen.cargarMatriz(img5);
        
        String willej2 = "ImagenesWill"+ File.separator+"Will_ej2.bmp";
        BufferedImage imgej2 = imagen.cargarImagen(willej2);
        int[][] matrizWillej2= imagen.cargarMatriz(imgej2);
        
        /*String willC2 = "ImagenesWill"+ File.separator+"Will_Canal2.bmp";
        BufferedImage imgC2 = imagen.cargarImagen(willC2);
        int[][] matrizWillC2= imagen.cargarMatriz(imgC2);
        
        String willC10 = "ImagenesWill"+ File.separator+"Will_Canal10.bmp";
        BufferedImage imgC10 = imagen.cargarImagen(willC10);
        int[][] matrizWillC10= imagen.cargarMatriz(imgC10);
        
        String willC8 = "ImagenesWill"+ File.separator+"Will_Canal8.bmp";
        BufferedImage imgC8 = imagen.cargarImagen(willC8);
        int[][] matrizWillC8= imagen.cargarMatriz(imgC8);*/
        ///FIN DE CARGA DE MATRIZ
        
        ///EJECUCION DE EJERCICIOS
        Ejercicio1 e1= new Ejercicio1();
        e1.ejecutar(matrizWillOriginal, matrizWill1, matrizWill2, matrizWill3, matrizWill4, matrizWill5);
        
        Ejercicio2 e2= new Ejercicio2();
        e2.ejecutar(matrizWillOriginal, matrizWill1, matrizWillej2);///Estaria bueno pasar el resultado de ejercicio1 y no "matrizWill1", o sea hacerlo dinamico
        
        Ejercicio3 e3= new Ejercicio3();
        try {///AGREGADO PARA EL ARCH
			e3.ejecutar(matrizWillOriginal);
		} catch (IOException e) {
			e.printStackTrace();
		}
        ///FIN DE EJECUCION DE EJERCICIOS
        
    }

}