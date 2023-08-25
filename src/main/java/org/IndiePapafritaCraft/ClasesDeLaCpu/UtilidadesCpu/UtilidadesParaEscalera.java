package org.IndiePapafritaCraft.ClasesDeLaCpu.UtilidadesCpu;

import org.IndiePapafritaCraft.Carta;
import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.Mano;
import org.IndiePapafritaCraft.ValoresJuntados.FullProb;
import org.IndiePapafritaCraft.ValoresJuntados.ValorDeMano;
import org.IndiePapafritaCraft.ValoresJuntados.ValorYProbabilidad;

public class UtilidadesParaEscalera {
    public UtilidadesParaEscalera(){
}
    public static ValorYProbabilidad[] posibilidadesDeEscalera(int cantDeNumerosEnMazo, int contador, boolean[] cambioCartas, Mano manoDeJugador, JuegoPoker juego, FullProb[]probsEsc) {
        switch (contador) {
            case 1:
                return ValorYProbabilidad.crearArrayTamanoFullProb();
            case 2:
                return ValorYProbabilidad.crearArrayTamanoFullProb();
            case 3:
                int cartasEnEscalera = UtilidadesParaEscalera.cuantasCartasEnEscalera(contador, cambioCartas, manoDeJugador);
                switch (cartasEnEscalera) {
                    case 1:
                        return probsEsc[0].getProb();
                    case 2:
                        Carta cartaMasChica = manoDeJugador.cartaMenorNoCambiar(cambioCartas);
                        Carta cartaMasGrande = manoDeJugador.cartaMayorNoCambiar(cambioCartas);
                        int distMayor = UtilidadesGenerales.distCartaMayorMazoConMax(cartaMasGrande, 1,juego);
                        int distMenor =UtilidadesGenerales.distCartaMenorMazoConMax(cartaMasChica, 1,juego);
                        int distEntreMasGrandeYmasChica = cartaMasGrande.getNumero() - cartaMasChica.getNumero();
                        if (distEntreMasGrandeYmasChica == 3 && distMenor + distMayor == 2) {
                            return probsEsc[1].getProb();
                        } else {
                            return probsEsc[2].getProb();
                        }
                    case 3:
                        Carta cartaMenor = manoDeJugador.cartaMenorNoCambiar(cambioCartas);
                        Carta cartaMayor = manoDeJugador.cartaMayorNoCambiar(cambioCartas);
                        int numerosParaCarta1 = UtilidadesGenerales.distCartaMayorMazoConMax(cartaMayor, 2,juego) +
                                UtilidadesGenerales.distCartaMenorMazoConMax(cartaMenor, 2,juego);
                        if (numerosParaCarta1 == 4) {
                            return probsEsc[3].getProb();
                        } else if (numerosParaCarta1 == 3) {
                            return probsEsc[4].getProb();
                        } else {
                            return probsEsc[5].getProb();
                        }
                    default:
                        System.out.println("ha habido un error en el método posibilidadesDeEscalera contador= " + contador);
                        break;
                }
            case 4:
                int numeroDeCartasEnEsc = UtilidadesParaEscalera.cuantasCartasEnEscalera(contador, cambioCartas, manoDeJugador);
                int distanciaCartaMenor = UtilidadesGenerales.distCartaMenorMazoConMax(manoDeJugador.cartaMenorNoCambiar(cambioCartas), 1,juego);
                int distanciaCartaMayor = UtilidadesGenerales.distCartaMayorMazoConMax(manoDeJugador.cartaMayorNoCambiar(cambioCartas), 1,juego);
                if (numeroDeCartasEnEsc == 4 &&
                        distanciaCartaMayor + distanciaCartaMenor == 2) {
                    return probsEsc[6].getProb();
                } return probsEsc[7].getProb();
            case 5:  ValorYProbabilidad[] x  = ValorYProbabilidad.crearArrayTamanoFullProb();
            x[4] = new ValorYProbabilidad(1, ValorDeMano.ESCALERA);
            return x;
            default:
                System.out.println("ha habido un error en posibilidadDeEscalera contador=" + contador);
                return ValorYProbabilidad.crearArrayTamanoFullProb();
        }
    }
    /**
     * @param nroDeEsc es un entero que representa el numero de inicio de la escalera
     * @return devuelve un array con true en las que se cambiarian y false en las que no
     */
    public static boolean[] cambiarCartasParaEscalera(int nroDeEsc, Mano manoDeJugador) {
        boolean[] cambioCartas = new boolean[]{true, true, true, true, true};
        for (int recorrerEsc = 0; recorrerEsc < 5; recorrerEsc++) {
            for (int nroDeCarta = 0; nroDeCarta < 5; nroDeCarta++) {
                int posEsc = nroDeEsc + recorrerEsc;
                if (posEsc == manoDeJugador.getCard(nroDeCarta).getNumero()) {
                    cambioCartas[nroDeCarta] = false;
                    break;
                }
            }
        }
        return cambioCartas;
    }
    /**
     * ejemplo: 1,2,3,5 daria 3 ya que estos estan "pegados"
     *
     * @param contador     debe llevar cuantas cartas hay para formar escalera ejemplo 3 de 5
     * @param cambioCartas debe llevar true en las cartas a cambiar
     * @return devuelve un int que dice cuantas cartas hay en escalera
     */
    public static int cuantasCartasEnEscalera(int contador, boolean[] cambioCartas,Mano manoDeJugador) {
        Carta[] cartasDeEscalera = new Carta[contador];
        int cuenta = 0;
        for (int x = 0; x < 5; x++) {
            if (cambioCartas[x] == false) {
                cartasDeEscalera[cuenta] = manoDeJugador.getCard(x);
                cuenta++;
            }
        }
        Carta.ordenar(cartasDeEscalera);
        for (int tamanoEsc = contador; tamanoEsc > 0; tamanoEsc--) {
            int ultimaPosicion = contador - tamanoEsc;
            for (int posInicial = 0; posInicial <= ultimaPosicion; posInicial++) {
                cuenta = 0;
                for (int posActual = 0; posActual < tamanoEsc; posActual++) {
                    if (cartasDeEscalera[posInicial].getNumero() == cartasDeEscalera[posInicial + posActual].getNumero() - posActual) {
                        cuenta++;
                    }
                }
                if (cuenta == tamanoEsc) {
                    return cuenta;

                }
            }
        }
        return -1;
    }
    /**
     * devuelve un int [] donde guarda el conteo de cuantas cartas tiene para cada posible escarlera
     * ejemplo : en la pos 0 va a guardar cuantas cartas hay para la escalera 0,1,2,3,4
     */
    public static int[] memorizarResultadosDeCadaEscaleras(JuegoPoker juego, Mano manoDeJugador) {
        int escalerasTotales = juego.cantDeNumerosDelMazo() - 4;
        int[] memorizarResultados = new int[escalerasTotales];
        for (int nDeEscalera = 0; nDeEscalera < escalerasTotales; nDeEscalera++) {
            int cuentaMomentanea = 0;
            for (int posEscalera = 0; posEscalera < 5; posEscalera++) {
                int posicionBuscada = nDeEscalera + posEscalera;
                for (int a = 0; a < 5; a++) {
                    if (manoDeJugador.getCard(a).getNumero() == posicionBuscada) {
                        cuentaMomentanea++;
                        break;
                    }
                }
            }
            memorizarResultados[nDeEscalera] = cuentaMomentanea;
        }
        return memorizarResultados;
    }
}
