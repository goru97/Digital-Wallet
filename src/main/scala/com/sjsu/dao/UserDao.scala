package com.sjsu.dao

import com.sjsu.vo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.{Update, Criteria, Query}
import java.time.Instant
import scala.beans.BeanProperty

class UserDao() {

  @BeanProperty
  @Autowired
  var mongoOperation:MongoOperations = null

  def saveUser(user:User):User={
mongoOperation.save(user)
 user.setUser_id("u-"+user.getUser_id)
    user
  }

  def getUser(user_id:String):User={
    var new_user_id = user_id.substring(2)
    var searchUserQuery:Query = new Query(Criteria.where("_id").is(new_user_id))
   var savedUser:User = mongoOperation.findOne(searchUserQuery, classOf[User])
    if(savedUser != null)
    savedUser.setUser_id("u-"+savedUser.getUser_id)
    savedUser
  }

  def modifyUser(user_id:String,user:User):User={
    var new_user_id = user_id.substring(2)
    var searchUserQuery:Query = new Query(Criteria.where("_id").is(new_user_id));
    mongoOperation.updateFirst(searchUserQuery,Update.update("password", user.getPassword),classOf[User])
    mongoOperation.updateFirst(searchUserQuery,Update.update("email", user.getEmail),classOf[User])
    mongoOperation.updateFirst(searchUserQuery,Update.update("updated_at", Instant.now.toString),classOf[User])
    var savedUser:User = mongoOperation.findOne(searchUserQuery, classOf[User])
    savedUser.setUser_id("u-"+savedUser.getUser_id)
    savedUser
  }

}