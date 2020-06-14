package Trabajo_Especial;

import java.util.TreeMap;

public class Ejercicio4 {
	
	//public Ejercicio4() {}

	private final static double epsilon = 0.0001;
	private final static int min_muestras = 500;
	
	public static void main(String[] args) {
		
		int[][] me = {{5,3,4},{3,2,1},{2,2,2}};
		int[][] ms = {{5,2,3},{1,2,1},{2,3,1}};
		imprimirMatriz(me); imprimirMatriz(ms);
		TreeMap<Integer, Integer> de = getDistribucion(me);
		TreeMap<Integer, Integer> ds = getDistribucion(ms);
		float[][] matriz= generarMatrizTransicional(me,de,ms,ds);
		///INCISO B
		System.out.println("ruido de la matriz: "+calcularRuido(matriz, de));
		///FIN INCISO B
		
	}
	
	public static float[][] generarMatrizTransicional(int[][] mat_entrada, TreeMap<Integer, Integer> dist_entrada, int[][] mat_salida, TreeMap<Integer, Integer> dist_salida) {
		
		//INICIALIZO LA MATRIZ CON TODOS 0
		float[][] matriz = new float[dist_salida.size()+1][dist_entrada.size()+1];
		for (int i=0; i<dist_salida.size()+1; i++) {
			for (int j=0; j<dist_entrada.size()+1; j++) {
				matriz[i][j]=0;
			}
		}
		
		//PONGO TODOS LOS COLORES DE LAS IMAGENES EN LAS COLUMNAS Y FILAS DE LA MATRIZ
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
		
		//LLENO LA MATRIZ
		for (i=0; i<mat_entrada.length; i++) {
			for (j=0; j<mat_entrada[0].length; j++) {
				int entrada=mat_entrada[i][j];
				int salida=mat_salida[i][j];
				insertarEnMatriz(entrada, salida, matriz);
			}
		}
		
		//DIVIDO SOBRE LA CANTIDAD TOTAL
		for (i=1; i<matriz[0].length; i++) {
			int total = 0;
			for (j=1; j<matriz.length; j++) {
				total+=matriz[j][i];
			}
			for (j=1; j<matriz.length; j++) {
				matriz[j][i]=(float)matriz[j][i]/total;
			}
		}
		
		System.out.println("CUARTA IMPRESION (MATRIZ CON PROBABILIDADES DE TRANSICION)");
		imprimirMatriz(matriz);
		return matriz;
	}
	
	///METODOS INCISO A
	public static void insertarEnMatriz(int entrada, int salida, float[][] matriz) {
		//NO CONTROLO LIMITES, PERO NO PODRIA PASAR QUE NO ENCUENTRE LA ENTRADA O LA SALIDA
		int i=1;
		while (matriz[0][i]!=entrada) {
			i++;
		}
		int j=1;
		while (matriz[j][0]!=salida) {
			j++;
		}
		matriz[j][i]=matriz[j][i]+1;
	}
	
	public static void imprimirMatriz(int[][] matriz) {
		for (int i=0; i<matriz.length; i++) {
			for (int j=0; j<matriz[0].length; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public static void imprimirMatriz(float[][] matriz) {
		for (int i=0; i<matriz.length; i++) {
			for (int j=0; j<matriz[0].length; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public static TreeMap<Integer, Integer> getDistribucion(int[][] matriz){
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
	///FIN METODOS INCISO A
	
	///METODOS INCISO B
	public static double calcularRuido(float[][] mat_transicion, TreeMap<Integer, Integer> dist_entrada) {
		TreeMap<Integer, Float> prob=calcularProb(dist_entrada);
		TreeMap<Integer, Float> p_acum=calcularAcumulada(prob);
		int muestras=0;
		double ruido_ant=-1;
		double ruido_act=0;
		System.out.println("INICIO");///PRUEBA
		while (!converge(ruido_ant, ruido_act) || muestras<min_muestras) {
			int entrada=generarEntrada(prob, p_acum);
		    float salida=generarSalida(entrada, mat_transicion);
		    System.out.println("SALIDA: "+ salida);///PRUEBA
			muestras++;
			ruido_ant=ruido_act;
			ruido_act+=(float)(prob.get(entrada)*(-salida*Math.log10(salida)*3.322))/(float)muestras;///DA CUALQUIER COSA 
		}
		return ruido_act;
	}
	
	public static boolean converge(double ant, double act) {
		return (Math.abs(ant-act)<epsilon);
	}
	
	public static TreeMap<Integer, Float> calcularProb(TreeMap<Integer, Integer> dist_entrada) {
		TreeMap<Integer, Float> p= new TreeMap<Integer, Float>();
		int total=0;
		for(int i: dist_entrada.keySet()) {
			total+=dist_entrada.get(i);
		}
		for(int i: dist_entrada.keySet()) {
			p.put(i, (float)dist_entrada.get(i)/(float)total);
		}
		return p;
	}
	
	public static TreeMap<Integer, Float> calcularAcumulada(TreeMap<Integer, Float> prob) {
		TreeMap<Integer, Float> a= new TreeMap<Integer, Float>();
		float total=0;
		for (int i: prob.keySet()) {
			total+=prob.get(i);
			a.put(i, total);
		}
		return a;
	}
	
	public static int generarEntrada(TreeMap<Integer, Float> prob, TreeMap<Integer, Float>acumulada) {
		float c=(float) Math.random();
		for (int i: acumulada.keySet()) {
			if (c<=acumulada.get(i)) {
				return i;
			}
		}
		System.out.println("c de -1 es "+c);
		return -1;
	}
	
	public static float generarSalida(int entrada, float[][] matriz) {
		float c=(float) Math.random();
		float acum=0;
		for (int i=1; i<matriz.length; i++) {
			acum+=matriz[i][entrada];
			if (c<=acum)
				return matriz[i][entrada];
		}
		return -1;
	}	
	///FIN METODOS INCISO B
	
	
}