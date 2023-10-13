package org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.EstadisticasDelJuegoPoker.Singleton;
import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.EstadisticasDelJuegoPoker.PartesDelJuego;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.ClasesDeJugador.Jugador;
import org.IndiePapafritaCraft.ValoresJuntados.Estrategia;

import java.io.*;
import java.util.ArrayList;

public class MetodosDeApuestas {
    public static double logaritmo(double base, double x) {
        return Math.log(x) / Math.log(base);
    }
    //Métodos de estimaciónDeRondas
    public static double[] regresionCuadratica(ArrayList<double[]> puntos){
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
    private static double[][] crearMatrizRegCuadratica(ArrayList<double[]> puntos) {
        double n = puntos.size();
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
     * @param x es el jugador que REALIZA el metodo
     */
    public static int verApuesta(Jugador x, Jugador ultimoEnSubirApuesta, int apuestaMax){
        PartesDelJuego parteDelJuego = x.getJuego().parteDelJuego();
        int cantApostadaRival = ultimoEnSubirApuesta.getDineroApostado();
        //caso 1 cantRival > ApuestaMax
        if(cantApostadaRival > apuestaMax){

        }
        //casp 2 cantRival < apuesta Max
        if (cantApostadaRival<apuestaMax){

        }
        //caso 3 cantRival == apuestaMax
        if (cantApostadaRival == apuestaMax){
            return apuestaMax;
        }
return 0;
    }
    private static void verApuestaCaso1(){}

    /**
     * @param puntos    son los puntos(x,y), el primer array contiene los puntos y el segundo contiene en 0: la coordenada x y en 1: la coordenada y
     * @param potenciaX es el numero al que se eleva x
     * @param potenciaY es el nro al que se eleva y
     * @return devuelve la suma de las multiplicaciones de las potencias de x,y
     */
    private static double sumaDePotenciasPuntos(ArrayList<double[]> puntos, int potenciaX, int potenciaY) {
        int cantDePuntos = puntos.size();
        double sumaTotal = 0;
        for (int a = 0; a < cantDePuntos; a++) {
            //voy a calcular x,y potenciados
            double xFinal = Math.pow(puntos.get(a)[0], potenciaX);
            double yFinal = Math.pow(puntos.get(a)[1], potenciaY);
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
    public static int apuestaMax(PartesDelJuego parteDelJuego,Jugador x, Estrategia estrategia){
        int apuestaMax=0;
        if (parteDelJuego == PartesDelJuego.PRIMERAAPUESTA)
            apuestaMax = MetodosDeApuestas.apuestaMax1eraApuesta(x, estrategia.getProbDeGanarObjetiva(), Singleton.get(x).estimacionDeRondas);
        if (parteDelJuego == PartesDelJuego.SEGUNDAAPUESTA)
            apuestaMax = MetodosDeApuestas.apuestaMax2daApuesta(x, estrategia.getProbDeGanarObjetiva(), Singleton.get(x).estimacionDeRondas);
        return apuestaMax;
    }

    /**
     * @return devuelve lo mismo que en apuestaMax2daRonda solo que se divide por 1/2 si apuestaMax>2*apuestaMin
     */
    private static int apuestaMax1eraApuesta(Jugador x, double probaDeGanar, int estimacionDeRondas){
        int apuestaMax2daRonda = apuestaMax2daApuesta(x,probaDeGanar,estimacionDeRondas);
        int apuestaMin  = apuestaMinima(x);
        if ( apuestaMax2daRonda > 2 * apuestaMin) apuestaMax2daRonda = apuestaMax2daRonda / 2;
        return  apuestaMax2daRonda;
    }
    private static int apuestaMax2daApuesta(Jugador x, double probDeGanar, int estimacionDeRondas){
        double camax = camax(x.getJuego(),probDeGanar,estimacionDeRondas);
        int apuestaMin = apuestaMinima(x);
         int dineroTotal = x.getFinanzas();
         int apuestaMax =  (int)(camax*dineroTotal);
         if (apuestaMax < apuestaMin && apuestaMax < 20/100* x.getJuego().getBalanceInicial()) apuestaMax = apuestaMin;
         return  apuestaMax;
    }

    public static int apuestaMinima(Jugador x) {
        int dineroRestante = x.getFinanzas();
        int rondasRestantes = Singleton.get(x).rondasRestantes;
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
            String filepath = "C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\ClasesDeJugador\\ClasesJugadorMaquina\\UtilidadesCpu\\EstadisticasDelJuegoPoker\\Files\\DatosApuesta\\CantDeManosJugadasEn10UltimasPartidas.txt";
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
    public static ArrayList<double[]> readerDePuntos(){
        String filepath = "C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\ClasesDeJugador\\ClasesJugadorMaquina\\UtilidadesCpu\\EstadisticasDelJuegoPoker\\Files\\DatosApuesta\\Puntos x,y Qrival y probDeGanarTotal";
        String[] lectura = MetodosDeApuestas.leerRenglones(filepath);
        //En este arraylist voy a tener cada punto como un double[]
        ArrayList<double[]> puntos  =  new ArrayList<double[]>();
        for (int a=0;a<(lectura.length/2);a++){
            if (lectura[a*2] == null) continue;
            else{
                double x = Double.parseDouble(lectura[a*2]);
                double y =  Double.parseDouble(lectura[a*2+1]);
                puntos.add(new double[]{x,y});
            }
        }
        return puntos;
    }

    /**
     * @param cantApostada es la cant apostada por el rival
     * @param probDeGanar es la prob de ganar rival (va de 0 a 100)
     * Este metodo va a escrbir la cant apostada y la prob de ganar en renglones consecutivos en el file
     *
     */
    public static void writerDePuntos(int cantApostada, double probDeGanar) {
        try {
            String filepath = "C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\ClasesDeJugador\\ClasesJugadorMaquina\\UtilidadesCpu\\EstadisticasDelJuegoPoker\\Files\\DatosApuesta\\Puntos x,y Qrival y probDeGanarTotal";
            String[] lectura = MetodosDeApuestas.leerRenglones(filepath);
            //encontrar el primer renglon vacio
            int primerRenglonVacio = 0;
            for (int x = 0; x < lectura.length; x++) {
                if (lectura[x].length() == 0) {primerRenglonVacio = x;break;}
            }
            //Nros de renglones vacio y de x,y
            int renglonX= primerRenglonVacio;
            int renglonY = primerRenglonVacio + 1;
            int renglonVacio1 = primerRenglonVacio + 2;
            int renglonVacio2 = primerRenglonVacio + 3;
            //Si los renglones vacios se pasan de 29 los paso a 0 y 1
            if (renglonVacio2>29){
                renglonVacio1=0;
                renglonVacio2=1;
            }
            //Reescribir el archivo con el nuevo punto y el nuevo espacio
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            for (int x = 0; x < lectura.length; x++) {
                boolean r = false;
                if (x == renglonX) {writer.write(cantApostada + "");writer.newLine();r = true;}
                if (x == renglonY) {writer.write(probDeGanar + "");writer.newLine();r = true;}
                if (x == renglonVacio1 || x ==  renglonVacio2) {writer.newLine();r = true;}
                if (r != true){
                    if (lectura[x]==null || lectura[x].isBlank());
                    else  writer.write(lectura[x]);
                    writer.newLine();}
            }
            writer.close();
        } catch (Exception x) {
            System.out.println("Ha habido un error en MetodosDeApuestas : writerDePuntos " + x);
        }
    }
    private static String[] leerRenglones(String filepath){
        String[] lectura = new String[60];
        try {
            BufferedReader lector = new BufferedReader(new FileReader(filepath));
            for (int a = 0; a < 60; a++) {
                lectura[a] = lector.readLine();
            }
        }catch (Exception x ){System.out.println("Ha habido un error en MetodosDeApuesta.LeerRenglones");}
            return lectura; }
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
