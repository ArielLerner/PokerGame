package org.IndiePapafritaCraft.ClasesJuegoPoker;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.EstadisticasDelJuegoPoker.PartesDelJuego;
import org.IndiePapafritaCraft.ClasesRestantes.BalanceDeLaRonda;
import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.JugadorMaquina;
import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorReal.JugadorReal;
import org.IndiePapafritaCraft.ClasesDeJugador.Jugador;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ClasesRestantes.Mazo;
import org.IndiePapafritaCraft.ValoresJuntados.TipoDeJugador;

public class JuegoPoker {
    private Mazo mazo;
    private int precioDeLuz;
    private Jugador[] jugadores;
    private DatosMomentaneos datos;
    private  int balanceInicial;
    public void jugar(){
        boolean seguirJugando = true;
        while (seguirJugando==true ){
            UtilidadesPartesDelJuego.jugarMano(this);
            for (Jugador jugador : jugadores) jugador.entreManosAviso(seguirJugando);
        }
    }
    public JuegoPoker(Mazo mazoDelJuego, int precioDeLuz2, int balanceInicial) {
        mazo = mazoDelJuego;
        precioDeLuz= precioDeLuz2;
        datos = new DatosMomentaneos(0,0);
    }

    public void setJugadores(Jugador[] jugadores2){
        jugadores=jugadores2;
    }
    /**
     * @return Crea el juego , con el mazo, el balance de la ronda y los jugadores
     */
    public static JuegoPoker crearJuegoTerminal(double toleracionEstrategias){
        //hacer el juego
        TipoDeJugador[] tipoDeJugadores = UtilidadesJuegoPokerTerminal.scanNroDeJugadores();
        int nroDeBalanceInicial = UtilidadesJuegoPokerTerminal.scanBalanceInicial();
        int precioDeLuz = UtilidadesJuegoPokerTerminal.scanPrecioDeLuz(nroDeBalanceInicial);
        int nroDeJugadores = tipoDeJugadores.length;
        String[] nombres  = UtilidadesJuegoPokerTerminal.scanNombreDeJugadores(nroDeJugadores);
        Mazo mazo1  = JuegoPoker.crearMazo(nroDeJugadores);
        JuegoPoker juego = new JuegoPoker(mazo1,precioDeLuz, nroDeBalanceInicial);
        //Hacer los jugadores
        Mano[] manos = juego.crearManosDeJugadores(nroDeJugadores);
        Jugador[] jugadores = juego.crearJugadores(nroDeJugadores,tipoDeJugadores,manos,nroDeBalanceInicial,nombres,toleracionEstrategias);
        juego.setJugadores(jugadores);
        return juego;
    }

    public static Mazo crearMazo(int nroDeJugadores){
        return new Mazo();
    }
    /**
     * crea las manos de los jugadores
     */
    public Mano[] crearManosDeJugadores(int nroDeJugadores) {
        mazo.mezclar();
        Mano[] manosDeJugadores = new Mano[nroDeJugadores];
        for (int x = 0; x < nroDeJugadores; x++) {
            mazo.sacarManoDelMazo(x);
        }
        return manosDeJugadores;
    }
    /**
     * crea el balance de los jugadores
     */
    public static BalanceDeLaRonda creadorDeBalanceDeRonda(int nroDeJugadores, int nroDeBalanceInicial){
        int[] pozo = new int[nroDeJugadores];
        for (int x = 0; x < nroDeJugadores; x++) {
            pozo[x] = 0;
        }
        BalanceDeLaRonda finanzas = new BalanceDeLaRonda(nroDeBalanceInicial, nroDeJugadores, pozo);
        return finanzas;
    }
    /**
     * crea el array de jugadores
     */
    public Jugador[] crearJugadores(int nroDeJugadores, TipoDeJugador[] tipoDeJugadores, Mano[] manosDeJugadores ,int balanceInicial, String[] nombres,double toleracionEstrategias){
        Jugador[] jugadores = new Jugador[nroDeJugadores];
        for (int posActual = 0; posActual < nroDeJugadores; posActual++) {
            if (tipoDeJugadores[posActual] == TipoDeJugador.JUGADOR_DE_LA_MAQUINA) {
                jugadores[posActual] = new JugadorMaquina(manosDeJugadores[posActual],balanceInicial,this,nombres[posActual],toleracionEstrategias);
            }
            if (tipoDeJugadores[posActual] == TipoDeJugador.JUGADOR_REAL) {
                jugadores[posActual] = new JugadorReal(manosDeJugadores[posActual],balanceInicial, this, nombres[posActual]);
            }
        }
        return jugadores;
    }

    public int numeroMayorDelMazo() {return mazo.numeroMayor();}
    public int numeroMenorDelMazo() {return mazo.numeroMenor();}
    public int cantDeNumerosDelMazo() {return mazo.getMazo().length / 4;}
    public int cantDeCartasDelMazo() {return mazo.getMazo().length;}
    public int cantDeJugadores() {return 2;}
    public Jugador[] getJugadores() {return jugadores;}
    public int getPrecioDeLuz() {return precioDeLuz;}
    public Mazo getMazo() {return mazo;}
    public void  setMazo(Mazo mazo){this.mazo=mazo;}
    public DatosMomentaneos getDatos() {return datos;}
    public void setDatos(DatosMomentaneos datos) {this.datos = datos;}
    public Jugador getUltimoJugadorEnSubirApuesta(){return this.jugadores[datos.getIndexUltimoJugadorQueSubioApuesta()];}

    public int getBalanceInicial() {return balanceInicial;}

    public int getMayorApuesta(){
        int apuestaMasAlta = jugadores[0].getDineroApostado();
        for (int x=1;x<jugadores.length;x++){
            if (jugadores[x].getDineroApostado() > apuestaMasAlta)
                apuestaMasAlta = jugadores[x].getDineroApostado();
        }
        return apuestaMasAlta;
    }
    public PartesDelJuego parteDelJuego(){
        return this.getDatos().getParteDelJuego();
    }
}
