package com.jzo2o.health.service.impl;


import cn.hutool.db.DbRuntimeException;
import com.alibaba.excel.EasyExcel;
import com.jzo2o.common.utils.BeanUtils;
import com.jzo2o.health.mapper.ReservationSettingMapper;
import com.jzo2o.health.model.domain.ReservationSetting;
import com.jzo2o.health.model.dto.request.ReservationSettingUpsertReqDTO;
import com.jzo2o.health.model.dto.response.ReservationDateResDTO;
import com.jzo2o.health.model.dto.response.ReservationSettingResDTO;
import com.jzo2o.health.model.excel.ReservationImportData;
import com.jzo2o.health.service.IReservationSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 预约设置 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-07-04
 */
@Service
public class ReservationSettingServiceImpl extends ServiceImpl<ReservationSettingMapper, ReservationSetting> implements IReservationSettingService {

    @Override
    public List<ReservationSettingResDTO> queryReservationByMonth(String date) {
        List<ReservationSettingResDTO> reservationSettingResDTOS = baseMapper.selectByMonth(date);
        return reservationSettingResDTOS;
    }

    @Override
    public void editByDate(ReservationSettingUpsertReqDTO reservationSettingUpsertReqDTO) {
        LocalDate orderDate = reservationSettingUpsertReqDTO.getOrderDate();
        Integer number = reservationSettingUpsertReqDTO.getNumber();
        baseMapper.updateByDate(orderDate,number);
    }

    @Override
    public void updateByExcel(MultipartFile file) {
        try {
            List<ReservationImportData> list = EasyExcel.read(file.getInputStream())
                    .head(ReservationImportData.class)
                    .sheet()
                    .doReadSync();
            for (ReservationImportData data : list) {
                int insert = baseMapper.insert(BeanUtils.toBean(data, ReservationSetting.class));
                if (insert <= 0){
                    throw new DbRuntimeException("本条记录插入失败:{}",data.toString());
                }
            }
        } catch (IOException ignored) {
        }
    }


}
