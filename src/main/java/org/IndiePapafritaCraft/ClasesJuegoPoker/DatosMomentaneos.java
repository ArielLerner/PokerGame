package org.IndiePapafritaCraft.ClasesJuegoPoker;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.EstadisticasDelJuegoPoker.PartesDelJuego;

public class DatosMomentaneos {
    // Es una clase para que el juego guarde ciertos datos pero no está constantemente actualizada
    // asi que no usarla para metodos del jugador
    //El partesDelJuego se esta actualizado en cualquier momento
    private int indexMano;
    private int indexUltimoJugadorQueSubioApuesta;
    private PartesDelJuego parteDelJuego;

    public DatosMomentaneos (int indexDeMano,int indexDelUltimoQueSubio){
        indexMano = indexDeMano;
        indexUltimoJugadorQueSubioApuesta = indexDelUltimoQueSubio;
    }

    public void setIndexUltimoJugadorQueSubioApuesta(int indexUltimoJugadorQueSubioApuesta) {
        this.indexUltimoJugadorQueSubioApuesta = indexUltimoJugadorQueSubioApuesta;
    }

    public void setIndexMano(int indexMano) {
        this.indexMano = indexMano;
    }

    public int getIndexUltimoJugadorQueSubioApuesta() {return indexUltimoJugadorQueSubioApuesta;}


    public int getIndexMano() {
        return indexMano;
    }

    public void setParteDelJuego(PartesDelJuego parteDelJuego) {
        this.parteDelJuego = parteDelJuego;
    }

    public PartesDelJuego getParteDelJuego() {
       if (parteDelJuego==null) return PartesDelJuego.PAGODELUZ;
        return parteDelJuego;
    }
}
