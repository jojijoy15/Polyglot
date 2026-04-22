package com.problems.learning.tags;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD,  ElementType.TYPE})
public @interface Hard {

    String trick() default "";
    String reference() default "";
    String explainer() default "";
    String revisionDate() default "01-01-2026";

}
