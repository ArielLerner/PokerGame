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
        String stringLeido = "";
        Scanner scan = new Scanner(System.in);
        boolean seCumplenCondiciones = false;
        while (seCumplenCondiciones == false) {
            stringLeido = scan.nextLine();
            //En este nesterd try, se hace parseDouble, si sale bien se hace parseInt
            try {
                Double.parseDouble(stringLeido);
                try {
                    nroLeido = Integer.parseInt(stringLeido);
                    if (nroLeido >= nroMenor && nroLeido <= nroMayor) {
                        seCumplenCondiciones = true;
                    } else System.out.println("El numero debe estar entre " + nroMenor + " y " + nroMayor);
                } catch (NumberFormatException x) {
                    System.out.println("Introducir un entero");
                }
            } catch (Throwable x) {
                System.out.println("Introducir un numero ");
            }

        }
        return nroLeido;
    }

    /**
     * @return devuelve un int que entra por input que esta entre el 1 y el 1000
     * si no se devuelve nada da -1
     */
    public static int scanBalanceInicial() {
        System.out.println("¿Cuanto dinero empezara teniendo cada jugador?");
        int balanceInicial = scanIntEntreDosNros(100, 1000);
        return balanceInicial;
    }

    /**
     * @return la luz debe ser por lo menos el (balanceInicial/5) - 5
     */
    public static int scanPrecioDeLuz(int nroBalanceInicial) {
        System.out.println("Con cuanto dinero se entra a la mano?");
        int precioDeLuz = scanIntEntreDosNros(1, (nroBalanceInicial / 5) - 5);
        return precioDeLuz;
    }

    /**
     * siempre estan en los primeros lugares los jugadores reales y despues los de la maquina
     *
     * @return devuelve un array de tipo de jugador con los jugadores de la maquina y los jugadores reales
     */
    public static TipoDeJugador[] scanNroDeJugadores() {
        int jugadoresTotales = 0;
        int jugadoresReales = 0;
        //preguntar al usuario los jugadores
        System.out.println("¿De a cuantos jugadores quieres jugar?");
        jugadoresTotales = scanIntEntreDosNros(2, 5);
        jugadoresReales = 1;
        //hacer el array
        TipoDeJugador[] tipoDeJugador = new TipoDeJugador[jugadoresTotales];
        for (int x = 0; x < jugadoresTotales; x++) {
            if (x < jugadoresReales) {
                tipoDeJugador[x] = TipoDeJugador.JUGADOR_REAL;
            }
            if (x >= jugadoresReales) {
                tipoDeJugador[x] = TipoDeJugador.JUGADOR_DE_LA_MAQUINA;
            }
        }
        return tipoDeJugador;
    }

    public static String[] scanNombreDeJugadores(int nroDeJugadores) {
        Scanner scan = new Scanner(System.in);
        String[] nombres = new String[nroDeJugadores];
        for (int x = 0; x < nroDeJugadores; x++) {
            boolean condicionesDeNombre = false;
            String nombre = "";
            while (condicionesDeNombre == false) {
                System.out.println("Introducir nombre del jugador numero " + (x + 1));
                nombre = scan.nextLine();
                nombre = UtilidadesJuegoPokerTerminal.sacarEspaciosAlFinal(nombre);
                // Ahora voy a chequear las condiciones a.Si es null ; b.Si tiene chars invalidos ; c.Si se repite
                if (nombre.isEmpty() == false) {
                    if (UtilidadesJuegoPokerTerminal.charDigitosYNumerosValidos(nombre) == true) {
                        if (UtilidadesJuegoPokerTerminal.nombreRepetido(nombre, nombres) == false) {
                            condicionesDeNombre = true;
                        } else System.out.println("El nombre no puede repetirse");
                    } else System.out.println("Introducir digitos y numeros");
                } else System.out.println("El nombre no puede estar vacio");
            }
            nombres[x] = nombre;
        }
        return nombres;
    }

    /**
     * Este metodo se fija si hay algun nombrex en []demasNombres
     */
    private static boolean nombreRepetido(String nombreX, String[] demasNombres) {
        for (String nombre : demasNombres) {
            try {
                if (nombre.equals(nombreX)) return true;
            } catch (Throwable x) {
            }
        }
        return false;
    }

    /**
     * devuelve true si el string esta conformado por numeros y letras
     */
    private static boolean charDigitosYNumerosValidos(String nombre) {
        for (char caracter : nombre.toCharArray()) {
            if (Character.isLetterOrDigit(caracter) == true) continue;
            else return false;
        }
        return true;
    }

    /**
     * @return devuelve un string sin los espacios al final que podia tener el otro
     * si todo eln nombre tiene espacios puede devolver null
     */
    private static String sacarEspaciosAlFinal(String x) {
        int ultimaPos = x.length() - 1;
        int primeraPosicSinEspacio = -1;  // de atras para adelante
        //El 32 en ascii es el espacio
        for (int posActual = ultimaPos; posActual >= 0; posActual--) {
            if (Character.valueOf(x.charAt(posActual)) == 32) continue;
            else {
                primeraPosicSinEspacio = posActual;
                break;
            }
        }
        //Hago el string sacando todos los espacios
        if (primeraPosicSinEspacio == -1) return new String();//Caso, que el nombre este lleno de espacios
        return x.substring(0, primeraPosicSinEspacio + 1); //El indexFinal del metodo substring no esta incluido entonces hay que agregar 1
    }

    public static int scanMenorNroDelMazo(int nroJug) {
        System.out.println("Si quieres jugar con todo el mazo, ingresa |J| , si quiers sacar cartas del mazo ingresa |S|");
        Scanner x = new Scanner(System.in);
        while (true) {
            String line = x.nextLine().toUpperCase();
            if (line.equals("J")) {
                return 12;
            }
            if (!line.equals("S")) {
                System.out.println("Entrada invalida");
                continue;
            } else { //En este caso line es igual a "S"
                System.out.println("¿Desde que numero quieres jugar? Si necesitas ayuda ingresa |A|");
                while (true) {
                    line = x.nextLine().toUpperCase();
                    if (line.equals("A")) {
                        System.out.println("puedes ingresar:");
                        if (nroJug == 2) System.out.println("2,3,4,5,6,7,8(recomendado),9,10");
                        if (nroJug == 3) System.out.println("2,3,4,5,6,7,8(recomendado)");
                        if (nroJug == 4) System.out.println("2,3,4,5,6(recomendado)");
                        if (nroJug == 5) System.out.println("2(recomendado),3");
                        System.out.println("Si por ejemplo decides ingresar 8 , el numero mas bajo es el 8: 8,9,10,J,Q,K,AS");
                        System.out.println("Entonces, ¿Que quieres hacer?");
                        continue;
                    }
                    if (line.equals("J")) {
                        System.out.println("Si se jugara un mazo comenzando de la J, seria muy chico para poder jugar, ingresa otro valor...");
                        continue;
                    }
                    if (line.equals("Q")) {
                        System.out.println("Si se jugara un mazo comenzando de la Q, seria muy chico para poder jugar, ingresa otro valor...");
                        continue;
                    }
                    if (line.equals("K")) {
                        System.out.println("Si se jugara un mazo comenzando de la K, seria muy chico para poder jugar, ingresa otro valor...");
                        continue;
                    }
                    if (line.equals("AS")) {
                        System.out.println("Si se jugara un mazo comenzando de la AS, seria muy chico para poder jugar, ingresa otro valor...");
                        continue;
                    }
                    if (nroJug==2){
                        if (lineaEntre2numeros(2,10,line))return (Integer.parseInt(line))-2;//Le resto 1 para que empiece desde el numero qeu se ingresa
                    }
                    if (nroJug==3){
                        if (lineaEntre2numeros(2,8,line))return (Integer.parseInt(line))-2;
                    }
                    if (nroJug==4){
                        if (lineaEntre2numeros(2,6,line))return (Integer.parseInt(line))-2;
                    }
                    if (nroJug==5){
                        if (lineaEntre2numeros(2,3,line))return (Integer.parseInt(line))-2;
                    }
                }
            }
        }

    }

    /**
     * @return este metodo printea cosas
     */
    public static boolean lineaEntre2numeros(int nMenor, int nMayor, String line) {
        try {
            int nro = Integer.parseInt(line);
            if (nro < nMenor || nro > nMayor) {
                System.out.println("El numero debe estar entre " + nMenor + " y " + nMayor);
                return false;
            } else {
                return true;
            }
        } catch (Exception abc) {
            System.out.println("Entrada invalida");
            return false;
        }
    }
}