package org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorReal;


import org.IndiePapafritaCraft.ClasesDeJugador.Jugador;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.ClasesJuegoPoker.PartesDelJuego;
import org.IndiePapafritaCraft.ClasesJuegoPoker.UtilidadesJuegoPoker;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ValoresJuntados.TipoDeJugador;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.SplittableRandom;
import java.util.StringTokenizer;

public class JugadorReal extends Jugador {
    public JugadorReal(Mano mano, int balanceInicial, JuegoPoker juegoDePoker, String nombre) {
        super(juegoDePoker, mano, balanceInicial, nombre);
    }

    public void verApuesta() {
        if (this.getFinanzas() > 1) {
            System.out.print("¡te quedan " + this.getFinanzas() + "$!");
        } else {
            System.out.print("¡te queda " + this.getFinanzas() + "$!");
        }
        int apuestaMasAlta = juego.getMayorApuesta();
        int miApuesta = this.getDineroApostado();
        String aceptar;
        String noSubir;
        String pozo = "POZO";
        String ordenDeMano = "ORDEN";
        if (apuestaMasAlta == miApuesta) {
            System.out.println(" ¿Cuanto quieres apostar? si quieres pasar, ingresa |P|, si quieres subir, escribe la CANTIDAD que quieres apostar");
            aceptar = "P";
            noSubir = "P";
        } else {
            System.out.println(" ¿Cuanto quieres apostar? si. quieres aceptar la apuesta, escribe: |A| , si no quieres apostar mas escribe |NA| ");
            aceptar = "A";
            noSubir = "NA";
        }
        System.out.println("ingresa |POZO| si quieres ver el pozo, ingresa |ORDEN| si quieres ver el orden de la mano");
        int maximoNroParaSubir = dineroApostado + finanzas;
        int cantParaApostar = this.scanApuesta(aceptar, noSubir, pozo, ordenDeMano, maximoNroParaSubir, apuestaMasAlta);
        this.apostarXcantidad(cantParaApostar);
    }

    public boolean[] cambioCartas() {
        Mano mano = this.getMano(); // print de la mano
        System.out.println();
        System.out.println("Tu mano es: " + mano);
        System.out.println("¿Que cartas quieres cambiar? si(para cambiar) , no(no cambiar) ejemplo: si si no no si  ");
        boolean[] cambioCartas = this.scanCambioCartas("si", "no");
        return cambioCartas;
    }

    public void pagarLuzAviso() {
        int nroDeJugEnJuego = 0;
        Jugador[] jugadores = juego.getJugadores();
        System.out.print("Los jugadores que juegan son: ");
        for (int x = 0; x < jugadores.length; x++) {
            if (jugadores[x].getEstarEnElJuego() == true)
                System.out.print(jugadores[x].getNombre() + " ");
        }
        System.out.println();
    }

    public void repartirCartasAviso() {
        Mano mano = this.getMano();
        System.out.print("Mano: ");
        System.out.println(mano);
        System.out.println();
    }

    public void jugadorVeApuestaAviso(Jugador x) {
        JuegoPoker juego = x.getJuego();
        if (x != this) {
            System.out.print("El jugador " + x.getNombre() + " ");
        } //casos de pasar
        if (juego.getParteDelJuego() == PartesDelJuego.PRIMERAAPUESTA) { // caso pasar 1eraApuesta
            if (x.getDineroApostado() == juego.getPrecioDeLuz() && x.getDineroApostado() == juego.getMayorApuesta()) {
                if (x == this) {
                    System.out.println("has pasado, tu apuesta es de " + x.getDineroApostado());
                } else {
                    System.out.println("ha pasado, su apuesta es de " + x.getDineroApostado());
                }
                return;
            }
        }
        if (x.getJuego().getParteDelJuego() == PartesDelJuego.SEGUNDAAPUESTA) { //caso pasar segunda apuesta
            int indexJugadorApuestaMax1eraApuesta = juego.getDatos().getIndexUltimoJugadorQueSubioApuesta();
            int apuestaMax1eraApuesta = juego.getJugadores()[indexJugadorApuestaMax1eraApuesta].getDineroApostado();
            if (x.getDineroApostado() == apuestaMax1eraApuesta && x.getDineroApostado() == juego.getMayorApuesta()) {
                if (x == this) {
                    System.out.println("has pasado, tu apuesta es de " + x.getDineroApostado());
                } else {
                    System.out.println("ha pasado, su apuesta es de " + x.getDineroApostado());
                }
                return;
            }
        }
        //caso subir, aceptar o no aceptar la apuesta
        int queHizo = x.queHizoEnLaApuesta(juego);
        if (queHizo == 0) {
            if (x == this) System.out.println("no has aceptado la apuesta de " + x.getDineroApostado());
            else System.out.println("no ha aceptado la apuesta, su apuesta era de " + x.getDineroApostado());
        }
        if (queHizo == 1) {
            if (x == this) System.out.println("has aceptado la apuesta de " + x.getDineroApostado());
            else System.out.println("ha aceptado la apuesta de " + x.getDineroApostado());
        }
        if (queHizo == 2) {
            if (x == this) System.out.println("has subido la apuesta a " + x.getDineroApostado());
            else System.out.println("ha subido la apuesta a " + x.getDineroApostado());
        }
    }

