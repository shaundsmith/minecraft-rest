package dev.shaundsmith.minecraft.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the HTTP route accepted by a {@link RestHttpHandler}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {

    /** The URL path. */
    String path();

    /** The HTTP method. */
    HttpMethod method();

}
