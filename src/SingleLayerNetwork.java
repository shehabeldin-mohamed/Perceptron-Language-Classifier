public class SingleLayerNetwork {
    private Perceptron[] perceptrons;
    private String[] classes;

    public SingleLayerNetwork(String[] classes, double learningRate, int epochs) {
        this.classes = classes;
        this.perceptrons = new Perceptron[classes.length];
        for (int i = 0; i < classes.length; i++) {
            perceptrons[i] = new Perceptron(learningRate, epochs);
        }
    }

    public void train(double[][] X, int[][] y) {
        for (int i = 0; i < perceptrons.length; i++) {
            int[] binaryTargets = new int[y.length];
            for (int j = 0; j < y.length; j++) {
                binaryTargets[j] = y[j][i];
            }
            perceptrons[i].train(X, binaryTargets);
        }
    }

    public int predict(double[] input) {
        double max = Double.NEGATIVE_INFINITY;
        int label = -1;
        for (int i = 0; i < perceptrons.length; i++) {
            double net = perceptrons[i].predictRaw(input);
            if (net > max) {
                max = net;
                label = i;
            }
        }
        return label;
    }

    public double accuracy(double[][] X, int[][] y) {
        int correct = 0;
        for (int i = 0; i < X.length; i++) {
            int pred = predict(X[i]);
            int trueLabel = -1;
            for (int j = 0; j < y[i].length; j++) {
                if (y[i][j] == 1) {
                    trueLabel = j;
                    break;
                }
            }
            if (pred == trueLabel) correct++;
        }
        return (correct / (double) X.length) * 100.0;
    }

    public void printPerClassAccuracy(double[][] X, int[][] y) {
        int[] total = new int[classes.length];
        int[] correct = new int[classes.length];

        for (int i = 0; i < X.length; i++) {
            int trueLabel = -1;
            for (int j = 0; j < y[i].length; j++) {
                if (y[i][j] == 1) {
                    trueLabel = j;
                    break;
                }
            }
            total[trueLabel]++;
            if (predict(X[i]) == trueLabel) {
                correct[trueLabel]++;
            }
        }

        for (int i = 0; i < classes.length; i++) {
            double acc = total[i] == 0 ? 0 : (correct[i] / (double) total[i]) * 100.0;
            System.out.printf("Accuracy for %s: %.2f%%\n", classes[i], acc);
        }
    }
}
