///////////////////////////////////////////////////////////////////////////////////////////////
///         NAME        :   Gavin Dominique
///         DATE        :   October 12th, 2025
///     Assignment      : Assignment 2: Part 1
/// PROGRAM DESCRIPTION : A working three-layer, fully-connected, feed-forward network with  
///                         “stochastic” gradient descent and back propagation. Uses the
///                         methods and formulas talked about in class, the lecture notes, and
///                         in Michael Nielswn's Deep Learning textbook.
///////////////////////////////////////////////////////////////////////////////////////////////
/// Notes for proffesor :
///         I read Michael Nielsen's textbook while working on this project and
///         it helped out a ton. Instead of doing all of my calculations element wise
///         ,like in the lecture notes, I transformed them all into Matrix math versions
///         and used his textbook as a guide for it. Chapter 2 was a massive help for
///         getting a better idea of the formulas and the math behind them.
/////////////////////////////////////////////////////////////////////////////////////////////// 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class old_p2 
{
    /////// Some global variables used in the main program loop.
    static Random random = new Random();

    static double eta = 3;
    static int epochs = 10;

    static int mb_size = 10;

    static int input_nodes = 784;
    static int hidden_nodes = 30;
    static int output_nodes = 10;

    static double[][][] outputVetors = { 
            //   training data        expected output
            { {1}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0} },   // 0 output vector
            { {0}, {1}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0} },   // 1 output vector
            { {0}, {0}, {1}, {0}, {0}, {0}, {0}, {0}, {0}, {0} },   // 2 output vector
            { {0}, {0}, {0}, {1}, {0}, {0}, {0}, {0}, {0}, {0} },   // 3 output vector
            { {0}, {0}, {0}, {0}, {1}, {0}, {0}, {0}, {0}, {0} },   // 4 output vector
            { {0}, {0}, {0}, {0}, {0}, {1}, {0}, {0}, {0}, {0} },   // 5 output vector
            { {0}, {0}, {0}, {0}, {0}, {0}, {1}, {0}, {0}, {0} },   // 6 output vector
            { {0}, {0}, {0}, {0}, {0}, {0}, {0}, {1}, {0}, {0} },   // 7 output vector
            { {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {1}, {0} },   // 8 output vector
            { {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {1} },   // 9 output vector
        };

    public static void main(String[] args)
    {

        ////////////// SETTING UP INITIAL WEIGHTS AND BIASES //////////////
        double[][] weights_1 = randMatrix(hidden_nodes, input_nodes);
        double[][] biases_1 = randMatrix(hidden_nodes, 1);

        double[][] weights_2 = randMatrix(output_nodes, hidden_nodes);
        double[][] biases_2 = randMatrix(output_nodes, 1);

        double[][] mnist_training_data = getTrainingData();

        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < 60000; i++)
        {
            indices.add(i);
        }

        ///////////////////////////////////// MAIN PROGRAM RUN THROUGH /////////////////////////////////////
        
        /*/ loop that just asks the user what type of output they want to see when the main bunch runs
        // mainly just a test / placeholder for Part 2's option list
        Scanner input = new Scanner(System.in);
        boolean asking = true;
        int outputs = 0;

        System.out.println("What type of output is desired?");
        System.out.println("[0] - All calculated variables during each epoch.");
        System.out.println("[1] - Updated weight and bias matrices at the end of each epoch.");
        System.out.println("[2] - Final weight and bias matrices.");
        System.out.println();

        while (asking) 
        {
            while (!input.hasNextInt())         // this makes sure the user actually inputs a number
            {
                System.out.println("Please enter a valid option.");
                input.next();
            }

            outputs = input.nextInt();

            if (outputs != 0 && outputs != 1)   // this makes sure the user inputs one of the options
            {
                System.out.println("Please enter a valid option.");
            } else {
                asking = false;
            }
        }

        input.close();
        System.out.println(); // */

        System.out.println("Thinking. . .");
        System.out.println();

        //////// THESE VARIABLES STORE THE TOTAL WEIGHT AND BIAS GRADIENTS THROUGH EACH MINI-BATCH ////////
        /// allocating space for them now and just resetting them inside of the loop to save overhead
        double[][] total_wg_1 = createMatrix(weights_1.length, weights_1[0].length, 0);
        double[][] total_wg_2 = createMatrix(weights_2.length, weights_2[0].length, 0);

        double[][] total_bg_1 = createMatrix(biases_1.length, biases_1[0].length, 0);
        double[][] total_bg_2 = createMatrix(biases_2.length, biases_2[0].length, 0);

        for (int e = 0; e < epochs; e++) //////////////////////////////////////////////////////////////////////////////////////////////// FOR EACH EPOCH ////////
        {
            // System.out.println("################################################ Epoch " + (e + 1) + " ################################################");

            // randomize training set using Fisher-Yates
            for (int i = indices.size() - 1; i > 0; i--) 
            {
                int j = random.nextInt(i + 1);
                int temp = indices.get(i);
                indices.set(i, indices.get(j));
                indices.set(j, temp);
            }


            // break into mini-batches
            int num_batches = indices.size() / mb_size;

            double[][][] batches = new double[num_batches][mb_size][785];

            for (int mb = 0; mb < num_batches; mb++)
            {
                for (int i = 0; i < mb_size; i++)
                {
                    int idx = indices.get(mb * mb_size + i);
                    batches[mb][i] = mnist_training_data[idx];
                }
            }

            // holds the total amount of each number seen and guessed correctly
            double[] seen = new double[10];
            double[] correct = new double[10];

            for (int mb = 0; mb < num_batches; mb++) ////////////////////////////////////////////////////////////////////////////// FOR EACH MINI-BATCH ////////
            {
                // if (outputs == 0) {System.out.println("################################ Mini-Batch " + (mb + 1) + " ################################");}
                
                //////// SET THE ACTIVE MINI-BATCH ////////
                double[][] batch = batches[mb];

                //////// RESET THE TOTAL WEIGHT AND BIAS MATRICES ////////
                fillMatrix(total_wg_1, 0.0);
                fillMatrix(total_wg_2, 0.0);
                fillMatrix(total_bg_1, 0.0);
                fillMatrix(total_bg_2, 0.0);

                for (int i = 0; i < mb_size; i++) ///////////////////////////////////////////////////////////////// FOR EACH TRAINING SET ////////
                {
                    //////// SET ACTIVE TRAINING DATA AND EXPECTED OUTPUT ////////
                    double[][] training_data = flipArray(Arrays.copyOfRange(batch[i], 1, batch[i].length));
                    int label = (int) batch[i][0];
                    double[][] expected_output = outputVetors[label];

                    seen[label]++;

                    //////// CALCULATE ACTIVATIONS, ERROR, AND WEIGHT GRADIENTS FOR ALL LAYERS ////////
                    double[][] l1_a = layerActivation(weights_1, training_data, biases_1);

                    double[][] l2_a = layerActivation(weights_2, l1_a, biases_2);

                    if (guessLabel(l2_a) == label)
                    {
                        correct[label]++;
                    }

                    double[][] l2_e = final_layer_error(l2_a, expected_output);
                    double[][] l2_wg = weightGradient(l1_a, l2_e);

                    double[][] l1_e = hidden_layer_error(l1_a, l2_e, weights_2);
                    double[][] l1_wg = weightGradient(training_data, l1_e);
                    
                    //////// KEEP TRACK OF WEIGHT AND BIAS GRADIENTS WITHIN MINI-BATCH ////////
                    total_wg_1 = mAddition(total_wg_1, l1_wg);
                    total_wg_2 = mAddition(total_wg_2, l2_wg);

                    total_bg_1 = mAddition(total_bg_1, l1_e);
                    total_bg_2 = mAddition(total_bg_2, l2_e);

                }

                //////// AT THE END OF EACH MINI-BATCH UPDATE THE WEIGHTS AND BIASES ////////
                weights_1 = updateWeightOrBias(weights_1, eta, mb_size, total_wg_1);
                biases_1 = updateWeightOrBias(biases_1, eta, mb_size, total_bg_1);

                weights_2 = updateWeightOrBias(weights_2, eta, mb_size, total_wg_2);
                biases_2 = updateWeightOrBias(biases_2, eta, mb_size, total_bg_2);

            }

            if (e == epochs - 1)
            {
                for (int i = 0; i < 10; i++)
                {
                    System.out.println(i + ": " + correct[i] + "/" + seen[i]);
                }
                System.out.println("% : " + sum(correct) + "/" + sum(seen) + " : " + ((sum(correct) / sum(seen)) * 100));
            }

        }
    }

    /////////////////////////////////////////////////////////////////////// FUNCTIONS USED FOR THE NETWORK CALCULATIONS ///////////////////////////////////////////////////////////////////////

    ////////// calculates the new weight or bias matrix //////////
    public static double[][] updateWeightOrBias(double[][] oldWeights, double learning_rate, double size_of_trainingset, double[][] weight_gradient)
    {
        // using formula
        // new_weight[j][k] = old_weight[j]k] - (learning_rate / size_of_trainingset * summation(weight_gradient[j][k]))

        double eta_fraction = learning_rate / size_of_trainingset;                                      // eta / size_of_trainingset

        double[][] intermediate = elementMultiplication(weight_gradient, eta_fraction);                  // eta_fraction * weight_gradient
        
        return mSubtraction(oldWeights, intermediate);                                                   // oldweights - intermediate

    }

    ////////// calculates the weight gradient for a given layer //////////
    public static double[][] weightGradient(double[][] previous_layer_activations, double[][] error)
    {
        return mMultiplication(error, transpose(previous_layer_activations));

        // tried a * e but that didnt work cause the sizes didnt match so maybe try transpose???
        // tried a * t(e) and it gave the right numbers but the matrix was the wrong way around :(
        // tried t(a) * e and that didnt work either, another size mismatch
        // settled on e * t(a) because it worked
    }

    ////////// final layer error function //////////
    public static double[][] final_layer_error(double[][] activations, double[][] expected)
    {
        double[][] OneMatrix = createMatrix(activations.length, activations[0].length, 1);

        //// implimentation of the formula:
        // error = (activations - expected) * activations * (1 - activations)

        double[][] leftmost = mSubtraction(activations, expected);                                          // (activations - expected)
        double [][] rightmost = mSubtraction(OneMatrix, activations);                                       // (1 - activations)

        double[][] intermediate = elementMultiplication(leftmost, activations);                             // leftmost * activations
        double[][] finalError = elementMultiplication(intermediate, rightmost);                             // intermediate * rightmost

        return finalError;
    }

    ////////// hidden layer error function //////////
    public static double[][] hidden_layer_error(double[][] activations, double[][] previous_layer_error, double[][] weights_of_layer)
    {
        double[][] OneMatrix = createMatrix(activations.length, activations[0].length, 1);                  // just a matrix of 1s used later

        //// implimentation of the formula:
        // error = (weights(l+1)T * error(l+1)) * activations * (1 - activations)

        double[][] bpError = mMultiplication(transpose(weights_of_layer), previous_layer_error);            // (weights * error)
        double [][] rightmost = mSubtraction(OneMatrix, activations);                                       // (1 - activations)

        double[][] intermediate = elementMultiplication(bpError, activations);                              // leftmost * activations
        double[][] finalError = elementMultiplication(intermediate, rightmost);                             // intermediate * rightmost

        return finalError;
    }

    ////////// calculates layer activations //////////
    public static double[][] layerActivation(double[][] weights, double[][] nextLayerActivation, double[][] biases)
    {
        //// implimentation of the formula:
        // y = sigmoid(weight_matrix * previous_layer_activations + bias)

        double[][] z = mMultiplication(weights, nextLayerActivation);         // weight_matrix * previous_layer_activations

        for (int i = 0; i < z.length; i++)
        {
            z[i][0] = 1.0 / (1.0 + Math.exp(-(z[i][0] + biases[i][0])));    // runs sigmoid on (z + bias)
        }

        return z;
    }

    /////////////////////////////////////////////////////////////////////// HELPER FUNCTIONS USED BY THE REST OF THE PROGRAM ///////////////////////////////////////////////////////////////////////

    ////////// uber simple matrix printing //////////
    public static void mPrint(double[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                System.out.print(matrix[i][j] + "\t");
            }

            System.out.println();
        }
    }
    
    ////////// creates a (rows) by (cols) matrix filled with with a given number //////////
    public static double[][] createMatrix(int rows, int cols, int fill)
    {
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) 
        {
            Arrays.fill(result[i], fill);
        }
        return result;
    }

    ////////// matrix addition //////////
    public static double[][] mAddition(double[][] m1, double[][] m2)
    {
        // check if matrices are of equal size
        if ((m1.length != m2.length) || (m1[0].length != m2[0].length))
        {
            System.out.println("Error trying to add matrices. Sizes do not match.");
            return null;
        }

        double[][] result = new double[m1.length][m1[0].length];

        for (int i = 0; i < m1.length; i++)
        {
            for (int j = 0; j < m1[0].length; j++)
            {
                result[i][j] = m1[i][j] + m2[i][j];
            }
        }

        return result;
    }

    ////////// matrix subtraction //////////
    public static double[][] mSubtraction(double[][] m1, double[][] m2)
    {
        // check if matrices are of equal size
        if ((m1.length != m2.length) || (m1[0].length != m2[0].length))
        {
            System.out.println("Error trying to subtract matrices. Sizes do not match.");
            return null;
        }

        double[][] result = new double[m1.length][m1[0].length];

        for (int i = 0; i < m1.length; i++)
        {
            for (int j = 0; j < m1[0].length; j++)
            {
                result[i][j] = m1[i][j] - m2[i][j];
            }
        }

        return result;
    }

    ////////// basic matrix multiplication implementation //////////
    public static double[][] mMultiplication(double[][] m1, double[][] m2)
    {
        // check if columns in m1 match the rows of m2
        if (m1[0].length != m2.length)
        {
            System.out.println("Error trying to multiply matrices. Sizes do not match.");
            return null;
        }

        double[][] result = new double[m1.length][m2[0].length];

        for (int i = 0; i < m1.length; i++) 
        {
            for (int j = 0; j < m2[0].length; j++) 
            {
                for (int k = 0; k < m2.length; k++)
                {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }

        return result;
    }

    ////////// element wise multiplcation, also I have two versions of this function //////////
    /// This implements the Hadamard Product talked about in Michael Neilson's textbook
    /// Multiples each element in a matrix by the corresponding element in the other matrix
    public static double[][] elementMultiplication(double[][] m1, double[][] m2) // i am NOT calling this the hadamard product because i will forget so quickly
    {
        // check if matrices are of equal size
        if ((m1.length != m2.length) || (m1[0].length != m2[0].length))
        {
            System.out.println("Error trying to do element wise multiplication. Sizes do not match.");
            return null;
        }

        double[][] result = new double[m1.length][m1[0].length];

        for (int i = 0; i < m1.length; i++)
        {
            for (int j = 0; j < m1[0].length; j++)
            {
                result[i][j] = m1[i][j] * m2[i][j];
            }
        }

        return result;
    }

    /// this version of it instead just multiplies each element in the array m1
    /// by a scalar number called num, i didn't feel like making a brand new
    /// function so i just overloaded it
    public static double[][] elementMultiplication(double[][] m1, double num)
    {

        double[][] result = new double[m1.length][m1[0].length];

        for (int i = 0; i < m1.length; i++)
        {
            for (int j = 0; j < m1[0].length; j++)
            {
                result[i][j] = m1[i][j] * num;
            }
        }

        return result;
    }

    ////////// returns a transposed version of the matrix //////////
    /// Iterates through each element in the original matrix and sets it
    /// in the opposite spot in the new matrix
    public static double[][] transpose(double[][] matrix)
    {
        double[][] result = new double[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }

    ///////////////////////////////// HELPER FUNCTIONS ADDED IN PART 2 /////////////////////////////////
    static double[][] randMatrix(int rows, int cols)
    {
        double[][] matrix = new double[rows][cols];

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                matrix[i][j] = random.nextDouble() * 2 -1;
            }
        }

        return matrix;
    }

    static double[][] getTrainingData()
    {
        String filePath = "mnist_train.csv";  // 60,000 line mnist training set
        double[][] matrix = new double[60000][785];

        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Read line by line
            for(int i = 0; i < 60000; i++) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                // Next 785 numbers = pixels
                double[] data = new double[785];
                data[0] = Double.parseDouble(values[0]);
                for (int j = 1; j < values.length; j++) {
                    data[j] = Double.parseDouble(values[j]) / 255.0;
                }

                matrix[i] = data;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return matrix;
    }

    public static double[][] flipArray(double[] array)
    {
        double[][] result = new double[array.length][1];

        for (int i = 0; i < array.length; i++)
        {
            result[i][0] = array[i];
        }

        return result;
    }

    static int guessLabel(double[][] matrix)
    {
        int max_i = 0;
        double max = matrix[0][0];

        for (int i = 1; i < matrix.length; i++)
        {
            if (matrix[i][0] > max)
            {
                max = matrix[i][0];
                max_i = i;
            }
        }

        return max_i;
    }

    public static double sum(double[] array)
    {
        double total = 0.0;

        for (int i = 0; i < array.length; i++)
        {
                total += array[i];
        }

        return total;
    }

    public static void fillMatrix(double[][] matrix, double fill)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                matrix[i][j] = fill;
            }
        }
    }

}