    public void cambioCartasAviso(Jugador x, int cartasCambiadas) {
        System.out.println();
        if (x == this) { // para que printee el cambio de cartas
            Mano mano = this.getMano();
            System.out.println("Mano con cartas cambiadas: ");
            System.out.println(mano);
        } else {
            boolean[] cambioRival = x.cambioCartas();
            System.out.println("El jugador: " + x.getNombre() + " cambio " + cartasCambiadas + " cartas");

        }

    }

    /**
     * @param pozo debe contener el dinero que habia em el pozo a la hora de final del juego
     */
    public void finalDelJuegoAviso(ArrayList<Mano> mostrarCartas, ArrayList<Jugador> jugadoresGanadores,
                                   int pozo) {
        if (mostrarCartas.size() > 1) { //solo se muestran las cartas si hay mas de 1 jugador que llego al final
            for (int x = 0; x < mostrarCartas.size(); x++) {
                Jugador jugador = UtilidadesJuegoPoker.buscarJugador(mostrarCartas.get(x), juego.getJugadores());
                System.out.println();
                System.out.println(" El jugador " + jugador.getNombre() + " tiene: " + jugador.getMano().valorDeLaMano(juego.numeroMayorDelMazo(), true));
                System.out.println(" Sus cartas son: " + jugador.getMano());
            }
        }
        //Println de los ganadores
        //caso 1 ganador
        if (jugadoresGanadores.size() == 1) {
            Jugador ganador = jugadoresGanadores.get(0);
            System.out.println();
            System.out.print("|| EL GANADOR ES... ");
            esperarSegundos(1);
            System.out.println("El jugador " + ganador.getNombre() + " quien se lleva el pozo de: " + pozo + " ||");
            esperarSegundos(1);
            System.out.println();
        }
        //caso mas de 1 ganador
        else {
            System.out.println("Los " + jugadoresGanadores.size() + "ganadores son:");
            for (int x = 0; x < jugadoresGanadores.size(); x++) {
                //calculo de cantidad de dinero que se lleva el jugador
                int baseParaCadaGanador = pozo / jugadoresGanadores.size();
                int dineroQueRecibe;
                if (x == 0)
                    dineroQueRecibe = baseParaCadaGanador + pozo - (baseParaCadaGanador * jugadoresGanadores.size());
                else dineroQueRecibe = baseParaCadaGanador;
                System.out.println("El jugador " + jugadoresGanadores.get(x).getNombre() + " se lleva esta cantidad: " + dineroQueRecibe);
            }
        }
    }

    public void entreManosAviso(boolean[] seguirJugando) {
        Scanner scan = new Scanner(System.in);

        menuEntreManos();
        boolean tomarDecision = false;
        while (tomarDecision == false) {
            String input = scan.nextLine().toUpperCase();
            if (input.equals("D")) {
                System.out.println("Tienes " + this.getFinanzas() + " dinero");
            } else if (input.equals("F")) {
                for (Jugador jugador : juego.getJugadores()) {
                    System.out.println("El jugador " + jugador.getNombre() + " tiene una cantidad de " + jugador.getFinanzas());
                }
            } else if (input.equals("C")) {
                tomarDecision = true;
            } else if (input.equals("T")) {
                seguirJugando[0] = false;
                tomarDecision = true;
            } else {
                System.out.println("Error en comando: " + input);
                menuEntreManos();
            }
        }
    }

