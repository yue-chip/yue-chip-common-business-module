package com.yue.chip.upms.domain.service.tenant;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import com.yue.chip.core.tenant.TenantConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
/**
 * @author Mr.Liu
 * @description: TODO
 * @date 2023/10/24 下午2:43
 */
public class CreateSql {

    @SneakyThrows
    public static void execute(@NotNull DataSource dataSource, @NotNull Connection connection, @NotBlank String database, @NotBlank String tableName, @NotNull Long tenantNumber, @NotNull TempBean tempBean) {
        Db db = DbUtil.use(dataSource);
        Statement stat = connection.createStatement();
        String newDateBase = database.concat(TenantConstant.PREFIX_TENANT).concat(String.valueOf(tenantNumber));
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("");
        sqlBuffer.append("USE " + newDateBase + ";\n");
        stat.execute(sqlBuffer.toString());
        sqlBuffer.delete(0,sqlBuffer.length());
        sqlBuffer.append("SET NAMES utf8mb4;\n");
        stat.execute(sqlBuffer.toString());
        sqlBuffer.delete(0,sqlBuffer.length());
        sqlBuffer.append("SET FOREIGN_KEY_CHECKS = 0;\n");
        stat.execute(sqlBuffer.toString());
        sqlBuffer.delete(0,sqlBuffer.length());
        sqlBuffer.append("\n\n\n");
        if (tempBean.create) {
            // DROP TABLE
//                sqlBuffer.append("DROP TABLE IF EXISTS " + tableName + ";\n");
            // CREATE TABLE
            Entity createTableEntity = db.queryOne("SHOW CREATE TABLE " + database.concat(".").concat(tableName));
            sqlBuffer.append((String) createTableEntity.get("Create Table"));
            sqlBuffer.append(";\n");
            stat.execute(sqlBuffer.toString());
            sqlBuffer.delete(0,sqlBuffer.length());
        }
        // 看配置，是否需要insert语句
        if (tempBean.insert) {
            // INSERT INTO
            List<Entity> dataEntityList = db.query("SELECT * FROM " + database.concat(".").concat(tableName));
            for (Entity dataEntity : dataEntityList) {
                StrBuilder field = StrBuilder.create();
                StrBuilder data = StrBuilder.create();

                dataEntity.forEach((key, valueObj) -> {
                    String valueStr = StrUtil.toStringOrNull(valueObj);
                    field.append(key).append(", ");
                    if (ObjectUtil.isNotNull(valueStr)) {
                        // 值包含 ' 转义处理
                        valueStr = StrUtil.replace(valueStr, "'", "\\'");
                        // boolean 值处理
                        if (StrUtil.equals("true", valueStr)) {
                            data.append("b'1'");
                        } else if (StrUtil.equals("false", valueStr)) {
                            data.append("b'0'");
                        } else {
                            data.append("'").append(valueStr).append("'");
                        }
                    } else {
                        data.append("NULL");
                    }
                    data.append(", ");
                });

                sqlBuffer.append("INSERT INTO " + tableName + "(");
                String fieldStr = field.subString(0, field.length() - 2);
                sqlBuffer.append(fieldStr);
                sqlBuffer.append(") VALUES (");
                String dataStr = data.subString(0, data.length() - 2);
                sqlBuffer.append(dataStr);
                sqlBuffer.append(");\n");
                stat.execute(sqlBuffer.toString());
                sqlBuffer.delete(0,sqlBuffer.length());
            }
        }
        sqlBuffer.append("SET FOREIGN_KEY_CHECKS = 1;\n");
        stat.executeUpdate(sqlBuffer.toString());
        if (!tempBean.insert) {
            stat.executeUpdate("alter table ".concat(tableName).concat(" auto_increment=1;"));
        }
        stat.close();
    }

    @Data
    @Accessors(chain = true)
    public static class TempBean {
        /**
         * 是否需要 create 建表语句，默认需要
         */
        public Boolean create = true;
        /**
         * 是否需要 insert 语句，默认需要
         */
        public Boolean insert = true;
    }
}
