package org.IndiePapafritaCraft.ValoresJuntados;

public class FullProb {
    private  String nombre;
    private  ValorYProbabilidad[] prob;

    /**
     *
     * @param probabilidades debe tener en cada posicion la probabilidad de sacar el valor de mano correspondiente a ese ordinal
     */
    public FullProb(double[] probabilidades, String nombreDeProbs ){
        prob = new ValorYProbabilidad[10];
        for (int a=0;a<10;a++){
            prob[a] = new ValorYProbabilidad(probabilidades[a], ValorDeMano.getEnumConOrdinal(a));
            nombre=nombreDeProbs;
        }
    }
    public FullProb(ValorYProbabilidad[] x,String nombreDeProbs ){
           prob=x;
           nombre=nombreDeProbs;
    }

    /**
     * Crea un fullProb de prob 0
     */
    public FullProb(String nombre){
        this.prob = new ValorYProbabilidad[]{new ValorYProbabilidad(0,ValorDeMano.NADA)};
        this.nombre =nombre;
    }
    public ValorYProbabilidad DevolverPos( String name){
        return  prob[ValorDeMano.valueOf(name).ordinal()];
    }
    public ValorYProbabilidad DevolverPos( int ordinal){
        return  prob[ordinal];
    }
    public double sumaDeProb(){
        int length=prob.length;
        double sumatoria = 0;
        for ( int x=0;x<length;x++ ){
            sumatoria=sumatoria+prob[x].getProbabilidad();
        }
        return sumatoria;
    }

    public String getNombre() {
        return nombre;
    }

    public ValorYProbabilidad[] getProb() {
        return prob;
    }

    public String toString(){
        String fullProb = "nombre: "+ nombre+"  ";
        for (int x=0;x<prob.length;x++){
            fullProb=fullProb + "prob " + prob[x].valor.name() + ": " +prob[x].getProbabilidad() + " ";
        }
        return  fullProb;
    }
}
