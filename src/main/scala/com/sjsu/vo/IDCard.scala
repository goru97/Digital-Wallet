package com.sjsu.vo

import javax.validation.constraints.NotNull

import org.hibernate.validator.constraints.NotBlank
import org.springframework.data.annotation.Id
import scala.beans.BeanProperty

/**
 * Created by gauravbajaj on 22/09/14.
 */

class IDCard {

  @BeanProperty
  @Id
  var card_id:String=_

  @BeanProperty
  @NotNull(message = "Card name cannot be null")
  @NotBlank(message = "Card name cannot be left blank")
  var card_name:String=_

  @NotNull(message = "Card number cannot be null")
  @NotBlank(message = "Card number cannot be left blank")
  @BeanProperty
  var card_number:String=_

  @BeanProperty
  var expiration_date:String=_


}
