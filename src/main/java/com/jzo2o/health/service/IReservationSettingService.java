package com.jzo2o.health.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.health.model.domain.ReservationSetting;
import com.jzo2o.health.model.dto.request.ReservationSettingUpsertReqDTO;
import com.jzo2o.health.model.dto.response.ReservationDateResDTO;
import com.jzo2o.health.model.dto.response.ReservationSettingResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 预约设置 服务类
 * </p>
 *
 * @author author
 * @since 2024-07-04
 */
public interface IReservationSettingService extends IService<ReservationSetting> {

    /**
     * 按月查询预约设置
     * @param date
     * @return
     */
    List<ReservationSettingResDTO> queryReservationByMonth(String date);

    /**
     * 编辑预约
     * @param reservationSettingUpsertReqDTO
     */
    void editByDate(ReservationSettingUpsertReqDTO reservationSettingUpsertReqDTO);

    /**
     * 根据上传的Excel文件批量设置预约
     * @param file
     */
    void updateByExcel(MultipartFile file);


}
