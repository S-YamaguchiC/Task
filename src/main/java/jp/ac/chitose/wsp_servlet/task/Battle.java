package jp.ac.chitose.wsp_servlet.task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/battle")
public class Battle extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        makeHtml(resp, session);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //
        String bb_len = req.getParameter("bb_len");
        String bb_wid = req.getParameter("bb_wid");
        // 二度目の送信でNULLになる件
        HttpSession session = req.getSession();
        session.setAttribute("s_len", bb_len);
        session.setAttribute("s_wid", bb_wid);
        System.out.println(session.getAttribute("s_len") + " " + session.getAttribute("s_wid"));

        //
        makeHtml(resp, session);
    }

    private void makeHtml(HttpServletResponse resp, HttpSession session)
            throws IOException {

        try(PrintWriter out = resp.getWriter() ) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head><body>");
            out.println("<p>お互いの準備ができました、攻撃開始</p>");
            out.println("<form action=\"./battle\" method=\"POST\">");
            out.println("攻撃座標（縦）：<input type=\"text\" name=\"att_len\"><br>");
            out.println("攻撃座標（横）：<input type=\"text\" name=\"att_wid\"><br>");
            out.println("<input type=\"submit\" value=\"攻撃\">");
            out.println("</form>");
            makeBattlefield(resp, session);
        }
    }

    private void makeBattlefield(HttpServletResponse resp, HttpSession session)
            throws IOException {
        try(PrintWriter out = resp.getWriter() ) {
            out.println("<h5>あなたの陣地</h5>");
            out.println("<table border=\"3\"><th></th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th>");
            String BB = "W";
            for (int i=0; i<5; i++) {
                out.println("<tr>");
                out.println("<th>"+ (i+1) +"</th>");
                for (int j=0; j<5; j++) {
                    //
                    if (session.getAttribute("s_len").equals(String.valueOf(i+1))
                            && session.getAttribute("s_wid").equals(String.valueOf(j+1))) {
                        out.println("<td id="+ String.valueOf(i)+String.valueOf(j) + " align='right' width='20'>" + BB + "</td>");
                    } else {
                        // CPUから攻撃されたときinnerHTMLで変更できるように空文字
                        out.println("<td id="+ String.valueOf(i)+String.valueOf(j) + " align='right' width='20'>" + " " + "</td>");
                    }
                }
                // 内側のループの終了
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<h5>CPUの陣地</h5>");
            out.println("<table border=\"3\"><th></th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th>");
            for (int i=0; i<5; i++) {
                out.println("<tr>");
                out.println("<th>"+ (i+1) +"</th>");
                for (int j=0; j<5; j++) {
                    //
                    out.println("<td id="+ String.valueOf(i)+String.valueOf(j) + " align='right' width='20'>" + " " + "</td>");
                }
                // 内側のループの終了
                out.println("</tr>");
            }
            out.println("</table><br>");
            out.println("<a href=\"./register\">対戦の中断</a>");
            out.println("</body></html>");
        }
    }
}
