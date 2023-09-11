package com.accionmfb.omnibus.advice;

import com.accionmfb.omnibus.exception.OmniApiException;
import com.accionmfb.omnibus.constants.ResponseCodes;
import com.accionmfb.omnibus.exception.OmniBusException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Configuration
@RestControllerAdvice
public class OmniBusExceptionAdvice extends ResponseEntityExceptionHandler
{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        String message = String.join(" ", errors);
        return this.getGenericBadRequestResponseEntity(ex, headers, request, message);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        String error = ex.getParameterName() + " parameter is missing";

        return this.getGenericBadRequestResponseEntity(ex, headers, request, error);
    }


    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpHeaders headers,
            WebRequest request) {
        String error = ex.getName() + " should be of type " + (ex.getRequiredType() != null ? ex.getRequiredType().getName() : "");
        return this.getGenericBadRequestResponseEntity(ex, headers, request, error);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex,HttpHeaders headers,  WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }
      return this.getGenericBadRequestResponseEntity(ex, headers, request, String.join(" ", errors));
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request)
    {
        String error = "No handler/resource found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        OmniApiException apiException = OmniApiException.builder()
                .responseCode(ResponseCodes.RESOURCE_NOT_FOUND.getResponseCode())
                .responseMessage(error)
                .timestamp(Instant.now().toString())
                .path(getRequestURI(request))
                .stackTrace(ExceptionUtils.getStackTrace(ex))
                .build();
        return handleExceptionInternal(ex, apiException, headers, HttpStatus.NOT_FOUND, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));

        OmniApiException apiException = OmniApiException.builder()
                .responseCode(ResponseCodes.FAILED_MODEL.getResponseCode())
                .responseMessage(builder.toString())
                .path(getRequestURI(request))
                .timestamp(Instant.now().toString())
                .stackTrace(ExceptionUtils.getStackTrace(ex))
                .build();
        return handleExceptionInternal(ex, apiException, headers, HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        OmniApiException apiException = OmniApiException.builder()
                .responseCode(ResponseCodes.FAILED_MODEL.getResponseCode())
                .responseMessage(builder.toString())
                .path(getRequestURI(request))
                .timestamp(Instant.now().toString())
                .stackTrace(ExceptionUtils.getStackTrace(ex))
                .build();
        return handleExceptionInternal(ex, apiException, headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        OmniApiException apiException = OmniApiException.builder()
                .responseCode(ResponseCodes.INTERNAL_SERVER_ERROR.getResponseCode())
                .responseMessage(String.format("Internal server error: %s", ex.getMessage()))
                .path(getRequestURI(request))
                .timestamp(Instant.now().toString())
                .stackTrace(ExceptionUtils.getStackTrace(ex))
                .build();
        return handleExceptionInternal(ex, apiException, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
    }

    @ExceptionHandler({ OmniBusException.class })
    public ResponseEntity<Object> handleCustomOmniBusException(OmniBusException ex, HttpHeaders headers, WebRequest request){
        OmniApiException apiException = OmniApiException.builder()
                .responseCode(ex.getResponseCode())
                .responseMessage(ex.getResponseMessage())
                .path(getRequestURI(request))
                .timestamp(Instant.now().toString())
                .stackTrace(ExceptionUtils.getStackTrace(ex))
                .build();
        return handleExceptionInternal(ex, apiException, headers, HttpStatus.BAD_REQUEST, request);
    }

    private String getRequestURI(WebRequest request){
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private ResponseEntity<Object> getGenericBadRequestResponseEntity(
            Exception ex,
            HttpHeaders headers,
            WebRequest request,
            String error)
    {
        OmniApiException apiException = OmniApiException
                .builder()
                .responseCode(ResponseCodes.FAILED_MODEL.getResponseCode())
                .responseMessage(error)
                .path(getRequestURI(request))
                .timestamp(Instant.now().toString())
                .stackTrace(ExceptionUtils.getStackTrace(ex))
                .build();

        return handleExceptionInternal(ex, apiException, headers, HttpStatus.BAD_REQUEST, request);
    }
}
