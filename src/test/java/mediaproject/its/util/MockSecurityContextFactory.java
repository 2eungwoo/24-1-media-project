package mediaproject.its.util;

import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockSecurityContextFactory implements WithSecurityContextFactory<MockTester> {

    @Override
    public SecurityContext createSecurityContext(MockTester annotation) {
        User mockTester = User.builder()
                .id(annotation.userId())
                .username(annotation.username())
                .password(annotation.password())
                .role(annotation.role())
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(mockTester);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customUserDetails,"",customUserDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;

    }
}
