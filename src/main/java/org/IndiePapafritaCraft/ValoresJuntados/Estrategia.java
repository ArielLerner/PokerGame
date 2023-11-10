package org.IndiePapafritaCraft.ValoresJuntados;

import org.IndiePapafritaCraft.ClasesRestantes.Mano;

public class Estrategia {
    private ValorYProbabilidad[] valoresYprobabilidades;
    private boolean[] cartasParaCambiar;
    private double probDeGanarObjetiva;
        private Mano mano;
        private int apuestaMax; // la apuesta max se settea cuando el jugador maquina termina de hacer la apuesta
    public Estrategia(ValorYProbabilidad[] relacionesEntreValorYProbabilidad, boolean[]cartasCambio, double probObjetivaDeGanar, Mano mano2){
            valoresYprobabilidades =relacionesEntreValorYProbabilidad;
            cartasParaCambiar=cartasCambio;
            probDeGanarObjetiva = probObjetivaDeGanar;
            mano = mano2;
        }
        public String toString(){
            String x="";
            x = x + "probDeGanar: " + probDeGanarObjetiva+ "   ";
            for (int a = 0; a< valoresYprobabilidades.length; a++){
                x=x+"prob: "+  valoresYprobabilidades[a].getProbabilidad() +" " + "valor: "+ valoresYprobabilidades[a].getValor().name()+"    ";
            }
            x=x+
                    "c1: "+cartasParaCambiar[0]+" "+
                    "c2: "+" "+ cartasParaCambiar[1]+" "+
                    "c3: "+" "+cartasParaCambiar[2]+" "+
                    "c4: "+" " + cartasParaCambiar[3] +" "+
                    "c5: "+" " + cartasParaCambiar[4];
            return x;
        }

        public double getProbDeGanarObjetiva() {return probDeGanarObjetiva;}

        public ValorYProbabilidad[] getValoresYprobabilidades() {
            return valoresYprobabilidades;
        }

        public boolean[] getCartasParaCambiar() {
            return cartasParaCambiar;
        }

        public Mano getMano() {
            return mano;
        }

        public void setApuestaMax(int apuestaMax) {
            this.apuestaMax = apuestaMax;
        }
    }
