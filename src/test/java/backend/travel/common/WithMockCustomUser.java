package backend.travel.common;

import backend.travel.domain.user.entity.UserRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface WithMockCustomUser {
    long id() default 1L;
    UserRole role() default  UserRole.ROLE_USER;
}
