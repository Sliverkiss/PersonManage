package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.dao.PersonalDao;
import io.github.sliverkiss.dao.RenewalDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Personal;
import io.github.sliverkiss.domain.entity.Renewal;
import io.github.sliverkiss.domain.vo.EmployeeVo;
import io.github.sliverkiss.domain.vo.RenewalVo;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.RenewalService;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 续约申请表(Renewal)表服务实现类
 *
 * @author tistzach
 * @since 2023-07-07 11:44:20
 */
@Service("renewalService")
public class RenewalServiceImpl extends ServiceImpl<RenewalDao, Renewal> implements RenewalService {

    @Resource
    private PersonalDao personalDao;
    /**
     * 分页获取续约记录表
     *
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult selectRenewalPage(){
        try {
            Page<Renewal> page = new Page<>(1,5);
            Page<Renewal> renewalPage=this.page ( page );
            IPage<RenewalVo> renewalVoIPage= EntityUtils.toPage( renewalPage,RenewalVo::new);
            List<Integer> personalIds=EntityUtils.toList (  renewalVoIPage.getRecords (), RenewalVo::getPersonalId);
            if (personalIds.size () > 0) {
                LambdaQueryWrapper<Personal> wrapper = Wrappers.lambdaQuery ( Personal.class ).in ( Personal::getId, personalIds );
                List<Personal> personalList = personalDao.selectList ( wrapper );
                Map<Integer,Personal> map=EntityUtils.toMap ( personalList,Personal::getId, e->e );
                renewalVoIPage.getRecords ().forEach ( renewalVo->{
                    Personal personal = map.get(renewalVo.getPersonalId());
                    Optional.ofNullable(personal).ifPresent(p-> renewalVo.setName(p.getName()));
                });
            }
            return ResponseResult.okResult (renewalVoIPage);
        }catch (Exception e) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR );
        }

    }
}

