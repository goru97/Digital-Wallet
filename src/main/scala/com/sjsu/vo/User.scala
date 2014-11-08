package com.sjsu.vo

import javax.validation.constraints.NotNull
import org.codehaus.jackson.annotate.JsonIgnore
import org.hibernate.validator.constraints.{Email, NotBlank}
import org.springframework.data.annotation.Id
import scala.beans.BeanProperty

/**
 * Created by gauravbajaj on 20/09/14.
 */



class User {

  @Id
  @BeanProperty
  var user_id : String = _

  @BeanProperty
  @NotNull(message = "User email cannot be null")
  @NotBlank(message = "User email cannot be left blank")
  @Email(message = "Not a valid email")
  var email: String = _


  @BeanProperty
  @NotNull(message = "User password cannot be null")
  @NotBlank(message = "User password cannot be left blank")
  var password: String = _

    @BeanProperty
    @JsonIgnore
    var name: String = _

  @BeanProperty
  var created_at: String = _

  @BeanProperty
  @JsonIgnore
  var updated_at: String = _

  @BeanProperty
  @JsonIgnore
  var idCards:Array[IDCard] = _

  @BeanProperty
  @JsonIgnore
  var webLogins:Array[WebLogin] = _

  @BeanProperty
  @JsonIgnore
  var bankAccounts:Array[BankAccount] = _
}
