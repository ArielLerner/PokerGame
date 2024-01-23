package org.IndiePapafritaCraft.ClasesRestantes;

import org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.utilidades.UtilidadesGenerales;
import org.IndiePapafritaCraft.ClasesRestantes.Carta;
import org.IndiePapafritaCraft.ValoresJuntados.ValorDeMano;

public class Mano {
    private Carta[] mano;

    public Mano(Carta[] mano2) {
        mano = new Carta[5];
        for (int x = 0; x < 5; x++) {
            mano[x] = mano2[x];
        }
        this.ordenar();
    }

    public Carta[] getCartaArray() {
        return mano;
    }

    /**
     * en este constructor se pasa solo el numero y todas las cartas son asignadas al palo 0, no repetir numeros
     */
    public Mano(int c1, int c2, int c3, int c4, int c5) {
        mano = new Carta[]{
                new Carta(c1, 0),
                new Carta(c2, 0),
                new Carta(c3, 0),
                new Carta(c4, 0),
                new Carta(c5, 0)};
        this.ordenar();

    }

    /**
     * devuelve el string de una mano
     */
    public String toString() {
        String a = "| ";
        for (int x = 0; x < 5; x++) {
            // a = a + "C" + (x) + ": " + this.mano[x].toString() + "  ";
            a = a + this.mano[x].toString() + "  " + "| " ;

        }
        return a;
    }

    /**
     * devuelve una carta de la mano
     */
    public Carta getCard(int numeroCarta) {
        return this.mano[numeroCarta];
    }

    /**
     * ordena la mano del menor(posicion 0) al mayor(posicion 4)
     */
    public void ordenar() {
        Carta[] copia = new Carta[5];
        for (int x = 0; x < 5; x++) {
            copia[x] = this.mano[x];
        }

        for (int x = 0; x < 5; x++) {
            int contador = 0;
            for (int y = 0; y < 5; y++) {
                if (copia[x].posDeCartaEnMazo() > copia[y].posDeCartaEnMazo()) {
                    contador++;
                }
            }
            this.mano[contador] = copia[x];
        }

    }

    /**
     * @param cambioCartas con las cartas que se van a cambiar
     * @return devuelve null si no encuentra cartas que no se vayan a cambiar
     */
    public Carta cartaMenorNoCambiar(boolean[] cambioCartas) {
        for (int y = 0; y < 5; y++) {
            if (cambioCartas[y] == false) {
                return this.getCard(y);
            }
        }
        return null;
    }

    /**
     * @param cambioCartas con las cartas que se van a cambiar
     * @return devuelve null si no encuentra cartas que no se vayan a cambiar
     */
    public Carta cartaMayorNoCambiar(boolean[] cambioCartas) {
        for (int x = 4; x >= 0; x--) {
            if (cambioCartas[x] == false) {
                return this.getCard(x);
            }
        }
        return null;
    }

    /**
     * @param posicion      debe ser la posicion de la carta en la mano
     * @param cartaAcambiar debe tener la carta
     */
    public void cambiarUnaCarta(int posicion, Carta cartaAcambiar) {
        mano[posicion] = cartaAcambiar;
    }

