package org.IndiePapafritaCraft;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.MetodosDeApuestas;
import org.IndiePapafritaCraft.ClasesJuegoPoker.DatosMomentaneos;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.ClasesRestantes.Carta;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ClasesRestantes.Mazo;
import org.IndiePapafritaCraft.ValoresJuntados.TipoDeJugador;
import org.IndiePapafritaCraft.clasesDePruebas.TestearProbabilidades;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {         int balanceInicial = 50;
        int precioLuz =15;
        TipoDeJugador[] tipoDeJugadores = {TipoDeJugador.JUGADOR_REAL, TipoDeJugador.JUGADOR_DE_LA_MAQUINA};
        String[] nombres = new String[]{"j1", "j2" };
        double toleracionDeEstrategias = 0.2;
        int nroMinimoMazo = 10; //Es el nro  //IR CAMBIANDO

        JuegoPoker juego = JuegoPoker.crearJuegoPorArgumentos(balanceInicial, precioLuz, tipoDeJugadores, nombres, toleracionDeEstrategias,nroMinimoMazo-2);
          //JuegoPoker juego = JuegoPoker.crearJuegoTerminal(toleracionDeEstrategias);
           //juego.jugar();
         Carta[] abcd = new Carta[] {new Carta("diez",1), //IR CAMBIANDO
                                    new Carta("j",2),
                                    new Carta("as",3),
                                     new Carta("as",0),
                                      new Carta("as",1)};
         Mano m = new Mano(abcd);
         System.out.println(m);
         boolean c =  true;
         boolean nc = false;
         boolean[] cambioCartas = {c,c,nc,nc,nc};  //CAMBIAR CADA VEZ
        TestearProbabilidades.probarTodasLasPosibilidades(m,juego,cambioCartas,true,false,true, 4);
           }
}