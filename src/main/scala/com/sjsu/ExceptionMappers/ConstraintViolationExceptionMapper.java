package com.sjsu.ExceptionMappers;


import javax.annotation.Priority;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Array;


/**
 * Created by gauravbajaj on 01/10/14.
 */
@Provider
@Priority(Priorities.USER)
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {

       // rb.entity(exception.getConstraintViolations());
      //  System.out.println("Localized message " + exception.getLocalizedMessage());
        Object setArray[] =  exception.getConstraintViolations().toArray();
        ConstraintViolation constraintViolation =  (ConstraintViolation)setArray[0];
        Response.ResponseBuilder rb = Response.ok(constraintViolation.getMessageTemplate());
        rb.status(Response.Status.BAD_REQUEST);
        rb.type(MediaType.TEXT_PLAIN_TYPE);
        System.out.println("Localized message " +  constraintViolation.getMessageTemplate());

       return rb.build();

    }
}