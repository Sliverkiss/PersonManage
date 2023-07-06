package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Employee;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.service.PersonalService;
import io.github.sliverkiss.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

/**
 * 人员信息表(Personal)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-05 11:45:33
 */
@Service("personalService")
public class PersonalServiceImpl extends ServiceImpl<PersonalDao, Personal> implements PersonalService {

}

