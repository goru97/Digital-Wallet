package com.sjsu.vo

import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.NotBlank
import scala.beans.BeanProperty

/**
 * Created by gauravbajaj on 24/09/14.
 */
class BankAccount {
  @BeanProperty
  var ba_id:String=_

  @BeanProperty
  var account_name:String=_

  @NotNull(message = "Routing number cannot be null")
  @NotBlank(message = "Routing number cannot be left blank")
  @BeanProperty
  var routing_number:String=_

  @NotNull(message = "Account number cannot be null")
  @NotBlank(message = "Account number cannot be left blank")
  @BeanProperty
  var account_number:String=_

}
