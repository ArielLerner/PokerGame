package org.IndiePapafritaCraft;


import org.IndiePapafritaCraft.ClasesDeLaCpu.*;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.clasesDePruebas.PruebasAuxiliares;

public class Main {
    public static void main(String[] args) {
        Mano mano = new Mano(new Carta[]{new Carta(6, 2), new Carta(6, 3), new Carta(6, 0),
                new Carta(9, 2), new Carta(6, 1)});

        //balanceDeLaRonda
        BalanceDeLaRonda balance = new BalanceDeLaRonda(0, 0, new int[]{0, 0});
        //juegoDePoker
        //JuegoPoker juego = new JuegoPoker(new Mazo(), balance);
        //JugadorMaquina j1 = new JugadorMaquina(mano, balance, juego);
        double inicio = System.currentTimeMillis();
        //PruebasAuxiliares.ProbarErrores(j1,100000,0.5);
        double terminar = System.currentTimeMillis();
        //System.out.println(terminar-inicio);
        System.out.println( 3/2);





        }
    }
    //Mano a = new Mano(new Carta[]{new Carta(7, 2), new Carta(8, 2), new Carta(8, 1),
    //                new Carta(10, 3), new Carta(8, 3)});
    //no funciona

