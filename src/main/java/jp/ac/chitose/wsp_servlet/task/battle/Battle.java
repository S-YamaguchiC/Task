package jp.ac.chitose.wsp_servlet.task.battle;

import jp.ac.chitose.wsp_servlet.task.dao.HistoryDAO;

import javax.servlet.RequestDispatcher;
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

    private CreateRand createRand = new CreateRand();
    private Judge judge = new Judge();  // 攻撃判定用
    private HistoryDAO historyDAO = new HistoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 突然の訪問は困るのでforwardする
        RequestDispatcher dispatcher = req.getRequestDispatcher("/register");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        // RequestParamがnullでないときのみ実行(要は初回のみ)
        if ( req.getParameter("bb_len") != null && req.getParameter("bb_wid") != null ) {
            // requestリソースから座標を取得
            String bb_len = req.getParameter("bb_len");
            String bb_wid = req.getParameter("bb_wid");
            // registerで入力した座標を設定
            session.setAttribute("s_len", bb_len);
            session.setAttribute("s_wid", bb_wid);
            judge.setPlayerCoords( (String)session.getAttribute("s_len") + session.getAttribute("s_wid"));
            // CPUの座標設定
            createRand.createComCoords();
            session.setAttribute("c_len", createRand.getComLen());
            session.setAttribute("c_wid", createRand.getComWid());
//            System.out.println((session.getAttribute("c_len")) + String.valueOf(session.getAttribute("c_wid")));
            judge.setCpuCoords(String.valueOf(session.getAttribute("c_len")) + String.valueOf(session.getAttribute("c_wid")));
            // CPUの全攻撃パターンをロード
            createRand.allAttPatern();
        } else {
            // next
            historyDAO.insertPlayerHistory(req.getParameter("att_len") + "," + req.getParameter("att_wid"));
        }
        // 自機：CPUの座標を表示
//        System.out.println(session.getAttribute("s_len") + " : " + session.getAttribute("s_wid"));
//        System.out.println(session.getAttribute("c_len") + " : " + session.getAttribute("c_wid"));

        // 攻撃座標が存在した場合に、CPUもランダムで攻撃
        judge.judgeResult(req, resp, session, createRand);
        // html出力
        makeHtml(resp, session);
    }

    /*
    * 攻撃座標の入力フォームを出力する
    * */
    private void makeHtml(HttpServletResponse resp, HttpSession session)
            throws IOException {

        try(PrintWriter out = resp.getWriter() ) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head><body>");
            out.println("<p>お互いの準備ができました、攻撃開始</p>");
            out.println("<form action=\"./battle\" method=\"POST\">");
            out.println("攻撃座標（縦）：<input type=\"number\" name=\"att_len\" placeholder=\"縦座標\" min=\"1\" max=\"5\" required><br>");
            out.println("攻撃座標（横）：<input type=\"number\" name=\"att_wid\" placeholder=\"横座標\" min=\"1\" max=\"5\" required><br>");
            out.println("<input type=\"submit\" value=\"攻撃\">");
            out.println("</form>");
            makeBattlefield(resp, session);
        }
    }

    /*
    * 自陣と敵陣のテーブル表示の出力
    * */
    private void makeBattlefield(HttpServletResponse resp, HttpSession session)
            throws IOException {
        try(PrintWriter out = resp.getWriter() ) {
            out.println("<h5>あなたの陣地</h5>");
            out.println("<table border=\"3\"><th></th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th>");
            String BB = "W";
            String CPU = "C";
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
                    // CreateRandクラスで出力した座標をテーブルに入れる
                    if(session.getAttribute("c_len").equals(String.valueOf(i+1))
                            && session.getAttribute("c_wid").equals(String.valueOf(j+1))) {
                        out.println("<td id=" + String.valueOf(i) + String.valueOf(j) + " align='right' width='20'>" + CPU + "</td>");
                    } else {
                        out.println("<td id=" + String.valueOf(i) + String.valueOf(j) + " align='right' width='20'>" + " " + "</td>");
                    }
                }
                // 内側のループの終了
                out.println("</tr>");
            }
            out.println("</table><br>");

            out.println("<a href=\"./register\">対戦の中断</a>");
            // 追加で、攻撃・撃破の履歴表示いれよっかな
            out.println("<p>-----------------------履歴-------------------------</p>");
            int i=1;
            out.println("<p>Player----------------------------------------------</p>");
            for (String player : historyDAO.selectPlayerAttackHistory()) {
                out.println(i + ". " + player + "<br>");
                i++;
            }
            out.println("<p>Computer--------------------------------------------</p>");
            int j=1;
            for (String player : historyDAO.selectComputerAttackHistory()) {
                out.println(j + ". " + player + "<br>");
                j++;
            }
            out.println("</body></html>");
        }
    }
}
