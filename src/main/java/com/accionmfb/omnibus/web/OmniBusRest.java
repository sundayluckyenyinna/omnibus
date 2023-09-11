package com.accionmfb.omnibus.web;

import java.util.Map;

public interface OmniBusRest {
    OmniBusHttpResponse<String> getForObject(String url, Map<String, String> headers, Map<String, Object> params);

    OmniBusHttpResponse<String> postForObject(String url, Object body, Map<String, String> headers, Map<String, Object> params);

    OmniBusHttpResponse<String> postForForm(String url, Map<String, Object> form, Map<String, String> headers, Map<String, Object> params);

    OmniBusHttpResponse<String> putForObject(String url, Object body, Map<String, String> headers, Map<String, Object> params);

    OmniBusHttpResponse<String> headForObject(String url, Map<String, String> headers, Map<String, Object> params);

    OmniBusHttpResponse<String> patchForObject(String url, Object body, Map<String, String> headers, Map<String, Object> params);

    OmniBusHttpResponse<String> optionsForObject(String url, Object body, Map<String, String> headers, Map<String, Object> params);

    OmniBusHttpResponse<String> deleteForObject(String url, Object body, Map<String, String> headers, Map<String, Object> params);
}
