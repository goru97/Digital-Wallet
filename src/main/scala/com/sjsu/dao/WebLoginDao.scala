package com.sjsu.dao

import com.sjsu.vo.{WebLogin, User}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.{Update, Criteria, Query}

import scala.beans.BeanProperty

class WebLoginDao{
  @BeanProperty
  @Autowired
  var mongoOperation:MongoOperations = null

  def getUser(user_id:String):User={

    var new_user_id = user_id.substring(2)
    var searchUserQuery:Query = new Query(Criteria.where("_id").is(new_user_id))
    mongoOperation.findOne(searchUserQuery, classOf[User])

  }

  def WebLogins(user_id:String):Array[WebLogin]={
    var user:User = getUser(user_id)
    user.getWebLogins
  }

  def updateWebLogins(user_id:String,webLogins:Array[WebLogin]) ={
    var new_user_id = user_id.substring(2)
    var searchUserQuery:Query = new Query(Criteria.where("_id").is(new_user_id))

    mongoOperation.updateFirst(searchUserQuery,Update.update("webLogins", webLogins),classOf[User])

  }


  def saveWebLogin(user_id:String, web_login:WebLogin):WebLogin={

    var savedUser:User = getUser(user_id)
    if(savedUser != null) {

      var webLogins:Array[WebLogin] =  savedUser.getWebLogins
      if(webLogins == null)
        webLogins = Array[WebLogin](web_login)
      else
        webLogins = webLogins :+ web_login
      updateWebLogins(user_id,webLogins)
    }
    web_login

  }

  def getWebLogins(user_id:String):Array[WebLogin]={
    var user:User = getUser(user_id)
    user.getWebLogins

  }
}