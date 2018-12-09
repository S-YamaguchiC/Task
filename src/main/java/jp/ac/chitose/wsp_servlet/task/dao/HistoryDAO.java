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
    public List<String> selectPlayerAttackHistory() {

        List<String> pAttackArray = new ArrayList<>();
        String sql = "select P_HISTORY from PLAYERHISTORY";

        //noinspection Duplicates
        try( Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD)) {
            try(PreparedStatement ppst = connection.prepareStatement(sql)) {
                ResultSet rs = ppst.executeQuery();
                while (rs.next()) {
                    pAttackArray.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pAttackArray;
    }

    @Override
    public List<String> selectComputerAttackHistory() {

        List<String> cAttackArray = new ArrayList<>();
        String sql = "select C_HISTORY from COMPUTERHISTORY";

        //noinspection Duplicates
        try( Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD)) {
            try (PreparedStatement ppst = connection.prepareStatement(sql)){
                ResultSet rs = ppst.executeQuery();
                while (rs.next()) {
                    cAttackArray.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cAttackArray;
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

    @Override
    public void deleteAttackHistory() {

        String sql1 = "delete from PLAYERHISTORY";
        String sql2 = "delete from COMPUTERHISTORY";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD)){
            try (PreparedStatement ppst1 = connection.prepareStatement(sql1)){
                ppst1.executeUpdate();
            }
            try (PreparedStatement ppst2 = connection.prepareStatement(sql2)){
                ppst2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