    /**
     * este metodo crea otro juego por lo tanto hay que terminar el juego
     */
    public void jugadorGanadorAviso() {
        System.out.println();
        System.out.println("|||||   El GANADOR DEL JUEGO ES: " + juego.jugadorConMasDinero().getNombre() + " |||||");
        Scanner scan = new Scanner(System.in);
        menuFinDelJuego();
        boolean tomarDecision = false;
        while (tomarDecision == false) {
            String input = scan.nextLine().toUpperCase();
            if (input.equals("J")) {
                System.out.println();
                JuegoPoker otroJuego = JuegoPoker.crearJuegoTerminal(juego.getDatos().getToleracionDeEstrategia()); //inicia una nueva partida
                otroJuego.jugar();
                tomarDecision = true;
            } else if (input.equals("T")) {
                System.out.println("Fin del Juego");
                tomarDecision = true;
            } else if (input.equals("F")) {
                for (Jugador jugador : juego.getJugadores()) {
                    System.out.println("El jugador " + jugador.getNombre() + " tiene una cantidad de " + jugador.getFinanzas());
                }
            } else {
                System.out.println("Error en comando: " + input);
                System.out.println();
                menuFinDelJuego();
            }
        }
    }

    private void menuEntreManos() {
        System.out.println("      ----------------------------------    ");
        System.out.println("Si quieres CONTINUAR con la siguiente mano apreta C");
        System.out.println("Si quieres TERMINAR el juego apreta T");
        System.out.println("Si quieres saber cuanto dinero tienes, apreta D");
        System.out.println("Si quieres saber cuanto dinero tiene cada jugador , apreta F");
        System.out.println("Finalmente apreta ENTER para CONFIRMAR");
        System.out.println("      ----------------------------------    ");
    }

    private void menuFinDelJuego() {
        System.out.println("    ---------------------------   ");
        System.out.println("Si quieres jugar una nueva partida apreta J");
        System.out.println("Si quieres TERMINAR el juego apreta T");
        System.out.println("Si quieres saber cuanto dinero tiene cada jugador apreta F");
        System.out.println("Finalmente apreta ENTER para CONFIRMAR");
        System.out.println("    ----------------------------  ");
    }

    public TipoDeJugador claseDeJugador() {
        return TipoDeJugador.JUGADOR_REAL;
    }

    /**
     * @param nroMin representa el nroMin para aceptar
     * @param nroMax representa el nroMax que se puede subir
     * @return devuelve la cant que el jug quiere apostar
     */
    public int scanApuesta(String aceptar, String noSubir, String pozo, String ordenDeMano, int nroMax, int nroMin) {
        Scanner scan = new Scanner(System.in);
        boolean terminarMetodo = false;
        try {
            while (true) {
                String line = scan.nextLine();
                //paso todo a mayusculas
                line = line.toUpperCase();
                aceptar = aceptar.toUpperCase();
                noSubir = noSubir.toUpperCase();
                pozo = pozo.toUpperCase();
                ordenDeMano = ordenDeMano.toUpperCase();

                if (line.equals(aceptar)) return nroMin;
                if (line.equals(noSubir)) return this.getDineroApostado();
                if (line.equals(pozo)) {
                    printPozo();
                    System.out.println("¿Que quieres hacer?");
                    continue;
                }
                if (line.equals(ordenDeMano)) {
                    printOrdenDeMano();
                    System.out.println("¿Que quieres hacer?");
                    continue;
                }
                try {
                    int input = Integer.parseInt(line);
                    if (input > nroMax || input < nroMin)
                        System.out.println("El nro debe estar entre " + nroMin + " y " + nroMax + " o se debe escribir |" + noSubir + "| para no aceptar");
                    else return input;
                } catch (Exception x) {
                    System.out.println("Entrada no valida");
                }
            }
        } catch (Exception x) {
            System.out.println("Hubo un error en JugadorReal: scanApuesta");
        }
        return -1;
    }

