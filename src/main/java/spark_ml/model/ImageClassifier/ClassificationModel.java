package spark_ml.model.ImageClassifier;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;
import spark_ml.imageClassifier.utils.ImageFilter;
import spark_ml.imageClassifier.utils.Utilities;

/**
 * @author Ripunjoy Gohain <ripunjoygohain79@gmail.com>
 * @version 1.0
 * 
 * Build a basic model, giving 88.88 % accuracy (less train images, no normalization or PCA performed)
 * 
 * The motive of the project is not to IMPROVE the model
 * The motive is to show how SPARK can be used for model building
 */

public class ClassificationModel {
	static int scaledWidth = 100;
	static int scaledHeight = 100;

	// Directory for Apple
	static File parentdir = new File("resources/ImageProcess/");

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		// SparkSession spark =
		// SparkSession.builder().appName("Classifier").getOrCreate();

		//SparkConf conf = new SparkConf().setAppName("Classifier").setMaster("local[2]");
		//JavaSparkContext jsc = new JavaSparkContext(conf);
		
		SparkSession spark = SparkSession
	            .builder()
	            .appName("ImageClassifier")
	            .master("local[2]")
	            .getOrCreate();
		
		JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());


		// It gives a empty set
		JavaRDD<LabeledPoint> data = jsc.emptyRDD();

		int lablefolder = 0;

		for (File subdir : parentdir.listFiles()) {
			int numimages = 0;
			if (subdir.isDirectory()) {

				for (File imageFile : subdir.listFiles()) {

					if (ImageFilter.accept(imageFile)) {

						double[] pixels = Utilities.ConvertGrayPixels(imageFile, scaledWidth, scaledHeight);

						JavaRDD<LabeledPoint> newdata = data.union(jsc.parallelize(
								Arrays.asList(new LabeledPoint((double) lablefolder, Vectors.dense(pixels)))));
						data = newdata;
						System.out.println("Extracting + " + imageFile);
						numimages++;
					}
				}
				System.out.println(subdir + " Dir files " + numimages);
				// So that we can label the different folder images with
				// different label
				lablefolder++;
			}
		}

		System.out.println("Total dir " + lablefolder);

		// printing the labels to check the dataset
		data.foreach(i -> System.out.println("Printing the labels : " + i.label()));

		// Split initial RDD into two... [60% training data, 40% testing data].
		JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[] { 0.8, 0.2 }, 11L);
		JavaRDD<LabeledPoint> training = splits[0].cache();
		JavaRDD<LabeledPoint> test = splits[1];

		// Run training algorithm to build the model.
		LogisticRegressionModel model = new LogisticRegressionWithLBFGS().setNumClasses(2).run(training.rdd());

		// Compute raw scores on the test set.
		JavaPairRDD<Object, Object> predictionAndLabels = test
				.mapToPair(p -> new Tuple2<>(model.predict(p.features()), p.label()));

		// Get evaluation metrics.
		MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());
		double accuracy = metrics.accuracy();
		System.out.println("Accuracy = " + accuracy * 100 + " %");
		
		//Check the time taken to complete all operations
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Time taken : " + elapsedTime / 1000 + " seconds");
		
		
//		// Save and load model (TODO: Some Hadoop Dependency issue)
//		model.save(spark.sparkContext(), "resources/model/javaLogisticRegressionWithLBFGSModel");
//		//Loading
//		LogisticRegressionModel sameModel = LogisticRegressionModel.load(spark.sparkContext(),
//				"resources/model/javaLogisticRegressionWithLBFGSModel");
		
		jsc.close();
	}
}
