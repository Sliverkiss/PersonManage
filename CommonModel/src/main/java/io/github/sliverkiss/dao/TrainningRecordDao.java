package io.github.sliverkiss.dao;

import io.github.sliverkiss.domain.entity.TrainningRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.ScheduledCache;

import java.util.List;

/**
 * 培训记录(TrainningRecord)表数据库访问层
 *
 * @author tistzach
 * @since 2023-08-05 19:35:02
 */
@Mapper
@CacheNamespace(flushInterval = 5 * 60 * 1000, eviction = ScheduledCache.class, blocking = true)
public interface TrainningRecordDao extends ICrudDao<TrainningRecord> {

    @Select("select * from hros.trainning_record where employee_id=#{employeeId}")
    @Results({@Result(property = "employee", column = "employee_id", one = @One(select = "io.github.sliverkiss.dao.PersonalDao.getById"))})
    List<TrainningRecord> getRecordListByEmployeeId(@Param("employeeId") Integer employeeId);
}

