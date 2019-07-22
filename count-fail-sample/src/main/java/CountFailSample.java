import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CountFailSample {
    private final static Logger LOGGER = LoggerFactory.getLogger(CountFailSample.class);
    private final static String APP_NAME = "CountFailSample";
    private final static String FILE_PATH = "/home/brainstorm/spark-2.4.3-bin-hadoop2.7/README.md";
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(APP_NAME);
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        Dataset<String> logData = spark.read().textFile(FILE_PATH);
        System.out.println("Number of strings: " + logData.count());
        logData = spark.read().textFile(FILE_PATH);
        List<String> lsr = logData.collectAsList();
        System.out.println("ourlist: " + lsr.stream().reduce((s1, s2)->s1+s2).get());
        JavaRDD<String> rdd = sc.parallelize(lsr, 2);
        System.out.println( "Number of data partitiond: " + rdd.getNumPartitions());
        AtomicInteger oInt = new AtomicInteger(0);
        System.out.println(  "Start sum counters");
        rdd.foreach( (item)-> {
            System.out.println(
                    "GOT STRING : " + oInt.incrementAndGet() + " IN FOREACH: " + item);
        });
        System.out.println( "counted via rdd foreach" + oInt.get());
        spark.stop();
    }
}
