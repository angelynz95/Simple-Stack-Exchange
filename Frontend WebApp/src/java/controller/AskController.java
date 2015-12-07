/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import QuestionWS.QuestionWS_Service;
import UserWS.UserWS_Service;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author angelynz95
 */
public class AskController extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8081/Stackexchange_WS/QuestionWS.wsdl")
    private QuestionWS_Service service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8081/Stackexchange_WS/UserWS.wsdl")
    private UserWS_Service service;

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
        
        // Memperoleh user id berdasarkan token
        if ((request.getParameter("token") != "not-valid") && (request.getParameter("token") != null)) {
            int userId = getUserByToken(request.getParameter("token"), "http://localhost:8082/Identity_Service/TokenController");
            if (userId > 0) {
                if (request.getParameter("name") == null) {
                    boolean addQuestion = addQuestion(request.getParameter("question-topic"), request.getParameter("question-content"), request.getParameter("token"));
                    if (addQuestion) {
                        response.sendRedirect("IndexController?token="+request.getParameter("token"));
                    } else {
                        response.sendRedirect("log-in.jsp");
                    }
                } else {
                    request.getServletContext().getRequestDispatcher("/ask-question.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("log-in.jsp");
            }
        } else {
            response.sendRedirect("log-in.jsp");
        }
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private int getUserByToken(java.lang.String token, java.lang.String urlString) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        UserWS.UserWS port = service.getUserWSPort();
        return port.getUserByToken(token, urlString);
    }

    private boolean addQuestion(java.lang.String topic, java.lang.String content, java.lang.String token) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        QuestionWS.QuestionWS port = service_1.getQuestionWSPort();
        return port.addQuestion(topic, content, token);
    }

}
