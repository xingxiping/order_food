package com.order_food.web.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.order_food.web.dao.ShipAddressDao;
import com.order_food.web.entity.model.ShipAddress;
import com.order_food.web.entity.model.ShopCartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 配送地址
 * <p>
 * Created by xingxiping on 2019/5/2.
 */
@Service
public class ShipAddressService {

    @Autowired
    private ShipAddressDao shipAddressDao;

    /**
     * 根据主键，查询地址信息
     *
     * @param id
     * @return
     */
    public ShipAddress queryById(Long id) {
        return shipAddressDao.selectById(id);
    }

    /**
     * 查询用户配送地址信息
     *
     * @param userId 用户id
     * @return
     */
    public List<ShipAddress> queryUserShipAddresses(Long userId) {
        return shipAddressDao.queryByUserId(userId);
    }
    /**
     * 查询用户配送地址信息
     *
     * @param isdefault
     * @return
     */
    public List<ShipAddress> queryShipAddressesByisdefault(Long userId , String isdefault) {
        LambdaQueryWrapper<ShipAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShipAddress::getIsDefault, isdefault);
        queryWrapper.eq(ShipAddress::getUser_id, userId);
        return shipAddressDao.selectList(queryWrapper);
    }
    /**
     * 保存用户配送地址信息
     *
     * @param userId  用户id
     * @param name    收货人姓名
     * @param phone   收获人手机号
     * @param address 详细地址
     */
    public void saveShipAddress(Long userId, String name, String phone, String address,String isDefault) {
        Date now = new Date();
        ShipAddress shipAddress = new ShipAddress()
                .setUser_id(userId)
                .setName(name)
                .setPhone(phone)
                .setAddress(address)
                .setIsDefault(isDefault)
                .setUpdate_time(now)
                .setCreate_time(now);
        shipAddressDao.insert(shipAddress);
    }

    /**
     * 批量删除配送地址
     *
     * @param ids
     */
    public void deleteShipAddress(Long ids) {
        shipAddressDao.deleteById(ids);
    }

    /**
     * 修改配送地址信息
     *
     * @param id       配送地址id
     * @param name     收货人姓名
     * @param phone    收获人手机号
     * @param address  详细地址
     */
    public void updateShipAddress(Long id, String name, String phone,  String address, String isDefault) {
        ShipAddress shipAddress = shipAddressDao.selectById(id);
        if (shipAddress == null) {
            return;
        }
        shipAddress.setName(name)
                .setPhone(phone)
                .setAddress(address)
                .setIsDefault(isDefault)
                .setUpdate_time(new Date());

        shipAddressDao.updateById(shipAddress);

    }
}
