package org.IndiePapafritaCraft.ValoresJuntados;

public enum ValorDeMano {
    NADA(0.25), PAR(0.4), DOBLEPAR(0.8), PIERNA(0.9), ESCALERA(0.95), COLOR(0.97), FULL(0.98), POKER(0.99), ESCALERADECOLOR(0.995), ESCALERAREAL(0.9999);

    private ValorDeMano(double probDeGanarAUnaMano) {
        this.probDeGanarUnaMano = probDeGanarAUnaMano;
    }

    double probDeGanarUnaMano;

    public double getProbDeGanarUnaMano() {
        return probDeGanarUnaMano;
    }

    public static String getNameConOrdinal(int ordinal) {
         return  ValorDeMano.values()[ordinal].name();
    }
    public static ValorDeMano getEnumConOrdinal(int ordinal) {
        return  ValorDeMano.values()[ordinal];
    }
}
