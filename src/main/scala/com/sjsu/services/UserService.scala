package com.sjsu.services

/**
 * Created by gauravbajaj on 20/09/14.
 */

import com.sjsu.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.sjsu.vo._
import scala.beans.BeanProperty
import scala.util.Random
import java.time.Instant


@Component
class UserService() {

  @BeanProperty
  @Autowired
  var userDao:UserDao = null

  var users:Map[String,User] = Map()

  def setUserDetail(user:User): User = {
 // var newUser=generateUserID(user)
    /*newUser.setCreated_at(new Date().toString);*/
    user.setCreated_at(Instant.now.toString);
  // users += (newUser.getUser_id -> user);
var newUser:User = userDao.saveUser(user)


   // println("Keys in users" + users.keys)
    newUser
  }


  def getUserDetail(user_id:String): User = {

  userDao.getUser(user_id)

  }

  def modifyUserDetail(user_id:String, user:User): User = {
userDao.modifyUser(user_id,user)

  }

  def generateUserID(user:User): User ={

    var alreadyPresent:Boolean = true
    var user_id:String =null;

    while(alreadyPresent) {
      var rn = new Random()
      var random_user_id = rn.nextInt(50000 - 10000 + 1) + 10000
   user_id = "u-"+random_user_id
      if (users.contains(user_id))
        alreadyPresent=true;
      else
        alreadyPresent=false;

    }

    user.setUser_id(user_id)
    user
  }
}
