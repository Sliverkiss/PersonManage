package io.github.sliverkiss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.sliverkiss.domain.DTO.SalaryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Salary;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工资表(Salary)表服务接口
 *
 * @author tistzach
 * @since 2023-07-16 08:21:39
 */
public interface SalaryService extends IService<Salary> {

    ResponseResult selectSalaryPage(SalaryDTO salaryDTO);

    @Transactional(rollbackFor = Exception.class)
    ResponseResult saveSalary(Salary salary);

    ResponseResult deleteSalary(Integer id);

    ResponseResult updateSalary(Salary salary);
}

