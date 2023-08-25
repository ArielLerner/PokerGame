package org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu;

import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.Mano;
import org.IndiePapafritaCraft.ValoresJuntados.MapaFullProbs;
import org.IndiePapafritaCraft.ValoresJuntados.ValorDeMano;
import org.IndiePapafritaCraft.ValoresJuntados.ValorYProbabilidad;

public class UtilidadesParaColor {
    public UtilidadesParaColor(){
    }
    public static ValorYProbabilidad[] probabilidadColor(int nroDePaloConMasCartas, int[] conteoDePalos, JuegoPoker juego, MapaFullProbs probs) {
        int nroDeCartDelMismoPalo= conteoDePalos[nroDePaloConMasCartas];
        switch (nroDeCartDelMismoPalo){
            case 1: return new ValorYProbabilidad[]{new ValorYProbabilidad(0,ValorDeMano.NADA)};
            case 2: return new ValorYProbabilidad[]{new ValorYProbabilidad(0,ValorDeMano.NADA)};
            case 3:
                return probs.mapa.get(ValorDeMano.COLOR)[0].getProb();
            case 4:
                return probs.mapa.get(ValorDeMano.COLOR)[1].getProb();
            case 5:
                return new ValorYProbabilidad[]{new ValorYProbabilidad(1,ValorDeMano.COLOR)};
            default:
                System.out.println("ha habido un error en probabilidadColor de JugadorMaquina");
                return new ValorYProbabilidad[]{new ValorYProbabilidad(0,ValorDeMano.NADA)};
        }
    }
    /**
     * @param nroDePaloConMasCartas debe ser el numero de palo que mas cartas tenga
     * @return devuelve las cartas a cambiar en true y las que no en false
     */
    public static boolean[] cambiarCartasParaColor(int nroDePaloConMasCartas, Mano manoDeJugador){
        boolean[] cambioCartas = new boolean[]{true,true,true,true,true};
        for (int x=0;x<5;x++){
            if (manoDeJugador.getCard(x).getPalo()==nroDePaloConMasCartas)
                cambioCartas[x]= false;
        }
        return cambioCartas;
    }

    /**
     * @return devuelve un conteo donde dice cuantas cartas hay de cada palo
     */
    public static int[] conteoDePalos(Mano manoDeJugador){
        int [] conteoDePalos= new int[5];
        for (int x = 0; x < 5; x++) {
            conteoDePalos[manoDeJugador.getCard(x).getPalo()]++;
        }
        return conteoDePalos;
    }

    /**
     * @param conteo debe tener cuantas cartas hay de cada palo
     * @return devuelve el numeroDelPalo que mas cartas tiene
     */
    public static int NumeroDePaloConMasCartas(int[]conteo){
        int paloConMasCartas=0;
        int cantDeCartas=0;
        for (int x=0;x<4;x++){
            if (conteo[x]>cantDeCartas){
                cantDeCartas=conteo[x];
                paloConMasCartas=x;
            }
        }
        return paloConMasCartas;
    }
}
