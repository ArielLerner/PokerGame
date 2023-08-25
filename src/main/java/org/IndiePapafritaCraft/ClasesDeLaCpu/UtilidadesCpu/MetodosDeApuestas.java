package org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu;

import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;

import java.io.*;

public class MetodosDeApuestas {
    public static double logaritmo(double base, double x){
        return Math.log(x)/Math.log(base);
    }
    //Métodos de estimaciónDeRondas
    public static void anotarCantManosJugadas(int manosJugadas){
        try {
            String filepath = "C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\ClasesDeLaCpu\\UtilidadesCpu\\EstadisticasDelJuegoPoker\\CantDeManosJugadasEn10UltimasPartidas.txt";
            File f = new File(filepath);
            System.out.println(f.exists());
            BufferedWriter writer = new BufferedWriter(new BufferedWriter(new FileWriter(f)));
            BufferedReader reader  = new BufferedReader(new FileReader(filepath));
            for (int x = 0 ; x<10;x++){
                writer.write("1");
                writer.close();
            }
        }catch (Exception x){System.out.println("Ha habido un error " + x);}
    }

    //Metodos de Camax (Coeficiente de apuesta máxima )
    /**
     * @return devuelve el coeficiente de apuesta máxima
     */
    public static double camax(JuegoPoker juego , double probDeGanar , int estimacionDeRondas){
        double b = MetodosDeApuestas.beneficio(probDeGanar,juego.cantDeJugadores());
        double f = func1(b, juego.cantDeJugadores());
        f = func2(f,estimacionDeRondas);
        f = func3Random(f);
        return f;
    }
    /**
     * @return devuelve un nro entre 0 y el nroDeJug que dice cuantas veces es el beneficio estimado de la mano
     * si b>1 conviene arriesgarse, sino no.
     * */
    public static double beneficio(double probDeGanar,int cantDeJug){
        return probDeGanar*cantDeJug;
    }

    /**
     * @return devuelve un nro entre 0 y 1 , si es mayor a 0.5 conviene arriesgarse, sino no.
     */
    public static double func1(double beneficio ,int cantDeJug  ){
        if (beneficio<1){
            //beneficio sobre 2 da un nro entre 0 y 0.5 y el (*2/3) es un desincentivo, ya que no conviene
            return (beneficio/2) * 2/3;

        }
        else {
            //caso  (beneficio > 1) uso una funcion logarítmica
            // resto 1 al beneficio para obtener un nro en rango: 0-(cantDeJug-1)
            beneficio--;
            return MetodosDeApuestas.logaritmo(12.901, (9.309 * beneficio /(cantDeJug-1)  + 3.591));
        }
    }
    public static double func2(double func1,int estimacionDeRondas){
        // cuanto mas grande es el coeficiente de la funcion 1, mas va a ignorar a la estimacionDeRondas
        return func1/((estimacionDeRondas*(1-func1))+1);
    }

    /**
     *@return le suma al coeficiente un nroRandom entre 0.1 y -0.1
     */
    public static double func3Random(double func2){
        double nroRandom =  (Math.random()-0.5)/5;
        double resultado = func2+nroRandom;
        if (resultado>1) resultado = 1;
        if (resultado<0) resultado = 0;
        return resultado;
    }
}
