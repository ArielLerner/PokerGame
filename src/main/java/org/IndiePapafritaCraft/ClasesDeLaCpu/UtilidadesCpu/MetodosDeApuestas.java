package org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu;

import org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu.EstadisticasDelJuegoPoker.DatosParaApuesta;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.Jugador;

import java.io.*;

public class MetodosDeApuestas {
    public static double logaritmo(double base, double x) {
        return Math.log(x) / Math.log(base);
    }
    //Métodos de estimaciónDeRondas
    public static double[] regresionCuadratica(double[][] puntos){
        double[][] coeficientes = MetodosDeApuestas.crearMatrizRegCuadratica(puntos);
        double[] valoresDeVariables = MetodosDeApuestas.metodoDeCramer3x3(coeficientes);
        return valoresDeVariables;
    }

    /**
     * @param puntos son los puntos de un grafico con x = %ganarRival  y = CantApostada
     *               en la primera dimension el array tiene la cant de puntos y en la segunda es 0 si es x y 1 si es y
     * @return devuelve una matriz donde el vector 0 multiplica A, v1 multiplica B, v2 multiplica  C y v3 son los resultados de la ecuacion
     */
    //Las ecuaciones son:
    //a*Ex2 + b Ex + c*4 = Ey
    //a*Ex3 + b*Ex2 +c*Ex =Exy
    //a*Ex4 * b*Ex3 +c*Ex2 =Ex2y
    private static double[][] crearMatrizRegCuadratica(double[][] puntos) {
        double n = puntos.length;
        double Ex = MetodosDeApuestas.sumaDePotenciasPuntos(puntos, 1, 0);
        double Ey = MetodosDeApuestas.sumaDePotenciasPuntos(puntos, 0, 1);
        //E significa la suma de los puntos y x2 significa x al cuadrado , x3 significa x al cubo...
        double Ex2 = MetodosDeApuestas.sumaDePotenciasPuntos(puntos, 2, 0);
        double Ex3 = MetodosDeApuestas.sumaDePotenciasPuntos(puntos, 3, 0);
        double Ex4 = MetodosDeApuestas.sumaDePotenciasPuntos(puntos, 4, 0);
        double Exy = MetodosDeApuestas.sumaDePotenciasPuntos(puntos, 1, 1);
        double Ex2y = MetodosDeApuestas.sumaDePotenciasPuntos(puntos, 2, 1);
        //Creacion de matriz: ordenada como dice en la API del metodo
        double[][] coeficientes = {
                {Ex2, Ex3, Ex4},
                {Ex, Ex2, Ex3},
                {n, Ex, Ex2},
                {Ey, Exy, Ex2y}
        };
        return coeficientes;
    }

    /**
     * @param puntos    son los puntos(x,y), el primer array contiene los puntos y el segundo contiene en 0: la coordenada x y en 1: la coordenada y
     * @param potenciaX es el numero al que se eleva x
     * @param potenciaY es el nro al que se eleva y
     * @return devuelve la suma de las multiplicaciones de las potencias de x,y
     */
    private static double sumaDePotenciasPuntos(double[][] puntos, int potenciaX, int potenciaY) {
        int cantDePuntos = puntos.length;
        double sumaTotal = 0;
        for (int a = 0; a < cantDePuntos; a++) {
            //voy a calcular x,y potenciados
            double xFinal = Math.pow(puntos[a][0], potenciaX);
            double yFinal = Math.pow(puntos[a][1], potenciaY);
            double nFinal = xFinal * yFinal;
            sumaTotal = sumaTotal + nFinal;
        }
        return sumaTotal;
    }

    /**
     *
     * @param coeficientes es una matriz, que tiene en el vector 1 los nros que multiplican a, en el 2 los que multiplican b,
     *                    en el 3 los que multiplican c y en el 4to vector los resultados
     * @return devuelve los valores de las variables en un array[] = {a,b,c}
     * SI devuelve 0 es que no tiene solucion o tiene soluciones infinitas
     */
    public static double[] metodoDeCramer3x3(double[][] coeficientes){
        double determinante = valorDeterminanteMetodoCramer(coeficientes[0],coeficientes[1],coeficientes[2]);
        double determinanteA =valorDeterminanteMetodoCramer(coeficientes[3],coeficientes[1],coeficientes[2]);
        double determinanteB =valorDeterminanteMetodoCramer(coeficientes[0],coeficientes[3],coeficientes[2]);
        double determinanteC =valorDeterminanteMetodoCramer(coeficientes[0],coeficientes[1],coeficientes[3]);
        if (determinante==0)return new double[]{0,0,0};
        else {
            double a = determinanteA/determinante;
            double b = determinanteB/determinante;
            double c = determinanteC/determinante;
            return new double[]{a,b,c};
        }
    }
    /**
     *
     * @param vA es un vector
     * @param vB es un vector
     * @param vC es un vector
     * @return calcula el determinante de estos vectores, suponiendo que el determinante es asi  |vA,vB,vC|
     */
    private static double valorDeterminanteMetodoCramer(double[]vA,double[]vB,double[]vC){
        double determinante = (vA[0]*vB[1]*vC[2] + vA[1]*vB[2]*vC[0] + vA[2]*vB[0]*vC[1])
                                                -
                                (vA[2]*vB[1]*vC[0] + vA[0]*vB[2]*vC[1] + vA[1]*vB[0]*vC[2]);
        return determinante;
    }

