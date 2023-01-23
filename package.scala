import org.apache.spark.sql.{DataFrame, SparkSession}
import com.danielasfregola.twitter4s.TwitterClient
import com.danielasfregola.twitter4s.TwitterRestClient
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext



object TwitterDataProcessor {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("TwitterDataProcessor")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Twitter Credentials
    val consumerKey = "mA2esTAMULdF5bofqwu5HM022"
    val consumerSecret = "gzZb2Zs5Uri7mdObu8lQKIHiNP438GRIrHpZ9kAOeyveLQuSJl"
    val accessToken = "714230983-u9IytadUnD8IUXapARMWHd1iR6Qix6AYyoUsqTkB"
    val accessTokenSecret = "ACZ3UGYXmkCvxDROo5LcyBaCBeTOQtKh11MA4PasMuirf"

    // Extract tweets from Twitter API
    val tweets = spark.read
      .format("com.danielasfregola.twitter4s.TwitterSource")
      .option("consumerKey", consumerKey)
      .option("consumerSecret", consumerSecret)
      .option("accessToken", accessToken)
      .option("accessTokenSecret", accessTokenSecret)
      .option("query", "#scala")
      .load()

    // Transformation and Aggregation
    val tweetsByLanguage = tweets
      .groupBy("lang")
      .count()
      .sort(desc("count"))

    // Adding comments
    // Filter tweets with more than 50 retweets
    val popularTweets = tweets
      .filter($"retweet_count" > 50)

    //Save to RDS MySQL
    val url = "jdbc:mysql://rds-endpoint:3306/database"
    val table = "tweets"
    val properties = new java.util.Properties()
    properties.setProperty("user", "admin")
    properties.setProperty("password", "Pardon2010#")
    properties.setProperty("ssl", "true")
    popularTweets.write.jdbc(url, table, properties)

    // Stop Spark session
    spark.stop()
  }
}
