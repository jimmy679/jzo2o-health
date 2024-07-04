package com.jzo2o.health.controller.admin;

import com.jzo2o.health.model.dto.request.ReservationSettingUpsertReqDTO;
import com.jzo2o.health.model.dto.response.ReservationSettingResDTO;
import com.jzo2o.health.service.IReservationSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预约设置操作
 *
 * @author itcast
 */
@Slf4j
@RestController("adminReservationSettingController")
@RequestMapping("/admin/reservation-setting")
@Api(tags = "管理端 - 预约设置相关接口")
public class ReservationSettingController {


    @Resource
    private IReservationSettingService reservationSettingService;


    @GetMapping("/getReservationSettingByMonth")
    @ApiOperation("按月查询预约设置")
    @ApiImplicitParam(name = "date", value = "月份，格式：yyyy-MM", required = true, dataTypeClass = String.class)
    public List<ReservationSettingResDTO> getReservationSettingByMonth(@RequestParam("date") String date) {
        return reservationSettingService.queryReservationByMonth(date);
    }

    @PutMapping("/editNumberByDate")
    @ApiOperation("编辑预约设置")
    public void editNumberByDate(@RequestBody ReservationSettingUpsertReqDTO reservationSettingUpsertReqDTO) {
        reservationSettingService.editByDate(reservationSettingUpsertReqDTO);
    }
}
