package org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu;

import org.IndiePapafritaCraft.Carta;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.Mano;
import org.IndiePapafritaCraft.ValoresJuntados.Estrategia;

import java.util.ArrayList;

public class UtilidadesGenerales {
    public UtilidadesGenerales() {

    }

    /**
     * @return devuelve el nro mas grande
     */
    public static int nroMasGrandeEnUnArray(int[] x) {
        int nroMasGrande = x[0];
        int longitudX = x.length;
        for (int pos = 1; pos < longitudX; pos++) {
            if (x[pos] > nroMasGrande)
                nroMasGrande = x[pos];
        }
        return nroMasGrande;
    }

    /**
     * @param maximo si la diferencia es mayor a este, devuelve el maximo
     * @return la diferencia entre el numero mayor del mazo y el numero de la carta
     */
    public static int distCartaMayorMazoConMax(Carta a, int maximo, JuegoPoker juego) {
        int x = juego.numeroMayorDelMazo() - a.getNumero();
        if (x > maximo)
            return maximo;
        else
            return x;
    }

    /**
     * @param maximo si la diferencia es mayor a este, devuelve el maximo
     * @return la diferencia entre el numero de la carta y el numero menor del mazo
     */
    public static int distCartaMenorMazoConMax(Carta a, int maximo, JuegoPoker juego) {
        int x = a.getNumero() - juego.numeroMenorDelMazo();
        if (x > maximo)
            return maximo;
        else
            return x;
    }

    /**
     * @return devuelve en true los numeros que se repiten en la mano
     */
    public static boolean[] IndexDeNumerosQueSeRepiten(Mano manoDeJugador) {
        boolean[] numerosQueSeRepitenIndex = new boolean[5];
        for (int numeroInicial = 0; numeroInicial < 4; numeroInicial++) {
            for (int numeroActual = numeroInicial + 1; numeroActual < 5; numeroActual++) {
                if (manoDeJugador.getCard(numeroInicial).getNumero() == manoDeJugador.getCard(numeroActual).getNumero()) {
                    numerosQueSeRepitenIndex[numeroInicial] = true;
                    numerosQueSeRepitenIndex[numeroActual] = true;
                }
            }
        }
        return numerosQueSeRepitenIndex;
    }

    public static int nroDeParesPiernasPokerQueHayEnLaMano(Mano manoDeJugador, boolean[] IndexDenumerosQueSeRepiten) {
        ArrayList<Carta> cartasQueSeRepiten = new ArrayList<Carta>();
        for (int x = 0; x < 5; x++) {
            if (IndexDenumerosQueSeRepiten[x] == true) {
                cartasQueSeRepiten.add(manoDeJugador.getCard(x));
            }
        }
        if (cartasQueSeRepiten.size() == 0) {
            return 0;
        }
        int tamanoDeLaLista = cartasQueSeRepiten.size();
        for (int x = 0; x < tamanoDeLaLista; x++) {
            if (cartasQueSeRepiten.get(0).getNumero() != cartasQueSeRepiten.get(x).getNumero()) {
                return 2;
            }
        }
        return 1;
    }

    public static int nroDeCartasACambiar(boolean[] cambioCartas) {
        int contador = 0;
        for (int x = 0; x < 5; x++) {
            if (cambioCartas[x] == true)
                contador++;
        }
        return contador;
    }

    /**
     * @param array es un double array
     * @param  posOriginales debe tener el nro de la pos en cada index (pos 0 : 0 ; pos 1: 1 etc)
     * @return devuelve en la posicion 0 al nroMayor y en al ultima al menor y devuelve en la misma pos de array en posOriginales el index original de ese nro
     */
    public static void ordenarDoubleArrayManteniendoPosOriginales(double[] array, int[] posOriginales) {
        int arrayLenght = array.length;
        double cambio;
        int contador=1;
        while (contador!=0){
            contador=0;
            for (int pos = 0; pos < arrayLenght - 1; pos++) {
                if (array[pos] < array[pos + 1]) {
                    contador++;
                    //switch array
                    cambio = array[pos + 1];
                    array[pos + 1] = array[pos];
                    array[pos] = cambio;
                    //switch posOriginal
                    int cambio2 = posOriginales[pos+1];
                    posOriginales[pos+1] = posOriginales[pos];
                    posOriginales[pos] = cambio2;

                }
            }
        }
    }

    /**
     * @return devuelve el index de la estrategia con pro de ganar mas alta
     */
    public static int indexProbMasAlta(Estrategia[] estrategias){
        int cantDeEstrategias = estrategias.length;
        double probMasAlta=-1;
        int indexDeLaProb=-1;
        for (int x = 0; x< cantDeEstrategias;x++){
            if (estrategias[x].getProbDeGanarObjetiva()>probMasAlta){
                probMasAlta = estrategias[x].getProbDeGanarObjetiva();
                indexDeLaProb = x;
            }
        }
        return indexDeLaProb;
    }

    /**
     * @return devuelve un arrraylist de cartasNoRepetidas
     */
    public static ArrayList<Carta> cartasNoRepetidas(Mano A){
        boolean[] nrosRepetidos = UtilidadesGenerales.IndexDeNumerosQueSeRepiten(A);
        ArrayList<Carta> cartasNoRepetidas = new ArrayList<Carta>();
        for (int x=0;x<5;x++){
            if (nrosRepetidos[x]==false) cartasNoRepetidas.add(A.getCard(x));
        }
        return cartasNoRepetidas;
    }

    /**
     *
     * @return devuelve las estrategias con las probabilidades que estan en el margen de toleracion de la mejor que sean desiguales a 0
     */
    public static ArrayList<Estrategia> mejoresEstrategias(Estrategia[]estrategias, int indexProbMasAlta, double toleracion){
        double probMasAlta = estrategias[indexProbMasAlta].getProbDeGanarObjetiva();
        ArrayList<Estrategia> mejoresEstrategias = new ArrayList<Estrategia>();
        for (int x = 0 ; x < estrategias.length ; x++){
            if (estrategias[x].getProbDeGanarObjetiva() >= probMasAlta-toleracion && estrategias[x].getProbDeGanarObjetiva() != 0){
                mejoresEstrategias.add(estrategias[x]);
            }
        }
        return mejoresEstrategias;
    }
}
