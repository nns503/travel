package backend.travel.global.auth;

import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.entity.UserRole;
import backend.travel.global.auth.entity.AuthUser;
import backend.travel.global.auth.exception.AuthJWTException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return ;
        }

        String token = authorization.substring("Bearer ".length());
        if (!jwtUtil.isValidToken(token)) {
            throw new AuthJWTException();
        }
        Long userId = jwtUtil.getUserId(token);
        UserRole role = jwtUtil.getRole(token);

        User user = User.builder()
                .id(userId)
                .role(role)
                .build();

        AuthUser authUser = new AuthUser(user);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
