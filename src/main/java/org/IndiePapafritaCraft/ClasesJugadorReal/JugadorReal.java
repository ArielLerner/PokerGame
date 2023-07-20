package org.IndiePapafritaCraft.ClasesJugadorReal;

import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.ClasesJuegoPoker.UtilidadesJuegoPoker;
import org.IndiePapafritaCraft.Jugador;
import org.IndiePapafritaCraft.Mano;

import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.println("Los jugadores que juegan son : ");
        for (int x = 0; x < jugadores.length; x++) {
            if (jugadores[x].getEstarEnElJuego() == true)
                System.out.print(jugadores[x].getNombre() + " , ");
        }
        System.out.println();
    }

    public void repartirCartasAviso() {
        Mano mano = this.getMano();
        System.out.println("Mano: ");
        System.out.println(mano);
    }

    public void jugadorVeApuestaAviso(Jugador x) {
        int queHizo = x.queHizoEnLaApuesta(juego);
        System.out.print("El jugador " + x.getNombre() + " ");
        if (queHizo == 0) System.out.println("no ha aceptado la apuesta");
        if (queHizo == 1) System.out.println("ha aceptado la apuesta de: " + x.getDineroApostado());
        if (queHizo == 2) System.out.println("ha subido la apuesta a: " + x.getDineroApostado());
    }

    //Metodos no implementados
    public void cambioCartasAviso() {
        Mano mano = this.getMano();
        System.out.println("Mano con cartas cambiadas: ");
        System.out.println(mano);
    }

    /**
     * @param pozo debe contener el dinero que habia em el pozo a la hora de final del juego
     */
    public void finalDelJuegoAviso(ArrayList<Mano> mostrarCartas, ArrayList<Jugador> jugadoresGanadores, int pozo) {
        //Println de las cartas que se muestran en mesa
        for (int x = 0; x < mostrarCartas.size(); x++) {
            Jugador jugador = UtilidadesJuegoPoker.buscarJugador(mostrarCartas.get(x), juego.getJugadores());
            System.out.println(" El jugador " + jugador.getNombre() + " tiene: " + jugador.getMano().valorDeLaMano(juego.numeroMayorDelMazo(), true));
            System.out.println(" Sus cartas son: " + jugador.getMano());
        }
        //Println de los ganadores
        //caso 1 ganador
        if (jugadoresGanadores.size() == 1) {
            Jugador ganador = jugadoresGanadores.get(0);
            System.out.println("EL GANADOR ES:");
            System.out.println("El jugador " + ganador.getNombre() + " quien se lleva el pozo de: " + pozo);
        }
        //caso mas de 1 ganador
        else {
            System.out.println("Los " + jugadoresGanadores.size() + "ganadores son:");
            for (int x = 0; x < jugadoresGanadores.size(); x++) {
                //calculo de cantidad de dinero que se lleva el jugador
                int baseParaCadaGanador = pozo / jugadoresGanadores.size();
                int dineroQueRecibe;
                if (x == 0)
                    dineroQueRecibe = baseParaCadaGanador + pozo - (baseParaCadaGanador * jugadoresGanadores.size());
                else dineroQueRecibe = baseParaCadaGanador;
                System.out.println("El jugador " + jugadoresGanadores.get(x).getNombre() + " se lleva esta cantidad: " + dineroQueRecibe);
            }
        }
    }

    public void entreManosAviso(boolean seguirConElJuego) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Si quieres CONTINUAR con la siguiente mano apreta C");
        System.out.println("Si quieres TERMINAR el juego apreta T");
        System.out.println("Si quieres saber cuanto dinero tienes, apreta D");
        System.out.println("Si quieres saber cuanto dinero tiene cada jugador , apreta F");
        System.out.println("Finalmente apreta ENTER para CONFIRMAR");
        boolean tomarDecision = false;
        while ( tomarDecision == false ){
            String input = scan.nextLine();
            if (input == "D" || input == "d") System.out.println("Tienes " + this.getFinanzas() + " dinero");
            if (input == "F" || input == "f") {
                for (Jugador jugador : juego.getJugadores()) {
                    System.out.println("El jugador " + jugador.getNombre() + " tiene una cantidad de " + this.getFinanzas());
                }
            }
            if (input == "C" || input == "c"){
                tomarDecision = true;
            }
            if (input == "T" || input == "t"){
                tomarDecision = true;
                seguirConElJuego = false;
            }
        }
    }
}
