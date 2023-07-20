package org.IndiePapafritaCraft.ClasesJuegoPoker;

import org.IndiePapafritaCraft.Jugador;
import org.IndiePapafritaCraft.Mano;

import java.util.ArrayList;

public class UtilidadesJuegoPoker {
    /**
     * Este método permite seguir la apuesta a partir de que un jugador la suba  o vaya dsps del primero
     *
     * @return devuelve el index del ultimo jugador que subio la apuesta
     */
    public static int modeloDeApuestaGeneral(int indexUltimoJugadorQueSubioApuesta, Jugador[] jugadores) {
        boolean terminoLaApuesta = false;
        while (terminoLaApuesta == false) {
            int indexJugadorAnterior = indexUltimoJugadorQueSubioApuesta;
            int nroDeJugQueNoSuben = 0;
            for (int x = 0; x < (jugadores.length - 1); x++) {
                int indexJugadorActual = indexDeJugadorSiguiente(indexJugadorAnterior, jugadores);
                if (jugadores[indexJugadorActual].getEstarEnElJuego() == true) {
                    jugadores[indexJugadorActual].verApuesta();
                    if (seSubioLaApuesta(jugadores, indexJugadorActual) == true) {
                        indexUltimoJugadorQueSubioApuesta = indexJugadorActual;
                        break;
                    } else {
                        nroDeJugQueNoSuben++;
                    }
                }
                //como ya termino, el jugadorActual Pasa a ser el anterior
                indexJugadorAnterior = indexJugadorActual;
                continue;
            }
            if (nroDeJugQueNoSuben == jugadores.length - 1) {
                terminoLaApuesta = true;
            }
        }
        return indexUltimoJugadorQueSubioApuesta;
    }

    public static boolean seSubioLaApuesta(Jugador[] jugadores, int indexDeJugador) {
        int dineroApostado = jugadores[indexDeJugador].getDineroApostado();
        int cantDeJugadores = jugadores.length;
        int contador = 0;
        for (int x = 0; x < cantDeJugadores; x++) {
            if (dineroApostado > jugadores[x].getDineroApostado()) {
                contador++;
            }
        }
        //El dinero apostado deberia ser mayor a todos los demas jugadores menos el mismo
        if (contador == cantDeJugadores - 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return Este metodo no considera que un jugador puede estar o no en el juego
     */
    public static int indexDeJugadorSiguiente(int indexJugador, Jugador[] jugadores) {
        try {
            Jugador x = jugadores[indexJugador + 1];
            return indexJugador + 1;
        } catch (ArrayIndexOutOfBoundsException x) {
            return 1;
        }
    }

    public static int indexDeJugadorAnterior(int indexJugador, Jugador[] jugadores) {
        try {
            Jugador x = jugadores[indexJugador - 1];
            return indexJugador - 1;
        } catch (ArrayIndexOutOfBoundsException x) {
            return jugadores.length - 1;
        }
    }

    public static int indexJugadorUltimo(int indexJugadorInicial, Jugador[] jugadores) {
        return UtilidadesJuegoPoker.indexDeJugadorAnterior(indexJugadorInicial, jugadores);
    }

    /**
     * puede dar error si todos los jugadores tienen la variable EstarEnElJuego en false
     *
     * @return devuelve el primer jugador que sigue en el juego
     */
    public static int buscarIndexDePrimerJugadorEnElJuego(Jugador[] jugadores, int indexPrimerJugador) {
        boolean jugEnElJuegoEncontrado = false;
        int indexJugActual = indexPrimerJugador;
        int indexJugadorEncontrado = 0;
        while (jugEnElJuegoEncontrado == false) {
            if (jugadores[indexJugActual].getEstarEnElJuego() == true) {
                jugEnElJuegoEncontrado = true;
                indexJugadorEncontrado = indexJugActual;
            } else indexJugActual = UtilidadesJuegoPoker.indexDeJugadorSiguiente(indexJugActual, jugadores);
        }
        return indexJugadorEncontrado;
    }

    /**
    rota el arraylist hasta que quede el primerJugador quede en la posicion 0
     */
    public static void ordenarArraylistManosEnMesa(ArrayList<Mano> manosEnMesa, Jugador primerJugador){
        while (true){
            if (primerJugador.getMano() == manosEnMesa.get(0)){
                return;
            }
            else {
                Mano manoParaRotar  = manosEnMesa.remove(0);
                manosEnMesa.add(manoParaRotar);
            }
        }
    }
    public static ArrayList<Mano> hacerArrayDeMostrarCartas(JuegoPoker juego) {
        // haciendo el array de manos
        Jugador[] jugadores = juego.getJugadores();
        ArrayList<Mano> manosEnMesa = new ArrayList<Mano>();
        for (int x = 0; x < jugadores.length; x++) {
            if (jugadores[x].getEstarEnElJuego() == true) {
                manosEnMesa.add(jugadores[x].getMano());
            }
        }
        //ordenarlo
        Jugador primerJugadorEnMostar = jugadores[juego.getDatos().getIndexUltimoJugadorQueSubioApuesta()];
        UtilidadesJuegoPoker.ordenarArraylistManosEnMesa(manosEnMesa, primerJugadorEnMostar);
        return manosEnMesa;
    }
    public static int calcularPozo(Jugador[] jugadores){
        int pozo=0;
        for (int x=0;x<jugadores.length;x++){
            pozo = pozo + jugadores[x].getDineroApostado();
        }
        return  pozo;
    }

    /**
     * establece la variable dineroApostado de todos los jugadores en 0
     */
    public static void dineroApostadoEn0(Jugador[] jugadores){
        for (int x=0;x<jugadores.length;x++){
            jugadores[x].setDineroApostadoEn0();
        }
    }
    public static Jugador buscarJugador (Mano x,Jugador[] jugadores){
        for (int a=0;a<jugadores.length;a++){
            if (jugadores[a].getMano()==x){
                return jugadores[a];
            }
        }
        System.out.println(" error buscarJugador utilidadesPokerJava jugador no encontrado");
        return jugadores[0];
    }
}
