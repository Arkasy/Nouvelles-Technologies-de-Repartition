package com.istv.airbnb.Interceptor;

import com.istv.airbnb.Model.User;
import com.istv.airbnb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.attribute.UserPrincipal;

@Component
public class GeneralInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserRepository userRepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isLogged(request)) {
            addToModelUserDetails(request);
            return true;
        }
        return true ;

    }

    private boolean isLogged(HttpServletRequest request) {
        if (request.getUserPrincipal() == null)
            return false;
        else
            return true;
    }

    private void addToModelUserDetails(HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
            User currentUser = userRepo.findByUsername(request.getUserPrincipal().getName());
            request.getSession().setAttribute("current_user", currentUser);
        }

    }
}
