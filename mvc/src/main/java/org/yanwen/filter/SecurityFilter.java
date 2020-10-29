package org.yanwen.filter;


import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.yanwen.model.User;
import org.yanwen.service.JWTService;
import org.yanwen.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"},dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    private static String AUTH_URI = "/auth";

    @Autowired
    private JWTService jwtService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //1.Extract Authorization header
        //2.Remove Bearer to get token
        //3.Decrypt token to get claim
        //4.Verify username info in our database from claim
        //5.doFilter dispatch to controller

        if (userService == null) {
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, request.getServletContext());
        }

        int statusCode = authorization((HttpServletRequest)request);
        if (statusCode == HttpServletResponse.SC_ACCEPTED) chain.doFilter(request, response);
        else ((HttpServletResponse)response).sendError(statusCode);

    }

    private int authorization(HttpServletRequest req){
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        String verb = req.getMethod();
        if (uri.equalsIgnoreCase(AUTH_URI)) return HttpServletResponse.SC_ACCEPTED;

        try{
            String token = req.getHeader("Authorization").replaceAll("(.*?): ","");
            if (token == null || token.isEmpty()) return statusCode;

            Claims claims = jwtService.decryptJwtToken(token);
            //TODO pass username and check role

            if(claims.getId()!=null){
                User u = userService.getByID(Long.valueOf(claims.getId()));
                if(u==null) return statusCode;
//                statusCode = HttpServletResponse.SC_ACCEPTED;
//                u.getRoles();
            }
            String allowedResources = "/";
            switch(verb) {
                case "GET"    : allowedResources = (String)claims.get("allowedReadResources"); break;
                case "POST"   : allowedResources = (String)claims.get("allowedCreateResources"); break;
                case "PUT"    : allowedResources = (String)claims.get("allowedUpdateResources"); break;
                case "DELETE" : allowedResources = (String)claims.get("allowedDeleteResources"); break;
            }

            for (String s : allowedResources.split(",")) {
                if (uri.trim().toLowerCase().startsWith(s.trim().toLowerCase())) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("can't verify the token",e);
        }
        return statusCode;
    }

    @Override
    public void destroy() {

    }
}
