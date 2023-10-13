package org.IndiePapafritaCraft.clasesDePruebas;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.JugadorMaquina;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ClasesRestantes.Mazo;

public class PruebasAuxiliares {
    public static void ProbarErrores(JugadorMaquina j, int repeticiones, double toleracion){
        Mazo mazo = new Mazo();
        for (int x  = 0; x<repeticiones;x++){
            j.elegirEstrategia(toleracion);
            mazo.mezclar();
            Mano manoAcambiar = new Mano(mazo.division(0,5));
            j.cambiarMano(manoAcambiar);
        }
    }
}
