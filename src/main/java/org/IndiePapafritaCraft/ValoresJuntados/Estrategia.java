package org.IndiePapafritaCraft.ValoresJuntados;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.JugadorMaquina;
import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.MetodosDeApuestas;
import org.IndiePapafritaCraft.ClasesJuegoPoker.PartesDelJuego;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;

public class Estrategia {
    private ValorYProbabilidad[] valoresYprobabilidades;
    private boolean[] cartasParaCambiar;
    private double probDeGanarObjetiva;
    private Mano mano;
    private int apuestaMax; // la apuesta max se settea cuando el jugador maquina termina de hacer la apuesta
    private PartesDelJuego momentoApuestaMax; // contiene si apuesta max fue diseñado para PrimeraApuesta o SegundaApuesta

    public Estrategia(ValorYProbabilidad[] relacionesEntreValorYProbabilidad, boolean[] cartasCambio, double probObjetivaDeGanar, Mano mano2) {
        valoresYprobabilidades = relacionesEntreValorYProbabilidad;
        cartasParaCambiar = cartasCambio;
        probDeGanarObjetiva = probObjetivaDeGanar;
        mano = mano2;
    }

    public String toString() {
        String x = "";
        x = x + "probDeGanar: " + probDeGanarObjetiva + "   ";
        for (int a = 0; a < valoresYprobabilidades.length; a++) {
            x = x + "prob: " + valoresYprobabilidades[a].getProbabilidad() + " " + "valor: " + valoresYprobabilidades[a].getValor().name() + "    ";
        }
        x = x +
                "c1: " + cartasParaCambiar[0] + " " +
                "c2: " + " " + cartasParaCambiar[1] + " " +
                "c3: " + " " + cartasParaCambiar[2] + " " +
                "c4: " + " " + cartasParaCambiar[3] + " " +
                "c5: " + " " + cartasParaCambiar[4];
        return x;
    }

    public double getProbDeGanarObjetiva() {
        return probDeGanarObjetiva;
    }

    public ValorYProbabilidad[] getValoresYprobabilidades() {
        return valoresYprobabilidades;
    }

    public boolean[] getCartasParaCambiar() {
        return cartasParaCambiar;
    }

    public Mano getMano() {
        return mano;
    }
    public  void setApuestaMaxEnMenos1(){
        apuestaMax = -1;
    }
    public int singletonApuestaMax(JugadorMaquina x) {
        PartesDelJuego proxApuesta = Estrategia.proximaApuesta(x.getJuego().getParteDelJuego());
        if (apuestaMax == -1 || proxApuesta.ordinal()!= momentoApuestaMax.ordinal() ) { // el ordinal para ver si son iguales
            apuestaMax = MetodosDeApuestas.apuestaMax(x);
            momentoApuestaMax = proxApuesta;
        }
            return apuestaMax;
    }


    public static PartesDelJuego proximaApuesta(PartesDelJuego p){
        if (p== PartesDelJuego.PAGODELUZ || p== PartesDelJuego.REPARTIRCARTAS || p== PartesDelJuego.PRIMERAAPUESTA){
            return PartesDelJuego.PRIMERAAPUESTA;
        }
        else return PartesDelJuego.SEGUNDAAPUESTA;
    }
}
