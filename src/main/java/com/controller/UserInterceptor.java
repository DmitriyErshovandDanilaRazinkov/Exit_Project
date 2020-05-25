package com.controller;

import com.model.DTO.UserDetailsTo;
import com.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !isRedirectView(modelAndView) && isUserLogged()) {
            UserDetailsTo user = UserService.getCurrentUser();
            modelAndView.addObject("currentUser", user);
        }
    }

    public static boolean isRedirectView(ModelAndView modelAndView) {
        String viewName = modelAndView.getViewName();
        if (viewName.startsWith("redirect:/")) {
            return true;
        }

        View view = modelAndView.getView();
        return view instanceof SmartView && ((SmartView) view).isRedirectView();
    }

    public static boolean isUserLogged() {
        try {
            return !"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (Exception e) {
            return false;
        }
    }
}
