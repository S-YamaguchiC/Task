package jp.ac.chitose.wsp_servlet.task;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

@WebServlet("/battle")
public class Battle extends HttpServlet {

    private static ArrayList<String> resultArray;
    private CreateRand createRand = new CreateRand();
    private String p_att_coords;    // Playerの攻撃座標
    private String c_att_coords;    // CPUの攻撃座標
    private Judge judge = new Judge();  // 攻撃判定用

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
            // CPUの座標設定
            createRand.createComCoords();
            session.setAttribute("c_len", createRand.getComLen());
            session.setAttribute("c_wid", createRand.getComWid());
            // CPUの全攻撃パターンをロード
            createRand.allAttPatern();
        }
        // 自機：CPUの座標を表示
        System.out.println(session.getAttribute("s_len") + " : " + session.getAttribute("s_wid"));
        System.out.println(session.getAttribute("c_len") + " : " + session.getAttribute("c_wid"));

        // 攻撃座標が存在した場合に、CPUもランダムで攻撃
        if( req.getParameter("att_len") != null && req.getParameter("att_wid") != null ) {
            // 自分の攻撃座標
            p_att_coords = req.getParameter("att_len") + req.getParameter("att_wid");
            // CPUの攻撃座標を作成
            c_att_coords = createRand.createAttCoords();   // ex -> 23, 44, 02 ...
            // 攻撃の判定（自機->CPU、CPU->自機：ただし同時撃破はあり）
            if( judge.playerJudge(p_att_coords) && judge.cpuJudge(c_att_coords) ) {
                // 同時撃破
                // Resultにforwardして終了
            } else if ( judge.playerJudge(p_att_coords) ) {
                // Player勝利
                // Resultにforwardして終了
            } else if( judge.cpuJudge(c_att_coords) ) {
                // CPU勝利
                // Resultにforwardして終了
            } else {
                // 続行(何もしない)
            }
            // 確認のため表示
            System.out.println("Player-> " + p_att_coords + "\nComputer-> " + c_att_coords + "\n");
        }
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
            out.println("攻撃座標（縦）：<input type=\"text\" name=\"att_len\"><br>");
            out.println("攻撃座標（横）：<input type=\"text\" name=\"att_wid\"><br>");
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

            // 追加で、攻撃・撃破の履歴表示いれよっかな
            out.println("<p>-----------------------履歴-------------------------</p>");
//            for(int i=0; i<resultArray.size(); i++) {
//                //print
//                out.println("<p>座標：" + resultArray.get(i) + "</p>");
//            }
            out.println("<a href=\"./register\">対戦の中断</a>");
            out.println("</body></html>");
        }
    }
}
