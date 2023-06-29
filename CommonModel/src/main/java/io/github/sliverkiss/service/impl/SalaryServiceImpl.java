package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.SalaryDao;
import io.github.sliverkiss.domain.entity.Salary;
import io.github.sliverkiss.service.SalaryService;
import org.springframework.stereotype.Service;

/**
 * 薪资(Salary)表服务实现类
 *
 * @author tistzach
 * @since 2023-06-27 14:56:58
 */
@Service("salaryService")
public class SalaryServiceImpl extends ServiceImpl<SalaryDao, Salary> implements SalaryService {

}

