package com.jzo2o.health.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.jzo2o.health.config.LocalDateConverter;
import lombok.Data;

/**
 * 预约导入数据
 *
 * @author itcast
 * @create 2023/11/1 19:16
 **/
@Data
public class ReservationImportData {
    /**
     * 日期，格式：yyyy-MM-dd
     */
    @ExcelProperty(index = 0,converter = LocalDateConverter.class)
    private String date;

    /**
     * 预约人数
     */
    @ExcelProperty(index = 1)
    private Integer number;
}
