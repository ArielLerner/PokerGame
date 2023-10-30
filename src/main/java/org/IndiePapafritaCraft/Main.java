package org.IndiePapafritaCraft;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.MetodosDeApuestas;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JuegoPoker juego = org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker.crearJuegoTerminal(0.2);
        juego.jugar();
    }
}