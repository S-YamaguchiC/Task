package jp.ac.chitose.wsp_servlet.task;

import java.util.List;
import java.util.Random;

/*
* CPUの船の座標決定、攻撃座標決定のためのクラス
* */
public class CreateRand {

    // Random
    Random random = new Random();
    // CPU's Coordinate
    private int r_len, r_wid;
    // Atk Coordinate
    private int r_att_len, r_att_wid;

    // 攻撃座標の履歴用Array
    private List<String> attResultArray;

    // CPUの座標
    public String createComCoords() {
        // 座標のランダム出力(1~5), 縦横
        r_len = random.nextInt(4);
        r_wid = random.nextInt(4);
        // ret (HTMLの<td>タグのidに相当)
        return String.valueOf(r_len + r_wid);
    }

    // CPUの攻撃座標
    public String createAttCoords() {
        // 座標をランダム出力(1~5), 縦横
        r_att_len = random.nextInt(4);
        r_att_wid = random.nextInt(4);
        // すべての攻撃パターンから、今出力した値を削除(remove)
        attResultArray.remove(r_att_len +  "," + r_att_wid);    // 1,2 or 0,4 or 3,2 etc...
        // ret (HTMLの<td>タグのidに相当)
        return String.valueOf(r_att_len + r_att_wid);
    }

    // CPUの全攻撃パターンをArrayListに格納
    public void allAttPatern() {

        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                attResultArray.add(i+j, String.valueOf(i) + "," + String.valueOf(j));   // 1,2 or 0,4 or 3,2 etc...
            }
        }
    }

    // getter
    public String getComLen() {
        return String.valueOf(r_len);
    }

    public String getComWid() {
        return String.valueOf(r_wid);
    }
}
