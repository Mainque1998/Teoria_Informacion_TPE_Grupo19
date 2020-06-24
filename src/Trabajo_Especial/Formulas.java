package Trabajo_Especial;

import java.util.TreeMap;

public class Formulas {
	
	public Formulas(){}///CLASE PARA CALCULOS MATEMATICOS

    public double getMedia(int [][] imagen) {
    	double acum=0;
    	
    	for(int i=0; i < imagen.length; i++)
    		for(int j=0; j < imagen[0].length; j++)
    			acum+= imagen[i][j];
    	return acum / (double) (imagen.length * imagen[0].length);
    }
	
    public TreeMap<Integer, Integer> getDistribucion(int[][] matriz){
    	TreeMap<Integer, Integer> distribucion= new TreeMap<Integer, Integer>();
        for (int i = 0; i < matriz.length; i++)
            for (int j = 0; j < matriz[0].length; j++){
            	if(distribucion.containsKey(matriz[i][j]))
            		distribucion.put(matriz[i][j], distribucion.get(matriz[i][j])+1);//put asigna o remplaza el viejo elemento de la clave si tenia
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
}