    /**
     * @return Este método promedia el nro de rondas/manos jugadas en los ultimos 10 juegos y devuelve un entero que es el promedio con el decimal cortado
     */
    public static int estimacionDeRondas() {
        String filepath = "C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\ClasesDeLaCpu\\UtilidadesCpu\\EstadisticasDelJuegoPoker\\CantDeManosJugadasEn10UltimasPartidas.txt";
        String[] ultimasManos = MetodosDeApuestas.leerManosJugadasEnUltimasPartidas(filepath);
        double promedio = 0;
        for (String x : ultimasManos) {
            promedio = promedio + Double.parseDouble(x);
        }
        promedio = promedio / ultimasManos.length;
        int estimacionDeRondas = (int) promedio;
        return estimacionDeRondas;
    }

    public static int apuestaMinima(Jugador x) {
        int dineroRestante = x.getFinanzas();
        int rondasRestantes = DatosParaApuesta.singletonDatos().rondasRestantes;
        int apuestaMinima = dineroRestante / rondasRestantes;
        //En caso de que la apuesta minima sea 0 hay que hacer a la maquina jugar entonces hacemos que sea 1 hasta que se quede sin plata
        if (apuestaMinima == 0 && dineroRestante > 0) apuestaMinima = 1;
        return apuestaMinima;
    }

    /**
     * Este método permite agregar las manosJugadas en la ultima partida al archivo ,
     * Este método debería aplicarse cuando termina un juegoDePoker con la cuenta de manosJugadas
     *
     * @param manosJugadas es la cantDeManos que se jugó en la última partida
     */
    public static void writerDeManosJugadas(int manosJugadas) {
        try {
            String filepath = "C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\ClasesDeLaCpu\\UtilidadesCpu\\EstadisticasDelJuegoPoker\\CantDeManosJugadasEn10UltimasPartidas.txt";
            String[] ultimasManos = MetodosDeApuestas.leerManosJugadasEnUltimasPartidas(filepath);
            //Hay que abrir el bufferedWriter dsps de leer ya que cuando se abre, se resetea el file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            for (int x = 0; x < 10; x++) {
                if (x == 0) {
                    //Se agrega una coma para que pase el numero int a string, sino da un caracter especial que la compu no reconoce
                    writer.write("" + manosJugadas);
                } else {
                    writer.write(ultimasManos[x - 1]);
                }
                writer.newLine();
            }
            writer.close();
        } catch (Exception x) {
            System.out.println("Ha habido un error en MetodosDeApuestas : anotarCantManosJugadas " + x);
        }
    }

    /**
     * @return Devuelve un string array con los numeros en el filepath
     * @throws NumberFormatException El  método puede dar error si el el filepath al que hace referencia tiene una linea null entre el renglón 1 y 10
     */
    private static String[] leerManosJugadasEnUltimasPartidas(String filepath) {
        String[] manosJugadas = new String[10];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            for (int pos = 0; pos < 10; pos++) {
                manosJugadas[pos] = reader.readLine();
            }
            return manosJugadas;
        } catch (Exception x) {
            System.out.println("Ha habido un error en MetodosDeApuesta :  leerManosJugadasEnUltimasPartidas " + x);
        }
        return manosJugadas;
    }

    //Metodos de Camax (Coeficiente de apuesta máxima )

    /**
     * @return devuelve el coeficiente de apuesta máxima
     */
    public static double camax(JuegoPoker juego, double probDeGanar, int estimacionDeRondas) {
        double b = MetodosDeApuestas.beneficio(probDeGanar, juego.cantDeJugadores());
        double f = func1(b, juego.cantDeJugadores());
        f = func2(f, estimacionDeRondas);
        f = func3Random(f);
        return f;
    }

    /**
     * @return devuelve un nro entre 0 y el nroDeJug que dice cuantas veces es el beneficio estimado de la mano
     * si b>1 conviene arriesgarse, sino no.
     */
    private static double beneficio(double probDeGanar, int cantDeJug) {
        return probDeGanar * cantDeJug;
    }

    /**
     * @return devuelve un nro entre 0 y 1 , si es mayor a 0.5 conviene arriesgarse, sino no.
     */
    private static double func1(double beneficio, int cantDeJug) {
        if (beneficio < 1) {
            //beneficio sobre 2 da un nro entre 0 y 0.5 y el (*2/3) es un desincentivo, ya que no conviene
            return (beneficio / 2) * 2 / 3;

        } else {
            //caso  (beneficio > 1) uso una funcion logarítmica
            // resto 1 al beneficio para obtener un nro en rango: 0-(cantDeJug-1)
            beneficio--;
            return MetodosDeApuestas.logaritmo(12.901, (9.309 * beneficio / (cantDeJug - 1) + 3.591));
        }
    }

    private static double func2(double func1, int estimacionDeRondas) {
        // cuanto mas grande es el coeficiente de la funcion 1, mas va a ignorar a la estimacionDeRondas
        return func1 / ((estimacionDeRondas * (1 - func1)) + 1);
    }

    /**
     * @return le suma al coeficiente un nroRandom entre 0.1 y -0.1
     */
    private static double func3Random(double func2) {
        double nroRandom = (Math.random() - 0.5) / 5;
        double resultado = func2 + nroRandom;
        if (resultado > 1) resultado = 1;
        if (resultado < 0) resultado = 0;
        return resultado;
    }
}
