package backend.travel.common;

import backend.travel.domain.user.entity.User;
import backend.travel.global.auth.entity.AuthUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        User user = User.builder()
                .id(annotation.id())
                .role(annotation.role())
                .build();
        AuthUser authUser = new AuthUser(user);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
        context.setAuthentication(authToken);

        return context;
    }
}