    public boolean[] scanCambioCartas(String trueCase, String falseCase) {
        Scanner scan = new Scanner(System.in);
        StringTokenizer st;
        String token;
        boolean[] cambioCartas = new boolean[5];
        boolean parar = false;

        while (!parar) {
            try {
                String line = scan.nextLine();
                if (line.length() > 0) {
                    int cambios = 0;
                    st = new StringTokenizer(line, " ");
                    for (int x = 0; x < 5; x++) {
                        token = st.nextToken();
                        if (token == null) {
                            System.out.println("Falta especificar a algunas cartas");
                            break;
                        }
                        if (token.equals(trueCase)) {
                            cambioCartas[x] = true;
                            cambios++;
                            if (cambios >= 5) {
                                System.out.println("No podes cambiar todas tus cartas");
                                break;
                            }
                            if (x == 4) parar = true;
                        } else if (token.equals(falseCase)) {
                            cambioCartas[x] = false;
                            if (x == 4) parar = true;
                        } else {
                            System.out.println("Entrada invalida: si:(para cambiar) no:(no cambiar)");
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < 5; i++)
                        cambioCartas[i] = false;
                    parar = true;
                }
            } catch (Exception x) {
                System.out.println("Entrada invalida");
            }
        }
        return cambioCartas;
    }

    /**
     * este metodo puede servir para hacer interfaces
     */
    public static void esperarSegundos(int cantDeSegundos) {
        try { //hace que el programa espere 1 segundo
            Thread.sleep(cantDeSegundos * 1000);
        } catch (InterruptedException e) {
        }
    }

    public void printPozo() {
        Jugador[] jugadores = juego.getJugadores();
        switch (jugadores.length) {
            case 2: printPozoEleccionDeLugares(null,jugadores[0], null, null , jugadores[1], null );
            return;
            case 3: printPozoEleccionDeLugares(jugadores[0], null , jugadores[1], null, jugadores[2], null );
            return;
            case 4: printPozoEleccionDeLugares(jugadores[0], null , jugadores[1], jugadores[2], null, jugadores[3] );
            return;
            case 5: printPozoEleccionDeLugares(jugadores[0], jugadores[1], jugadores[2], jugadores[3], null, jugadores[4] );
            return;
            case 6: printPozoEleccionDeLugares(jugadores[0], jugadores[1], jugadores[2], jugadores[3], jugadores[4], jugadores[5] );
        }
    }

    /**
     * te permite elegir la posicion donde se muestran los jugadores ejemplo
     *   j1                      j2                          j3
     *    j4                     j5                          j6
    */

    public void printPozoEleccionDeLugares(Jugador j1, Jugador j2, Jugador j3, Jugador j4, Jugador j5, Jugador j6){
        System.out.println(" ------------------------------------");
        System.out.println("|" + strLgt12PuedeSerNull(j1) + strLgt12PuedeSerNull(j2) + strLgt12PuedeSerNull(j3)  + "|");
        System.out.println("|" + strLgt12DineroApostadoPuedeSerNull(j1) + strLgt12DineroApostadoPuedeSerNull(j2) + strLgt12DineroApostadoPuedeSerNull(j3) +  "|");
        System.out.println("|                                    |");
        System.out.println("|" + strLgt12DineroApostadoPuedeSerNull(j4) + strLgt12DineroApostadoPuedeSerNull(j5) + strLgt12DineroApostadoPuedeSerNull(j6) + "|");
        System.out.println("|" +  strLgt12PuedeSerNull(j4) + strLgt12PuedeSerNull(j5) + strLgt12PuedeSerNull(j6) + "|");
        System.out.println(" ------------------------------------");

    }
    public String strLenght12(String x){
        if (x.length()>=12) return x;
        else {
            while (true){
                x = x + " ";
                if (x.length()==12) return x;
                x = " " + x;
                if (x.length()==12) return x;
            }
        }
    }
    public String strLgt12PuedeSerNull(Jugador x){
        if (x==null) return "            ";
        else return strLenght12(x.getNombre());
    }
    public String strLgt12DineroApostadoPuedeSerNull(Jugador x){
        if (x==null) return "            ";
        else return strLenght12(x.getDineroApostado()+"");
    }

    public void printOrdenDeMano() {
        Jugador[] jugadores = this.juego.getJugadores();
        for (int x = 0; x < jugadores.length; x++) {
            System.out.print(jugadores[x].getNombre() + flecha());
        }
        System.out.println();
    }

    public String flecha() {
        return " --> ";
    }

}

