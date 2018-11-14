package jp.ac.chitose.wsp_servlet.task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        makeHtml(resp, session);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //
    }

    private void makeHtml(HttpServletResponse resp, HttpSession session)
            throws IOException {

        try(PrintWriter out = resp.getWriter() ) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head><body>");
            out.println("<p>戦艦の位置を設定</p>");
            out.println("<form action=\"./battle\" method=\"POST\">");
            out.println("<p>縦(1~5)<input type=\"text\" name=\"bb_len\" placeholder=\"縦座標\"></p>");
            out.println("<p>横(1~5)<input type=\"text\" name=\"bb_wid\" placeholder=\"横座標\"></p>");
            out.println("<input type=\"submit\" value=\"設定\">");
            out.println("</form>");
            out.println("</body></html>");
        }
    }
}
