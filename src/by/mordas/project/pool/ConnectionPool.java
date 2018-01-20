package by.mordas.project.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPool {
    private static ArrayBlockingQueue<DBConnection> connectionsStorage;
    public static ConnectionPool instance;
    private DBManager manager;
    private static Lock lock=new ReentrantLock();
    private static AtomicBoolean isInstance=new AtomicBoolean(false);
    private Properties properties;
    private final int POOL_SIZE;


    private ConnectionPool() {
        manager=new DBManager();
        manager.registerDriver();
        properties=manager.getProperties();
        POOL_SIZE=Integer.parseInt(properties.getProperty("poolSize"));
        connectionsStorage=new ArrayBlockingQueue<DBConnection>(POOL_SIZE);
        initializePool();
    }

    private void initializePool(){
        DBConnection dbConnection;
    for (int i=0;i<POOL_SIZE;i++){
        try {
            Connection connection=DriverManager.getConnection(properties.getProperty("url"),properties);
            dbConnection=new DBConnection(connection);
             connectionsStorage.offer(dbConnection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    }

    public static ConnectionPool getInstance() {
        if(!isInstance.get()) {
            try {
                lock.lock();
                if (!isInstance.get()) {
                    instance = new ConnectionPool();
                    isInstance.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public static DBConnection getConnection(){
        try {
            DBConnection connection=connectionsStorage.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void closeConnection(DBConnection connection){
        connectionsStorage.offer(connection);

    }

    public void closePool(){
        try {
            for (int i=0;i<POOL_SIZE;i++) {
                connectionsStorage.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        manager.deregisterDriver();
    }

    public static void main(String[] args) {
        ConnectionPool.getInstance();



    }
}
