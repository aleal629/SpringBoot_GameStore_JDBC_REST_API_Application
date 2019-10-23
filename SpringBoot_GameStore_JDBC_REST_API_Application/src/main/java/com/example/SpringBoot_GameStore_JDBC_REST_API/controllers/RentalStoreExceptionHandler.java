package com.example.SpringBoot_GameStore_JDBC_REST_API.controllers;

import com.example.SpringBoot_GameStore_JDBC_REST_API.customExceptions.*;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.err+json")
public class RentalStoreExceptionHandler {

    /**
     * Handles invalid item types sent by client to the Game Store Service
     * @param e Custom Invalid Item Type exception
     * @param request Web request sent by the client to Game Store API
     * @return Provides information on exception
     */
    @ExceptionHandler(value = {InvalidItemTypeException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> invalidItemTypeError(InvalidItemTypeException e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    /**
     * Handles exception when client requests to purchase more quantity than available
     * @param e Custom Insufficient Inventory exception
     * @param request Web request sent by the client to Game Store API
     * @return Provides information on exception
     */
    @ExceptionHandler(value = {InsufficientInventoryException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> insufficientInventoryError(InsufficientInventoryException e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    /**
     * Handles exception when client provides an Invalid US State, as purchases can only be made from the 50 United States
     * @param e Custom Invalid US State exception
     * @param request Web request sent by the client to Game Store API
     * @return Provides information on exception
     */
    @ExceptionHandler(value = {InvalidUSStateError.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> invalidStateError(InvalidUSStateError e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    /**
     * Handles exception when client requests to purchase more than database can store in Invoice database table (The
     * limit is $999.99).
     * @param e Custom Excessive spending exception
     * @param request Web request sent by the client to Game Store API
     * @return Provides information on exception
     */
    @ExceptionHandler(value = {ExcessiveSpendingError.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> excessiveSpendingError(ExcessiveSpendingError e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    /**
     * Handles exception when client does not provide valid information for item or purchase creation
     * @param e Method Argument Not Valid exception
     * @param request Web request sent by the client to Game Store API
     * @return Provides information on exception
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> methodArgumentNotValidError(MethodArgumentNotValidException e, WebRequest request) {

        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<VndErrors.VndError> vndErrorList = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            VndErrors.VndError vndError = new
                    VndErrors.VndError(request.toString(), fieldError.getDefaultMessage());
            vndErrorList.add(vndError);
        }
        VndErrors vndErrors = new VndErrors(vndErrorList);
        ResponseEntity<VndErrors> responseEntity = new
                ResponseEntity<>(vndErrors, HttpStatus.UNPROCESSABLE_ENTITY);

        return responseEntity;
    }

    /**
     * Handles exception when client requests to purchase an item with an ID that cannot be found in the database
     * @param e Custom Invalid item ID exception
     * @param request Web request sent by the client to Game Store API
     * @return Provides information on exception
     */
    @ExceptionHandler(value = {InvalidItemIdNumber.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> invalidItemIdNumber(InvalidItemIdNumber e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    /**
     * Handles exception when service cannot connect to the back end database or data store
     * @param e CannotCreateTransaction exception
     * @param request Web request sent by the client to Game Store API
     * @return Provides information on exception
     */
    @ExceptionHandler(value = {CannotCreateTransactionException.class})
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<VndErrors> databaseUnreachableError(CannotCreateTransactionException e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), "Database is not reachable, please contact your site " +
                "administrator.");
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
        return responseEntity;
    }

    /**
     * Handles exception when client does not provide valid information for item or purchase creation
     * @param e Method Argument Not Valid exception
     * @param request Web request sent by the client to Game Store API
     * @return Provides information on exception
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> missingInformationException(HttpMessageNotReadableException e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), "Incomplete information has been provided. Please provide " +
                "valid arguments for item or purchase creation.");
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }
}
