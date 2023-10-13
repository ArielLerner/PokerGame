package org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.utilidades;

import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ValoresJuntados.ValorDeMano;
import org.IndiePapafritaCraft.ValoresJuntados.ValorYProbabilidad;

import java.io.BufferedReader;
import java.io.FileReader;

public class UtilidadesParaParPiernaPoker {
    public static boolean[] cambiarCartasParaPoker(Mano manoDeJugador) {
        boolean[] cambioCartas = new boolean[]{true, true, true, true, true};
        boolean[] indexesRepetidos = UtilidadesGenerales.IndexDeNumerosQueSeRepiten(manoDeJugador);
        int contador = 0;
        for (int x = 0; x < 5; x++) {
            if (indexesRepetidos[x] == false)
                contador++;
        }
        if (contador == 5) {
            cambioCartas[4]=false;
            return cambioCartas;
        }
        int contadorFinal = 0;
        int nroDeIndexFinal = 0;
        for (int x = 0; x < 5; x++) {
            int indexNroActual = x;
            int contadorActual = 0;
            for (int y = 0; y < 5; y++) {
                if (manoDeJugador.getCard(indexNroActual).getNumero() == manoDeJugador.getCard(y).getNumero()) {
                    contadorActual++;
                }
            }
            if (contadorActual >= contadorFinal) {
                contadorFinal = contadorActual;
                nroDeIndexFinal = indexNroActual;
            }
        }
        int nroFinal = manoDeJugador.getCard(nroDeIndexFinal).getNumero();
        for (int x = 0; x < 5; x++) {
            if (manoDeJugador.getCard(x).getNumero() == nroFinal) {
                cambioCartas[x] = false;
            }
        }
        return cambioCartas;
    }

    public static int nroDeCartasIguales(boolean[] cambioCartas) {
        int cartasQnoSeCambian = 0;
        for (int x = 0; x < 5; x++) {
            if (cambioCartas[x] == false) {
                cartasQnoSeCambian++;
            }
        }
        return cartasQnoSeCambian;
    }

    /**
     * @return la posiciones de index para acceder son asi: el primer index nroDeCartasIguales que uno tiene; segundo index: nroDeCartasIgualesQueUnoQuiere y está
     * una probabilidad
     */
    public static double[][] lectorDePosibilidades(int nroDeCartasDelMazo) {
        String filePath = "C:\\Users\\Gamer\\OneDrive\\Escritorio\\PokerGame\\src\\main\\java\\org\\IndiePapafritaCraft\\ClasesDeLaCpu\\probabilidades\\" + nroDeCartasDelMazo + "cartasMazo.txt";
        double[][] posibilidades = new double[5][5];
        try {
            FileReader x = new FileReader(filePath);
            BufferedReader lector = new BufferedReader(x);
            int posInicial = 5;
            for (int a = 1; a < posInicial; a++) {
                lector.readLine();
            }
            int cartasIgualesQTengo = 1;
            int cartasIgualesQquiero = 1;
            for (int renglon = posInicial; renglon < 21; renglon++) {
                posibilidades[cartasIgualesQTengo][cartasIgualesQquiero] = Double.parseDouble(lector.readLine());
                if (cartasIgualesQquiero < 4) {
                    cartasIgualesQquiero++;
                } else {
                    cartasIgualesQquiero = 1;
                    cartasIgualesQTengo++;
                }
            }
            lector.close();
        } catch (java.io.FileNotFoundException x) {
            System.out.println("no se encontro el archivo");
        } catch (java.io.IOException x) {
            System.out.println("Ioexception en el metodo lector de posibilidades en UtilidadesParaParPiernaPoker");
        }
        return posibilidades;
    }

    /**
     * @param nroDeCartasIguales probabilidad de que salga
     * @param valor              es lo que puede salir
     */
    public static ValorYProbabilidad valorYprobabilidad(int nroDeCartasIguales, ValorDeMano valor, double[][] probabilidades) {
        //0 es el ordinal de carta alta, 1 es el ordinal del par,3 es de la pierna, 7 es de poker
        int nroDeCartQueSeBuscan = -1;
        switch (valor.ordinal()) {
            case 0:
                nroDeCartQueSeBuscan = 1;
                break;
            case 1:
                nroDeCartQueSeBuscan = 2;
                break;
            case 3:
                nroDeCartQueSeBuscan = 3;
                break;
            case 7:
                nroDeCartQueSeBuscan = 4;
                break;
            default:
                System.out.println("ha habido un error en UtilidadesParaParPiernaPoker");
                nroDeCartQueSeBuscan = -1;
                break;
        }
        double probabilidad = probabilidades[nroDeCartasIguales][nroDeCartQueSeBuscan];
        return new ValorYProbabilidad(probabilidad,valor);

    }
}

