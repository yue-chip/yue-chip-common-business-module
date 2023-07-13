package com.yue.chip.common.business.infrastructure.dao.file.impl;

import com.yue.chip.common.business.infrastructure.dao.file.FileDaoEx;
import com.yue.chip.common.business.infrastructure.po.file.FilePo;
import com.yue.chip.core.persistence.curd.BaseDao;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Liu
 * @date 2023/7/12 下午4:51
 */
public class FileDaoImpl implements FileDaoEx {

    @Resource
    private BaseDao<FilePo> filePoBaseDao;

    @Override
    public List<FilePo> find(Long tableId, String fileFieldName, String tableName) {
        StringBuffer sb = new StringBuffer();
        Map<String, Object> para = new HashMap<>();
        sb.append("select f from FilePo f join FileRelationalPo fr on f.id = fr.fileId where fr.tableId=:tableId and fr.fileFieldName=:fileFieldName and fr.tableName=: tableName");
        para.put("tableId",tableId);
        para.put("fileFieldName",fileFieldName);
        para.put("tableName",tableName);
        return (List<FilePo>) filePoBaseDao.findAll(sb.toString(),para);
    }
}
