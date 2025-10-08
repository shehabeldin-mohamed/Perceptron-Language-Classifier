import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int NUM_FEATURES = 26;
    private static final String[] LANGUAGES = {"English", "German", "Polish", "Spanish"};

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java LanguageClassifier <learning_rate> <train_file> <test_file>");
            return;
        }

        try {
            double learningRate = Double.parseDouble(args[0]);
            String trainFile = args[1];
            String testFile = args[2];

            SingleLayerNetwork network = new SingleLayerNetwork(LANGUAGES, learningRate, 1000);

            List<String[]> trainTexts = loadCSV(trainFile);
            List<String[]> testTexts = loadCSV(testFile);

            double[][] X_train = vectorizeTexts(trainTexts);
            int[][] y_train = encodeLabels(trainTexts);

            double[][] X_test = vectorizeTexts(testTexts);
            int[][] y_test = encodeLabels(testTexts);

            network.train(X_train, y_train);
            double accuracy = network.accuracy(X_test, y_test);
            System.out.printf("Overall Test Set Accuracy: %.2f%%\n", accuracy);

            network.printPerClassAccuracy(X_test, y_test);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nEnter text to classify:");
                String input = scanner.nextLine();
                double[] vector = normalize(letterFrequencies(input));
                int prediction = network.predict(vector);
                System.out.println("✅ Predicted Language: " + LANGUAGES[prediction]);
                System.out.print("Classify another? (y/n): ");
                if (!scanner.nextLine().equalsIgnoreCase("y")) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> loadCSV(String filename) throws IOException {
        List<String[]> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",", 2);
            if (parts.length == 2) {
                data.add(new String[]{parts[0].trim(), parts[1].trim()});
            }
        }
        br.close();
        return data;
    }

    private static double[][] vectorizeTexts(List<String[]> dataset) {
        double[][] vectors = new double[dataset.size()][NUM_FEATURES];
        for (int i = 0; i < dataset.size(); i++) {
            vectors[i] = normalize(letterFrequencies(dataset.get(i)[1]));
        }
        return vectors;
    }

    private static int[][] encodeLabels(List<String[]> dataset) {
        int[][] labels = new int[dataset.size()][LANGUAGES.length];
        for (int i = 0; i < dataset.size(); i++) {
            for (int j = 0; j < LANGUAGES.length; j++) {
                if (dataset.get(i)[0].equalsIgnoreCase(LANGUAGES[j])) {
                    labels[i][j] = 1;
                    break;
                }
            }
        }
        return labels;
    }

    private static double[] letterFrequencies(String text) {
        double[] freq = new double[NUM_FEATURES];
        for (char c : text.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                freq[c - 'a']++;
            }
        }
        return freq;
    }

    private static double[] normalize(double[] vector) {
        double norm = 0.0;
        for (double v : vector) norm += v * v;
        norm = Math.sqrt(norm);
        if (norm == 0) return vector;
        for (int i = 0; i < vector.length; i++) vector[i] /= norm;
        return vector;
    }
}

