package org.IndiePapafritaCraft.clasesDePruebas;

import org.IndiePapafritaCraft.ClasesJuegoPoker.JuegoPoker;
import org.IndiePapafritaCraft.ClasesRestantes.Carta;
import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ClasesRestantes.Mazo;
import org.IndiePapafritaCraft.ValoresJuntados.ValorDeMano;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestearProbabilidades {
    public static double[] probarTodasLasPosibilidades(Mano manoDeJugador, JuegoPoker juego, boolean[] cambioCartas, boolean escribirProbabilidades, boolean escribirNombresDeValores, boolean escribirProbsEntre0y1) {
        //parametros conteo de casos
        int[] indexesDeCartasACambiar = TestearProbabilidades.indicesCartasQueSeVanACambiar(cambioCartas);
        int nroDeCartasACambiar = indexesDeCartasACambiar.length;
        int gradoDeLaFuncion = 0;
        int nroMasAltoDelMazo = juego.numeroMayorDelMazo();
        double[] conteoDeCasos = new double[10];
        Mazo mazo = new Mazo(manoDeJugador);
        TestearProbabilidades.conteoDeCasos(manoDeJugador, indexesDeCartasACambiar, nroDeCartasACambiar, gradoDeLaFuncion, nroMasAltoDelMazo, conteoDeCasos, mazo);
        if (escribirProbsEntre0y1==true){
            TestearProbabilidades.cambiarCasosPorProbs(conteoDeCasos);
        }
        if (escribirProbabilidades == true) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\clasesDePruebas\\EscritorDeProbabilidades.txt"));
                for (int recorrer = 0; recorrer < conteoDeCasos.length; recorrer++) {
                    if (escribirNombresDeValores == true) {
                        writer.write(ValorDeMano.getNameConOrdinal(recorrer) + ":  " + conteoDeCasos[recorrer]);
                    } else {
                        writer.write("" + conteoDeCasos[recorrer]);
                    }
                    writer.newLine();
                }
                writer.write("el numero de casos totales es de:  " + TestearProbabilidades.casosTotales(conteoDeCasos));
                writer.close();
            } catch (IOException x) {
                System.out.println("ha habido un error en la escritura");
            }
        }
        return conteoDeCasos;

    }

    /**
     * @return devuelve un el numero mayor de cazrtas iguales de una mano
     */
    private static int ResultadoDeLaMano(Mano mano) {
        int contador = 0;
        for (int x = 0; x < 5; x++) {
            int contadorMomentaneo = 0;

            for (int y = 0; y < 5; y++) {
                if (mano.getCard(x).getNumero() == mano.getCard(y).getNumero()) {
                    contadorMomentaneo++;
                }
            }
            if (contadorMomentaneo > contador) {
                contador = contadorMomentaneo;
            }
        }
        return contador;
    }

    private static int[] indicesCartasQueSeVanACambiar(boolean[] cambioCartas) {
        int contador = 0;
        for (int x = 0; x < 5; x++) {
            if (cambioCartas[x] == true) {
                contador++;
            }
        }
        int contadorDeCartas = 0;
        int[] indexesDeCartasQueSeCambian = new int[contador];
        for (int x = 0; x < 5; x++) {
            if (cambioCartas[x] == true) {
                indexesDeCartasQueSeCambian[contadorDeCartas] = x;
                contadorDeCartas++;
            }
        }
        return indexesDeCartasQueSeCambian;
    }

    /**
     * @param manoDeJugador
     * @param indexesDeCartasACambiar
     */
    private static void conteoDeCasos(Mano manoDeJugador, int[] indexesDeCartasACambiar, int nroDeCartasACambiar, int gradoDeLaFuncion, int nroMasAltoDelMazo, double[] conteoDeCasos, Mazo mazo) {
        int mazoLength = mazo.getMazo().length;

        for (int a = 0; a < mazoLength; a++) {
            if (TestearProbabilidades.chequearQueLaCartaNoSeRepita(mazo.getCarta(a), indexesDeCartasACambiar, manoDeJugador) == false) {
                manoDeJugador.cambiarUnaCarta(indexesDeCartasACambiar[gradoDeLaFuncion], mazo.getCarta(a));
                if (gradoDeLaFuncion == nroDeCartasACambiar - 1)
                    conteoDeCasos[manoDeJugador.valorDeLaMano(nroMasAltoDelMazo, false).ordinal()]++;
                else
                    TestearProbabilidades.conteoDeCasos(manoDeJugador, indexesDeCartasACambiar, nroDeCartasACambiar, gradoDeLaFuncion + 1, nroMasAltoDelMazo, conteoDeCasos, mazo);

            }
        }
        manoDeJugador.cambiarUnaCarta(indexesDeCartasACambiar[gradoDeLaFuncion], new Carta(-1, -1));

    }

    private static boolean chequearQueLaCartaNoSeRepita(Carta a, int[] indexesDeCartasACambiar, Mano manoDeJugador) {
        int indexLenght = indexesDeCartasACambiar.length;
        for (int x = 0; x < indexLenght; x++) {
            if (manoDeJugador.getCard(indexesDeCartasACambiar[x]).getNumero() == a.getNumero()
                    &&
                    manoDeJugador.getCard(indexesDeCartasACambiar[x]).getPalo() == a.getPalo()) {
                return true;
            }
        }
        return false;
    }

    private static double casosTotales(double[] conteoDeCasos) {
        double sumatoria = 0;
        for (int x = 0; x < conteoDeCasos.length; x++) {
            sumatoria = sumatoria + conteoDeCasos[x];
        }
        return sumatoria;
    }
    private static void cambiarCasosPorProbs(double[] conteoDeCasos){
        double casosTotales = TestearProbabilidades.casosTotales(conteoDeCasos);
        for (int x=0; x< conteoDeCasos.length;x++){
            conteoDeCasos[x] = conteoDeCasos[x]/casosTotales;
        }
    }
}
//for (int x = 0; x < nroMasAltoDelMazo; x++) {
//                if (manoDeJugador.getCard(indexesDeCartasACambiar[gradoDeLaFuncion]).getNumero() == nroMasAltoDelMazo) {
//                    conteoDeCasos[TestearProbabilidades.nadaParPiernaOPoker(manoDeJugador)]++;
//                    manoDeJugador.cambiarUnaCarta(indexesDeCartasACambiar[gradoDeLaFuncion], new Carta(0, 0));
//
//                } else {
//                    conteoDeCasos[TestearProbabilidades.nadaParPiernaOPoker(manoDeJugador)]++;
//                    manoDeJugador.cambiarUnaCarta(indexesDeCartasACambiar[gradoDeLaFuncion], new Carta(manoDeJugador.getCard(indexesDeCartasACambiar[gradoDeLaFuncion]).getNumero() + 1, 0));
//                }
//            }
//for (int x = 0; x <= nroMasAltoDelMazo; x++) {
//                TestearProbabilidades.conteoDeCasos(manoDeJugador, indexesDeCartasACambiar, nroDeCartasACambiar, gradoDeLaFuncion + 1, nroMasAltoDelMazo, conteoDeCasos);
//                if (manoDeJugador.getCard(indexesDeCartasACambiar[gradoDeLaFuncion]).getNumero() == nroMasAltoDelMazo) {
//                    manoDeJugador.cambiarUnaCarta(indexesDeCartasACambiar[gradoDeLaFuncion], new Carta(0, 0));
//                } else {
//                    manoDeJugador.cambiarUnaCarta(indexesDeCartasACambiar[gradoDeLaFuncion], new Carta(manoDeJugador.getCard(indexesDeCartasACambiar[gradoDeLaFuncion]).getNumero() + 1, 0));
//                }
//            }
/*
else {
        for (int b = 0; b < mazoLength; b++) {
        if (TestearProbabilidades.chequearQueLaCartaNoSeRepita(mazo.getCarta(b), indexesDeCartasACambiar, manoDeJugador) == false) {
        manoDeJugador.cambiarUnaCarta(indexesDeCartasACambiar[gradoDeLaFuncion], mazo.getCarta(b));
        //System.out.println(manoDeJugador+" "+ gradoDeLaFuncion);
        TestearProbabilidades.conteoDeCasos(manoDeJugador, indexesDeCartasACambiar, nroDeCartasACambiar, gradoDeLaFuncion+1, nroMasAltoDelMazo, conteoDeCasos, mazo);
        }
        else
        continue;
        }
        manoDeJugador.cambiarUnaCarta(indexesDeCartasACambiar[gradoDeLaFuncion], new Carta(-1, -1));
        }

 */