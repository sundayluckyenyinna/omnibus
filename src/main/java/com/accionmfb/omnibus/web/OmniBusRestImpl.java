package com.accionmfb.omnibus.web;

import com.accionmfb.omnibus.config.OmniBusObjectMapper;
import com.accionmfb.omnibus.logger.OmniBusApiLogger;
import com.accionmfb.omnibus.utils.OmniBusUtils;
import kong.unirest.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@ConditionalOnMissingBean(value = OmniBusRest.class)
public class OmniBusRestImpl implements OmniBusRest{

    private final OmniBusObjectMapper objectMapper = new OmniBusObjectMapper();
    private final OmniBusApiLogger omniBusApiLogger;

    @Override
    public OmniBusHttpResponse<String> getForObject(String url, Map<String, String> headers, Map<String, Object> params){
        Unirest.config().verifySsl(false);
        omniBusApiLogger.logIrisApiRequest(url, HttpMethod.GET.name(), "", headers, params);
        HttpResponse<String> httpResponse = Unirest.get(url)
                .headers(OmniBusUtils.defaultValue(headers, new HashMap<>()))
                .queryString(OmniBusUtils.defaultValue(params, new HashMap<>()))
                .asString();
        omniBusApiLogger.logIrisApiResponse(httpResponse);
        return new OmniBusHttpResponse<>(httpResponse);
    }

    @Override
    public OmniBusHttpResponse<String> postForObject(String url, Object body, Map<String, String> headers, Map<String, Object> params){
        Unirest.config().verifySsl(false);
        omniBusApiLogger.logIrisApiRequest(url, HttpMethod.POST.name(), body, headers, params);
        HttpRequestWithBody httpRequestWithBody = Unirest.post(url)
                .headers(OmniBusUtils.defaultValue(headers, new HashMap<>()))
                .queryString(OmniBusUtils.defaultValue(params, new HashMap<>()));
        return getOmniBodyJson(body, httpRequestWithBody);
    }

    @Override
    public OmniBusHttpResponse<String> postForForm(String url, Map<String, Object> form, Map<String, String> headers, Map<String, Object> params){
        Unirest.config().verifySsl(false);
        omniBusApiLogger.logIrisApiRequest(url, HttpMethod.POST.name(), form, headers, params);
        HttpResponse<String> httpResponse = Unirest.post(url)
                .headers(OmniBusUtils.defaultValue(headers, new HashMap<>()))
                .queryString(OmniBusUtils.defaultValue(params, new HashMap<>()))
                .fields(form)
                .asString();
        omniBusApiLogger.logIrisApiResponse(httpResponse);
        return new OmniBusHttpResponse<>(httpResponse);
    }

    @Override
    public OmniBusHttpResponse<String> putForObject(String url, Object body, Map<String, String> headers, Map<String, Object> params){
        Unirest.config().verifySsl(false);
        omniBusApiLogger.logIrisApiRequest(url, HttpMethod.PUT.name(), body, headers, params);
        HttpRequestWithBody httpRequestWithBody = Unirest.put(url)
                .headers(OmniBusUtils.defaultValue(headers, new HashMap<>()))
                .queryString(OmniBusUtils.defaultValue(params, new HashMap<>()));
        return getOmniBodyJson(body, httpRequestWithBody);
    }

    @Override
    public OmniBusHttpResponse<String> headForObject(String url, Map<String, String> headers, Map<String, Object> params){
        Unirest.config().verifySsl(false);
        omniBusApiLogger.logIrisApiRequest(url, HttpMethod.HEAD.name(), "", headers, params);
        HttpResponse<String> httpResponse = Unirest.head(url)
                .headers(OmniBusUtils.defaultValue(headers, new HashMap<>()))
                .queryString(OmniBusUtils.defaultValue(params, new HashMap<>()))
                .asString();
        omniBusApiLogger.logIrisApiResponse(httpResponse);
        return new OmniBusHttpResponse<>(httpResponse);
    }

    @Override
    public OmniBusHttpResponse<String> patchForObject(String url, Object body, Map<String, String> headers, Map<String, Object> params){
        Unirest.config().verifySsl(false);
        omniBusApiLogger.logIrisApiRequest(url, HttpMethod.PATCH.name(), body, headers, params);
        HttpRequestWithBody httpRequestWithBody = Unirest.patch(url)
                .headers(OmniBusUtils.defaultValue(headers, new HashMap<>()))
                .queryString(OmniBusUtils.defaultValue(params, new HashMap<>()));
        return getOmniBodyJson(body, httpRequestWithBody);
    }

    @Override
    public OmniBusHttpResponse<String> optionsForObject(String url, Object body, Map<String, String> headers, Map<String, Object> params){
        Unirest.config().verifySsl(false);
        omniBusApiLogger.logIrisApiRequest(url, HttpMethod.OPTIONS.name(), body, headers, params);
        GetRequest getRequest = Unirest.options(url)
                .headers(OmniBusUtils.defaultValue(headers, new HashMap<>()))
                .queryString(OmniBusUtils.defaultValue(params, new HashMap<>()));
        HttpResponse<String> httpResponse = getRequest.asString();
        omniBusApiLogger.logIrisApiResponse(httpResponse);
        return new OmniBusHttpResponse<>(httpResponse);
    }

    @Override
    public OmniBusHttpResponse<String> deleteForObject(String url, Object body, Map<String, String> headers, Map<String, Object> params){
        Unirest.config().verifySsl(false);
        omniBusApiLogger.logIrisApiRequest(url, HttpMethod.OPTIONS.name(), body, headers, params);
        HttpRequestWithBody httpRequestWithBody = Unirest.delete(url)
                .headers(OmniBusUtils.defaultValue(headers, new HashMap<>()))
                .queryString(OmniBusUtils.defaultValue(params, new HashMap<>()));
        return getOmniBodyJson(body, httpRequestWithBody);
    }

    private OmniBusHttpResponse<String> getOmniBodyJson(Object body, HttpRequestWithBody httpRequestWithBody) {
        String requestJson;
        if(Optional.ofNullable(body).isEmpty()){
            requestJson = Strings.EMPTY;
        }else{
            if(body instanceof String){
                requestJson = (String) body;
            }else{
                requestJson = objectMapper.stringify(body);
            }
        }
        RequestBodyEntity requestBodyEntity = httpRequestWithBody.body(requestJson);
        HttpResponse<String> httpResponse = requestBodyEntity.asString();
        omniBusApiLogger.logIrisApiResponse(httpResponse);
        return new OmniBusHttpResponse<>(httpResponse);
    }
}
