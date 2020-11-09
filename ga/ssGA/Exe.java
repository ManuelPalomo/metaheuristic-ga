///////////////////////////////////////////////////////////////////////////////
///            Steady State Genetic Algorithm v1.0                          ///
///                by Enrique Alba, July 2000                               ///
///                                                                         ///
///   Executable: set parameters, problem, and execution details here       ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.io.File;
import java.io.FileWriter;

public class Exe {
    public static void main(String[] args) throws Exception {

        KnapsackProblem problem = new KnapsackProblem("mknap3.txt");                             // The problem being solved
        KnapsackData knapsackData = problem.getKnapsackData();

        int gn = knapsackData.getVariableNumber();                          // Gene number
        int gl = 1;                            // Gene length
        int popsize = 512;                          // Population size
        double pc = 0.7;                          // Crossover probability
        double pm = 1.0 / ((double) gn * (double) gl); // Mutation probability
        long MAX_ISTEPS = 50000;

        problem.setGeneNumber(gn);
        problem.setGeneLength(gl);
        problem.setTargetFitness(knapsackData.getOptimalValue());

        for (int iterations = 0; iterations < 30; iterations++) {
            Algorithm ga;          // The ssGA being used
            ga = new Algorithm(problem, popsize, gn, gl, pc, pm);

            StringBuffer stringBuffer = new StringBuffer();
            int steps = 0;
            double bestValue = 0.0d;
            for (int step = 0; step < MAX_ISTEPS; step++) {
                ga.goOneStep();
                System.out.print(step);
                System.out.print("  ");
                System.out.println(ga.get_bestf());

                stringBuffer.append(step).append(",").append(ga.get_bestf());
                stringBuffer.append("\n");
                steps = step;
                bestValue = ga.get_bestf();

                if ((problem.targetFitnessKnown()) &&
                        (ga.getSolution()).getFitness() >= problem.getTargetFitness()
                ) {
                    System.out.print("Solution Found! After ");
                    System.out.print(problem.getFitnessCounter());
                    System.out.println(" evaluations");
                    break;
                }

            }

            //Output execution file
            File executionFile = new File("./Execution.csv");
            FileWriter fileWriter = new FileWriter(executionFile);
            fileWriter.write(stringBuffer.toString());
            fileWriter.close();

            // Print the solution
            for (int i = 0; i < gn * gl; i++)
                System.out.print((ga.getSolution()).getAllele(i));
            System.out.println();
            System.out.println((ga.getSolution()).getFitness());

            //Output best resume file
            File resultsFile = new File("./Results.csv");
            FileWriter resultsFileWriter = new FileWriter(resultsFile, true);
            String resultLine = String.format("%d,%d,%d", iterations + 1, steps, (int) bestValue);
            resultsFileWriter.write(resultLine);
            resultsFileWriter.write("\n");
            resultsFileWriter.close();

            //Solution data
            System.out.println("\\\\\\\\\\\\");
            System.out.println("Solution data");
            final int[][] constraints = knapsackData.getConstraints();
            byte[] alleles = ga.getSolution().getAlleles();
            for (int i = 0; i < knapsackData.getConstraintNumber(); i++) {
                int constraintValue = 0;
                for (int j = 0; j < alleles.length; j++) {
                    if (alleles[j] == 1) constraintValue += constraints[i][j];
                }
                System.out.println(String.format("Constraint #%d: Value:%d , Limit:%d", i, constraintValue, knapsackData.getConstraintLimit()[i]));
            }
        }
    }

}
