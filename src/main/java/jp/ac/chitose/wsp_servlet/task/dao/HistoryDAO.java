package jp.ac.chitose.wsp_servlet.task.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO implements IHistoryDAO {

    public static final String DB_URL = "jdbc:h2:file:~/h2db/battle-ship;Mode=PostgreSQL";
    public static final String DB_USER = "root";
    public static final String DB_PASSWD = "root";

    @Override
    public void insertPlayerHistory(String playerAttackCoodinate) {

        String sql = "insert into PLAYERHISTORY (P_HISTORY) values (?)";

        //noinspection Duplicates
        try( Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD) ) {
            try( PreparedStatement ppst = connection.prepareStatement(sql) ) {
                ppst.setString(1, playerAttackCoodinate);
                ppst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertComputerHistory(String computerAttackHistory) {

        String sql = "insert into COMPUTERHISTORY (C_HISTORY) values (?)";

        //noinspection Duplicates
        try ( Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD)) {
            try ( PreparedStatement ppst = connection.prepareStatement(sql) ) {
                ppst.setString(1, computerAttackHistory);
                ppst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertGameResult(String result) {

        String sql = "insert into RESULTS (RESULT) values (?)";

        //noinspection Duplicates
        try ( Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD) ) {
            try ( PreparedStatement ppst = connection.prepareStatement(sql) ) {
                ppst.setString(1, result);
                ppst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> selectGameResults() {

        List<String> resultArray =  new ArrayList<>();
        String sql = "select RESULT from RESULTS";

        try ( Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD) ) {
            try (PreparedStatement ppst = connection.prepareStatement(sql) ) {
                ResultSet rs = ppst.executeQuery();
                while (rs.next()) {
                    resultArray.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {

        }
        return resultArray;
    }


}
