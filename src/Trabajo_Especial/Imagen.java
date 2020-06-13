package Trabajo_Especial;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Imagen {
	public Imagen() {}

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

    public void generarImagen(int [] [] matriz, String path) {
    	BufferedImage img = new BufferedImage(1310, 1700, BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < img.getWidth(); x++) 
		   for(int y = 0; y < img.getHeight(); y++) {
			   Color color = new Color (matriz[y][x] , matriz[y][x], matriz[y][x]);
			   img.setRGB(x,y,color.getRGB());
		   }
		try {ImageIO.write(img, "bmp", new File(path));}
		catch (IOException e) {System.out.println("Error de escritura");}
    }
	
}
