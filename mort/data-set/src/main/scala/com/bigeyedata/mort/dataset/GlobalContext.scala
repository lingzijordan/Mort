package com.bigeyedata.mort.dataset

import org.apache.spark.{SparkConf, SparkContext}

object GlobalContext extends DataSetConfiguration {

  lazy val sparkContext = new SparkContext(new SparkConf().setAll(sparkConfig))

}
