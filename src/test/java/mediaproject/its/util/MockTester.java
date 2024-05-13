package mediaproject.its.util;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface MockTester {

    int id() default 555;
    int userId() default 999;
    String username() default "mock_user";
    String password() default "1234";
    String role() default "ROLE_USER";
}
