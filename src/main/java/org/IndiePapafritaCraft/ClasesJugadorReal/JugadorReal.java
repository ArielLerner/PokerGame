package org.IndiePapafritaCraft.ClasesJugadorReal;

import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.Jugador;
import org.IndiePapafritaCraft.Mano;

public class JugadorReal extends Jugador {
    public JugadorReal(Mano mano, int balanceInicial, JuegoPoker juegoDePoker, String nombre) {
        super(juegoDePoker, mano, balanceInicial, nombre);
    }

    public void verApuesta() {
    }

    public boolean[] cambioCartas() {
        boolean[] x = new boolean[]{false, false, false, false, false};
        return x;
    }

    public void pagarLuzAviso() {
        int nroDeJugEnJuego = 0;
        Jugador[] jugadores = juego.getJugadores();
        for (int x = 0; x < jugadores.length; x++) {
            if (jugadores[x].getEstarEnElJuego() == true)
                nroDeJugEnJuego++;
        }
        System.out.println("El numero de jugadores en esta mano es de " + nroDeJugEnJuego);
    }

    public void repartirCartasAviso() {
        Mano mano = this.getManoDeJugador();
        System.out.println("Mano: ");
        System.out.println(mano);
    }

    public void jugadorVeApuestaAviso(Jugador x) {
        int queHizo = x.queHizoEnLaApuesta(juego);
        System.out.print("El jugador " + x.getNombre() + " ");
        if (queHizo == 0)  System.out.println("no ha aceptado la apuesta");
        if (queHizo==1) System.out.println("ha aceptado la apuesta de: " + x.getDineroApostado());
        if (queHizo==2) System.out.println("ha subido la apuesta a: " + x.getDineroApostado());
    }
    //Metodos no implementados
    public void cambioCartasAviso() {
    }
    public void finalDeJuegoAviso() {
    }
    public void entreManosAviso() {
    }
}
