package jp.ac.chitose.wsp_servlet.task.battle;

import jp.ac.chitose.wsp_servlet.task.dao.HistoryDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
* CPUの船の座標決定、攻撃座標決定のためのクラス
* */
public class CreateRand {

    // Random
    Random random = new Random();
    HistoryDAO historyDAO = new HistoryDAO();
    // CPU's Coordinate
    private int r_len, r_wid;
    // Atk Coordinate
    private int r_att_len, r_att_wid;

    // 攻撃座標の履歴用Array
    private List<String> attResultArray = new ArrayList<>();

    // CPUの座標
    public String createComCoords() {
        // 座標のランダム出力(1~5), 縦横
        // nextInt(int val) -> 0 ~ val-1 までの値で乱数
        r_len = random.nextInt(5) + 1;
        r_wid = random.nextInt(5) + 1;
        // ret (HTMLの<td>タグのidに相当)
        return String.valueOf(r_len + r_wid);
    }

    // CPUの攻撃座標
    public String createAttCoords() {
        // 座標をランダム出力(1~5), 縦横
        // 既に使われた座標の場合はもう一回ランダム
        while(true) {
            // 0 ~ 5-1
            r_att_len = random.nextInt(5);
            r_att_wid = random.nextInt(5);
            if ( attResultArray.get(r_att_len + r_att_wid).equals("") ) {
                // なにもしない、ループs
            } else {
                break;
            }
        }
        // すべての攻撃パターンから、今出力した値を削除(remove)
        String val = (r_att_len+1) + "," + (r_att_wid+1);
        try {
            // 0が入るとcatchになってしまう
            attResultArray.remove(val);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exception(val) -> " + val);
        }
        // 1,2 or 0,4 or 3,2 etc...
        // ret (HTMLの<td>タグのidに相当)
//        System.out.println("Computer ->" + r_att_len + ", " + r_att_wid + "\n");
        historyDAO.insertComputerHistory(val);
        return String.valueOf(r_att_len+1) + String.valueOf(r_att_wid+1);
    }

    // CPUの全攻撃パターンをArrayListに格納
    public void allAttPatern() {
        // 0~4 の5通り^2 = 25パターン
        // 1~5 に変更
        int index = 0;
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                String val = (i+1) + "," + (j+1);
//                System.out.println(val);
                attResultArray.add(index, val);
                index++;
            }
        }
        System.out.println(Arrays.toString(attResultArray.toArray()));
    }

    // getter
    public String getComLen() {
        return String.valueOf(r_len);
    }

    public String getComWid() {
        return String.valueOf(r_wid);
    }
}
