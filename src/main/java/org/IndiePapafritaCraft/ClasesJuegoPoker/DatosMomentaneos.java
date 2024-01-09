package org.IndiePapafritaCraft.ClasesJuegoPoker;

public class DatosMomentaneos {
    // Es una clase para que el juego guarde ciertos datos pero no está constantemente actualizada
    // asi que no usarla para metodos del jugador
    //El partesDelJuego se esta actualizado en cualquier momento
    private int indexMano = -1;
    private int indexUltimoJugadorQueSubioApuesta;
    private PartesDelJuego parteDelJuego;

    private double toleracionDeEstrategia;
    public double getToleracionDeEstrategia() {
        return toleracionDeEstrategia;
    }

    public void setBalanceInicial(int balanceInicial) {
        this.balanceInicial = balanceInicial;
    }

    public int getBalanceInicial() {
        return balanceInicial;
    }

    private int balanceInicial; //Es el balance con el que empiezan los jugadores

    public DatosMomentaneos(int indexDelUltimoQueSubio, double toleracionDeEstrategia) {
        indexUltimoJugadorQueSubioApuesta = indexDelUltimoQueSubio;
    }

    public void setIndexUltimoJugadorQueSubioApuesta(int indexUltimoJugadorQueSubioApuesta) {
        this.indexUltimoJugadorQueSubioApuesta = indexUltimoJugadorQueSubioApuesta;
    }

    public void setIndexMano(int indexMano) {
        this.indexMano = indexMano;
    }
    public void actualizarIndexMano(JuegoPoker juego){
        if (indexMano+1 == juego.getJugadores().length){ //caso en que se vaya de rango
            indexMano = 0;
        }
        else indexMano++;
    }
    public void actualizarOinicializarIndexMano(JuegoPoker juego){
        if (indexMano== -1 ) { //caso en el que no se haya inicializado
            indexMano = juego.sortearIndexMano();
        }
        else  actualizarIndexMano(juego);
    }

    public int getIndexUltimoJugadorQueSubioApuesta() {
        return indexUltimoJugadorQueSubioApuesta;
    }


    public int getIndexMano() {
        return indexMano;
    }

    public void setParteDelJuego(PartesDelJuego parteDelJuego) {
        this.parteDelJuego = parteDelJuego;
    }

    public PartesDelJuego getParteDelJuego() {
        if (parteDelJuego == null) return PartesDelJuego.PAGODELUZ;
        return parteDelJuego;
    }
}
