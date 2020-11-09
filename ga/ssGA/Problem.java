///////////////////////////////////////////////////////////////////////////////
///                 Steady State Genetic Algorithm v1.0                     ///
///                      by Enrique Alba, July 2000                         ///
///                                                                         ///
///         Problem Function AND Representation (GL, GN, Ranges)            ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

public abstract class Problem                    // Maximization task
{
    protected int geneLength = 1;                // Gene length in binary
    protected int geneNumber = 1;                // Gene number in one string
    protected int chromosomeLength;            // Chromosome length
    protected long fitnessCounter;    // Number of evaluations
    protected double targetFitness;    // Target fitness value -MAXIMUM-
    protected boolean isTargetFitnessKnown;        // Is the target fitness known????

    public Problem() {
        chromosomeLength = geneNumber * geneLength;
        fitnessCounter = 0;
        isTargetFitnessKnown = false;
        targetFitness = -999999.9;
    }

    public double evaluateStep(Individual Indiv) {
        fitnessCounter++;
        return evaluate(Indiv);
    }

    public abstract double evaluate(Individual individual);

    public long getFitnessCounter() {
        return fitnessCounter;
    }

    public double getTargetFitness() {
        return targetFitness;
    }

    public boolean targetFitnessKnown() {
        return isTargetFitnessKnown;
    }

    public void setTargetFitness(double tf) {
        targetFitness = tf;
        isTargetFitnessKnown = true;
    }

    public void setGeneLength(int gl) {
        geneLength = gl;
        chromosomeLength = geneNumber * geneLength;
    }

    public void setGeneNumber(int gn) {
        geneNumber = gn;
        chromosomeLength = geneNumber * geneLength;
    }

}
