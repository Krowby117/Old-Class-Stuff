//////////////////////////////// PROGRAM DESCRIPTION //////////////////////////////////
/// Uses a hand coded neural network trained on the Mnist data set to recognize the
/// handwritten digits 0-9. Also includes a small menu with multiple features to use!
/// These features include: 
///     [1] Training the network
///     [2] Uploading a pre-trained network state
///     [3] Display the network's stats on TRAINING data
///     [4] Display the network's stats on TESTING data
///     [5] Run the network in TESTING data, showing images and labels
///     [6] Display misclassified TESTING images
///     [7] Save the current network state to a file   
///////////////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class MNIST_NN 
{
    /////// Some global variables used in the main program loop.
    static Random random = new Random();
    static Scanner input = new Scanner(System.in);

    static double eta = 3;
    static int epochs = 30;

    static int mb_size = 10;

    static int input_nodes = 784;
    static int hidden_nodes = 100;              // this can be changed and the program will run accordingly
    static int output_nodes = 10;

    static boolean trained = false;

    static double[][] accuracy = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},     //  seen
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}      // correct
        };

    static double[][][] outputVetors = { 
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

    // initialize the weight and bias matrices
    static double[][] weights_1 = randMatrix(hidden_nodes, input_nodes);
    static double[][] biases_1 = randMatrix(hidden_nodes, 1);

    static double[][] weights_2 = randMatrix(output_nodes, hidden_nodes);
    static double[][] biases_2 = randMatrix(output_nodes, 1);

    public static void main(String[] args) throws IOException
    {
        ///////////////////////////////////// MAIN PROGRAM RUN THROUGH /////////////////////////////////////
        boolean running = true;

        while (running) {
            boolean asking = true;
            int choice = 0;
            String file = "";

            while (asking) {
                System.out.println("Please Select an Option");
                System.out.println("[1] - Train the network.");
                System.out.println("[2] - Load a pre-trained network.");
                if (trained) {
                    System.out.println("[3] - Display network accuracy on TRAINING data.");
                    System.out.println("[4] - Display network accuracy on TESTING data.");
                    System.out.println("[5] - Run network on TESTING data showing images and labels.");
                    System.out.println("[6] - Display the misclassified TESTING images");
                    System.out.println("[7] - Save the network state to file.");
                }
                System.out.println("[0] - Exit.");
                System.out.print("> ");

                choice = input.nextInt();
                input.nextLine();

                if (trained && (choice < 0 || choice > 7)) {
                    System.out.println("Please enter a valid option.\n");
                } 
                else if (!trained && (choice < 0 || choice > 2)) {
                    System.out.println("Please enter a valid option.\n");
                } 
                else {
                    asking = false;
                }
            }

            System.out.println();

            switch (choice) {
                case 1:     // train the model
                    System.out.println("Training with " + hidden_nodes + " nodes in the hidden layer.");
                    System.out.println("Note: Training with 100 nodes in the hidden layer takes ~9 minutes.");
                    train();
                    trained = true;
                    pauseBreak();
                    break;
                case 2:     // load a pre-trained model
                    System.out.print("Please enter the file name: ");
                    file = input.next();
                    input.nextLine(); 
                    loadNetwork(file);
                    trained = true;
                    pauseBreak();
                    break;
                case 3:     // show training scores
                    System.out.println("Showing training accuracy: \n");
                    test(0, 0);     // print accuracy after running through the training data
                    pauseBreak();
                    break;
                case 4:     // test and show testing scores
                    System.out.println("Showing testing accuracy: \n");
                    test(0, 1);    // means to only print the accuracy after testing
                    pauseBreak();
                    break;
                case 5:     // Run network on TESTING data showing images and labels.
                    test(1, 1);    // showing images and labels
                    clear();
                    break;
                case 6:     // Display the misclassified TESTING images
                    
                    test(2, 1);    // showing incorrect images and labels
                    clear();
                    break;
                case 7:     // save network status to file
                    System.out.print("Please enter the file name: ");
                    file = input.next();
                    input.nextLine(); 
                    saveNetwork(weights_1, weights_2, biases_1, biases_2, file);
                    System.out.println("Saved. \n");
                    pauseBreak();
                    break;
                default:
                    System.out.println("Goodbye.");
                    running = false;
            }
        }

        input.close();

    }

    // method to run the TESTING data through the network.
    public static void test(int outputType, int data)
    {
        double[][] mnist_testing_data;
        if (data == 0) {mnist_testing_data = getTrainingData();}   // loads in training data
        else {mnist_testing_data = getTestingData();}   // loads in testing data

        // keep track of seen vs correct for accuracy data
        double[] seen = new double[10];
        double[] correct = new double[10];

        double[][] training_data;
        int label;
        int guess;

        //Scanner input = new Scanner(System.in);
        String in = "";
        boolean testing = true;

        // initiliaze these here so that it doesn't re-allocate every time.
        double[][] l1_a;
        double[][] l2_a;

        for (int i = 0; i < mnist_testing_data.length && testing; i++)
        {
            // takes the ith input array from the testing data and turns it into a 784x1 2d array
            training_data = flipArray(Arrays.copyOfRange(mnist_testing_data[i], 1, mnist_testing_data[i].length));
            label = (int) mnist_testing_data[i][0]; // grabs the first value which is the label

            seen[label]++;

            // grabs activations for layer 1
            l1_a = layerActivation(weights_1, training_data, biases_1);

            // grabs activations for layer 1
            l2_a = layerActivation(weights_2, l1_a, biases_2);

            // takes the final output and finds the guess
            guess = guessLabel(l2_a);
            if (guess == label)
            {
                correct[label]++;
            }
            else if (outputType == 2)   // if guess does not match the label and the option to ONLY print incorrect results was chosen
            {
                clear();
                System.out.println("Test #" + (i + 1) + ": Correct Classification = " + label + " Network Output = " + guess + " Incorrect");    // top label

                for (int j = 0; j < 28; j++)
                {
                    for (int k = 0; k < 28; k++)
                    {
                        System.out.print(training_data[k + (28 * j)][0] > 0 ? "# " : ". ");            // fancy prints the 28x28 image from the 784 input array
                    }
                    System.out.println();
                }

                System.out.println("[0] Stop  |  [Enter] Continue.");
                in = input.nextLine();
                if (in.equals("0")) {testing = false;}
            }

            if (outputType == 1)    // if the option to print all inputs was chosen
            {
                clear();
                System.out.print("Test #" + (i + 1) + ": Correct Classification = " + label + " Network Output = " + guess);    // top label
                if (guess == label){System.out.print(" Correct.\n");}
                else {System.out.print(" Incorrect.\n");}

                for (int j = 0; j < 28; j++)
                {
                    for (int k = 0; k < 28; k++)
                    {
                        System.out.print(training_data[k + (28 * j)][0] > 0 ? "# " : ". ");         // fancy prints the 28x28 image from the 784 input array
                    }
                    System.out.println();
                }

                System.out.println("[0] Stop  |  [Enter] Continue.");
                in = input.nextLine();
                if (in.equals("0")) {testing = false;}
            }

        }

        if (outputType == 0)    // if the option to print testing accuracy was chosen
        {
            for (int i = 0; i < 10; i++)
            {
                System.out.println(i + ": " + correct[i] + "/" + seen[i]);
            }
            System.out.println("% : " + sum(correct) + "/" + sum(seen) + " : " + ((sum(correct) / sum(seen)) * 100) + "\n");
        }
    }

    public static void train() throws IOException
    {
        double[][] mnist_training_data = getTrainingData();                 // grabs the training data
        ArrayList<Integer> indices = new ArrayList<>();                     // creates an array of numbers 0-59,999
        for (int i = 0; i < 60000; i++)
        {
            indices.add(i);
        }

        int num_batches = indices.size() / mb_size;                         // sets number of batches based on predefined minibatch size
        double[][][] batches = new double[num_batches][mb_size][785];       // initialize batch arrays, 3d array holds all batches and 2d array holds a single batch
        double[][] batch;

        double[][] training_data;
        double[][] expected_output;

        /// randomize initial weights and biases
        weights_1 = randMatrix(hidden_nodes, input_nodes);
        biases_1 = randMatrix(hidden_nodes, 1);

        weights_2 = randMatrix(output_nodes, hidden_nodes);
        biases_2 = randMatrix(output_nodes, 1);

        /// initialize all of the 2D arrays used for calculations
        // do that here so it only happens once and not ever loop
        double[][] l1_a;
        double[][] l2_a;

        double[][] l2_e;
        double[][] l2_wg;

        double[][] l1_e;
        double[][] l1_wg;

        double[][] total_wg_1 = new double[weights_1.length][weights_1[0].length];
        double[][] total_wg_2 = new double[weights_2.length][weights_2[0].length];

        double[][] total_bg_1 = new double[biases_1.length][biases_1[0].length];
        double[][] total_bg_2 = new double[biases_2.length][biases_2[0].length];

        for (int e = 0; e < epochs; e++) //////////////////////////////////////////////////////////////////////////////////////////////// FOR EACH EPOCH ////////
        {
            /// randomize training set using Fisher-Yates method
            for (int i = indices.size() - 1; i > 0; i--) 
            {
                int j = random.nextInt(i + 1);
                int temp = indices.get(i);
                indices.set(i, indices.get(j));
                indices.set(j, temp);
            }

            /// break into mini-batches
            for (int mb = 0; mb < num_batches; mb++)
            {
                for (int i = 0; i < mb_size; i++)
                {
                    int idx = indices.get(mb * mb_size + i);
                    batches[mb][i] = mnist_training_data[idx];
                }
            }

            /// holds the total amount of each number seen and guessed correctly
            fillMatrix(accuracy, 0);

            for (int mb = 0; mb < num_batches; mb++) ////////////////////////////////////////////////////////////////////////////// FOR EACH MINI-BATCH ////////
            {   
                //////// SET THE ACTIVE MINI-BATCH ////////
                batch = batches[mb];

                //////// RESET THE TOTAL WEIGHT AND BIAS MATRICES ////////
                fillMatrix(total_wg_1, 0.0);
                fillMatrix(total_wg_2, 0.0);
                fillMatrix(total_bg_1, 0.0);
                fillMatrix(total_bg_2, 0.0);

                for (int i = 0; i < mb_size; i++) ///////////////////////////////////////////////////////////////// FOR EACH TRAINING SET ////////
                {
                    //////// SET ACTIVE TRAINING DATA AND EXPECTED OUTPUT ////////
                    training_data = flipArray(Arrays.copyOfRange(batch[i], 1, batch[i].length));
                    int label = (int) batch[i][0];
                    expected_output = outputVetors[label];

                    accuracy[0][label]++;

                    //////// CALCULATE ACTIVATIONS, ERROR, AND WEIGHT GRADIENTS FOR ALL LAYERS ////////
                    l1_a = layerActivation(weights_1, training_data, biases_1);

                    l2_a = layerActivation(weights_2, l1_a, biases_2);

                    if (guessLabel(l2_a) == label)
                    {
                        accuracy[1][label]++;       // keeps track of each input guessed correctly
                    }

                    l2_e = final_layer_error(l2_a, expected_output);
                    l2_wg = weightGradient(l1_a, l2_e);

                    l1_e = hidden_layer_error(l1_a, l2_e, weights_2);
                    l1_wg = weightGradient(training_data, l1_e);
                    
                    //////// KEEP TRACK OF WEIGHT AND BIAS GRADIENTS WITHIN MINI-BATCH ////////
                    mAddition(total_wg_1, l1_wg);
                    mAddition(total_wg_2, l2_wg);

                    mAddition(total_bg_1, l1_e);
                    mAddition(total_bg_2, l2_e);

                }

                //////// AT THE END OF EACH MINI-BATCH UPDATE THE WEIGHTS AND BIASES ////////
                weights_1 = updateWeightOrBias(weights_1, eta, mb_size, total_wg_1);
                biases_1 = updateWeightOrBias(biases_1, eta, mb_size, total_bg_1);

                weights_2 = updateWeightOrBias(weights_2, eta, mb_size, total_wg_2);
                biases_2 = updateWeightOrBias(biases_2, eta, mb_size, total_bg_2);

            }
            /// at the end of each epoch pring the accuracy
            //System.out.println("We're here!!!");
            System.out.println("#### Accuracy after Epoch " + (e + 1) + "\n");
            for (int i = 0; i < 10; i++)
            {
                System.out.println(i + ": " + accuracy[1][i] + "/" + accuracy[0][i]);
            }
            System.out.println("% : " + sum(accuracy[1]) + "/" + sum(accuracy[0]) + " : " + ((sum(accuracy[1]) / sum(accuracy[0])) * 100) + "\n");
        }

        trained = true;
    }

    /////////////////////////////////////////////////////////////////////// FUNCTIONS USED FOR THE NETWORK CALCULATIONS ///////////////////////////////////////////////////////////////////////
    ////////// calculates the new weight or bias matrix //////////
    public static double[][] updateWeightOrBias(double[][] oldWeights, double learning_rate, double size_of_trainingset, double[][] weight_gradient)
    {
        // using formula
        // new_weight[j][k] = old_weight[j]k] - (learning_rate / size_of_trainingset * summation(weight_gradient[j][k]))

        double[][] result = new double[oldWeights.length][oldWeights[0].length];

        double eta_fraction = learning_rate / size_of_trainingset;                              // eta / size_of_trainingset

        for (int i = 0; i < oldWeights.length; i++)
        {
            for (int j = 0; j < oldWeights[0].length; j++)
            {
                result[i][j] = oldWeights[i][j] - (eta_fraction * weight_gradient[i][j]);
            }
        }

        return result;
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
        double[][] result = new double[activations.length][activations[0].length];

        for (int i = 0; i < activations.length; i++)
        {
            for (int j = 0; j < activations[0].length; j++)
            {
                result[i][j] = (activations[i][j] - expected[i][j]) * activations[i][j] * (1 - activations[i][j]);
            }
        }

        return result;
    }

    ////////// hidden layer error function //////////
    public static double[][] hidden_layer_error(double[][] activations, double[][] previous_layer_error, double[][] weights_of_layer)
    {
        double[][] result = new double[activations.length][activations[0].length];

        //// implimentation of the formula:
        // error = (weights(l+1)T * error(l+1)) * activations * (1 - activations)

        double[][] bpError = mMultiplication(transpose(weights_of_layer), previous_layer_error);            // (weights * error)

        for (int i = 0; i < activations.length; i++)
        {
            for (int j = 0; j < activations[0].length; j++)
            {
                result[i][j] = bpError[i][j] * activations[i][j] * (1 - activations[i][j]);
            }
        }

        return result;
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

    ////////// matrix addition //////////
    public static void mAddition(double[][] m1, double[][] m2)
    {
        // check if matrices are of equal size
        if ((m1.length != m2.length) || (m1[0].length != m2[0].length))
        {
            System.out.println("Error trying to add matrices. Sizes do not match.");
        }

        for (int i = 0; i < m1.length; i++)
        {
            for (int j = 0; j < m1[0].length; j++)
            {
                m1[i][j] += m2[i][j];
            }
        }
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
    public static double[][] mMultiplication(double[][] A, double[][] B) 
    {
        int aRows = A.length, aCols = A[0].length, bCols = B[0].length;
        double[][] result = new double[aRows][bCols];

        double[][] bT = new double[bCols][aCols];
        for (int i = 0; i < aCols; i++) {
            for (int j = 0; j < bCols; j++) {
                bT[j][i] = B[i][j];
            }
        }

        for (int i = 0; i < aRows; i++) {
            double[] aRow = A[i];
            double[] resRow = result[i];
            for (int j = 0; j < bCols; j++) {
                double[] bRow = bT[j];
                double sum = 0;
                for (int k = 0; k < aCols; k++) {
                    sum += aRow[k] * bRow[k];
                }
                resRow[j] = sum;
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

        for (int i = 0; i < m1.length; i++)
        {
            for (int j = 0; j < m1[0].length; j++)
            {
                m1[i][j] = m1[i][j] * m2[i][j];
            }
        }

        return m1;
    }

    /// this version of it instead just multiplies each element in the array m1
    /// by a scalar number called num, i didn't feel like making a brand new
    /// function so i just overloaded it
    public static double[][] elementMultiplication(double[][] m1, double num)
    {

        for (int i = 0; i < m1.length; i++)
        {
            for (int j = 0; j < m1[0].length; j++)
            {
                m1[i][j] = m1[i][j] * num;
            }
        }

        return m1;
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
    // just creates a matrix of the given size and fills it with random numbers
    // used this to set random initial weights and biases
    static double[][] randMatrix(int rows, int cols)
    {
        double[][] matrix = new double[rows][cols];

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                matrix[i][j] = random.nextDouble() * 2 -1;  // just fills each element with a random value
            }
        }

        return matrix;
    }

    // reads in the mnist training file and makes it a 2d array
    static double[][] getTrainingData()
    {
        String filePath = "mnist_train.csv";  // 60,000 line mnist training set
        double[][] matrix = new double[60000][785];

        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Read line by line
            for(int i = 0; i < 60000; i++) {
                String line = scanner.nextLine();                       // grabs the next line
                String[] values = line.split(",");                      // splits it by comma

                // Next 785 numbers = pixels
                double[] data = new double[785];
                data[0] = Double.parseDouble(values[0]);
                for (int j = 1; j < values.length; j++) {
                    data[j] = Double.parseDouble(values[j]) / 255.0;    // normalizes it
                }

                matrix[i] = data;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return matrix;
    }

    // reads in the mnist testing file and makes it a 2d array
    static double[][] getTestingData()
    {
        String filePath = "mnist_test.csv";  // 10,000 line mnist training set
        double[][] matrix = new double[10000][785];

        try (Scanner scanner = new Scanner(new File(filePath))) {
            // Read line by line
            for(int i = 0; i < 10000; i++) {
                String line = scanner.nextLine();                       // grabs the next line
                String[] values = line.split(",");                      // splits it by comma

                // Next 785 numbers = pixels
                double[] data = new double[785];
                data[0] = Double.parseDouble(values[0]);
                for (int j = 1; j < values.length; j++) {
                    data[j] = Double.parseDouble(values[j]) / 255.0;    // normalizes it
                }

                matrix[i] = data;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return matrix;
    }

    // I used this to take in an array and flip it into a matrix
    // takes in a 1xN array and returns an Nx1 matrix
    // really just used this for converting the input into a matrix
    public static double[][] flipArray(double[] array)
    {
        double[][] result = new double[array.length][1];

        for (int i = 0; i < array.length; i++)
        {
            result[i][0] = array[i];
        }

        return result;
    }

    // used to find the max value in the output array and
    // return the corresponding label
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

    // adds up all the elements in an array
    public static double sum(double[] array)
    {
        double total = 0.0;

        for (int i = 0; i < array.length; i++)
        {
                total += array[i];
        }

        return total;
    }

    // adds up all the elements in a matrix
    public static double sum(double[][] matrix)
    {
        double total = 0.0;

        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                total += matrix[i][j];
            }
        }

        return total;
    }

    // fills a given matrix with a number, really just used to zero out a matrix
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

    // fills a given array with a number, agaim really just used to zero out an array
    public static void fillArray(double[] array, double fill)
    {
        for (int i = 0; i < array.length; i++)
        {
            array[i] = fill;
        }
    }

    /// takes the current state of the network and saves it to a file
    /// saves the weights, biases, and accuracy
    public static void saveNetwork(double[][] w1, double[][] w2, double[][] b1, double[][] b2, String fileName) throws IOException 
    {
        File myFile = new File(fileName);
        if (myFile.createNewFile()) 
        { 
            System.out.println("File created: " + myFile.getName());                        // attempts to create the file if it doesn't already exist
        }  
        try (PrintWriter out = new PrintWriter(new FileWriter(myFile)))                     // for each thing it prints its rows and columns
        {                                                                                   // then prints all of its elements afterwards.
            // Save weight1 matrix                                                          // it repeats this for each thing that it prints to the file
            out.println(w1.length + " " + w1[0].length);
            for (double[] row : w1) {
                for (double val : row)
                    out.print(val + " ");
                out.println();
            }

            // Save weight2 matrix
            out.println(w2.length + " " + w2[0].length);
            for (double[] row : w2) {
                for (double val : row)
                    out.print(val + " ");
                out.println();
            }

            // Save bias1 matrix
            out.println(b1.length + " " + b1[0].length);
            for (double[] row : b1) {
                for (double val : row)
                    out.print(val + " ");
                out.println();
            }

            // Save bias2 matrix
            out.println(b2.length + " " + b2[0].length);
            for (double[] row : b2) {
                for (double val : row)
                    out.print(val + " ");
                out.println();
            }
        }
    }

    /// takes in a file and sets it as the current state of the network
    /// sets the weights, biases, and accuracy
    public static void loadNetwork(String file)
    {
        try (Scanner sc = new Scanner(new File(file))) 
        {
            int rows;
            int cols;

            for (int i = 0; i < 4; i++)
            {
                rows = sc.nextInt();                                        // reads in the rows and columns put before each thingy
                cols = sc.nextInt();

                if (i == 0)
                {
                    hidden_nodes = rows;
                    weights_1 = new double[rows][784];                      // checks the first number and allocates space for the weight and biases
                    weights_2 = new double[10][rows];                       // this just lets me upload states with a different number of hidden layer nodes
                    biases_1 = new double[rows][1];
                }

                for (int j = 0; j < rows; j++)
                {
                    for (int k = 0; k < cols; k++)                          // nested for loops read in the data
                    {   
                        switch (i) {                                        // switch case that adds it to the corresponding matrix
                            case 0:
                                weights_1[j][k] = sc.nextDouble();
                                break;
                            case 1:
                                weights_2[j][k] = sc.nextDouble();
                                break;
                            case 2:
                                biases_1[j][k] = sc.nextDouble();
                                break;
                            case 3:
                                biases_2[j][k] = sc.nextDouble();
                                break;
                        }
                    }
                }
            }

            System.out.println("Loaded. \n");
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("Error: File not found - " + file + "\n");
        } 
        catch (Exception e) 
        {
            System.err.println("An unexpected error occurred: " + e.getMessage() + "\n");
        }

    }

    public static void clear()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n");
    }

    public static void pauseBreak()
    {
        //Scanner check = new Scanner(System.in);
        System.out.print("[Enter] To continue. "); 
        input.nextLine();    

        clear();
    }

}
