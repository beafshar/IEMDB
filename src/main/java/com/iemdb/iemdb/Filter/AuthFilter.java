package com.iemdb.iemdb.Filter;

import java.io.IOException;
import com.iemdb.iemdb.Model.Error.UserNotFound;
import com.iemdb.iemdb.Model.IEMDBController;
import com.iemdb.iemdb.Model.User;
import io.jsonwebtoken.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@WebFilter("/*")
public class AuthFilter implements Filter {

    List<String> allowedURLs = Arrays.asList("login", "signup", "callback", "movies", "actors");

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String header = ((HttpServletRequest) servletRequest).getHeader(HttpHeaders.AUTHORIZATION);
        String[] path = ((HttpServletRequest) servletRequest).getRequestURI().split("/");

        if (allowedURLs.contains(path[1])) {
            filterChain.doFilter(request, response);
            return;
        }
        if (header == null || header.split(" ").length < 2) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String jwt = header.split(" ")[1];
        SecretKey key = new SecretKeySpec(IEMDBController.KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Jws<Claims> jwsClaims;
        try {
            jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);
            if (jwsClaims.getBody().getExpiration().before(new Date()))
                throw new JwtException("Token is expired");
            String userEmail = jwsClaims.getBody().get("user", String.class);
            User user = IEMDBController.userHandler.findUserFromGit(userEmail);
            request.setAttribute("user", user);
            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            System.out.println(e.getMessage());
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"BAD JWT\"}");
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json;charset=UTF-8");
        } catch (UserNotFound e) {}

    }

    @Override
    public void destroy() {}
}
