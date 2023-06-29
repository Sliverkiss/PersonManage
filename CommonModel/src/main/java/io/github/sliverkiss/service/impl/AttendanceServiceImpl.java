package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.AttendanceDao;
import io.github.sliverkiss.domain.entity.Attendance;
import io.github.sliverkiss.service.AttendanceService;
import org.springframework.stereotype.Service;

/**
 * 考勤记录(Attendance)表服务实现类
 *
 * @author tistzach
 * @since 2023-06-27 14:47:51
 */
@Service("attendanceService")
public class AttendanceServiceImpl extends ServiceImpl<AttendanceDao, Attendance> implements AttendanceService {

}

