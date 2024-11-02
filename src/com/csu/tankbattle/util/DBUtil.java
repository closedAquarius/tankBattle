package com.csu.tankbattle.util;

import java.sql.*;
import java.util.ArrayList;

public class DBUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql:///tankbattle?useSSL=false&serverTimeZone=UTC", "root", "238373");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(ResultSet resultSet,PreparedStatement preparedStatement, Connection connection) {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 增加
     * @param objects
     * @return
     */
    public static int insert(Object... objects) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int j = 0;
        try {
            statement = connection.prepareStatement("insert into users values(null,?,?,0)");
            for (int i = 0; i < objects.length; i++) {
                statement.setObject(i+1,objects[i]);
            }
            j = statement.executeUpdate();
            return j;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(null,statement,connection);
        }
        return 0;
    }

    public static int update(String sql, Object... params){
        Connection connection = getConnection();
        PreparedStatement statement=null;
        try {
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(null,statement,connection);
        }
        return 0;
    }
    /**
     * 查
     * @param username
     * @return
     */
    public static ArrayList<String> query(String username) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        ArrayList<String> results = new ArrayList<>();

        try {
            statement = connection.prepareStatement("select * from users where account = ?");
            statement.setObject(1,username);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                results.add(resultSet.getString("id"));
                results.add(resultSet.getString("account"));
                results.add(resultSet.getString("password"));
                results.add(resultSet.getString("score"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet, statement, connection);
        }

        return results;
    }
}
