package jp.ac.chitose.wsp_servlet.task.battle;

import jp.ac.chitose.wsp_servlet.task.battle.CreateRand;
import jp.ac.chitose.wsp_servlet.task.battle.Result;
import jp.ac.chitose.wsp_servlet.task.dao.HistoryDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
* 攻撃の判定を行うクラス
* */
public class Judge {

    private String player;
    private String cpu;
    private Result result = new Result();
    private HistoryDAO historyDAO = new HistoryDAO();
    private static final String P_WIN = "Playerの勝利";
    private static final String C_WIN = "Computerの勝利";
    private static final String DRAW = "引き分け";

    /*
    * @Param p_att_coords 自機の攻撃座標
    * */
    public boolean playerJudge(String p_att_coords) {
        // 攻撃座標がComputerの船の座標と同じなら死
        if( p_att_coords.equals(this.cpu) ) {
            return true;
        } else {
            return false;
        }
    }

    /*
    * @Param c_att_coords CPUの攻撃座標
    * */
    public boolean cpuJudge(String c_att_coords) {
        // 攻撃座標がPlayerの船の座標と同じなら死
        if( c_att_coords.equals(this.player) ) {
            return true;
        } else {
            return false;
        }
    }

    /*
    *
    * */
    public void setPlayerCoords(String playerCoords) {
        this.player = playerCoords;
    }

    /*
    *
    * */
    public void setCpuCoords(String cpuCoords) {
        this.cpu = cpuCoords;
    }

    /*
    *
    * */
    public void judgeResult(HttpServletRequest req, HttpServletResponse resp, HttpSession session, CreateRand createRand)
            throws ServletException, IOException {

        if( req.getParameter("att_len") != null && req.getParameter("att_wid") != null ) {
            // 自分の攻撃座標
            String p_att_coords = req.getParameter("att_len") + req.getParameter("att_wid");
            System.out.println("P_Att -> " + p_att_coords);
            // CPUの攻撃座標を作成
            String c_att_coords = createRand.createAttCoords();   // ex -> 23, 44, 02 ...
            System.out.println("C_Att -> " + c_att_coords);
            // 攻撃の判定（自機->CPU、CPU->自機：ただし同時撃破はあり）
            if( playerJudge(p_att_coords) && cpuJudge(c_att_coords) ) {
                // 同時撃破
                // Resultにforwardして終了
                session.setAttribute("winner", DRAW);
                historyDAO.insertGameResult(DRAW);
                System.out.println("おわり：PE");
                result.doGet(req, resp);
            } else if ( playerJudge(p_att_coords) ) {
                // Player勝利
                // Resultにforwardして終了
                session.setAttribute("winner", P_WIN);
                historyDAO.insertGameResult(P_WIN);
                System.out.println("おわり：P");
                result.doGet(req, resp);
            } else if( cpuJudge(c_att_coords) ) {
                // CPU勝利
                // Resultにforwardして終了
                session.setAttribute("winner", C_WIN);
                historyDAO.insertGameResult(C_WIN);
                System.out.println("おわり：E");
                result.doGet(req, resp);
            } else {
                // 攻撃座標を保存して続行
                //
            }
            // 確認のため表示
//            System.out.println("Player-> " + p_att_coords + "\nComputer-> " + c_att_coords + "\n");
        }
    }

    private void
}
