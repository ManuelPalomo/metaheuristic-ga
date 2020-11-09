package ga.ssGA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class KnapsackProblem extends Problem {
    private final KnapsackData knapsackData;

    public KnapsackProblem(String fileName) throws FileNotFoundException {
        this.knapsackData = parseKnapsackData(fileName);
    }

    @Override
    public double evaluate(Individual individual) {
        Chromosome chromosome = individual.getChromosome();
        double fitness = 0.0d;
        if (isViolatingAConstraint(chromosome.getAlleles())) {
            individual.setFitness(fitness);
            return fitness;
        }

        for (int i = 0; i < knapsackData.getVariableNumber(); i++) {
            if (chromosome.getAllele(i) == 1) fitness += knapsackData.getValues()[i];
        }
        individual.setFitness(fitness);
        return fitness;
    }

    private boolean isViolatingAConstraint(byte[] alleles) {
        final int[][] constraints = knapsackData.getConstraints();
        for (int i = 0; i < knapsackData.getConstraintNumber(); i++) {
            int constraintValue = 0;
            for (int j = 0; j < alleles.length; j++) {
                if (alleles[j] == 1) constraintValue += constraints[i][j];
            }
            if (constraintValue > knapsackData.getConstraintLimit()[i]) return true;
        }
        return false;
    }

    private KnapsackData parseKnapsackData(String filename) throws FileNotFoundException {
        KnapsackData knapsackData = new KnapsackData();

        File file = new File(KnapsackProblem.class.getResource(filename).getFile());
        Scanner scanner = new Scanner(file);

        //Header Line
        String headerLine = scanner.nextLine();
        String[] headerLineSplit = headerLine.split(" ");
        knapsackData.setVariableNumber(Integer.parseInt(headerLineSplit[0]));
        knapsackData.setConstraintNumber(Integer.parseInt(headerLineSplit[1]));
        knapsackData.setOptimalValue(Integer.parseInt(headerLineSplit[2]));

        //Value Line
        String valueLine = scanner.nextLine();
        knapsackData.setValues(convertStringIntoIntArray(valueLine));

        //Constraints
        int[][] constraints = new int[knapsackData.getConstraintNumber()][knapsackData.getVariableNumber()];
        for (int i = 0; i < knapsackData.getConstraintNumber(); i++) {
            String constraintLine = scanner.nextLine();
            constraints[i] = convertStringIntoIntArray(constraintLine);
        }
        knapsackData.setConstraints(constraints);

        //Constraint Limits
        String constraintLimitLine = scanner.nextLine();
        knapsackData.setConstraintLimit(convertStringIntoIntArray(constraintLimitLine));

        return knapsackData;
    }

    private int[] convertStringIntoIntArray(String string) {
        return Arrays.stream(string.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    public KnapsackData getKnapsackData() {
        return knapsackData;
    }
}
