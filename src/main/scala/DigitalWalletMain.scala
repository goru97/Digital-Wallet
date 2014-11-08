import java.util
import javax.servlet.Filter

import com.mongodb.{ServerAddress, MongoClient, MongoCredential}
import com.sjsu.dao.{WebLoginDao, IdCardDao, UserDao, BankAccountDao}
import com.sjsu.services.{BankAccountService, WebLoginService, IDCardService, UserService}
import configurations.JerseyConfig
import org.glassfish.jersey.servlet.ServletContainer
import org.glassfish.jersey.servlet.ServletProperties
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.embedded.ServletRegistrationBean
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.{Configuration, Bean}
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.{MongoTemplate, SimpleMongoDbFactory, MongoOperations}


/**
 * @author Gaurav Bajaj
 */

object DigitalWalletMain {

  def main(args: Array[String]) {
    SpringApplication.run(classOf[DigitalWalletMain]);
  }


}

@Configuration
@EnableAutoConfiguration
class DigitalWalletMain extends  SpringBootServletInitializer{



@Bean
def jerseyServlet(): ServletRegistrationBean = {

 val registration = new ServletRegistrationBean(new ServletContainer(), "/api/v1/*")
 registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, classOf[JerseyConfig].getName)
 registration
  }

  @Bean
  def userService():UserService={
  new UserService()
  }

  @Bean
  def idCardService():IDCardService={
    new IDCardService()
  }

  @Bean
  def webLoginService():WebLoginService={
    new WebLoginService()
  }

  @Bean
  def bankAccountService():BankAccountService={
    new BankAccountService()
  }

  @Bean
  def userDao():UserDao={
    new UserDao()
  }

  @Bean
  def idCardDao():IdCardDao={
    new IdCardDao()
  }

  @Bean
  def webLoginDao():WebLoginDao={
    new WebLoginDao()
  }

  @Bean
  def bankAccountDao():BankAccountDao={
    new BankAccountDao()
  }

  //MongoDB COnfiguration Starts
  @Bean
  def mongoDbFactory(): MongoDbFactory = {
    var userName = "goru97"
    var password = "Welcome@97"
    var dbName = "cmpe273db"
    var credential:MongoCredential = MongoCredential.createMongoCRCredential(userName,dbName ,password.toCharArray)
    new SimpleMongoDbFactory(new MongoClient(new ServerAddress("ds043200.mongolab.com:43200"),util.Arrays.asList(credential)),dbName)

  }


  @Bean
  def mongoOperation(): MongoOperations = {
    val mongoTemplate = new MongoTemplate(mongoDbFactory())
    mongoTemplate
  }

  //MongoDB COnfiguration ends

  override protected def configure(application: SpringApplicationBuilder): SpringApplicationBuilder = {
    application.sources(classOf[DigitalWalletMain])
  }

}