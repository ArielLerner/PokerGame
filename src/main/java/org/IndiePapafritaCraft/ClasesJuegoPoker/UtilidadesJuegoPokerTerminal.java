package org.IndiePapafritaCraft.ClasesJuegoPoker;

import org.IndiePapafritaCraft.ValoresJuntados.TipoDeJugador;

import java.util.Scanner;

public class UtilidadesJuegoPokerTerminal {
    /**
     * Lee un int  por input
     *
     * @param nroMenor es el menor nro que puede ser
     * @param nroMayor es el mayor nro que puede ser
     * @return el nro leido
     */
    public static int scanIntEntreDosNros(int nroMenor, int nroMayor) {
        int nroLeido = -1;
        Scanner scan = new Scanner(System.in);
        boolean seCumplenCondiciones = false;
        while (seCumplenCondiciones == false) {
            try {
                System.out.println();
                nroLeido = Integer.parseInt(scan.nextLine());
                if (nroLeido >= nroMenor && nroLeido <= nroMayor) {
                    seCumplenCondiciones = true;
                } else System.out.println("El numero debe estar entre +" + nroMenor + " y " + nroMayor);
            } catch (NumberFormatException x) {
                System.out.println("El numero debe ser entero");
            }
        }
        return nroLeido;
    }

    /**
     * @return devuelve un int que entra por input que esta entre el 1 y el 1000
     * si no se devuelve nada da -1
     */
    public static int scanBalanceInicial() {
        int balanceInicial = -1;
        Scanner scan = new Scanner(System.in);
        boolean hayUnNumero = false;
        while (hayUnNumero == false) {
            try {
                System.out.println("¿Con cuanto dinero empiezan los jugadores?");
                balanceInicial = Integer.parseInt(scan.nextLine());
                if (balanceInicial > 0 && balanceInicial < 1000) {
                    hayUnNumero = true;
                } else {
                    System.out.println("Que el numero este entre 0 y 1000");
                }
            } catch (NumberFormatException x) {
                System.out.println("Poner un numero entero entre 0 y 1000");
            }
        }
        return balanceInicial;
    }

    public static int scanPrecioDeLuz(int nroBalanceInicial) {
        System.out.println("Con cuanto dinero se entra a la mano?");
        int precioDeLuz = scanIntEntreDosNros(1, nroBalanceInicial);
        return precioDeLuz;
    }

    /**
     * siempre estan en los primeros lugares los jugadores reales y despues los de la maquina
     *
     * @return devuelve un array de tipo de jugador con los jugadores de la maquina y los jugadores reales
     */
    public static TipoDeJugador[] scanNroDeJugadores() {
        Scanner scan = new Scanner(System.in);
        int jugadoresReales = 0;
        int jugadoresMaquina = 0;
        boolean seCumplenCondiciones = false;
        //preguntar al usuario los jugadores
        while (seCumplenCondiciones == false) {
            System.out.println("Introducir numero de jugadores ( puede ser de 2 a 5 )");
            System.out.println("¿Cuantos jugadores reales van a haber?");
            jugadoresReales = scanIntEntreDosNros(2, 5);
            jugadoresMaquina = scanIntEntreDosNros(2, 5);
            int jugadoresTotales = jugadoresReales + jugadoresMaquina;
            if (jugadoresTotales >= 2 && jugadoresTotales <= 5) {
                seCumplenCondiciones = true;
            } else System.out.println("El numero de jugadores debe estar entre 2 y 6");
        }
        //hacer el array
        int jugadoresTotales = jugadoresReales + jugadoresMaquina;
        TipoDeJugador[] tipoDeJugador = new TipoDeJugador[jugadoresTotales];
        for (int x = 0; x < jugadoresTotales; x++) {
            if (x < jugadoresReales) {
                tipoDeJugador[x] = TipoDeJugador.JUGADOR_REAL;
            }
            if (x > jugadoresReales) {
                tipoDeJugador[x] = TipoDeJugador.JUGADOR_DE_LA_MAQUINA;
            }
        }
        return tipoDeJugador;
    }

    public static String[] scanNombreDeJugadores(int nroDeJugadores) {
        Scanner scan = new Scanner(System.in);
        String[] nombres = new String[nroDeJugadores];
        for (int x = 0; x < nroDeJugadores; x++) {
            boolean nombreNoRepetido = false;
            String nombre = "";
            while (nombreNoRepetido == false) {
                System.out.println("Introducir nombre del jugador numero " + x);
                nombre = scan.nextLine();
                // Ahora tengo que chequear si el nombre se repite entonces hago me fijo s en las demas posiciones se repite con esta
            }
            nombres[x] = nombre;
        }

    }
}
