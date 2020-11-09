////////////////////////////////////////////////////////////////////////////////
///                          Steady State Genetic Algorithm v1.0             ///
///                            by Enrique Alba, September 1999               ///
///                                                                          ///
///   Population of "popsize" binary individuals(GN,GL) and their stats      ///
////////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

public class Population {
    private final int populationSize;  // The number of individuals
    private final Individual[] population;   // The vector of individuals

    private final int chromosomeLength;   // The length of the chromosomes

    // STATISTICS
    private int bestIndividualPosition; // The position of the best  individual: [0..popsize-1]
    private int worstIndividualPosition;// The position of the worst individual: [0..popsize-1]
    private double bestPopulationFitness; // The best fitness of the present population
    private double averagePopulationFitness;  // The average fitness of the present population
    private double worstPopulationFitness;// The worst fitness of the present population
    private double bestOverallFitness; // The best fitness ever found during the search


    public Population(int populationSize, int chromosomeLength) {
        this.populationSize = populationSize;
        population = new Individual[this.populationSize];
        this.chromosomeLength = chromosomeLength;

        for (int i = 0; i < this.populationSize; i++) population[i] = new Individual(chromosomeLength);

        // Initialize statistics
        bestIndividualPosition = 0;
        worstIndividualPosition = 0;
        bestPopulationFitness = 0.0;
        averagePopulationFitness = 0.0;
        worstPopulationFitness = Integer.MAX_VALUE;
        bestOverallFitness = 0.0;
    }

    public Individual getIth(int index) throws Exception {
        if ((index < populationSize) && (index >= 0))
            return population[index];
        else
            throw new Exception("Index out of range when getting a copy of an individual");
    }

    public void setIth(int index, Individual indiv) throws Exception {
        if ((index < populationSize) && (index >= 0))
            population[index].assign(indiv);
        else
            throw new Exception("Index out of range when inserting and individual");

        // ALWAYS RECOMPUTE STATS AFTER INSERTION
        computeStats();
    }

    public void setFitness(int index, double fitness) {
        population[index].setFitness(fitness);
    }

    public void computeStats() {
        double f, total;

        // Initialize values (always needed!!!)
        total = 0.0;
        worstPopulationFitness = population[0].getFitness();
        worstIndividualPosition = 0;
        bestPopulationFitness = population[0].getFitness();
        bestIndividualPosition = 0;

        for (int i = 0; i < populationSize; i++) {
            f = population[i].getFitness();
            if (f <= worstPopulationFitness) {
                worstPopulationFitness = f;
                worstIndividualPosition = i;
            }
            if (f >= bestPopulationFitness) {
                bestPopulationFitness = f;
                bestIndividualPosition = i;
            }
            if (f >= bestOverallFitness) {
                bestOverallFitness = f;
            }
            total += f;
        }

        averagePopulationFitness = total / (double) populationSize;
    }

    public int getWorstIndividualPosition() {
        return worstIndividualPosition;
    }

    public int getBestIndividualPosition() {
        return bestIndividualPosition;
    }

    public double getWorstPopulationFitness() {
        return worstPopulationFitness;
    }

    public double getAveragePopulationFitness() {
        return averagePopulationFitness;
    }

    public double getBestPopulationFitness() {
        return bestPopulationFitness;
    }

    public double getBestOverallFitness() {
        return bestOverallFitness;
    }

}
