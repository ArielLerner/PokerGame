package org.IndiePapafritaCraft.ValoresJuntados;

public class ValorYProbabilidad {
   final double probabilidad;
     final ValorDeMano valor;
     public ValorYProbabilidad(double prob, ValorDeMano valores){
        probabilidad=prob;
        valor=valores;
    }

    /**
     *
     * @return crea un array de valores y probabilidades de el tamaño de un full prob con todos valores de probabilidad 0;
     */
    public static ValorYProbabilidad[] crearArrayTamanoFullProb(){
        ValorYProbabilidad[] x = new ValorYProbabilidad[10];
        for (int a=0;a<10;a++){
            x[a] = new ValorYProbabilidad(0,ValorDeMano.getEnumConOrdinal(a));
        }
        return x;
    }
    public ValorDeMano getValor() {
        return valor;
    }

    public double getProbabilidad() {
        return probabilidad;
    }
}
