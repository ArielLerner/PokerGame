package org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.ClasesJugadorReal;


import org.IndiePapafritaCraft.ClasesDeJugador.Jugador;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.ClasesJuegoPoker.UtilidadesJuegoPoker;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ValoresJuntados.TipoDeJugador;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class JugadorReal extends Jugador {
    public JugadorReal(Mano mano, int balanceInicial, JuegoPoker juegoDePoker, String nombre) {
        super(juegoDePoker, mano, balanceInicial, nombre);
    }

    public void verApuesta() {
        System.out.println("Tu balance es de " + this.getFinanzas());
        System.out.println("¿Cuanto quieres apostar? " + " si quieres aceptar, escribe: a , si no quieres apostar mas escribe , na ");
        int maximoNroParaSubir = dineroApostado + finanzas;
        int apuestaMasAlta = 0;
        for (Jugador j : juego.getJugadores())
            if (j.getDineroApostado() > apuestaMasAlta) apuestaMasAlta = j.getDineroApostado();
        int cantParaApostar = this.scanApuesta("a", "na", maximoNroParaSubir, apuestaMasAlta);
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
        System.out.println("Los jugadores que juegan son : ");
        for (int x = 0; x < jugadores.length; x++) {
            if (jugadores[x].getEstarEnElJuego() == true)
                System.out.print(jugadores[x].getNombre() + " ");
        }
        System.out.println();
    }

    public void repartirCartasAviso() {
        Mano mano = this.getMano();
        System.out.println("Mano: ");
        System.out.println(mano);
    }

    public void jugadorVeApuestaAviso(Jugador x) {
        int queHizo = x.queHizoEnLaApuesta(juego);
        System.out.print("El jugador " + x.getNombre() + " ");
        if (queHizo == 0) System.out.println("no ha aceptado la apuesta");
        if (queHizo == 1) System.out.println("ha aceptado la apuesta de: " + x.getDineroApostado());
        if (queHizo == 2) System.out.println("ha subido la apuesta a: " + x.getDineroApostado());
    }
        public void cambioCartasAviso (Jugador x,int cartasCambiadas){
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
        public void finalDelJuegoAviso (ArrayList < Mano > mostrarCartas, ArrayList < Jugador > jugadoresGanadores,
        int pozo){
            //Println de las cartas que se muestran en mesa
            for (int x = 0; x < mostrarCartas.size(); x++) {
                Jugador jugador = UtilidadesJuegoPoker.buscarJugador(mostrarCartas.get(x), juego.getJugadores());
                System.out.println(" El jugador " + jugador.getNombre() + " tiene: " + jugador.getMano().valorDeLaMano(juego.numeroMayorDelMazo(), true));
                System.out.println(" Sus cartas son: " + jugador.getMano());
            }
            //Println de los ganadores
            //caso 1 ganador
            if (jugadoresGanadores.size() == 1) {
                Jugador ganador = jugadoresGanadores.get(0);
                System.out.println("EL GANADOR ES:");
                System.out.println("El jugador " + ganador.getNombre() + " quien se lleva el pozo de: " + pozo);
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

        public void entreManosAviso ( boolean[] seguirJugando){
            Scanner scan = new Scanner(System.in);

            menu();
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
                    menu();
                }
            }
        }

        private void menu () {
            System.out.println("Si quieres CONTINUAR con la siguiente mano apreta C");
            System.out.println("Si quieres TERMINAR el juego apreta T");
            System.out.println("Si quieres saber cuanto dinero tienes, apreta D");
            System.out.println("Si quieres saber cuanto dinero tiene cada jugador , apreta F");
            System.out.println("Finalmente apreta ENTER para CONFIRMAR");
        }

        public TipoDeJugador claseDeJugador () {
            return TipoDeJugador.JUGADOR_REAL;
        }

        /**
         * @param nroMin representa el nroMin para aceptar
         * @param nroMax representa el nroMax que se puede subir
         * @return devuelve la cant que el jug quiere apostar
         */
        public int scanApuesta (String aceptar, String noSubir,int nroMax, int nroMin){
            Scanner scan = new Scanner(System.in);
            boolean terminarMetodo = false;
            try {
                while (true) {
                    String line = scan.nextLine();
                    if (line.equals(aceptar)) return nroMin;
                    if (line.equals(noSubir)) return this.getDineroApostado();
                    try {
                        int input = Integer.parseInt(line);
                        if (input > nroMax || input < nroMin)
                            System.out.println("El nro debe estar entre " + nroMin + " y " + nroMax + " o " + noSubir + " para no aceptar");
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

        public boolean[] scanCambioCartas (String trueCase, String falseCase){
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
    }

