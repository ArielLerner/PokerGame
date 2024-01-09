package org.IndiePapafritaCraft.ClasesJuegoPoker;

import org.IndiePapafritaCraft.ClasesDeJugador.Jugador;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;

import java.util.ArrayList;
import java.util.Scanner;

public class UtilidadesJuegoPoker {
    /**
     * @param indexMano es el jugador que le tocaria ser primero pero si decidia no jugar la mano entonces puede ser otro
     * @return devuelve el index del ultimo jugador en subir, si no hay ningun jugador en el juego, devuelve -1
     */
    public static int Apuesta(JuegoPoker juego, int indexMano) {
        Jugador[] jugadores = juego.getJugadores();
        int indexPrimerJugadorEnElJuego = UtilidadesJuegoPoker.buscarIndexDePrimerJugadorEnElJuego(jugadores, indexMano);
        int ultimoJugadorQueSubio = -1;
        if (juego.jugQueSiguenEnElJuego() != 0)// chequea que haya por lo menos un jugador
        {
            jugadores[indexPrimerJugadorEnElJuego].verApuesta();
            for (Jugador jugador : jugadores) //avisoVerApuesta
                jugador.jugadorVeApuestaAviso(jugadores[indexMano]);
            ultimoJugadorQueSubio = modeloDeApuesta(indexPrimerJugadorEnElJuego,juego,jugadores);
        }
        return ultimoJugadorQueSubio;
    }

    /**
     * Este metodo estÃ¡ obsoleta
     * @return devuelve el index del ultimo jugador que subio la apuesta
     *
     */
    public static int modeloDeApuestaGeneral(int indexJugadorAnterior, JuegoPoker juego , Jugador[] jugadores) {
        System.out.println("Se estÃ¡ usando un metodo obsoleto");
        boolean terminoLaApuesta = false;
        while (terminoLaApuesta == false) { //Este while chequea cuando se pasan suficientes turnos sin subir para que termine la apuesta
            int nroDeJugQueNoSuben = 0;
            for (int x = 0; x < (jugadores.length - 1); x++) {
                int indexJugadorActual = indexDeJugadorSiguiente(indexJugadorAnterior, jugadores);
                if (jugadores[indexJugadorActual].getEstarEnElJuego() == true) {
                    jugadores[indexJugadorActual].verApuesta();
                    //avisoVeApuesta
                    for (Jugador jugador : jugadores )
                        jugador.jugadorVeApuestaAviso(jugadores[indexJugadorActual]);
                    if (seSubioLaApuesta(jugadores[indexJugadorActual],jugadores) ) {
                        indexJugadorAnterior = indexJugadorActual;
                        indexJugadorAnterior = indexJugadorAnterior;
                        nroDeJugQueNoSuben = 0;
                        break;
                    } else {
                        nroDeJugQueNoSuben++;
                    }
                }
                //como ya termino, el jugadorActual Pasa a ser el anterior
                indexJugadorAnterior = indexJugadorActual;
                continue;
            }
            if (nroDeJugQueNoSuben == juego.jugQueSiguenEnElJuego()-1 )  { //chequeo si termino la apuesta
                terminoLaApuesta = true;
            }
        }
        return indexJugadorAnterior;
    }
    /**
     * Este mÃ©todo permite seguir la apuesta a partir de que un jugador la suba  o vaya dsps del primero
     * @return devuelve el index del ultimo jugador que subio la apuesta
     */
    public static int modeloDeApuesta(int indexJugadorAnterior, JuegoPoker juego , Jugador[] jugadores) {
        boolean sigueApuesta = true;
        int indexDelUltimoEnSubirLaApuesta = indexJugadorAnterior; // esta variable mantiene el index del ultimo en subir
        while (sigueApuesta==true){ // Este while mantiene la apuesta
            boolean apuestaSubida = false;
            for (int x = 0; x < (jugadores.length - 1) && apuestaSubida == false; x++){ //Este for sigue hasta que todos apuestan o se suba la apuesta
                int indexJugadorActual = indexDeJugadorSiguiente(indexJugadorAnterior,jugadores);
                Jugador jugadorActual = jugadores[indexJugadorActual];
                if (jugadorActual.getEstarEnElJuego()== false){}
                if (jugadorActual.getEstarEnElJuego()==true){
                    jugadorActual.verApuesta();
                    for (Jugador jugador : jugadores ) //avisoVeApuesta
                        jugador.jugadorVeApuestaAviso(jugadores[indexJugadorActual]);
                    if (!seSubioLaApuesta(jugadorActual,jugadores) ) {
                        if (!aceptoLaApuesta(jugadorActual,jugadores)){
                            jugadorActual.setEstarEnElJuegoFalse();
                        }
                    }
                    else { // caso se subio la apuesta
                        indexDelUltimoEnSubirLaApuesta = indexJugadorActual;
                        indexJugadorAnterior = indexJugadorActual; //Tengo que cambiar esta variable para que guarde cual es el siguiente jugador
                        apuestaSubida = true;
                        continue; // Para que se rompa el for cuando apuesta subida != true
                    }
                }
            }
            if  (apuestaSubida==false) {
             sigueApuesta = false; }
        }
        return indexDelUltimoEnSubirLaApuesta;
    }


    public static boolean seSubioLaApuesta(Jugador jugadorActual, Jugador[] jugadores) {
        int dineroApostado = jugadorActual.getDineroApostado();
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
    public static boolean aceptoLaApuesta (Jugador jugadorActual,Jugador[] jugadores){
        int mayorDineroApostado = 0;
        for (Jugador x: jugadores){
            if (mayorDineroApostado<x.getDineroApostado())
                mayorDineroApostado = x.getDineroApostado();
        }
        if (jugadorActual.getDineroApostado() >= mayorDineroApostado) return  true;
        else return false;
    }

    /**
     * @return Este metodo no considera que un jugador puede estar o no en el juego
     */
    public static int indexDeJugadorSiguiente(int indexJugador, Jugador[] jugadores) {
        try {
            Jugador x = jugadores[indexJugador + 1];
            return indexJugador + 1;
        } catch (ArrayIndexOutOfBoundsException x) {
            return 0;
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
     * devuelve -1 si no hay ningun jugador en el juego
     *
     * @return devuelve el primer jugador que sigue en el juego
     */
    public static int buscarIndexDePrimerJugadorEnElJuego(Jugador[] jugadores, int indexPrimerJugador) {
        int indexJugActual = indexPrimerJugador;
        int indexJugadorEncontrado = -1;
        for (int x = 0;x< jugadores.length ; x++){
            if (jugadores[indexJugActual].getEstarEnElJuego() == true) {
                indexJugadorEncontrado = indexJugActual;
                break;
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
