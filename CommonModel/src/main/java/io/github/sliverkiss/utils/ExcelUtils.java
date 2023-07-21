package io.github.sliverkiss.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/7/20
 */


public class ExcelUtils {

    /**
     * 导出Excel到web
     *
     * @param response  响应
     * @param excelName Excel名称
     * @param sheetName sheet页名称
     * @param clazz     Excel要转换的类型
     * @param data      要导出的数据
     *
     * @throws Exception
     */
    public static void export2Web(HttpServletResponse response, String excelName, String sheetName, Class clazz, List data) throws Exception {
        response.setContentType ( "application/vnd.ms-excel" );
        response.setCharacterEncoding ( "utf-8" );
        // 这里URLEncoder.encode可以防止中文乱码
        excelName = URLEncoder.encode ( excelName, "UTF-8" );
        response.setHeader ( "Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue () );
        EasyExcel.write ( response.getOutputStream (), clazz ).sheet ( sheetName ).doWrite ( data );
    }
}
