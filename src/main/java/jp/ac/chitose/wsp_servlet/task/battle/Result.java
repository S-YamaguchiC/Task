package jp.ac.chitose.wsp_servlet.task.battle;

import jp.ac.chitose.wsp_servlet.task.dao.HistoryDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/result")
public class Result extends HttpServlet {

    private HistoryDAO historyDAO = new HistoryDAO();
    private int i=1;

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
            out.println("<p>----------過去の対戦履歴----------</p>");
            for (String result : historyDAO.selectGameResults()) {
                out.println(i + ". " + result + "<br>");
                i++;
            }
            out.println("</body></html>");
        }
    }
}
