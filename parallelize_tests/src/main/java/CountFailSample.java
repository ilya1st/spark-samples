import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.spark.sql.functions.col;


public class CountFailSample {
    private final static Logger LOGGER = LoggerFactory.getLogger(CountFailSample.class);
    private final static String APP_NAME = "CountFailSample";
    private final static String FILE_PATH = "examples/src/main/resources/people.json";
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(APP_NAME);
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();
        Dataset<Row> df = spark.read().json(FILE_PATH);
        df.show();
        df.printSchema();
        /*
        df.select("name").show();
        df.select(col("name"), col("age").plus(1)).show();
        df.filter(col("age").gt(21)).show();
        df.filter(col("age").gt(21)).show();
        df.groupBy("age").count().show();*/
    }
}
