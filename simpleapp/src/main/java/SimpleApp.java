import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
public class SimpleApp {
  public static void main(String[] args) {
    // Should be some file on your system
    String logFile = "/home/brainstorm/spark-2.4.3-bin-hadoop2.7/README.md";
    SparkSession spark = SparkSession.builder().appName("Simple Application").getOrCreate();
    Dataset<String> logData = spark.read().textFile(logFile).cache();
    long numAs = logData.filter(
      (String s) -> {return s.contains("a");}).count();
    long numBs = logData.filter((String s) -> {return s.contains("b");}).count();
    System.out.println("Lines with 'a': " + numAs + ", lines with 'b': " + numBs);
    spark.stop();
  }
}
