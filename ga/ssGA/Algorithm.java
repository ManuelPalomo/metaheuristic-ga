///////////////////////////////////////////////////////////////////////////////
///               Steady State Genetic Algorithm v1.0                       ///
///                 by Enrique Alba, September 1999                         ///
///                                                                         ///
///  2TOURNAMENT+SPX(rand_parent)+Bit_Mutation+Replacement(Worst_Always)    ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.util.Random;

public class Algorithm {
    private final int chromosomeLength; // Alleles per chromosome
    private final int geneNumber;  // Number of genes in every chromosome
    private final int bitsPerGene;  // Number of bits per gene
    private final int populationSize;      // Number of individuals in the population
    private final double crossoverProbability, mutationProbability;      // Probability of applying crossover and mutation
    private final Problem problem;     // The problem being solved
    private final Population population;         // The population
    private static Random r;           // Source for random values in this class
    private final Individual auxiliaryIndividual;  // Internal auxiliar individual being computed


    // CONSTRUCTOR
    public Algorithm(Problem problem, int populationSize, int geneNumber, int bitsPerGene, double crossoverProbability, double mutationProbability)
            throws Exception {
        this.geneNumber = geneNumber;
        this.bitsPerGene = bitsPerGene;
        this.chromosomeLength = geneNumber * bitsPerGene;
        this.populationSize = populationSize;
        this.crossoverProbability = crossoverProbability;
        this.mutationProbability = mutationProbability;
        this.problem = problem;
        this.population = new Population(populationSize, chromosomeLength);// Create initial population
        r = new Random();
        this.auxiliaryIndividual = new Individual(chromosomeLength);

        for (int i = 0; i < populationSize; i++)
            population.setFitness(i, this.problem.evaluateStep(population.getIth(i)));
        population.computeStats();
    }

    // BINARY TOURNAMENT
    public Individual selectByBinaryTournament() throws Exception {
        int p1, p2;

        p1 = (int) (r.nextDouble() *
                (double) populationSize + 0.5); // Round and then trunc to int

        if (p1 > populationSize - 1) p1 = populationSize - 1;
        do {
            p2 = (int) (r.nextDouble() *
                    (double) populationSize + 0.5);  // Round and then trunc to int
            if (p2 > populationSize - 1) p2 = populationSize - 1;
        }
        while (p1 == p2);
        if (population.getIth(p1).getFitness() > population.getIth(p2).getFitness())
            return population.getIth(p1);
        else
            return population.getIth(p2);
    }

    // SINGLE POINT CROSSOVER - ONLY ONE CHILD IS CREATED (RANDOMLY DISCARD
    // THE OTHER)
    public Individual singlePointCrossover(Individual p1, Individual p2) {
        int rand;

        rand = (int) (r.nextDouble() *
                (double) chromosomeLength - 1 + 0.5); // From 0 to L-1 rounded
        if (rand > chromosomeLength - 1) rand = chromosomeLength - 1;

        if (r.nextDouble() > crossoverProbability)  // If no crossover then randomly returns one parent
            return r.nextDouble() > 0.5 ? p1 : p2;

        // Copy CHROMOSOME 1
        for (int i = 0; i < rand; i++) {
            auxiliaryIndividual.setAllele(i, p1.getAllele(i));
        }
        // Copy CHROMOSOME 2
        for (int i = rand; i < chromosomeLength; i++) {
            auxiliaryIndividual.setAllele(i, p2.getAllele(i));
        }

        return auxiliaryIndividual;
    }


    // MUTATE A BINARY CHROMOSOME
    public Individual mutate(Individual p1) {
        byte alelle = 0;
        Random r = new Random();

        auxiliaryIndividual.assign(p1);

        for (int i = 0; i < chromosomeLength; i++)
            if (r.nextDouble() <= mutationProbability)  // Check mutation bit by bit...
            {
                if (auxiliaryIndividual.getAllele(i) == 1)
                    auxiliaryIndividual.setAllele(i, (byte) 0);
                else
                    auxiliaryIndividual.setAllele(i, (byte) 1);
            }

        return auxiliaryIndividual;

    }

    // REPLACEMENT - THE WORST INDIVIDUAL IS ALWAYS DISCARDED
    public void replace(Individual new_indiv) throws Exception {
        population.setIth(population.getWorstIndividualPosition(), new_indiv);
        //pop.compute_stats();                  // Recompute avg, best, worst, etc.
    }

    // EVALUATE THE FITNESS OF AN INDIVIDUAL
    private double evaluateStep(Individual indiv) {
        return problem.evaluateStep(indiv);
    }

    public void goOneStep() throws Exception {
        auxiliaryIndividual.assign(singlePointCrossover(selectByBinaryTournament(), selectByBinaryTournament()));
        auxiliaryIndividual.setFitness(problem.evaluateStep(mutate(auxiliaryIndividual)));
        replace(auxiliaryIndividual);
    }

    public Individual getSolution() throws Exception {
        return population.getIth(population.getBestIndividualPosition());// The better individual is the solution
    }

    public double get_bestf() {
        return population.getBestPopulationFitness();
    }
}

