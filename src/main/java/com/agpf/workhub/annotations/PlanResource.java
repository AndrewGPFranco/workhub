package com.agpf.workhub.annotations;

import com.agpf.workhub.enums.plan.PlanResourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface PlanResource {

    PlanResourceType verify();

}
