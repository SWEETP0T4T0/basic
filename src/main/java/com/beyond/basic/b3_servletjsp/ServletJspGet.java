package com.beyond.basic.b3_servletjsp;

import com.beyond.basic.b1_hello.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/servlet/jsp/get")
public class ServletJspGet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("myData","hello world java");
//        sec/main/webapp폴더를 찾아가는것으로 약속
        req.getRequestDispatcher("/WEB-INF/views/hello.jsp").forward(req, resp);

    }
}
