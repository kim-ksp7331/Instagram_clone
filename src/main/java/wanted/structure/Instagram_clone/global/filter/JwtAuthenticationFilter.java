package wanted.structure.Instagram_clone.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import wanted.structure.Instagram_clone.api.auth.service.JwtProvider;
import wanted.structure.Instagram_clone.global.exception.ForbiddenException;
import wanted.structure.Instagram_clone.global.exception.TokenExpireException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/auth/signup", "/auth/login", "/auth/mail", "/user/reissue"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        log.info("JwtAuthenticationFilter : jwt 인증 처리");

        String jwtToken = parseJwt(request);
        log.info("jwtToken = {}", jwtToken);
        if (jwtToken != null) {
            if (jwtProvider.validateToken(jwtToken)) {
                Authentication auth = jwtProvider.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                throw new TokenExpireException();
            }

        } else {
            throw new ForbiddenException();
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}