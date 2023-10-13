package org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.EstadisticasDelJuegoPoker;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.MetodosDeApuestas;
import org.IndiePapafritaCraft.ClasesDeJugador.Jugador;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;

public class Singleton {
    public static SingletonDatos datosEstadisticos;

    /**
     * Este metodo crea un objeto que contiene variables de valor para la apuesta
     * Para hacer que solo 1 jugador le pase la informacion necesaria, para poder actualizarlo se necesita la clave: que es el nombre del jugador que crea el singleton
     */
    public static SingletonDatos get(Jugador x) {
        if (datosEstadisticos == null) {
            String nombreClave = x.getNombre();
            datosEstadisticos = new SingletonDatos(MetodosDeApuestas.estimacionDeRondas(),nombreClave, x.getJuego());
        }

        return datosEstadisticos;
    }
}
