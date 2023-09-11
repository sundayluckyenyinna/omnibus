package com.accionmfb.omnibus.web;

import kong.unirest.Cookies;
import kong.unirest.Headers;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestParsingException;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class OmniBusHttpResponse<T> {

    private final HttpResponse<T> unirestResponse;

    public OmniBusHttpResponse(HttpResponse<T> unirestResponse) {
        this.unirestResponse = unirestResponse;
    }

    public boolean is1xxProcessing(){
        return this.unirestResponse.getStatus() >= 100 && this.unirestResponse.getStatus() <= 199;
    }

    public boolean is2xxSuccessful(){
        return this.unirestResponse.isSuccess();
    }

    public boolean is3xxRedirectional(){
        return this.unirestResponse.getStatus() >= 300 && this.unirestResponse.getStatus() <= 399;
    }

    public boolean is4xx(){
        return this.unirestResponse.getStatus() >= 400 && this.unirestResponse.getStatus() <= 499;
    }

    public boolean is5xxServerError(){
        return this.unirestResponse.getStatus() >= 500;
    }

    public T getBody(){
        return this.unirestResponse.getBody();
    }

    public int getStatusCode(){
        return this.unirestResponse.getStatus();
    }

    public String getStatusText(){
        return this.unirestResponse.getStatusText();
    }

    public Cookies getResponseCookies(){
        return this.unirestResponse.getCookies();
    }

    public Headers getResponseHeaders(){
        return this.unirestResponse.getHeaders();
    }

    public Optional<UnirestParsingException> getParsingError(){
        return this.unirestResponse.getParsingError();
    }

    public HttpResponse<T> ifSuccess(Consumer<HttpResponse<T>> consumer){
        return this.unirestResponse.ifSuccess(consumer);
    }

    public HttpResponse<T> ifFailure(Consumer<HttpResponse<T>> consumer){
        return this.unirestResponse.ifFailure(consumer);
    }

    public <E> HttpResponse<T> ifFailure(Class<? extends E> aClass, Consumer<HttpResponse<E>> consumer){
        return this.unirestResponse.ifFailure(aClass, consumer);
    }

    public <V> HttpResponse<V> map(Function<T, V> function){
        return this.unirestResponse.map(function);
    }

    public <V> V mapBody(Function<T, V> function){
        return this.unirestResponse.mapBody(function);
    }

    <E> E mapError(Class<? extends E> clazz){
        return this.unirestResponse.mapError(clazz);
    }
}
