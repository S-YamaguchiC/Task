package jp.ac.chitose.wsp_servlet.task;

/*
* 攻撃の判定を行うクラス
* */
public class Judge {

    private String player;
    private String cpu;

    /*
    * @Param p_att_coords 自機の攻撃座標
    * */
    public boolean playerJudge(String p_att_coords) {
        if( p_att_coords.equals(this.player) ) {
            return true;
        } else {
            return false;
        }
    }

    /*
    * @Param c_att_coords CPUの攻撃座標
    * */
    public boolean cpuJudge(String c_att_coords) {
        if( c_att_coords.equals(this.cpu) ) {
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
    public void judgeRsult() {

    }
}
