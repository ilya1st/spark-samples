import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SQL {
    private final static Logger LOGGER = LoggerFactory.getLogger(SQL.class);
    private final static String APP_NAME = "SQL";
    private final static String FILE_PATH = "examples/src/main/resources/people.json";
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(APP_NAME);
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();
        Dataset<Row> df = spark.read().json(FILE_PATH);
        df.show();
        df.printSchema();
        System.out.println("Number of partitions"+ df.rdd().partitions().length);
        /*
        df.select("name").show();
        df.select(col("name"), col("age").plus(1)).show();
        df.filter(col("age").gt(21)).show();
        df.filter(col("age").gt(21)).show();
        df.groupBy("age").count().show();*/
        Dataset<Row> sqlDF = spark.sql("SELECT * FROM people");
        sqlDF.show();
        // Register the DataFrame as a global temporary view
        //df.createGlobalTempView("people");
// Global temporary view is tied to a system preserved database `global_temp`
        //spark.sql("SELECT * FROM global_temp.people").show();
        //spark.createDataset();
    }
}
