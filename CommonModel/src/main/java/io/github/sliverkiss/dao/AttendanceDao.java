package io.github.sliverkiss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.sliverkiss.domain.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;

/**
 * 考勤记录(Attendance)表数据库访问层
 *
 * @author tistzach
 * @since 2023-06-27 14:47:49
 */
@Mapper
public interface AttendanceDao extends MyMapper<Attendance> {

}

