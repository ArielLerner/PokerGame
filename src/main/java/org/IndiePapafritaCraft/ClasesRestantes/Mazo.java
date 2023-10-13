package org.IndiePapafritaCraft.ClasesRestantes;

import org.IndiePapafritaCraft.ClasesRestantes.Carta;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;

public class Mazo {
    private Carta[] mazo;
    /**
     Crea un mazo con todas las cartas, los numeros van del 0 al 12 y los palos del 0 al 3
     */
    public Mazo() {
        Carta[] mazo2 = new Carta[52];
        for (int n = 0; n < 13; n++) {
            for (int p = 0; p < 4; p++) {
                mazo2[4 * n + p] = new Carta(n, p);
            }
        }
        mazo= mazo2;
    }

    /**
     * @param manoDeJugador crea un mazo sin las cartas de la mano
     */
    public Mazo(Mano manoDeJugador) {
         int contadorDeCartasDeLaMano=0;
        Carta[] mazo2 = new Carta[47];
        for (int n = 0; n < 13; n++) {
            for (int p = 0; p < 4; p++) {
                if (manoDeJugador.chequearSiUnaCartaEstaEnUnaMano(new Carta(n,p))){
                    contadorDeCartasDeLaMano++;
                    continue;
                }
                mazo2[4 * n + p - contadorDeCartasDeLaMano] = new Carta(n, p);
            }
        }
        mazo= mazo2;
    }
    /**
     mezcla el mazo
     */
    public void mezclar(){
        Carta cartaMomentanea;
        int numeroRandom= 0;
        for (int x=0; x<52;x++){
            numeroRandom=x+(int)(Math.random()*(52-x));
            cartaMomentanea=this.mazo[numeroRandom];
            this.mazo[numeroRandom]= this.mazo[x];
            this.mazo[x]=cartaMomentanea;
        }
    }
    /**
     te devuelve la carta que hay en la posicion seleccionada
     */
    public Carta getCarta(int posicion){
        return this.mazo[posicion];
    }
    public Carta[] getMazo(){
        return this.mazo;
    }
    public String toString(){
        int b= this.mazo.length;
        String x = "este mazo esta compuesto por:";
        for (int a=0;a<b;a++ ){
            if (this.mazo[a]==null){
                System.out.print("posVacia"+ " ");
            }
            else
            x = x +"  " +  this.mazo[a].toString();
        }
        return x;
    }
    /**
    devuelve un array de cartas del mazo empezando por el comienzo y terminando en el fin
     */
    public  Carta[] division(int comienzo, int cantidad ){
        Carta[] x=new Carta[cantidad];
        for (int a=0;a<cantidad;a++){
            x[a]=mazo[a+comienzo];
}
        return x;
    }
    public Mano sacarManoDelMazo(int numeroDeJugador){
        return  new Mano(this.division(9*numeroDeJugador,5));
    }

    /**
     * @return saca 4 cartas del mazo que no se repartieron todavia
     */
    public Carta[] sacarCartasParaCambiar(int numeroDeJugador){
        return this.division((9*numeroDeJugador)+5,4);
    }
    public int numeroMayor(){
       return  (mazo.length/4)-1;
    }
    public int numeroMenor(){
        return 0;
    }
}


