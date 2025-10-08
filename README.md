# Perceptron Language Classifier

A simple **Perceptron-based** classifier in Java that distinguishes between four languages:
- **English**
- **Spanish**
- **German**
- **Polish**

The model uses **two perceptrons** to perform multi-class classification, extending the classic perceptron algorithm to handle multiple languages.

---

## 🧠 How It Works

This project demonstrates how a **single-layer perceptron network** can be used for **language classification**.  
Each perceptron learns to recognize specific language features based on character frequency or textual patterns.

- Two perceptrons are used to separate the four languages.  
- The final classification is determined by combining their outputs.  
- Parameters such as learning rate, epochs, and dataset files are fully configurable.

---

## 🚀 How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/shehabeldin-mohamed/Perceptron-Language-Classifier.git
   cd Perceptron-Language-Classifier
2. **Move to src and compile the program**
   ```bash
   cd src
   javac Perceptron.java
   javac SingleLayerNetwork.java
   javac Main.java
3. **Run the program by specifying the learning rate, training dataset, and test dataset
For example:**
   ```bash
   java Main.java 0.01 lang.train.csv lang.test.csv
