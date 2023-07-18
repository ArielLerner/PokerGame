package org.IndiePapafritaCraft.ClasesJuegoPoker;

import org.IndiePapafritaCraft.Jugador;

public class DatosMomentaneos {
    private int indexMano;
    private int indexUltimoJugadorQueSubioApuesta;

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
}
