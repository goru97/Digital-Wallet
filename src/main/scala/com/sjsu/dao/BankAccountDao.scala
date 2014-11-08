package com.sjsu.dao

import com.sjsu.vo.{BankAccount, User}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.{Update, Criteria, Query}

import scala.beans.BeanProperty

class BankAccountDao{
  @BeanProperty
  @Autowired
  var mongoOperation:MongoOperations = null

  def getUser(user_id:String):User={

    var new_user_id = user_id.substring(2)
    var searchUserQuery:Query = new Query(Criteria.where("_id").is(new_user_id))
    mongoOperation.findOne(searchUserQuery, classOf[User])

  }

  def updateBankAccounts(user_id:String,bankAccounts:Array[BankAccount]) ={
    var new_user_id = user_id.substring(2)
    var searchUserQuery:Query = new Query(Criteria.where("_id").is(new_user_id))

    mongoOperation.updateFirst(searchUserQuery,Update.update("bankAccounts", bankAccounts),classOf[User])

  }


  def saveBankAccount(user_id:String, bankAccount:BankAccount):BankAccount={

    var savedUser:User = getUser(user_id)
    if(savedUser != null) {

      var bankAccounts:Array[BankAccount] =  savedUser.getBankAccounts
      if(bankAccounts == null)
        bankAccounts = Array[BankAccount](bankAccount)
      else
        bankAccounts = bankAccounts :+ bankAccount
      updateBankAccounts(user_id,bankAccounts)
    }
    bankAccount

  }

  def getBankAccounts(user_id:String):Array[BankAccount]={
    var user:User = getUser(user_id)
    user.getBankAccounts

  }


}