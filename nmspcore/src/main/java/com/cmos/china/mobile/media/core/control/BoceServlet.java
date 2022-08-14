package com.cmos.china.mobile.media.core.control;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lishuguang on 2017/3/14.
 */
@WebServlet(name = "BoceServlet")
public class BoceServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory
            .getApplicationLog(BoceServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doPost","boce is ok.");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
