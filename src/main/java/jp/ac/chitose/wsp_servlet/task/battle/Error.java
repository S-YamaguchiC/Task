package jp.ac.chitose.wsp_servlet.task.battle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/error")
public class Error extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        getHtml(resp);
    }

    private void getHtml(HttpServletResponse resp) throws IOException{

        //noinspection Duplicates
        try(PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta charaset=\"UTF-8\">");
            out.println("</head><body>");
            out.println("<p>まずゲームをしなさい</p>");
            out.println("<p><a href=\"./register\">TOP画面へ</a></p>");
            out.println("</body></html>");
        }

    }
}
