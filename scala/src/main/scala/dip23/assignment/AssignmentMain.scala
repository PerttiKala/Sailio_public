package dip23.assignment

// add anything that is required here
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window


object AssignmentMain extends App {
    // Create the Spark session
	val spark: SparkSession = SparkSession.builder()
        .appName("assignment")
        .config("spark.driver.host", "localhost")
        .master("local")
        .getOrCreate()

    // suppress informational log messages related to the inner working of Spark
    spark.sparkContext.setLogLevel("ERROR")


    // COMP.CS.320 Data-Intensive Programming, Assignment
    //
    // The instructions for the tasks in the assignment are given in
    // the markdown file "Assignment-tasks.md" at the root of the repository.
    // This file contains only the given starting code not the instructions.
    //
    // The tasks that can be done in either Scala or Python.
    // This is the Scala version intended for local development.
    //
    // For the local development the source data for the tasks can be located in the data folder of the repository.
    // For example, instead of the path "abfss://shared@tunics320f2023gen2.dfs.core.windows.net/assignment/sales/video_game_sales.csv"
    // for the Basic Task 1, you should use the path "../data/sales/video_game_sales.csv".
    //
    // Each task is separated by the printTaskLine() function. Add your solutions to replace the question marks.
    //
    // Comment out the additional tasks 2 and 3 if you did not implement them.
    //
    // Don't forget to submit your solutions to Moodle once your group is finished with the assignment.



    printTaskLine("Basic Task 1")
    // ==========================

    val salesDataFrame: DataFrame = spark
      .read
      .format("csv")
      .option("header", "true")
      .csv("../data/sales")

    val bestEUPublisher: String = salesDataFrame
      .filter(col("Year") >= 2000 && col("Year") <= 2009)
      .groupBy("Publisher")
      .agg(sum(col("EU_Sales") ).alias("total_sales"))
      .sort(col("total_sales").desc)
      .collect()(0)
      .getString(0)


    val bestEUPublisherSales: DataFrame = salesDataFrame
      .filter(col("Year") >= 2000 && col("Year") <= 2009 && col("Publisher") === "Nintendo")
      .groupBy("Year")
      .agg(sum(col("EU_Sales")).alias("eu_sales"), sum(col("Global_Sales")))
      .sort(col("Year"))

    println(s"The publisher with the highest total video game sales in European Union is: '${bestEUPublisher}'")
    println("Sales data for the publisher:")
    bestEUPublisherSales.show(10)




    printTaskLine("Basic Task 2")
    // ==========================
    val shotsDF: DataFrame = spark
      .read.parquet("../data/nhl_shots.parquet")

//    shotsDF.filter(col("season") === 2011  && col("time") > 3600).limit(500).show()


    printTaskLine("Basic Task 3")
    // ==========================


  // homeTeamGoalsNew and awayTeamGoalsNew -> added there because event "GOAL" does not update TeamGoals column
  // I suppose that in example these values are calculated using event column and this may cause some difference
  // when comparing to my values

    val help1DF: DataFrame = shotsDF
      .groupBy("season", "game_id")
      .agg(max("awayTeamGoals").alias("awayTeamGoals"),
          max("homeTeamGoals").alias("homeTeamGoals"),
          first("homeTeamWon").alias("homeTeamWon")
          )
      .withColumn("homeTeamGoalsNew",
          when(col("homeTeamGoals") === col("awayTeamGoals") && col("homeTeamWon") === 1, col("homeTeamGoals") + 1)
      .otherwise(col("homeTeamGoals")))
      .withColumn("awayTeamGoalsNew",
        when(col("homeTeamGoals") === col("awayTeamGoals") && col("homeTeamWon") === 0, col("awayTeamGoals") + 1)
      .otherwise(col("awayTeamGoals")))
//      .drop("awayTeamGoals", "homeTeamGoals")
//      .withColumnRenamed("awayTeamGoalsNew", "awayTeamGoals")
//      .withColumnRenamed("homeTeamGoalsNew", "homeTeamGoals")

    val help2DF = shotsDF
      .filter(col("event") === "GOAL")
      .groupBy("season", "game_id")
      .agg(
          first("homeTeamCode").alias("homeTeamCode"),
          first("awayTeamCode").alias("awayTeamCode"),
          first("isPlayOffGame").alias("isPlayOffGame"),
          max("time").alias("lastGoalTime")
      )

