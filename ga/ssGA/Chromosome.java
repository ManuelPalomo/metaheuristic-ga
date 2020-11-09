//////////////////////////////////////////////////////////////////////////////
///                   BINARY CHROMOSOME CLASS v1.0                         ///
///                 by Enrique Alba, September 1999                        ///
//////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.util.Random;
import java.io.*;

public class Chromosome implements Serializable {
    private final byte[] alleles;        // Allele vector
    private final int length;            // Length of the allele vector
    private final static Random r = new Random(); // Only the first time it is initialized


    // CONSTRUCTOR - FILL UP THE CONTENTS
    public Chromosome(int length) {
        alleles = new byte[length];
        this.length = length;
        for (int i = 0; i < length; i++)
            if (r.nextDouble() > 0.5)          // Returns values in [0..1]
                alleles[i] = 1;
            else
                alleles[i] = 0;
    }

    public byte[] getAlleles() {
        return alleles;
    }

    public void setAllele(int index, byte value) {
        alleles[index] = value;
    }

    public byte getAllele(int index) {
        return alleles[index];
    }

}
