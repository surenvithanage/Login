/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.login.connection;

import com.login.bean.LoginBean;
import com.login.bean.PageBean;
import com.login.dao.LoginDao;
import com.login.util.Log4jLogger;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author shalini_w
 */
public class LoginServlet extends HttpServlet {


    Log4jLogger log = new Log4jLogger();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //response.sendRedirect("index.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ArrayList<PageBean> al;
        LoginBean loginbean = new LoginBean();

        loginbean.setUsername(username);
        loginbean.setPassword(password);

        //return the boolean value = true when the user authenticate by username and password successfully
        boolean flag = LoginDao.authenticateUser(loginbean);

        if (flag == true) {
            try {
                //check the user's reset password status : for first login of user
                String reset = LoginDao.getResetStatus(loginbean);
                
                if (reset.equals("0")) {
                    request.setAttribute("username", username);
                    request.setAttribute("password", password);
                    request.getRequestDispatcher("password_reset.jsp").forward(request, response);
                } else {
                    
                    //check the reset password duration with the current date and last reset date : for all users
                    int reset_duration = LoginDao.getResetDuration(loginbean);
                    String reset_date = LoginDao.getResetTime(loginbean);

                    //get today date
                    LocalDate today = LocalDate.now();
                    

                    //get the last reset date into localdate object
                    LocalDate last_reset = LocalDate.parse(reset_date);

                    //calculate the interval days between today and last reset day
                    int intervalDays = (int) ChronoUnit.DAYS.between(last_reset, today);

                    //if the interval exceed the reset_duration user should reset password to login
                    if (reset_duration <= intervalDays) {
                        request.setAttribute("username", username);
                        request.setAttribute("password", password);
                        request.getRequestDispatcher("password_reset.jsp").forward(request, response);
                    } else {
                        //when the users complete the all checkpoints they can log
                        al = LoginDao.loadPages();

                        session.setAttribute("pages", al);
                        session.setAttribute("uname", username);
                        session.setAttribute("roleid", loginbean.getRoleid());
                        request.getRequestDispatcher("home.jsp").forward(request, response);

                        log.getLogger("System Log".toUpperCase(), "info", username, request);
                    }

                }
            } catch (IOException | ServletException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //authentication fails
            log.getLogger("unauthorized Login Attempt".toUpperCase(), "warn", username, request);
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
