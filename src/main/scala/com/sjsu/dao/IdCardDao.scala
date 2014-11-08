package com.sjsu.dao

import com.sjsu.vo.{IDCard, User}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.{Update, Criteria, Query}
import scala.beans.BeanProperty

class IdCardDao{

  @BeanProperty
  @Autowired
  var mongoOperation:MongoOperations = null

  def getUser(user_id:String):User={

    var new_user_id = user_id.substring(2)
    var searchUserQuery:Query = new Query(Criteria.where("_id").is(new_user_id))
    mongoOperation.findOne(searchUserQuery, classOf[User])

  }

  def updateIdCards(user_id:String,idCards:Array[IDCard]) ={
    var new_user_id = user_id.substring(2)
    var searchUserQuery:Query = new Query(Criteria.where("_id").is(new_user_id))

    mongoOperation.updateFirst(searchUserQuery,Update.update("idCards", idCards),classOf[User])

  }

  def saveIdCard(user_id:String, id_card:IDCard):IDCard={

    var savedUser:User = getUser(user_id)
    if(savedUser != null) {

      var idCards:Array[IDCard] =  savedUser.getIdCards
      if(idCards == null)
        idCards = Array[IDCard](id_card)
      else
        idCards = idCards :+ id_card
updateIdCards(user_id,idCards)
    }
    id_card

  }

  def getIdCards(user_id:String):Array[IDCard]={
    var user:User = getUser(user_id)
    user.getIdCards

  }

}