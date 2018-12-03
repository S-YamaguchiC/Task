package jp.ac.chitose.wsp_servlet.task.dao;

import java.util.List;

public interface IHistoryDAO {

    /**
     * 攻撃座標をPLAYERHISTORYテーブルに追加する
     * @param playerAttackCoodinate Playerの攻撃座標
     * */
    void insertPlayerHistory(String playerAttackCoodinate);

    /**
     * CPUの攻撃座標をCOMPUTERHISTORYテーブルに追加する
     * @param computerAttackHistory Computerの攻撃座標
     * */
    void insertComputerHistory(String computerAttackHistory);

    /**
     * 引数をもとに試合の結果を保存する
     * @param result 試合結果
     * */
    void insertGameResult(String result);

    /**
     * 過去すべての試合結果を取得する
     * */
    List<String> selectGameResults();
}