    val gamesDF = help2DF.join(help1DF, Seq("season", "game_id"), "inner")


//    gamesDF.filter(col("game_id") === 30116).limit(20).show()
//    println(gamesDF.count())

    printTaskLine("Basic Task 4")

    // ==========================

    val homeDF: DataFrame = gamesDF
      .filter(col("isPlayOffGame") === 1)
      .groupBy("season", "homeTeamCode")
      .agg(count("*").alias("homeGames"),
          count(when(col("homeTeamGoalsNew") > col("awayTeamGoalsNew"), true)).alias("homeWins"),
          count(when(col("homeTeamGoalsNew") < col("awayTeamGoalsNew"), true)).alias("homeLoses")
      )
      .withColumnRenamed("homeTeamCode", "teamCode")

    val awayDF: DataFrame = gamesDF
      .filter(col("isPlayOffGame") === 1)
      .groupBy("season", "awayTeamCode")
      .agg(count("*").alias("awayGames"),
          count(when(col("homeTeamGoalsNew") < col("awayTeamGoalsNew"), true)).alias("awayWins"),
          count(when(col("homeTeamGoalsNew") > col("awayTeamGoalsNew"), true)).alias("awayLoses")
      )
      .withColumnRenamed("awayTeamCode", "teamCode")

    val helpDF = homeDF.join(awayDF, Seq("season", "teamCode"), "inner")

    val playoffDF: DataFrame = helpDF
      .withColumn("games", col("homeGames") + col("awayGames"))
      .withColumn("wins", col("homeWins") + col("awayWins"))
      .withColumn("losses", col("homeLoses") + col("awayLoses"))
      .select("season", "teamCode", "games", "wins", "losses")

//    playoffDF.filter(col("season") === 2021).limit(5).show()
//    println(playoffDF.count())


    printTaskLine("Basic Task 5")
    // ==========================

    val windowSpec = Window.partitionBy("season").orderBy(desc("wins"))
    val bestPlayoffTeams: DataFrame = playoffDF
      .withColumn("rank", row_number.over(windowSpec))
      .filter(col("rank") === 1)
      .drop("rank")

    bestPlayoffTeams.show()

    val bestPlayoffTeam2022: Row = bestPlayoffTeams.where(col("season") === 2022).first()


    println("Best playoff team in 2022:")
    println(s"    Team: ${bestPlayoffTeam2022.getAs[String]("teamCode")}")
    println(s"    Games: ${bestPlayoffTeam2022.getAs[Long]("games")}")
    println(s"    Wins: ${bestPlayoffTeam2022.getAs[Long]("wins")}")
    println(s"    Losses: ${bestPlayoffTeam2022.getAs[Long]("losses")}")
    println("=========================================================")


    printTaskLine("Basic Task 6")
    // ==========================

  // Adding points for home and away team, first we make sure that we have collect data from latest event
  // Accidentally did not first use data frame from task 3 so things are more complicated in this task than should be
  val windowMaxTime = Window.partitionBy("season", "game_id").orderBy(desc("time"))
    val regularHelpDF = shotsDF
      .filter(col("isPlayOffGame") === 0)
      .withColumn("rank", row_number.over(windowMaxTime))
      .filter(col("rank") === 1)
      .drop("rank")
      .withColumn("pointsHome",
          when(col("homeTeamGoals") === col("awayTeamGoals") && col("event") =!= "GOAL", 1)
            .otherwise(when(col("homeTeamGoals") === col("awayTeamGoals") && col("homeTeamWon") === 1, 2)
              .otherwise(when(col("homeTeamGoals") === col("awayTeamGoals") && col("homeTeamWon") === 0, 1)
              .otherwise(when(col("homeTeamGoals") > col("awayTeamGoals"), 3)
                .otherwise( 0)))))

      .withColumn("pointsAway",
          when(col("homeTeamGoals") === col("awayTeamGoals") && col("event") =!= "GOAL", 1)
            .otherwise(when(col("homeTeamGoals") === col("awayTeamGoals") && col("homeTeamWon") === 1, 1)
              .otherwise(when(col("homeTeamGoals") === col("awayTeamGoals") && col("homeTeamWon") === 0, 2)
              .otherwise(when(col("homeTeamGoals") < col("awayTeamGoals"), 3)
                .otherwise(0)))))

