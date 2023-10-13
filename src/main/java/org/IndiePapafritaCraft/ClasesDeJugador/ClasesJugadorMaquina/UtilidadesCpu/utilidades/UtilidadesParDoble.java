package org.IndiePapafritaCraft.ClasesDeJugador.ClasesJugadorMaquina.UtilidadesCpu.utilidades;

import org.IndiePapafritaCraft.ClasesRestantes.Mano;
import org.IndiePapafritaCraft.ValoresJuntados.FullProb;
import org.IndiePapafritaCraft.ValoresJuntados.ValorDeMano;
import org.IndiePapafritaCraft.ValoresJuntados.ValorYProbabilidad;

public class UtilidadesParDoble {
    public static ValorYProbabilidad[] probabilidadesParDoble(int nroDeParesOPiernas, int cartaMasAlta, boolean[]cambioCartas, int indexNmasGrandeSobrante, Mano mano, FullProb[] parDoble){
        switch (nroDeParesOPiernas){
            case 0:
                //en este caso cambia lo mismo que ParPiernaPoker
                return new ValorYProbabilidad[]{new ValorYProbabilidad(0, ValorDeMano.NADA)};
            case 1:
                //caso cambia lo mismo que parPiernaPoker
                if (mano.getCard(indexNmasGrandeSobrante).getNumero()<cartaMasAlta/2){
                    return new ValorYProbabilidad[]{new ValorYProbabilidad(0, ValorDeMano.NADA)};
                }
                else {
                    return parDoble[0].getProb();
                }
            case 2:
                //casoFull
                if (indexNmasGrandeSobrante==-1){
                    return parDoble[2].getProb();
                }
                else return parDoble[1].getProb();
            default:
                System.out.println("ha habido un error en posibilidadParDoble");
                return new ValorYProbabilidad[]{new ValorYProbabilidad(0, ValorDeMano.NADA)};
        }
    }

    /**
     * @param cartasMasAltaDelMazo
     * @param nroDeParesOPiernas debe tener cuantos pares o piernas tiene la mano
     * @param indexNmasGrandeSobrante debe tener el nro mas grande qu no forme par, pierna o poker
     * @return devuelve un array con las cartas para cambiar
     */
    public static boolean[] cambioCartasParaParDoble(Mano manoDeJugador,int cartasMasAltaDelMazo, int nroDeParesOPiernas,int indexNmasGrandeSobrante) {
        boolean[] cambioCartas = new boolean[]{true, true, true, true, true};
        boolean[] indexesRepetidos = UtilidadesGenerales.IndexDeNumerosQueSeRepiten(manoDeJugador);
        for (int x = 0; x < 5; x++) {
            if (indexesRepetidos[x] == true)
                cambioCartas[x] = false;
        }
        switch (nroDeParesOPiernas) {
            case 0:
                cambioCartas[indexNmasGrandeSobrante] = false;
                return cambioCartas;
            case 1:
                if (manoDeJugador.getCard(indexNmasGrandeSobrante).getNumero() >= cartasMasAltaDelMazo / 2) {
                    cambioCartas[indexNmasGrandeSobrante] = false;
                }
                return cambioCartas;
            case 2:
                //caso full servido
                if (indexNmasGrandeSobrante == -1) {
                    return cambioCartas;
                }
                //caso par doble
                else {
                    cambioCartas[indexNmasGrandeSobrante] = true;
                }
                return cambioCartas;
            default:
                System.out.println("ha habido un error en switch en cambio cartas par doble");
                return cambioCartas;
        }
    }

    /**
     *
     * @param indexesDeNrosRepetidos debe tener los numeros de indice para pointear los nros que forman par,pierna o poker
     * @return devuelve el nro mas grande que no se repite, si todos los nros se repiten devuelve -1
     */
    public static int indexDeNroMasGrandeQueNoSeRepite(Mano manoDeJugador, boolean[] indexesDeNrosRepetidos){
        int index=-1;
        int numero=-1;
        for (int x=0;x<5;x++){
            if (indexesDeNrosRepetidos[x]==false){
                if (manoDeJugador.getCard(x).getNumero()>numero){
                    index= x;
                    numero= manoDeJugador.getCard(x).getNumero();
                }
            }
        }
        return index;
    }
}
