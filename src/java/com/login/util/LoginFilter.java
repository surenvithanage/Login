/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.login.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author shalini_w
 */
//@WebFilter(filterName = "LoginFilter", urlPatterns = {"/home"})
//@WebFilter("/home")
public class LoginFilter implements Filter {

    /**
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       

//        String password = request.getParameter("password");
//        if (password.equals("suren@123")) {
//            chain.doFilter(request, response);//sends request to next resource  
//        } else {
//            out.print("username or password error!");
//            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
//            rd.forward(request, response);
//        }

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath();

        boolean loggedIn = session != null;
//        System.out.println(session.getAttribute("uname") != null);
        boolean loginRequest = req.getRequestURI().equals(loginURI); //false
        
//        System.out.println(loggedIn || loginRequest);
        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
    }

}
