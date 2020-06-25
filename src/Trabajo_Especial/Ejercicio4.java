package Trabajo_Especial;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.TreeMap;

public class Ejercicio4 {
	
	public Ejercicio4() {}

	private final double ERROR = 0.000001;
	private final int MIN_MUESTRAS = 1000;
	private final int MILESTONE=100;
	
	public void ejecutar(int[][] matrizIn, int[][] matrizC1, int[][] matrizC2, int[][] matrizC3) {
		Formulas f= new Formulas();
		TreeMap<Integer, Integer> dIn = f.getDistribucion(matrizIn);
		PrintWriter out = null;
		PrintWriter outEvolucionError = null;
		try {out = new PrintWriter("Ejercicio4_IncisoB.txt");
			outEvolucionError = new PrintWriter("Ejercicio4_IncisoB_EvolucionError.txt");
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		
		///CANAL 2
		TreeMap<Integer, Integer> dOut = f.getDistribucion(matrizC1);
		float[][] matrizTrans= generarMatrizTransicional(matrizIn, dIn, matrizC1, dOut);
		imprimirMatriz(matrizTrans, "Ejercicio4_Matriz_C2.txt");///genera arch matriz
		outEvolucionError.println("Evolucion del error Canal 2:");
		out.println("Ruido del canal 2: "+ getRuido(this.getDistrAcum(dIn), this.getTransicionAcumColumna(matrizTrans), outEvolucionError));
		
		///CANAL 8
		dOut = f.getDistribucion(matrizC1);
		matrizTrans= generarMatrizTransicional(matrizIn, dIn, matrizC2, dOut);
		imprimirMatriz(matrizTrans, "Ejercicio4_Matriz_C8.txt");//genera arch matriz
		outEvolucionError.println("Evolucion del error Canal 8:");
		out.println("Ruido del canal 8: "+ getRuido(this.getDistrAcum(dIn), this.getTransicionAcumColumna(matrizTrans), outEvolucionError));
		
		///CANAL 10
		f.getDistribucion(matrizC1);
		matrizTrans= generarMatrizTransicional(matrizIn, dIn, matrizC3, dOut);
		imprimirMatriz(matrizTrans, "Ejercicio4_Matriz_C10.txt");//genera arch matriz
		outEvolucionError.println("Evolucion del error Canal 10:");
		out.println("Ruido del canal 10: "+ getRuido(this.getDistrAcum(dIn), this.getTransicionAcumColumna(matrizTrans), outEvolucionError));			
		
		///CIERRE DE ARCHIVOS
		outEvolucionError.close();
		out.close();
	}
	
	private float[][] generarMatrizTransicional(int[][] mat_entrada, TreeMap<Integer, Integer> dist_entrada, int[][] mat_salida, TreeMap<Integer, Integer> dist_salida) {
		
		///INICIALIZO LA MATRIZ CON TODOS 0
		float[][] matriz = new float[dist_salida.size()+1][dist_entrada.size()+1];
		for (int i=0; i<dist_salida.size()+1; i++) {
			for (int j=0; j<dist_entrada.size()+1; j++) {
				matriz[i][j]=0;
			}
		}
		
		///PONGO TODOS LOS TONOS DE COLORES DE LAS IMAGENES EN LA 1ER COLUMNA Y EN LA 1ER FILA DE LA MATRIZ
		int i=1;
		for (int e: dist_entrada.keySet()) {
			matriz[0][i]=e;
			i++;
		}
		int j=1;
		for (int e: dist_salida.keySet()) {
			matriz[j][0]=e;
			j++;
		}
		
		///LLENO LA MATRIZ
		for (i=0; i<mat_entrada.length; i++) {
			for (j=0; j<mat_entrada[0].length; j++) {
				int entrada=mat_entrada[i][j];
				int salida=mat_salida[i][j];
				insertarEnMatriz(entrada, salida, matriz);
			}
		}
		
		///DIVIDO SOBRE LA CANTIDAD TOTAL
		for (i=1; i<matriz[0].length; i++) {
			int total = 0;
			for (j=1; j<matriz.length; j++) {
				total+=matriz[j][i];
			}//consigue la sumatoria de la columna
			for (j=1; j<matriz.length; j++) {
				matriz[j][i]=(float)matriz[j][i]/total;
			}//divide cada termino por el total de la columna
		}
		
		return matriz;
	}
	
	///METODOS INCISO A
	private void insertarEnMatriz(int entrada, int salida, float[][] matriz) {
		//NO CONTROLO LIMITES, PERO NO PODRIA PASAR QUE NO ENCUENTRE LA ENTRADA O LA SALIDA
		int i=1;
		while (matriz[0][i]!=entrada) {//encuentra la columna
			i++;
		}
		int j=1;
		while (matriz[j][0]!=salida) {//encunetra la fila
			j++;
		}
		matriz[j][i]++;
	}
	
	private void imprimirMatriz(float[][] matriz, String path) {
		///ABRO ARCH
		PrintWriter out = null;
		try {out = new PrintWriter(path);}
		catch (FileNotFoundException e) {e.printStackTrace();}
		out.println("Matriz de transicion, las entradas y salidas son:");
		///IMPRIME LOS COLORES
		for (int i=1; i<matriz.length; i++) {
			out.print(new BigDecimal(String.valueOf(matriz[i][0])).setScale(0, BigDecimal.ROUND_FLOOR) + ", ");
		}
		out.println("");
		out.println("Matriz (con redondeo a 5 decimales):");
		///IMPRIME LA MATRIZ (sin los valores de entrada y salida)
		for (int i=1; i<matriz.length; i++) {//en 1 para no imprimir las salidas
			for (int j=1; j<matriz[0].length; j++)//en 1 para no imprimir las entradas
				out.print("|" + new BigDecimal(String.valueOf(matriz[i][j])).setScale(5, BigDecimal.ROUND_FLOOR));//imprimos solo 5 decimales para que sea mas leible
			out.println("|");
		}
		///CIERRO ARCH
		out.close();
	}
	
	/*FLETARprivate void imprimirMatriz(float[][] matriz, String path) {
		PrintWriter out = null;
		try {out = new PrintWriter(path);}
		catch (FileNotFoundException e) {e.printStackTrace();}
		for (int i=0; i<matriz.length; i++) {
			for (int j=0; j<matriz[0].length; j++) {
				out.print(matriz[i][j] + " ");
			}
			out.println("");
		}
		out.close();
	}*/
	///FIN METODOS INCISO A
	
	///METODOS INCISO B
	private float [][] getTransicionAcumColumna(float[][] matriz){
		float [][] resolucion= new float[matriz.length-1][matriz[0].length-1];//se achica en uno la fila y la columna porque ya no tiene los valores de entrada y salida
		for (int i=0; i<resolucion[0].length; i++) {
			float total=0;
			for (int j=0; j<resolucion.length; j++) {
				total+=matriz[j+1][i+1];//los +1 para no tomar la primer fila y primer columna
				resolucion[j][i]=total;
			}
		}
		return resolucion;
	}
	
	private float [] getDistrAcum(TreeMap<Integer, Integer> distr){//transforma de un treemap a un arreglo de probabilidades
		float[] prob=new float[distr.size()];
		int total=0;
		int k=0;
		for(int frec: distr.keySet()) {
			total+=distr.get(frec);
			prob[k]=distr.get(frec);
			k++;
		}
		float acum=0;
		for(int i=0; i<prob.length; i++) {
			acum+=prob[i]/(float)total;
			prob[i]=acum;
		}
		return prob;
	}
	
	private float getRuido(float [] distrAcumX , float [][] transicionAcumColumna, PrintWriter out) {
		//calcula por muestreo las prob de x y de y dado x, para calcular el ruido
		int cant_entrada= transicionAcumColumna[0].length;
		int cant_salida= transicionAcumColumna.length;
		int muestras = 1;
		int [] exitosEntrada = new int [cant_entrada]; //La dimension de exitos definida por la cantidad de simbolos de entrada
		int [][] exitosSalida = new int [cant_salida] [cant_entrada];
		float ruidoAnt = 0;
		float ruidoAct = 0;
		float [] probX = new float [cant_entrada];
		float [][] probYX = new float [cant_salida][cant_entrada];
		float [] ruidoxSimb = new float [cant_entrada];
		
		do {
			int entrada = this.generarEntrada(distrAcumX);  //Genera simbolo de entrada
			exitosEntrada[entrada]++; //Actualiza exito en la entrada
			ruidoxSimb[entrada] = 0; //Reseteo el ruido en el entrada ya que el valor se pisará
			int salida  = this.generarSalida(transicionAcumColumna, entrada); // Genera simbolo de salida dado el del entrada
			exitosSalida[salida][entrada]++; //Actualiza entrada en la salida dada la entrada
			
			for(int i = 0; i < probX.length;i++) //Actualiza la probabilidad de la entrada
				probX[i] = (float) exitosEntrada[i] / (float) muestras;
			for(int i = 0; i < probYX.length;i++) //Actualiza la probabilidad de salida dada la entrada
				probYX[i][entrada] = (float) exitosSalida[i][entrada]  / (float) exitosEntrada[entrada];
				
			for(int i = 0; i < probYX.length;i++) //Actualizo el ruido por simbolo solo en la columna de la entrada que salio
				if(probYX[i][entrada] > 0)
					ruidoxSimb[entrada]+= -( probYX[i][entrada] * (Math.log10(probYX[i][entrada]) / Math.log10(2)));
			
			ruidoAnt = ruidoAct;
			ruidoAct = 0; //Se resetea ya que el valor será pisado
			for(int i = 0; i < probX.length; i++) //Se calcula el nuevo ruido
				ruidoAct+= probX[i]*ruidoxSimb[i];
			if((muestras % MILESTONE == 0) || (muestras == 1)) {
				out.println("Iteracion: "+muestras+", Error de:"+Math.abs(ruidoAnt-ruidoAct));
			}
			muestras++;
		}
		while((!converge(ruidoAnt , ruidoAct)) || (muestras <= MIN_MUESTRAS ));
		out.println("Ultima iteracion: "+muestras+", Error de:"+Math.abs(ruidoAnt-ruidoAct));
		out.println("");
		return ruidoAct;
	}
	
	private boolean converge(float x , float y) {
		return (Math.abs(x-y) < ERROR);
	}
	
	private int generarEntrada (float [] distrAcumX) {
		float r = (float) Math.random();
		for (int i = 0; i < distrAcumX.length ; i++)
			if(r < distrAcumX[i])
				return i;
		return -1;
	}
	
	private int generarSalida (float [][] transicionAcumColumna , int entrada) {
		float r = (float) Math.random();
		for (int i = 0; i < transicionAcumColumna.length; i++)
			if(r < transicionAcumColumna[i][entrada])
				return i;
		return -1;
	}
	///FIN METODOS INCISO B
}