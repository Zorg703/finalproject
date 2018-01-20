package by.mordas.project;

import java.sql.*;
import java.util.Properties;

public class DBConnect {
        private String url = "jdbc:mysql://localhost:3306/test";
        private String name="root";
        private String password="123";
        private String all="SELECT*FROM tester";
        private String delete="DELETE FROM tester";
        private Connection connection;
        static private DBConnect instance;
        private DBConnect(){
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public static DBConnect getInstance() {
            if(instance==null){
                instance=new DBConnect();
            }

        return instance;
    }

    public Connection getConnection() {
            /*try {
                Properties prop = new Properties();
                prop.put("user", "root");
                prop.put("password", "123");
                prop.put("autoReconnect", "true");
                prop.put("useUnicode", "true");
                prop.put("characterEncoding", "UTF-8");

            connection = DriverManager.getConnection(url, prop);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return connection;
    }




        public void writeInTable(String s) {
            String sql="INSERT INTO tester(name) VALUES ("+"\""+s+"\""+")";
            try {
                connection=instance.getConnection();
                Statement statement = null;
                try {
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                } finally {
                    if (statement != null) {
                        statement.close();
                    } else {
                        System.err.println("Statement не создан");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        System.err.println("Сonnection close error: " + e);
                    }
                }
            }
        }
        public String readFromTable(){
            connection=instance.getConnection();
            String s=null;

            try {

                Statement statement = null;
                try {
                    statement = connection.createStatement();
                    ResultSet resultSet = null;
                    try {
                        resultSet = statement.executeQuery(all);
                        while (resultSet.next()) {
                            s = resultSet.getString(1);
                        }

                    } finally {
                        if (resultSet != null) {
                            resultSet.close();
                        } else {
                            System.err.println("ошибка во время чтения из БД");
                        }
                    }
                } finally {
                    if (statement != null) {
                        statement.close();
                    } else {
                        System.err.println("Statement не создан");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        System.err.println("Сonnection close error: " + e);
                    }
                }
            }
            return s;
        }
    public void deleteAllFromTable(){
        connection=instance.getConnection();
        try {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                statement.executeUpdate(delete);
            } finally {
                if (statement != null) {
                    statement.close();
                } else {
                    System.err.println("Statement не создан");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Сonnection close error: " + e);
                }
            }
        }

    }



}
