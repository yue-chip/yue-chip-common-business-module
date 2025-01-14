package com.yue.chip.upms.application.service.impl;

import com.security.log.LogExposeService;
import com.yue.chip.core.SystemLogService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @author jiacheng.liao on 2025/1/8
 */
@Service(value = "SecurityLogService")
@Primary
public class SystemLogServiceImpl implements SystemLogService {

    @DubboReference
    private LogExposeService logExposeService;
    @Value("${spring.datasource.url}")
    String DB_URL;
    @Value("${spring.datasource.username}")
    String USER;
    @Value("${spring.datasource.password}")
    String PASS;

    @Override
    public void save(String actionName) {
        logExposeService.saveLog(actionName);
    }

    @Override
    public void saveLog(String actionName, Long userId, String type) {
//        logExposeService.saveLogId(actionName, userId, type);
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // 加载金仓数据库驱动
            Class.forName("com.kingbase8.Driver");
            // 建立连接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 创建SQL语句，指定manage模式和log表
            String sql = "INSERT INTO \"security-xz\".\"system_log\" " +
                    " (create_date_time, create_user_id, update_date_time, update_user_id, action_name, user_id) " +
                    " VALUES (?, ?, null, null, ?, ?);";
            // 预编译SQL语句
            pstmt = conn.prepareStatement(sql);
            // 设置参数，这里的问号(?)是占位符
            pstmt.setString(1, LocalDateTime.now().toString());
            pstmt.setString(2, userId.toString());
            pstmt.setString(3, actionName);
            pstmt.setString(4, userId.toString());
            // 执行SQL语句
            pstmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            System.out.println("KingbaseES JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL error occurred.");
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    public Page<? extends Object> list(LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable) {
        return null;
    }
}
