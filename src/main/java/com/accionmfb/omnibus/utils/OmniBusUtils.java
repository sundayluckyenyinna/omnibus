package com.accionmfb.omnibus.utils;

import java.util.Optional;

public class OmniBusUtils {

    @SuppressWarnings("unchecked")
    public static <T> T defaultValue(Object value, Object defaultValue){
        if(Optional.ofNullable(value).isEmpty()){
            return (T) defaultValue;
        }
        return (T) value;
    }

}
