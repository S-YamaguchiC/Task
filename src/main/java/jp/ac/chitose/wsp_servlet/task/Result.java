package jp.ac.chitose.wsp_servlet.task;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/result")
public class Result extends HttpServlet {

    //いつもの
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession();

        try(PrintWriter out = resp.getWriter() ) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head><body>");
            out.println("<p>試合終了～" + session.getAttribute("winner") + "</p>");
            out.println("<a href=\"././register\">トップに戻る</a>");
            out.println("</body></html>");
        }
    }
}
