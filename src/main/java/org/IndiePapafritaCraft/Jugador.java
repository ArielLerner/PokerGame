package org.IndiePapafritaCraft;

import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;

public abstract class Jugador {
    protected JuegoPoker juego;
    protected Mano manoDeJugador;
    protected int finanzas;
    protected int dineroApostado;
    protected  boolean estarEnElJuego;

    protected String nombre;

    public Jugador(JuegoPoker juego2 , Mano mano, int balanceInicial,String nombreDelJugador ){
        juego = juego2;
        manoDeJugador=mano;
        finanzas = balanceInicial;
        dineroApostado=0;
        estarEnElJuego = false;
        nombre = nombreDelJugador;

    }
  public abstract  void  verApuesta();
    public abstract boolean[] cambioCartas();
    //Métodos de aviso // son metodos que le permiten al jugador pedir informacion uando pasa algo en el juego // el nombre refiere al momento del juego
    // sirven prioritariamente para mostrar la interfase de los jugadores reales
    public abstract void pagarLuzAviso();
    public abstract void repartirCartasAviso();
    /**
     * este metodo devuelve cuando un jugador usa el metodo ver apuesta, puede subir no querer o bajar
     */
    public abstract void jugadorVeApuestaAviso(Jugador x);
    /**
     * este metodo devuelve un aviso dsps de que un jugador haya usado el metodo verApuesta
     */
    public abstract void cambioCartasAviso();
    public abstract void finalDeJuegoAviso();
    public abstract void entreManosAviso();
    //metodos normales
    public Mano getManoDeJugador() {return manoDeJugador;}
    public void DarMano(Mano manoDeJugador2){
        manoDeJugador = manoDeJugador2;
    }
    public int getFinanzas() {return finanzas;}
    public int getIndexEnJugador(){
        for (int x=0;x<juego.getJugadores().length;x++){
            if (this==juego.getJugadores()[x]){
                return x;
            }
        }
        System.out.println("no se ha encontrado el index del jugador metodoGetFinanzas clase:jugador");
        return -1;
    }
    public int getDineroApostado() {return dineroApostado;}
    public JuegoPoker getJuego() {return juego;}
    public boolean getEstarEnElJuego() {return estarEnElJuego;}
    public void setEstarEnElJuegoFalse(){this.estarEnElJuego=false;}
    public void restarXaLasFinanzas(int x){this.finanzas = this.finanzas-x;}
    public void sumarXalDineroApostado(int x){this.dineroApostado=dineroApostado+x;}
    public void setMano(Mano mano){this.manoDeJugador=mano;}
    public void cambiarUnaCarta(int posicion, Carta carta){manoDeJugador.cambiarUnaCarta(posicion,carta);}
    public void setDineroApostadoEn0(){this.dineroApostado=0;}
    public void sumarXparaFinanzas(int x){finanzas=finanzas+x;}

    /**
     * Descuenta de finanzas un nro y lo apuesta
     */
    public void apostarXcant(int x){
        this.restarXaLasFinanzas(x);
        this.sumarXalDineroApostado(x);
    }

    /**
     * Este metodo apuesta la luz y deja en true el boolean estarEnElJuego
     */
    public void pagarLaLuz(int precioDeLaLuz){
        this.apostarXcant(precioDeLaLuz);
        this.estarEnElJuego=true;
    }

    /**
     *  Permite saber si un jugador sube,iguala o no acepta la apuesta
     * NO DEVUELVE LA CANTIDAD APOSTADAD
     @return devuelve 0 SI NO ACEPTA la APUESTA, devuelve 1 SI ACEPTA la APUESTA, devuelve 2 SI SUBE la APUESTA
     */
    public int queHizoEnLaApuesta( JuegoPoker juego){
        int mayorApuesta = juego.getMayorApuesta();
        int cantDeJugQueApostaronMayorApuesta = Jugador.cantDeJugQueApostaronX(mayorApuesta, juego);
        if (mayorApuesta>this.getDineroApostado()) return 0;
        if (cantDeJugQueApostaronMayorApuesta==1)return 2;
        else return 1;
    }
    /**
     * devuelve la cantidad de jugadores que apostaron x
     */
    private static int cantDeJugQueApostaronX( int x, JuegoPoker juego){
        int contador = 0;
        for (int a=0; a < juego.getJugadores().length;a++){
            if (juego.getJugadores()[a].getDineroApostado() == x ) contador++;
        }
        return contador;
    }
}
