import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.util.LongAccumulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RightCounter {
    private final static Logger LOGGER = LoggerFactory.getLogger(RightCounter.class);
    private final static String APP_NAME = "RightCounter";
    private final static String FILE_PATH = "/home/brainstorm/spark-2.4.3-bin-hadoop2.7/README.md";
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(APP_NAME);
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        JavaRDD<String> rdd = sc.textFile(FILE_PATH, 2);
        JavaPairRDD<String,Long> rddIndexed = rdd.zipWithIndex();
        LongAccumulator accumThenCounter = sc.sc().longAccumulator();
        rdd.foreach((s)-> {
            if (s.contains("Scala")||s.contains("scala")) {
                accumThenCounter.add(1L);
            }
        });
        System.out.println("World \"Scala\" found " + accumThenCounter.value() + " times");
        spark.stop();
    }
}
