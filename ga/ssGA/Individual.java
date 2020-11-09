////////////////////////////////////////////////////////////////////////////////
///                      Steady State Genetic Algorithm v1.0                 ///
///                       by Enrique Alba, September 1999                    ///
///                                                                          ///
///                      Individual = Chromosome + Fitness                   ///
////////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.io.*;

public class Individual implements Serializable {
    private final Chromosome chromosome;
    private int length;
    private double fitness;

    public Individual(int length) {
        chromosome = new Chromosome(length);
        fitness = 0.0;
        this.length = length;
    }

    private void copy(Chromosome source, Chromosome destination) {
        for (int i = 0; i < length; i++) {
            destination.setAllele(i, source.getAllele(i));
        }
    }

    public void assign(Individual individual) {
        copy(individual.getChromosome(), chromosome);
        fitness = individual.getFitness();
        length = individual.getLength();
    }

    public int getLength() {
        return length;
    }

    public void setFitness(double fit) {
        fitness = fit;
    }

    public double getFitness() {
        return fitness;
    }

    public void setAllele(int index, byte value) {
        chromosome.setAllele(index, value);
    }

    public byte getAllele(int index) {
        return chromosome.getAllele(index);
    }

    public byte[] getAlleles() {
        return chromosome.getAlleles();
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

}
