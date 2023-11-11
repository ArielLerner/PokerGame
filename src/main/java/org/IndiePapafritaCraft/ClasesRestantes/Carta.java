package org.IndiePapafritaCraft.ClasesRestantes;

public class Carta {
    private final int numero;
    private final int palo;
    public Carta(int numeroDeCarta,int paloDeCarta){
        if (numeroDeCarta>12 || paloDeCarta>3){
            System.out.println("el creador de Cartas escribiÃ³ una carta invÃ¡lida");
        }
        numero=numeroDeCarta;
        palo=paloDeCarta;
    }
    /**
     Pasa un objeto de la clase Carta a string
     */
    String[] numeros = {"DOS","TRES","CUATRO","CINCO","SEIS","SIETE","OCHO","NUEVE","DIEZ","J","Q","K","AS"};
    String palos[] = {"trebol","picas","corazon","diamante"};

    public  String toString(){
        return ""+ numeros[this.numero].toLowerCase()+" "+ "de " + " " + palos[this.palo];
    }
    /**
     devuelve el numero de la carta
     */
    public int getNumero() {
        return numero;
    }
    /**
     devuelve el palo de la carta
     */
    public int getPalo() {
        return palo;
    }
    /**
     devuelve la posicion inicial de la carta en el mazo, su valor varia entre 0 y 51
     */
    public int posDeCartaEnMazo(){
        return numero*4+palo;
    }

    public static boolean cartasEnEscalera(Carta[] cartas){
        Carta.ordenar(cartas);
        for (int x=0;x< cartas.length;x++){
            if (cartas[0].getNumero()!=cartas[x].getNumero()+x)
                return false;
        }
        return true;
    }
    public static void ordenar(Carta[] cartas){
        Carta[] copia=new Carta[cartas.length];
        for (int x=0;x<cartas.length;x++){
            copia[x]=cartas[x];
        }

        for (int x=0;x<cartas.length;x++){
            int contador=0;
            for (int y=0;y<cartas.length;y++){
                if (copia[x].posDeCartaEnMazo()>copia[y].posDeCartaEnMazo()) {
                    contador++;
                }
            }
            cartas[contador]=copia[x];
        }

    }
}


