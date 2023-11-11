package org.IndiePapafritaCraft;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.MetodosDeApuestas;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.ValoresJuntados.TipoDeJugador;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
            int balanceInicial = 70;
            int precioLuz = 10;
            TipoDeJugador[] tipoDeJugadores = {TipoDeJugador.JUGADOR_REAL, TipoDeJugador.JUGADOR_DE_LA_MAQUINA};
            String[] nombres = new String[]{"rumpelstinki", "eshrek"};
            double toleracionDeEstrategias = 0.2;

            JuegoPoker juego = JuegoPoker.crearJuegoPorArgumentos(balanceInicial, precioLuz, tipoDeJugadores, nombres, toleracionDeEstrategias);
            //JuegoPoker juego =JuegoPoker.crearJuegoTerminal(toleracionDeEstrategias);
        //JuegoPoker juego =  JuegoPoker.crearJuegoTerminal(0.2);
            juego.jugar();
    }
}