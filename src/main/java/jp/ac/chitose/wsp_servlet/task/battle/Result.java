package jp.ac.chitose.wsp_servlet.task.battle;

import jp.ac.chitose.wsp_servlet.task.dao.HistoryDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet("/result")
public class Result extends HttpServlet {

    HistoryDAO historyDAO = new HistoryDAO();

    //いつもの
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession();

        // 直でこのページを開くとおこられる
        if(Objects.isNull(session.getAttribute("winner"))) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("./error");
            dispatcher.forward(req, resp);
        }

        try(PrintWriter out = resp.getWriter() ) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head><body>");
            out.println("<p>試合終了～" + session.getAttribute("winner") + "</p>");
            out.println("<a href=\"././register\">トップに戻る</a>");
            out.println("<p>----------過去の対戦履歴----------</p>");
            int i = 1;
            for (String result : historyDAO.selectGameResults()) {
                out.println(i + ". " + result + "<br>");
                i++;
            }
            out.println("<p>----------勝率 : "+ historyDAO.countWin() +"%-----------</p>");
            out.println("</body></html>");
        }
        historyDAO.deleteAttackHistory();
    }
}
