package com.yuanstack.xconfig.server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * distributed locks.
 *
 * @author Sylvan
 * @date 2024/05/29  22:59
 */
@Component
public class DistributedLocks {


    @Autowired
    private DataSource dataSource;

    Connection connection;

    @Getter
    private AtomicBoolean locked = new AtomicBoolean(false);

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init() {
        try {
            connection = dataSource.getConnection();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        executor.scheduleWithFixedDelay(this::tryLock, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    public boolean lock() throws SQLException {

        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.createStatement().execute("set innodb_lock_wait_timeout=5");
        connection.createStatement().execute("select app from locks where id=1 for update");  // lock 5s

        if (locked.get()) {
            System.out.println(" ==>>>> reenter this dist lock.");
        } else {
            System.out.println(" ==>>>> get a dist lock.");
            // 重新加载一下数据库的数据
        }

        return true;
    }

    private void tryLock() {
        try {
            lock();
            locked.set(true);
        } catch (Exception ex) {
            System.out.println(" ===>>>> lock failed...");
            locked.set(false);
        }
    }

    @PreDestroy
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
                connection.close();
            }
        } catch (Exception ex) {
            System.out.println("ignore this close exception");
        }
    }
}
