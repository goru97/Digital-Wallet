package com.sjsu.services

import java.net.URI

import javax.ws.rs.client.{ ClientBuilder, Client}

import javax.ws.rs.core.{ MediaType, UriBuilder}


import com.fasterxml.jackson.databind.ObjectMapper
import com.sjsu.dao.BankAccountDao
import com.sjsu.vo.{RoutingNumber, BankAccount, User}

import org.glassfish.jersey.client.ClientConfig
import org.springframework.beans.factory.annotation.Autowired

import scala.beans.BeanProperty
import scala.util.Random
import scala.util.control.Breaks

/**
 * Created by gauravbajaj on 24/09/14.
 */
class BankAccountService {

  @BeanProperty
  @Autowired
  var bankAccountDao:BankAccountDao = null

  def setBankAccount(user_id:String,bankAccount:BankAccount): BankAccount = {
   var user:User = bankAccountDao.getUser(user_id)
    var bankAccounts:Array[BankAccount]= user.getBankAccounts

    var newBankAccount = generateBankAccount(user,bankAccount)

    if(newBankAccount.getAccount_name !=null && newBankAccount.getAccount_name != "")
    bankAccountDao.saveBankAccount(user_id,newBankAccount)

    else
      null

  }

  def getBankAccounts(user_id:String):Array[BankAccount]={
    bankAccountDao.getBankAccounts(user_id)
  }


  def generateBankAccount(user:User,bankAccount:BankAccount):BankAccount={

    var alreadyPresent:Boolean = true
    var ba_id:String=null;
    var bankAccounts:Array[BankAccount] = user.getBankAccounts
    // create a Breaks object
    val loop = new Breaks;

    while(alreadyPresent) {
      var rn = new Random()
      var random_ba_id = rn.nextInt(90000 - 70000 + 1) + 70000
      ba_id = "b-"+random_ba_id
      // Make the for loop breakable
      loop.breakable {
        if(bankAccounts!=null) {
          for (bankAccount: BankAccount <- bankAccounts) {
            if (bankAccount.getBa_id== ba_id)
              alreadyPresent = true
            else {
              alreadyPresent = false
              loop.break()
            }
          }
        }
        else
          alreadyPresent = false
      }

    }
    bankAccount.setBa_id(ba_id)

    //Making Client to consume REST service to get account name starts
    if(bankAccount.getAccount_name == null || bankAccount.getAccount_name == "") {

      val config = new ClientConfig()
      val client:Client = ClientBuilder.newClient(config)
      val target = client.target(getBaseURI)
    var response:String = target.queryParam("rn",bankAccount.getRouting_number()).request().accept(MediaType.TEXT_PLAIN).get(classOf[String])

var om:ObjectMapper = new ObjectMapper()
     var routingNumber:RoutingNumber = om.readValue(response,classOf[RoutingNumber])
      println("OK CODE -> "+routingNumber.getCode)
      if(routingNumber.getCode.equalsIgnoreCase("200"))
   bankAccount.setAccount_name(routingNumber.getCustomer_name)


    }
    //Making Client to consume REST service to get account name ends
    bankAccount
  }

  def deleteBankAccount(user_id:String, ba_id:String) = {

    var user:User = bankAccountDao.getUser(user_id)
    var bankAccounts: Array[BankAccount] = user.getBankAccounts

    if (bankAccounts != null) {
      val loop = new Breaks;
      // Make the for loop breakable
      loop.breakable {
        for (i <- 0 to (bankAccounts.length - 1)) {
          var bankAccount: BankAccount = bankAccounts(i)
          if (bankAccount.getBa_id == ba_id) {
            var b = bankAccounts.toBuffer
            b.remove(i)
            bankAccounts = b.toArray
            loop.break()
          }
        }
      }
      bankAccountDao.updateBankAccounts(user_id,bankAccounts)
    }
  }

  private def getBaseURI(): URI = {
    UriBuilder.fromUri("http://www.routingnumbers.info/api/data.json")
      .build()
  }


}