    public boolean chequearSiUnaCartaEstaEnUnaMano(Carta x) {
        for (int a = 0; a < 5; a++) {
            if (x.getNumero() == this.getCard(a).getNumero()
                    &&
                    x.getPalo() == this.getCard(a).getPalo()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Funciona para manos ordenadas
     */
    public ValorDeMano valorDeLaMano(int nroMasAltoDelMazo,  boolean manoOrdenada) {
        boolean color = this.color();
        boolean escalera;
        int nroMasAltoDeLaMano;
        if (manoOrdenada == true) {
            escalera = this.escalera();
            nroMasAltoDeLaMano = mano[4].getNumero();
        } else {
            nroMasAltoDeLaMano = this.nroMasAlto();
            escalera = this.escaleraManoNoOrdenada(nroMasAltoDeLaMano);
        }
        if (color == true && escalera == true) {
            if (nroMasAltoDeLaMano == nroMasAltoDelMazo) {
                return ValorDeMano.ESCALERAREAL;
            } else return ValorDeMano.ESCALERADECOLOR;
        }
        if (color == true)
            return ValorDeMano.COLOR;
        if (escalera == true)
            return ValorDeMano.ESCALERA;
        boolean[] indexesRepetidos = UtilidadesGenerales.IndexDeNumerosQueSeRepiten(this);
        int parDobleOfull = this.ParDobleFull(indexesRepetidos);
        if (parDobleOfull == 2) {
            return ValorDeMano.FULL;
        }
        if (parDobleOfull == 1) {
            return ValorDeMano.DOBLEPAR;
        }
        int cantCartIguales = this.cantDeCartasIguales(indexesRepetidos);
        switch (cantCartIguales) {
            case 4:
                return ValorDeMano.POKER;
            case 3:
                return ValorDeMano.PIERNA;
            case 2:
                return ValorDeMano.PAR;
            case 0:
                return ValorDeMano.NADA;
        }
        System.out.println("ha habido un error en el metodo ValorDeLaMano");
        return ValorDeMano.NADA;
    }
    private boolean color (){
        if (    mano[0].getPalo()== mano[1].getPalo() &&
                mano[0].getPalo()== mano[2].getPalo() &&
                mano[0].getPalo()== mano[3].getPalo() &&
                mano[0].getPalo()== mano[4].getPalo()
        ){
            return true;
        }
        else return false;
    }
    private boolean escalera(){
        if (    mano[0].getNumero()+1== mano[1].getNumero() &&
                mano[1].getNumero()+1== mano[2].getNumero() &&
                mano[2].getNumero()+1== mano[3].getNumero() &&
                mano[3].getNumero()+1== mano[4].getNumero()
        ){
            return true;
        }
        else return false;
    }
    private boolean escaleraManoNoOrdenada(int nroMasAltoDeLaMano){
        int nrosEncontrados=0;
        for (int x=nroMasAltoDeLaMano-1;x>nroMasAltoDeLaMano-5;x--){
            if (    mano[0].getNumero()== x||
                    mano[1].getNumero()== x||
                    mano[2].getNumero()== x||
                    mano[3].getNumero()== x||
                    mano[4].getNumero()== x
            ){
                nrosEncontrados++;
            }
        }
        if (nrosEncontrados==4){
            return true;
        }
        return false;
    }

    /**
     *
     * @return si devuelve 2 es full, si devuelve 1 es par doble, si devuelve 0 no es nada
     */
    private int ParDobleFull( boolean[] nrosRepetidos){
        int nroDeParesPiernaPoker = UtilidadesGenerales.nroDeParesPiernasPokerQueHayEnLaMano(this,nrosRepetidos);
        if (nroDeParesPiernaPoker==2){
            int contador=0;
            for (int x=0;x<5;x++){
                if (nrosRepetidos[x]==true){
                    contador++;
                }
            }
            switch (contador){
                case 4: return 1;
                case 5: return 2;
                default:
                    System.out.println("ha habido un error en el mÃ©todo que decide el valor de la mano");
                    return -1;
            }
        }
        else return 0;
    }

    /**
     * @return devuelve la cantDeCartasIguales
     */
    private int cantDeCartasIguales( boolean[] indexesNrosRepetidos){
        int contador=0;
        for (int x=0;x<5;x++){
            if (indexesNrosRepetidos[x]== true){
                contador++;
            }
        }
        return contador;
    }
    public int nroMasAlto(){
        int nroMasGrande=-1;
        int []nros = new int [5];
        for (int x=0;x<5;x++){
            nros[x] = this.mano[x].getNumero();
            nroMasGrande =  UtilidadesGenerales.nroMasGrandeEnUnArray(nros);
        }
        return nroMasGrande;
    }
    public int getNumero(int posDeCarta){
        return this.getCard(posDeCarta).getNumero();
    }
}
