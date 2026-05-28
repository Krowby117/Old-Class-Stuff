import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class test 
{
    static int mb_size = 10;
    static int eta = 3;
    static int epochs = 30;

    static Random rand = new Random();
    public static void main(String[] args) 
    {
        
        double[][] training_data = getTrainingData();
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < 60000; i++)
        {
            indices.add(i);
        }

        mPrint(randMatrix(3, 3));
    }

    static double[][] randMatrix(int rows, int cols)
    {
        double[][] matrix = new double[rows][cols];

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                matrix[i][j] = rand.nextDouble(1 - -1 + 1) + -1;
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
                for (int j = 0; j < values.length; j++) {
                    data[j] = Double.parseDouble(values[j]) / 255;
                }

                matrix[i] = data;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return matrix;
    }

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
}
