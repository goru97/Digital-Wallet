package configurations

import com.sjsu.ExceptionMappers.ConstraintViolationExceptionMapper
import com.sjsu.controllers.{ UserController}
import org.glassfish.jersey.filter.LoggingFilter
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.{ServerProperties, ResourceConfig}
import org.glassfish.jersey.server.spring.scope.RequestContextFilter
import org.springframework.context.annotation.Configuration

/**
 * Created by gauravbajaj on 20/09/14.
 */
@Configuration
class JerseyConfig extends ResourceConfig {

  packages("com.sjsu.controllers")
    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
    property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true)
  // Further configuration of ResourceConfig.
  register(classOf[RequestContextFilter])
  register(classOf[LoggingFilter]) //For logging request-response feature in terminal/console
  register(classOf[UserController])
  register(classOf[JacksonFeature]) //For JSONIgnore feature
  register(classOf[ConstraintViolationExceptionMapper])// For Overriding 500 Internal server error and giving 400

}
