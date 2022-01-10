package handwriting.core;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

// All implementations should have a constructor that takes zero arguments and
// prepares the classifier to be trained.

public interface RecognizerAI {
	// The progress queue is used for updating the progress bar.
	// When the method completes, place a 1.0 in the queue.
	void train(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException;
	
	// May return "Unknown" if it has not been trained.
	String classify(Drawing d);

	JPanel getVisualization();
	
	default boolean allTestsCorrect(SampleData testData) {
		return numCorrectTests(testData) == testData.numDrawings();
	}
	
	// Returns the number of samples from testData that were correctly classified
	default int numCorrectTests(SampleData testData) {
		int passed = 0;
		for (String label: testData.allLabels()) {
			for (int j = 0; j < testData.numDrawingsFor(label); ++j) {
				if (classify(testData.getDrawing(label, j)).equals(label)) {
					passed += 1;
				}
			}
		}
		return passed;
	}

	static double stdDev(ArrayList<Integer> data) {
		double mean = RecognizerAI.mean(data);
		double ssd = 0.0;
		for (int d: data) {
			double diff = mean - d;
			ssd += diff * diff;
		}
		double variance = ssd / data.size();
		return Math.sqrt(variance);
	}

	static double mean(ArrayList<Integer> data) {
		double sum = 0.0;
		for (int d: data) {sum += d;}
		return sum / data.size();
	}
}