      .withColumn("homeTeamWonSecond", when(col("pointsHome") > 1, 1).otherwise(0))
      .withColumn("awayTeamWonSecond", when(col("pointsAway") > 1, 1).otherwise(0))
      .withColumn("homeTeamGoalsNew",
        when(col("homeTeamGoals") === col("awayTeamGoals") && col("homeTeamWon") === 1, col("homeTeamGoals") + 1)
          .otherwise(col("homeTeamGoals")))
      .withColumn("awayTeamGoalsNew",
        when(col("homeTeamGoals") === col("awayTeamGoals") && col("homeTeamWon") === 0, col("awayTeamGoals") + 1)
          .otherwise(col("awayTeamGoals")))

//  regularHelpDF.where(col("season") === 2013).limit(20).show()

    // Counting amount of games played
    val regularHelp2 = regularHelpDF
      .groupBy("season", "homeTeamCode")
      .agg(countDistinct("game_id").alias("homeGames"))

    val regularHelp3 = regularHelpDF
      .groupBy("season", "awayTeamCode")
      .agg(countDistinct("game_id").alias("awayGames"))

    // Combining this data to home and away data
    // Here might be a problem
    val regularDF = regularHelpDF.join(regularHelp2, Seq("season", "homeTeamCode"), "inner")
    val regularDF2 = regularHelpDF.join(regularHelp3, Seq("season", "awayTeamCode"), "inner")


//    regularDF.limit(10).show()

    // Calculating other values
    val regularSeasonDFhome: DataFrame = regularDF
      .groupBy("season", "homeTeamCode", "game_id")
      .agg(first("homeGames").alias("homeGames"),
          first(col("homeTeamWonSecond")).alias("homeTeamWon"),
          first(col("homeTeamGoalsNew")).alias("homeGoals"),
          first(col("awayTeamGoalsNew")).alias("homeConceded"),
          first(col("pointsHome")).alias("homePoints"))
      .groupBy("season", "homeTeamCode")
      .agg(first("homeGames").alias("homeGames"),
        sum(when(col("homeTeamWon") === 1, 1).otherwise(0)).alias("homeWins"),
        sum(when(col("homeTeamWon") === 0, 1).otherwise(0)).alias("homeLosses"),
          sum(col("homeGoals")).alias("homeGoals"),
          sum(col("homeConceded")).alias("homeConceded"),
          sum(col("homePoints")).alias("homePoints"))
      .withColumnRenamed("homeTeamCode", "teamCode")

    val regularSeasonDFaway: DataFrame = regularDF2
      .groupBy("season", "awayTeamCode", "game_id")
      .agg(first("awayGames").alias("awayGames"),
          first("awayTeamWonSecond").alias("awayTeamWon"),
          max(col("awayTeamGoals")).alias("awayGoals"),
          max(col("homeTeamGoals")).alias("awayConceded"),
          first(col("pointsAway")).alias("awayPoints"))
      .groupBy("season", "awayTeamCode")
      .agg(first("awayGames").alias("awayGames"),
        sum(when(col("awayTeamWon") === 1, 1).otherwise(0)).alias("awayWins"),
        sum(when(col("awayTeamWon") === 0, 1).otherwise(0)).alias("awayLosses"),
          sum(col("awayGoals")).alias("awayGoals"),
          sum(col("awayConceded")).alias("awayConceded"),
          sum(col("awayPoints")).alias("awayPoints"))
      .withColumnRenamed("awayTeamCode", "teamCode")

//regularSeasonDFhome.limit(10).show()

    val regularSeasonDFjoined = regularSeasonDFaway.join(regularSeasonDFhome, Seq("season", "teamCode"), "inner")
    val regularSeasonDF = regularSeasonDFjoined.
      withColumn("games", col("homeGames") + col("awayGames"))
      .withColumn("wins", col("homeWins") + col("awayWins"))
      .withColumn("losses", col("homeLosses") + col("awayLosses"))
      .withColumn("goalsScored", col("homeGoals") + col("awayGoals"))
      .withColumn("goalsConceded", col("homeConceded") + col("awayConceded"))
      .withColumn("points", col("homePoints") + col("awayPoints"))
      .select("season", "teamCode", "games", "wins", "losses", "goalsScored", "goalsConceded", "points")

//    regularSeasonDF.limit(10).show()

