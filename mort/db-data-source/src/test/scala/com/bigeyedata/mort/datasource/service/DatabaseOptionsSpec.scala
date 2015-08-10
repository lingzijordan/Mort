package com.bigeyedata.mort.datasource.service

import org.scalatest.{Matchers, FlatSpec}

class DatabaseOptionsSpec extends FlatSpec with Matchers {

  import DatabaseOptionsImplicits._

  "Database options" should "contains host type field" in {
    val options = Map(
      "port" -> "3306",
      "database" -> "bigeye_test",
      "username" -> "bigeye",
      "password" -> "bigeye123",
      "databaseType" -> "mysql"
    )
    intercept[IllegalArgumentException] {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    }
  }

  it should "contains database type field" in {
    val options = Map(
      "port" -> "3306",
      "database" -> "bigeye_test",
      "username" -> "bigeye",
      "password" -> "bigeye123"
    )
    intercept[IllegalArgumentException] {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    }
  }


  it should "contains database field" in {
    val options = Map(
      "host" -> "localhost",
      "username" -> "bigeye",
      "password" -> "bigeye123",
      "databaseType" -> "mysql"
    )
    intercept[IllegalArgumentException] {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    }
  }

  it should "contains username field" in {
    val options = Map(
      "host" -> "localhost",
      "database" -> "bigeye_test",
      "password" -> "bigeye123",
      "databaseType" -> "mysql"
    )
    intercept[IllegalArgumentException] {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    }
  }

  "the port in options" should "be a number" in {
    val options = Map(
      "host" -> "localhost",
      "port" -> "localhost",
      "database" -> "bigeye_test",
      "password" -> "bigeye123",
      "databaseType" -> "mysql"
    )
    intercept[IllegalArgumentException] {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    }
  }

  "the database options" should "host should not be empty" in {
    val options = Map(
      "host" -> "",
      "port" -> "3306",
      "database" -> "bigeye_test",
      "username" -> "bigeye",
      "password" -> "bigeye123",
      "databaseType" -> "mysql"
    )
    intercept[IllegalArgumentException] {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    }
  }
  "the database options" should "database should not be empty" in {
    val options = Map(
      "host" -> "localhost",
      "port" -> "3306",
      "database" -> "",
      "username" -> "bigeye",
      "password" -> "bigeye123",
      "databaseType" -> "mysql"
    )
    intercept[IllegalArgumentException] {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    }
  }
  "the database options" should "username should not be empty" in {
    val options = Map(
      "host" -> "localhost",
      "port" -> "3306",
      "database" -> "bigeye",
      "username" -> "",
      "password" -> "bigeye123",
      "databaseType" -> "mysql"
    )
    intercept[IllegalArgumentException] {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    }
  }

  "the database options" should "databaseType should not be empty" in {
    val options = Map(
      "host" -> "localhost",
      "port" -> "3306",
      "database" -> "bigeye",
      "username" -> "bigyeye",
      "password" -> "bigeye123",
      "databaseType" -> ""
    )
    intercept[IllegalArgumentException] {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    }
  }

  "the database options validate" should "right when all options are right" in {
    val options = Map(
      "host" -> "localhost",
      "port" -> "3306",
      "database" -> "bigeye",
      "username" -> "bigyeye",
      "password" -> "bigeye123",
      "databaseType" -> "mysql"
    )
    try {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    } catch {
      case e: IllegalArgumentException =>fail()
    }
  }

  "the default password" should "be empty when the password does not exist in the options" in {
    val options = Map(
      "host" -> "localhost",
      "port" -> "3306",
      "database" -> "bigeye",
      "username" -> "bigyeye",
      "databaseType" -> "mysql"
    )

    options.password should be("")
  }

  "the default port of mysql" should "be 3306 when the port does not exist in the options" in {
    val options = Map(
      "host" -> "localhost",
      "database" -> "bigeye",
      "username" -> "bigyeye",
      "databaseType" -> "MySQL"
    )

    options.port should be(3306)
  }

  "the default port of oracle" should "be 1521 when the port does not exist in the options" in {
    val options = Map(
      "host" -> "localhost",
      "database" -> "bigeye",
      "username" -> "bigyeye",
      "databaseType" -> "Oracle"
    )

    options.port should be(1521)
  }


  "the mort current" should "not support except MySQL and Oracle databases" in {
    val options = Map(
      "host" -> "localhost",
      "database" -> "bigeye",
      "username" -> "bigyeye",
      "databaseType" -> "PostgreSQL"
    )
    intercept[IllegalArgumentException] {
      DatabaseDataSourceOptionsValidator.validateFor(options)
    }
  }
}
