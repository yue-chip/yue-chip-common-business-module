package com.yue.chip.upms.application.service.impl;

import com.yue.chip.upms.application.service.CCSPService;
import com.yue.chip.upms.infrastructure.dao.organizational.OrganizationalDao;
import com.yue.chip.upms.infrastructure.dao.tenant.TenantDao;
import com.yue.chip.upms.infrastructure.dao.user.UserDao;
import com.yue.chip.upms.infrastructure.dao.weixin.UserWeiXinDao;
import com.yue.chip.upms.infrastructure.po.organizational.OrganizationalPo;
import com.yue.chip.upms.infrastructure.po.tenant.TenantPo;
import com.yue.chip.upms.infrastructure.po.user.UserPo;
import com.yue.chip.upms.infrastructure.po.user.UserWeiXinPo;
import com.yue.chip.upms.util.CCSPUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiacheng.liao on 2025/1/8
 */
@Service
public class CCSPServiceImpl implements CCSPService {

    @Resource
    private OrganizationalDao organizationalDao;
    @Resource
    private TenantDao tenantDao;
    @Resource
    private UserDao userDao;
    @Resource
    private UserWeiXinDao userWeiXinDao;

    @Override
    public void organizational() {
        List<OrganizationalPo> all = organizationalDao.findAll();
        all.forEach(po -> {
            String phoneNumberEncrypt = CCSPUtil.SM4encrypt(po.getPhoneNumber());
            if (phoneNumberEncrypt.length() > 0) {
                po.setPhoneNumberEncrypt(phoneNumberEncrypt);
                po.setPhoneNumberHmac(CCSPUtil.getHMac(po.getPhoneNumber()));
            }
            organizationalDao.updateEncrypt(po.getPhoneNumberEncrypt(), po.getPhoneNumberHmac(),
                    po.getId());
        });
    }

    @Override
    public void tenant() {
        List<TenantPo> all = tenantDao.findAll();
        all.forEach(po -> {
            String phoneNumberEncrypt = CCSPUtil.SM4encrypt(po.getPhoneNumber());
            if (phoneNumberEncrypt.length() > 0) {
                po.setPhoneNumberEncrypt(phoneNumberEncrypt);
                po.setPhoneNumberHmac(CCSPUtil.getHMac(po.getPhoneNumber()));
            }
            String managerEncrypt = CCSPUtil.SM4encrypt(po.getManager());
            if (managerEncrypt.length() > 0) {
                po.setManagerEncrypt(managerEncrypt);
                po.setManagerHmac(CCSPUtil.getHMac(po.getManager()));
            }
            tenantDao.updateEncrypt(po.getPhoneNumberEncrypt(), po.getPhoneNumberHmac(),
                    po.getManagerEncrypt(), po.getManagerHmac(),
                    po.getId());
        });
    }

    @Override
    public void user() {
        List<UserPo> all = userDao.findAll();
        all.forEach(po -> {
            String phoneNumberEncrypt = CCSPUtil.SM4encrypt(po.getPhoneNumber());
            if (phoneNumberEncrypt.length() > 0) {
                po.setPhoneNumberEncrypt(phoneNumberEncrypt);
                po.setPhoneNumberHmac(CCSPUtil.getHMac(po.getPhoneNumber()));
            }
            String nameEncrypt = CCSPUtil.SM4encrypt(po.getName());
            if (nameEncrypt.length() > 0) {
                po.setNameEncrypt(nameEncrypt);
                po.setNameHmac(CCSPUtil.getHMac(po.getName()));
            }
            String passwordEncrypt = CCSPUtil.SM4encrypt(po.getPassword());
            if (passwordEncrypt.length() > 0) {
                po.setPasswordEncrypt(passwordEncrypt);
                po.setPasswordHmac(CCSPUtil.getHMac(po.getPassword()));
            }
            String idCardEncrypt = CCSPUtil.SM4encrypt(po.getIdentificationNumber());
            if (idCardEncrypt.length() > 0) {
                po.setIdentificationNumberEncrypt(idCardEncrypt);
                po.setIdentificationNumberHmac(CCSPUtil.getHMac(po.getIdentificationNumber()));
            }
            userDao.updateEncrypt(po.getPhoneNumberEncrypt(), po.getPhoneNumberHmac(),
                    po.getNameEncrypt(), po.getNameHmac(),
                    po.getPasswordEncrypt(), po.getPasswordHmac(),
                    po.getIdentificationNumberEncrypt(), po.getIdentificationNumberHmac(),
                    po.getId());
        });
    }

    @Override
    public void weiXinUser() {
        List<UserWeiXinPo> all = userWeiXinDao.findAll();
        all.forEach(po -> {
            String phoneNumberEncrypt = CCSPUtil.SM4encrypt(po.getPhoneNumber());
            if (phoneNumberEncrypt.length() > 0) {
                po.setPhoneNumberEncrypt(phoneNumberEncrypt);
                po.setPhoneNumberHmac(CCSPUtil.getHMac(po.getPhoneNumber()));
            }
            userWeiXinDao.updateEncrypt(po.getPhoneNumberEncrypt(), po.getPhoneNumberHmac(),
                    po.getId());
        });
    }
}
