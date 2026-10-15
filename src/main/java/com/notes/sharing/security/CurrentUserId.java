package com.notes.sharing.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Put this on a controller parameter and Spring fills in my user id from the token.
// Example: public ... getMine(@CurrentUserId Long userId)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUserId {
}
