package org.IndiePapafritaCraft;

public class BalanceDeLaRonda {
    private int[] balanceDeLosJugadores;
    private int[] pozo;
    public BalanceDeLaRonda(int balanceEnElComienzo, int numeroDeJugadores, int[]pozo2 ){
        int[]x= new int[numeroDeJugadores];
        for (int y=0;y<numeroDeJugadores;y++){
            x[y]= balanceEnElComienzo;
        }
        balanceDeLosJugadores = x;
        pozo=pozo2;
    }
    /**
     devuelve el balance del numero de jugador seleccionado
     */
    public int getBalanceJugador(int numeroJugador) {
        return balanceDeLosJugadores[ numeroJugador];
    }

    public int[] getPozo() {
        return pozo;
    }

    /**
     pasa el balance del jugador seleccionado a string
     */
    public String toString(int numeroJugador){
        return "El jugador "+ numeroJugador +" tiene: " + balanceDeLosJugadores[numeroJugador];
    }
}
