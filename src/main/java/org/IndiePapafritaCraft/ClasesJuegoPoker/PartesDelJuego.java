package org.IndiePapafritaCraft.ClasesJuegoPoker;

import org.IndiePapafritaCraft.Carta;
import org.IndiePapafritaCraft.Jugador;
import org.IndiePapafritaCraft.Mano;
import org.IndiePapafritaCraft.Mazo;

import java.util.ArrayList;

public class PartesDelJuego {
    public static void jugarMano(JuegoPoker juego) {
        PartesDelJuego.pagoDeLuz(juego);
        PartesDelJuego.repartirCartas(juego);
        PartesDelJuego.primeraApuesta(juego, 0);
        PartesDelJuego.cambioCartas(juego, 0);
        PartesDelJuego.segundaApuesta(juego);
        PartesDelJuego.finalDelJuego(juego);
    }

    /**
     * Este metodo hace pagar la luz a los jugadores para que estenEnElJuego
     */
    public static void pagoDeLuz(JuegoPoker juego) {
        Jugador[] jugadores = juego.getJugadores();
        int precioDeLuz = juego.getPrecioDeLuz();
        for (int x = 0; x < jugadores.length; x++) {
            if (jugadores[x].getFinanzas() >= precioDeLuz) {
                jugadores[x].pagarLaLuz(precioDeLuz);
            } else {
                jugadores[x].setEstarEnElJuegoFalse();
            }
        }
    }

    public static void repartirCartas(JuegoPoker juego) {
        Mazo mazo = juego.getMazo();
        mazo.mezclar();
        Jugador[] jugadores = juego.getJugadores();
        for (int x = 0; x < jugadores.length; x++) {
            if (jugadores[x].getEstarEnElJuego() == true)
                jugadores[x].setMano(mazo.sacarManoDelMazo(x));
        }
    }

    /**
     * siempre Es mano la posicion 0 de jugadores
     *
     * @param juego
     */
    public static void primeraApuesta(JuegoPoker juego, int indexDeMano) {
        Jugador[] jugadores = juego.getJugadores();
        int indexMano = UtilidadesJuegoPoker.buscarIndexDePrimerJugadorEnElJuego(jugadores, indexDeMano);
        jugadores[indexMano].verApuesta();
        int ultimoJugadorQueSubio = UtilidadesJuegoPoker.modeloDeApuestaGeneral(indexMano, jugadores);
        //se guarda el ultimo que subio la apuesta, quien comienza la segunda apuesta
        DatosMomentaneos datos = juego.getDatos();
        datos.setIndexUltimoJugadorQueSubioApuesta(ultimoJugadorQueSubio);
        juego.setDatos(datos);
    }

    public static void cambioCartas(JuegoPoker juego, int indexDeLaMano) {
        Jugador[] jugadores = juego.getJugadores();
        for (int x = 0; x < jugadores.length; x++) {
            if (jugadores[x].getEstarEnElJuego() == true) {
                boolean[] cambioCartas = jugadores[x].cambioCartas();
                Carta[] cartasDelMazo = juego.getMazo().sacarCartasParaCambiar(x);
                int cartaParaSacar = 0;
                for (int cartaCambiar = 0; cartaCambiar < 5; cartaCambiar++) {
                    if (cambioCartas[cartaCambiar] == true) {
                        jugadores[x].cambiarUnaCarta(cartaCambiar, cartasDelMazo[cartaParaSacar]);
                        cartaParaSacar++;
                    }
                }
            }
        }
    }

    public static void segundaApuesta(JuegoPoker juego) {
        Jugador[] jugadores = juego.getJugadores();
        int manoDeApuesta = UtilidadesJuegoPoker.buscarIndexDePrimerJugadorEnElJuego(jugadores, juego.getDatos().getIndexUltimoJugadorQueSubioApuesta());
        jugadores[manoDeApuesta].verApuesta();
        int ultimoJugadorQueSubio = UtilidadesJuegoPoker.modeloDeApuestaGeneral(manoDeApuesta, jugadores);
        //Se guarda el último que subio la apuesta
        DatosMomentaneos datos = juego.getDatos();
        datos.setIndexUltimoJugadorQueSubioApuesta(ultimoJugadorQueSubio);
        juego.setDatos(datos);
    }

    /**
     * si gana mas de un jugador y el nro de dinero a repartir no es divisible por el nro de jugadores dque gano
     * se le da a cada jugador el mismo nro y lo que falta por repartir se lo queda el jugador mas cercano a la posicion 0 del array[] jugadores
     */
    public static void finalDelJuego(JuegoPoker juego) {
        Jugador[] jugadores = juego.getJugadores();
        ArrayList<Mano> mostrarCartas = UtilidadesJuegoPoker.hacerArrayDeMostrarCartas(juego);
        ArrayList<Mano> manosGanadoras = ComparacionDeManos.mejoresManos(mostrarCartas, juego.numeroMayorDelMazo());
        int pozo = UtilidadesJuegoPoker.calcularPozo(juego.getJugadores());
        UtilidadesJuegoPoker.dineroApostadoEn0(juego.getJugadores());
        // para no tener que distribuir numeros con coma voy a darle el resto del empate al mas cercano de los ganadores a la mano
        int baseParaCadaUno = pozo / manosGanadoras.size();
        int restoParaPrimerGanador = pozo - (baseParaCadaUno * manosGanadoras.size());
        //Se hace un arrayList con los jugadores ganadores
        ArrayList<Jugador> jugadoresGanadores = new ArrayList<Jugador>();
        for (int x = 0; x < manosGanadoras.size(); x++) {
            jugadoresGanadores.add(UtilidadesJuegoPoker.buscarJugador(manosGanadoras.get(x), jugadores));
        }
        // le sumo a cada ganador lo que le corresponde
        for (int x = 0; x < jugadoresGanadores.size(); x++) {
            jugadoresGanadores.get(x).sumarXparaFinanzas(baseParaCadaUno);
            if (x == 0 && jugadoresGanadores.size() > 1)
                jugadoresGanadores.get(x).sumarXparaFinanzas(restoParaPrimerGanador);
        }
    }
}
