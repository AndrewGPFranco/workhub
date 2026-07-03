package com.agpf.workhub.utils;

import java.util.function.Consumer;

public class UtilsService {

    private UtilsService() {}

    public static <T> void updateField(T fieldValue, Consumer<T> setter) {
        if (fieldValue != null) {
            setter.accept(fieldValue);
        }
    }
}
