package com.sjsu.services

import com.sjsu.dao.WebLoginDao
import com.sjsu.vo.{WebLogin, User}
import org.springframework.beans.factory.annotation.Autowired

import scala.beans.BeanProperty
import scala.util.Random
import scala.util.control.Breaks

/**
 * Created by gauravbajaj on 24/09/14.
 */
class WebLoginService {

  @BeanProperty
  @Autowired
  var webLoginDao:WebLoginDao = null;

  def setWebLogin(user_id:String,webLogin:WebLogin): WebLogin = {


    var new_Web_Login = generateWebLogin(webLoginDao.getUser(user_id),webLogin)

    webLoginDao.saveWebLogin(user_id,new_Web_Login)

  }

  def getWebLogins(user_id:String):Array[WebLogin]={
  webLoginDao.getWebLogins(user_id)

  }

  def generateWebLogin(user:User,webLogin:WebLogin):WebLogin={

    var alreadyPresent:Boolean = true
    var login_id:String=null;
    var webLogins:Array[WebLogin] = user.getWebLogins
    // create a Breaks object
    val loop = new Breaks;

    while(alreadyPresent) {
      var rn = new Random()
     var random_login_id = rn.nextInt(50000 - 30000 + 1) + 30000
      login_id = "l-"+random_login_id
      // Make the for loop breakable
      loop.breakable {
        if(webLogins!=null) {
          for (webLogin: WebLogin <- webLogins) {
            if (webLogin.getLogin_id == login_id)
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
    webLogin.setLogin_id(login_id)
    webLogin
  }

  def deleteWebLogin(user_id:String, login_id:String) = {

    var user:User = webLoginDao.getUser(user_id)
    var webLogins: Array[WebLogin] = user.getWebLogins

    if (webLogins != null) {
      val loop = new Breaks;
      // Make the for loop breakable
      loop.breakable {
        for (i <- 0 to (webLogins.length - 1)) {
          var webLogin: WebLogin = webLogins(i)
          if (webLogin.getLogin_id == login_id) {
            var b = webLogins.toBuffer
            b.remove(i)
            webLogins = b.toArray
            loop.break()
          }
        }
      }
      webLoginDao.updateWebLogins(user_id,webLogins)
    }
  }
}
