package org.IndiePapafritaCraft.ClasesJuegoPoker;
import org.IndiePapafritaCraft.ClasesDeJugador.Jugador;
import org.IndiePapafritaCraft.ClasesRestantes.Carta;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ClasesRestantes.Mazo;
import org.IndiePapafritaCraft.ClasesJuegoPoker.PartesDelJuego;
import java.util.ArrayList;

public class UtilidadesPartesDelJuego {
    public static void jugarMano(JuegoPoker juego) {
        UtilidadesPartesDelJuego.pagoDeLuz(juego);
        UtilidadesPartesDelJuego.repartirCartas(juego);
        UtilidadesPartesDelJuego.primeraApuesta(juego, 0);
        UtilidadesPartesDelJuego.cambioCartas(juego, 0);
        UtilidadesPartesDelJuego.segundaApuesta(juego);
        UtilidadesPartesDelJuego.finalDelJuego(juego);
    }

    /**
     * Este metodo hace pagar la luz a los jugadores para que estenEnElJuego
     */
    public static void pagoDeLuz(JuegoPoker juego) {
        Jugador[] jugadores = juego.getJugadores();
        int precioDeLuz = juego.getPrecioDeLuz();
        int jugQpaganLuz=0; // chequear si un solo jugador pago la luz
        for (int x = 0; x < jugadores.length; x++) { //pago de luz
            if (jugadores[x].getFinanzas() >= precioDeLuz) {
                jugadores[x].pagarLaLuz(precioDeLuz);
                jugQpaganLuz ++;
            } else {
                jugadores[x].setEstarEnElJuegoFalse();
            }
        }
        for (Jugador jugador : juego.getJugadores())
            jugador.pagarLuzAviso();
    }

    public static void repartirCartas(JuegoPoker juego) {

        if (juego.jugQueSiguenEnElJuego()==0 || juego.jugQueSiguenEnElJuego()==1) return; //caso en el que no hay jugadores o hay 1
        juego.getDatos().setParteDelJuego(PartesDelJuego.REPARTIRCARTAS);
        Mazo mazo = juego.getMazo();
        mazo.mezclar();
        Jugador[] jugadores = juego.getJugadores();
        for (int x = 0; x < jugadores.length; x++) {
            if (jugadores[x].getEstarEnElJuego() == true)
                jugadores[x].setMano(mazo.sacarManoDelMazo(x));
        }
        for (Jugador jugador : juego.getJugadores())
            jugador.repartirCartasAviso();
    }

    /**
     * siempre Es mano la posicion 0 de jugadores
     *
     * @param juego
     */
    public static void primeraApuesta(JuegoPoker juego, int indexDeMano) {
        if (juego.jugQueSiguenEnElJuego()==0 || juego.jugQueSiguenEnElJuego()==1) return; //caso en el que no hay jugadores o hay uno
        juego.getDatos().setParteDelJuego(PartesDelJuego.PRIMERAAPUESTA);
        Jugador[] jugadores = juego.getJugadores();
        int ultimoJugadorQueSubio = UtilidadesJuegoPoker.Apuesta(juego,indexDeMano);
        //se guarda el ultimo que subio la apuesta, puede ser -1 si no hay jugadores en el juego
        DatosMomentaneos datos = juego.getDatos();
        datos.setIndexUltimoJugadorQueSubioApuesta(ultimoJugadorQueSubio);
        juego.setDatos(datos);
    }

    public static void cambioCartas(JuegoPoker juego, int indexDeLaMano) {
        if (juego.jugQueSiguenEnElJuego()==0 || juego.jugQueSiguenEnElJuego()==1) return; //caso en el que no hay jugadores o hay 1
        juego.getDatos().setParteDelJuego(PartesDelJuego.CAMBIOCARTAS);
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
        if (juego.jugQueSiguenEnElJuego()==0 || juego.jugQueSiguenEnElJuego()==1) return; //caso en el que no hay jugadores o hay 1
        juego.getDatos().setParteDelJuego(PartesDelJuego.SEGUNDAAPUESTA);
        Jugador[] jugadores = juego.getJugadores();
        int manoDeApuesta = UtilidadesJuegoPoker.buscarIndexDePrimerJugadorEnElJuego(jugadores, juego.getDatos().getIndexUltimoJugadorQueSubioApuesta());
        { //caso: si no hay jugadores en el juego
            int ultimoJugadorQueSubio = UtilidadesJuegoPoker.Apuesta(juego, manoDeApuesta);
            //Se guarda el Ãºltimo que subio la apuesta
            DatosMomentaneos datos = juego.getDatos();
            datos.setIndexUltimoJugadorQueSubioApuesta(ultimoJugadorQueSubio);
            juego.setDatos(datos);
        }
    }

    /**
     * si gana mas de un jugador y el nro de dinero a repartir no es divisible por el nro de jugadores dque gano
     * se le da a cada jugador el mismo nro y lo que falta por repartir se lo queda el jugador mas cercano a la posicion 0 del array[] jugadores
     */
    public static void finalDelJuego(JuegoPoker juego) {
        if (juego.jugQueSiguenEnElJuego()==0 ) return; //caso en el que no hay jugadores o hay 1
        Jugador[] jugadores = juego.getJugadores();//Buscar las manos ganadoras
        ArrayList<Mano> mostrarCartas = UtilidadesJuegoPoker.hacerArrayDeMostrarCartas(juego);
        ArrayList<Mano> manosGanadoras = ComparacionDeManos.mejoresManos(mostrarCartas, juego.numeroMayorDelMazo());
        int pozo = UtilidadesJuegoPoker.calcularPozo(juego.getJugadores()); // acÃ¡ empieza el calculo del pozo y la distribucion
        // para no tener que distribuir numeros con coma voy a darle el resto del empate al mas cercano de los ganadores a la mano
        int baseParaCadaUno = pozo / manosGanadoras.size();
        int restoParaPrimerGanador = pozo - (baseParaCadaUno * manosGanadoras.size());
        //Se hace un arrayList con los jugadores ganadores
        ArrayList<Jugador> jugadoresGanadores = new ArrayList<Jugador>();
        for (int x = 0; x < manosGanadoras.size(); x++) {
            jugadoresGanadores.add(UtilidadesJuegoPoker.buscarJugador(manosGanadoras.get(x), jugadores));
        }
        //FinalDeJuegoAviso
        for (Jugador jugador : jugadores)  jugador.finalDelJuegoAviso(mostrarCartas,jugadoresGanadores,pozo);
        // le sumo a cada ganador lo que le corresponde
        for (int x = 0; x < jugadoresGanadores.size(); x++) {
            jugadoresGanadores.get(x).sumarXparaFinanzas(baseParaCadaUno);
            if (x == 0 && jugadoresGanadores.size() > 1)
                jugadoresGanadores.get(x).sumarXparaFinanzas(restoParaPrimerGanador);
        }
        UtilidadesJuegoPoker.dineroApostadoEn0(juego.getJugadores());
    }
}
