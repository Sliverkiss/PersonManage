package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.SalaryQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Salary;

/**
 * 工资表(Salary)表服务接口
 *
 * @author tistzach
 * @since 2023-07-16 08:21:39
 */
public interface SalaryService extends ICrudService<Salary> {

    ResponseResult selectSalaryPage(SalaryQueryDTO salaryQueryDTO);
}