    printTaskLine("Basic Task 7")
    // ==========================
    val windowPoints = Window.partitionBy("season").orderBy("points")
    val worstRegularTeams: DataFrame = regularSeasonDF
      .withColumn("rank", row_number.over(windowPoints))
      .filter(col("rank") === 1)
      .drop("rank")


  worstRegularTeams.show()

    val worstRegularTeam2022: Row = worstRegularTeams.where(col("season") === 2022).first()

    println("Worst regular season team in 2022:")
    println(s"    Team: ${worstRegularTeam2022.getAs[String]("teamCode")}")
    println(s"    Games: ${worstRegularTeam2022.getAs[Long]("games")}")
    println(s"    Wins: ${worstRegularTeam2022.getAs[Long]("wins")}")
    println(s"    Losses: ${worstRegularTeam2022.getAs[Long]("losses")}")
    println(s"    Goals scored: ${worstRegularTeam2022.getAs[Long]("goalsScored")}")
    println(s"    Goals conceded: ${worstRegularTeam2022.getAs[Long]("goalsConceded")}")
    println(s"    Points: ${worstRegularTeam2022.getAs[Long]("points")}")



    printTaskLine("Additional Task 1")
    // ===============================



    printTaskLine("Additional Task 2")
    // ===============================
    // some constants that could be useful
    val englishLetters: String = "abcdefghijklmnopqrstuvwxyz"
    val finnishLetters: String = englishLetters + "åäö"
    val whiteSpace: String = " "
    val punctuationMark: Char = '-'
    val twoPunctuationMarks: String = "--"
    val allowedEnglishOneLetterWords: List[String] = List("a", "i")
    val wikiStr: String = "wiki"

    val englishStr: String = "English"
    val finnishStr: String = "Finnish"


    val commonWordsEn: DataFrame = ???

    println("The ten most common English words that appear in the English articles:")
    commonWordsEn.show()


    val common5LetterWordsFi: DataFrame = ???

    println("The five most common 5-letter Finnish words that appear in the Finnish articles:")
    common5LetterWordsFi.show()


    val longestWord: String = ???

    println(s"The longest word appearing at least 150 times is '${longestWord}'")


    val averageWordLengths: DataFrame = ???

    println("The average word lengths:")
    averageWordLengths.show()



    printTaskLine("Additional Task 3")
    // ===============================
    // some helpful constants
    val startK: Int = 7
    val seedValue: Long = 1

    // the building id for Sähkötalo building at Hervanta campus
    val hervantaBuildingId: String = "102363858X"
    val hervantaPostalCode: Int = 33720

    val maxAllowedClusterDistance: Double = 3.0


    // returns the distance between points (lat1, lon1) and (lat2, lon2) in kilometers
    // based on https://community.esri.com/t5/coordinate-reference-systems-blog/distance-on-a-sphere-the-haversine-formula/ba-p/902128
    def haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double = {
        val R: Double = 6378.1  // radius of Earth in kilometers
        val phi1 = scala.math.toRadians(lat1)
        val phi2 = scala.math.toRadians(lat2)
        val deltaPhi = scala.math.toRadians(lat2 - lat1)
        val deltaLambda = scala.math.toRadians(lon2 - lon1)

        val a = scala.math.sin(deltaPhi * deltaPhi / 4.0) +
            scala.math.cos(phi1) * scala.math.cos(phi2) * scala.math.sin(deltaLambda * deltaLambda / 4.0)

        2 * R * scala.math.atan2(scala.math.sqrt(a), scala.math.sqrt(1 - a))
    }


    val finalCluster: DataFrame = ???

    val clusterBuildingCount: Long = ???
    val clusterHervantaBuildingCount: Long = ???

    println(s"Buildings in the final cluster: ${clusterBuildingCount}")
    print(s"Hervanta buildings in the final cluster: ${clusterHervantaBuildingCount} ")
    println(s"(${scala.math.round(10000.0*clusterHervantaBuildingCount/clusterBuildingCount)/100.0}% of all buildings in the final cluster)")
    println("===========================================================================================")



    // Stop the Spark session
    spark.stop()

    def printTaskLine(taskName: String): Unit = {
        val equalsLine: String = "=".repeat(taskName.length)
        println(s"${equalsLine}\n${taskName}\n${equalsLine}")
    }
}
