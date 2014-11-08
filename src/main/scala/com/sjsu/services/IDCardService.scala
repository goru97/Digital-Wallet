package com.sjsu.services

import com.sjsu.vo.{User, IDCard}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import scala.beans.BeanProperty
import scala.util.Random
import scala.util.control.Breaks
import com.sjsu.dao.IdCardDao

/**
 * Created by gauravbajaj on 22/09/14.
 */

@Component
class IDCardService {

  @BeanProperty
  @Autowired
  var idCardDao:IdCardDao = null;

  def setIDCardDetail(user_id:String,idCard:IDCard): IDCard = {
var new_Id_Card = generateLibraryID(idCardDao.getUser(user_id),idCard)

idCardDao.saveIdCard(user_id,new_Id_Card)

  }

  def getIdCards(user_id:String):Array[IDCard]={
idCardDao.getIdCards(user_id)
  }

  def deleteCard(user_id:String, card_id:String) = {

    var user:User = idCardDao.getUser(user_id)
    var idCards: Array[IDCard] = user.getIdCards

    if (idCards != null) {
      val loop = new Breaks;
      // Make the for loop breakable
      loop.breakable {
        for (i <- 0 to (idCards.length - 1)) {
          var idCard: IDCard = idCards(i)
          if (idCard.getCard_id == card_id) {
            var b = idCards.toBuffer
            b.remove(i)
            idCards = b.toArray
            loop.break()
          }
        }
      }
      idCardDao.updateIdCards(user_id,idCards)
    }

  }
  def generateLibraryID(user:User,idCard:IDCard):IDCard={

    var alreadyPresent:Boolean = true
    var card_id:String = null;
    var idCards:Array[IDCard] = user.getIdCards
    // create a Breaks object
    val loop = new Breaks;

    while(alreadyPresent) {
      var rn = new Random()
    var  random_card_id = rn.nextInt(80000 - 60000 + 1) + 60000
         card_id = "c-"+random_card_id
      // Make the for loop breakable
      loop.breakable {
        if(idCards!=null) {
          for (idCard: IDCard <- idCards) {
            if (idCard.getCard_id == card_id)
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
    idCard.setCard_id(card_id)
    idCard
  }
}
