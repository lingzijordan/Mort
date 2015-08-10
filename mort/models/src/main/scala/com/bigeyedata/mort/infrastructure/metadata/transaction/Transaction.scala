package com.bigeyedata.mort.infrastructure.metadata.transaction

import com.bigeyedata.mort.infrastructure.metadata.exceptions.PersistanceException
import scalikejdbc.{ConnectionPool, DB, DBSession, Tx}

class Transaction(val db: DB) {

  def rollback() = {
    try {
      db.rollbackIfActive()
    } finally {
      db.close()
    }
  }

  def commit() = {
    try {
      val currentTx: Tx = db.currentTx
      currentTx.commit()
    } finally {
      db.close()
    }
  }

  def session = {
    db.withinTxSession()
  }
}


object Transaction {
  def begin(): Transaction = {
    val db: DB = DB(conn = ConnectionPool.borrow())
    db.begin()
    new Transaction(db)
  }

  def withTransaction[B]()(f: DBSession => B): B = {
    val transaction = Transaction.begin()
    implicit val session: DBSession = transaction.session
    try {

      val result = f(session)
      result
    } catch {
      case e: Exception =>
        transaction.rollback()
        throw new PersistanceException(e)
    } finally {
      transaction.commit()
    }

  }
}