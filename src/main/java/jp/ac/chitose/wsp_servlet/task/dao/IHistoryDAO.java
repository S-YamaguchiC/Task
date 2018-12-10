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
     * Playerの攻撃座標を出力
     * @return
     * */
    List<String> selectPlayerAttackHistory();

    /**
     * Computerの攻撃座標を出力
     * @return
     * */
    List<String> selectComputerAttackHistory();

    /**
     * 引数をもとに試合の結果を保存する
     * @param result 試合結果
     * */
    void insertGameResult(String result);

    /**
     * 過去すべての試合結果を取得する
     * @return
     * */
    List<String> selectGameResults();

    /**
     * 試合に勝った回数を取得
     * */
    double countWin();

    /**
     * 試合終了時に攻撃座標テーブルのレコードをすべて削除
     * */
    void deleteAttackHistory();

    /**
     * 攻撃をスカしたとき「・」を表示するためのSQL
     * */
    long selectPlayerMissShot(String coordinate);

    /**
     * 同上
     * */
    long selectComputerMissShot(String coordinate);
}
