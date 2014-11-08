package com.sjsu.vo

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.NotNull
import scala.beans.BeanProperty

/**
 * Created by gauravbajaj on 24/09/14.
 */
class WebLogin {

  @BeanProperty
  var login_id : String = _

  @BeanProperty
  @NotNull(message = "URL cannot be null")
  @NotBlank(message = "URL cannot be left blank")
  var url: String = _


  @BeanProperty
  @NotNull(message = "Login cannot be null")
  @NotBlank(message = "Login cannot be left blank")
  var login: String = _

  @BeanProperty
  @NotNull(message = "Password cannot be null")
  @NotBlank(message = "Password cannot be left blank")
  var password: String = _


}
