package com.sjsu.controllers

/**
 * Created by gauravbajaj on 21/09/14.
 */


import javax.validation.Valid
import javax.ws.rs._
import javax.ws.rs.core.Response.ResponseBuilder
import javax.ws.rs.core._
import com.sjsu.services.{BankAccountService, WebLoginService, IDCardService, UserService}
import com.sjsu.vo._
import org.springframework.beans.factory.annotation.Autowired

import scala.beans.BeanProperty


@Path("/users")
class UserController {



  @BeanProperty
  @Autowired
var userService:UserService=null;

  @BeanProperty
  @Autowired
var idCardService:IDCardService=null;

  @BeanProperty
  @Autowired
  var webLoginService:WebLoginService=null;

  @BeanProperty
  @Autowired
  var bankAccountService:BankAccountService=null;

  //Create new user
  @POST
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Valid
  def setUserDetail(user:User): Response = {
    val newUser = userService.setUserDetail(user)
   var builder:ResponseBuilder = Response.ok(newUser)
    builder.status(Response.Status.CREATED) // To send 201 status
    builder.build()

  }

  //Get new user
  @GET
  @Path("{user_id}")
  @Produces(Array(MediaType.APPLICATION_JSON))
  def getPersonDetail(@PathParam("user_id") user_id:String, @Context request:Request ): Response= {
    var cc: CacheControl = new CacheControl();
    cc.setMaxAge(300); // User objects are valid for 5 mins
    cc.setPrivate(false); // Proxies may cache this

    var user = userService.getUserDetail(user_id)
    var response: Response = null
    if(user !=null) {
      //Conditional GET feature starts
      var etag: EntityTag = computeEntityTag(user)
      var builder: ResponseBuilder = request.evaluatePreconditions(etag)

      if (builder == null)
        builder = Response.ok(user);

      builder.cacheControl(cc);
      builder.tag(etag)
      //Conditional GET feature ends
     response = builder.build()

    }
    else
      response = Response.status(Response.Status.BAD_REQUEST).build()

    response
  }

  //Modify existing user
  @PUT
  @Path("{user_id}")
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Consumes(Array(MediaType.APPLICATION_JSON))
  def updatePersonDetail(@PathParam("user_id") user_id:String, user:User): Response = {
    // var userService: UserService = new UserService
    val modifiedIUser = userService.modifyUserDetail(user_id,user)
    var builder:ResponseBuilder = Response.ok(modifiedIUser)
    builder.status(Response.Status.CREATED)  // To send 201 status
    builder.build()
  }

  //Create new ID Card
  @POST
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Valid
  @Path("{user_id}/idcards")
  def setIDCard(@PathParam("user_id") user_id:String, idCard:IDCard): Response = {

    var newIDCard:IDCard= idCardService.setIDCardDetail(user_id,idCard)
    var builder: ResponseBuilder = null

    builder = Response.ok(newIDCard)
    if(newIDCard != null)
    builder.status(Response.Status.CREATED) // To send 201 status
    else
    builder.status(Response.Status.BAD_REQUEST) // To send 400 status
    builder.build()

  }

  // Get all the ID Cards
  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Path("{user_id}/idcards")
  def getIDCards(@PathParam("user_id") user_id:String): Response = {
    var idCards:Array[IDCard]=idCardService.getIdCards(user_id)
    var builder: ResponseBuilder = null

      builder = Response.ok(idCards) // Status 200
    if(idCards ==null)
    builder.status(Response.Status.BAD_REQUEST) // Status 400
      builder.build()


  }

  //Delete the ID Card
  @DELETE
  @Path("{user_id}/idcards/{card_id}")
  def deleteIDCards(@PathParam("user_id") user_id:String, @PathParam("card_id") card_id:String): Response = {

     idCardService.deleteCard(user_id,card_id)
    var builder:ResponseBuilder = Response.ok()
      builder.status(204)
      builder.build()


  }

  // Create new Web Login
  @POST
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Valid
  @Path("{user_id}/weblogins")
  def setWebLogin(@PathParam("user_id") user_id:String, webLogin:WebLogin): Response = {

      var newWebLogin:WebLogin= webLoginService.setWebLogin(user_id,webLogin)
    var builder: ResponseBuilder = null
    builder = Response.ok(newWebLogin)
    if(newWebLogin != null)
      builder.status(Response.Status.CREATED) // To send 201 status
    else
      builder.status(Response.Status.BAD_REQUEST) // To send 400 status
    builder.build()

  }

  //Get Web Logins
  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Path("{user_id}/weblogins")
  def getWebLogins(@PathParam("user_id") user_id:String): Response = {
    var webLogins:Array[WebLogin]=webLoginService.getWebLogins(user_id)
    var builder: ResponseBuilder = null

    builder = Response.ok(webLogins) // Status 200
    if(webLogins ==null)
      builder.status(Response.Status.BAD_REQUEST) // Status 400
    builder.build()

  }

  //Delete Web Login
  @DELETE
  @Path("{user_id}/weblogins/{login_id}")
  def deleteWebLogin(@PathParam("user_id") user_id:String, @PathParam("login_id") login_id:String): Response = {
    webLoginService.deleteWebLogin(user_id,login_id)
    var builder:ResponseBuilder = Response.ok()
    builder.status(204)
    builder.build()
  }

//Create Bank Account
  @POST
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Valid
  @Path("{user_id}/bankaccounts")
  def setBankAccount(@PathParam("user_id") user_id:String, bankAccount:BankAccount): Response = {

      var newBankAccount:BankAccount= bankAccountService.setBankAccount(user_id,bankAccount)
  var builder: ResponseBuilder = null
  builder = Response.ok(newBankAccount)
  if(newBankAccount != null)
    builder.status(Response.Status.CREATED) // To send 201 status
  else
    builder.status(Response.Status.NOT_FOUND) // To send 404 status
  builder.build()

  }

  // Get Bank Account
  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  @Path("{user_id}/bankaccounts")
  def getBankAccounts(@PathParam("user_id") user_id:String): Response = {
    var bankAccounts:Array[BankAccount]=bankAccountService.getBankAccounts(user_id)
    var builder: ResponseBuilder = null

    builder = Response.ok(bankAccounts) // Status 200
    if(bankAccounts ==null)
      builder.status(Response.Status.BAD_REQUEST) // Status 400
    builder.build()

  }

  // Delete Bank Account
  @DELETE
  @Path("{user_id}/bankaccounts/{ba_id}")
  def deleteBankAccounts(@PathParam("user_id") user_id:String, @PathParam("ba_id") ba_id:String): Response = {
    bankAccountService.deleteBankAccount(user_id,ba_id)
    var builder:ResponseBuilder = Response.ok()
    builder.status(204)
    builder.build()
  }

  private def computeEntityTag(user: User): EntityTag = {
    new EntityTag(""+user.getUpdated_at)
  }

}