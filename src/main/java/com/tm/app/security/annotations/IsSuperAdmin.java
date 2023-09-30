package com.tm.app.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Annotation for specifying API access control
 * 
 * @author Deepak
 * 
 */

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Annotation for specifying API authority
 * @author Deepak
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority(T(com.tm.app.enums.UserRoles).SUPER_ADMIN)")
public @interface IsSuperAdmin {
	
}
