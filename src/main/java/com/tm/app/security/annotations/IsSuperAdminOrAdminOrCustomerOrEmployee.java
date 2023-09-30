package com.tm.app.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Annotation for specifying API authority
 * @author Deepak
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority(T(com.tm.app.enums.UserRoles).SUPER_ADMIN,T(com.tm.app.enums.UserRoles).ADMIN,T(com.tm.app.enums.UserRoles).EMPLOYEE,T(com.tm.app.enums.UserRoles).CUSTOMER)")
public @interface IsSuperAdminOrAdminOrCustomerOrEmployee {

}
