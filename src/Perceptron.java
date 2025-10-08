import java.util.Random;

public class Perceptron {
    private double[] weights;
    private double bias;
    private double learningRate;
    private int epochs;

    public Perceptron(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
    }

    private int activation(double x) {
        return x >= 0 ? 1 : 0;
    }

    public void train(double[][] X, int[] y) {
        int numSamples = X.length;
        int numFeatures = X[0].length;

        weights = new double[numFeatures];
        bias = Math.random();

        Random rand = new Random();
        for (int i = 0; i < numFeatures; i++) {
            weights[i] = rand.nextDouble();
        }

        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < numSamples; i++) {
                double netValue = 0.0;
                for (int j = 0; j < numFeatures; j++) {
                    netValue += X[i][j] * weights[j];
                }
                netValue -= bias;

                int predY = activation(netValue);
                int error = y[i] - predY;

                for (int j = 0; j < numFeatures; j++) {
                    weights[j] += learningRate * error * X[i][j];
                }
                bias -= learningRate * error;
            }
        }
    }

    public int predict(double[] X) {
        double netInput = 0.0;
        for (int i = 0; i < X.length; i++) {
            netInput += X[i] * weights[i];
        }
        netInput -= bias;
        return activation(netInput);
    }

    public double predictRaw(double[] X) {
        double netInput = 0.0;
        for (int i = 0; i < X.length; i++) {
            netInput += X[i] * weights[i];
        }
        return netInput - bias;
    }

    public double accuracy(double[][] X, int[] y) {
        int correct = 0;
        for (int i = 0; i < X.length; i++) {
            if (predict(X[i]) == y[i]) {
                correct++;
            }
        }
        return (correct / (double) X.length) * 100.0;
    }
}
