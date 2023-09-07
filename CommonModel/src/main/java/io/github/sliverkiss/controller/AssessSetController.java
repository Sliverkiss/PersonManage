package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.controller.DTO.AssessSetQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessAndItem;
import io.github.sliverkiss.domain.entity.assess.AssessDeclare;
import io.github.sliverkiss.domain.entity.assess.AssessSet;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.AssessAndItemService;
import io.github.sliverkiss.service.AssessDeclareService;
import io.github.sliverkiss.service.impl.AssessSetServiceImpl;
import io.github.sliverkiss.utils.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/17
 */
@RestController
@Slf4j
@RequestMapping("/admin/assess/set")
public class AssessSetController extends BaseController<AssessSetServiceImpl, AssessSet> {

    @Autowired
    private AssessAndItemService assessAndItemService;
    @Autowired
    private AssessDeclareService declareService;

    @GetMapping("/page")
    public ResponseResult selectPage(AssessSetQueryDTO dto) {
        return service.selectPage ( dto );
    }

    @PostMapping("/test")
    public void test(@RequestBody AssessSet set) {
        System.out.println ( set );
    }

    @Override
    public void beforeSave(AssessSet set) throws Exception {
        set.setCrateDate ( DateUtil.currentDateFormat () );
    }

    @PostMapping("/save")
    public ResponseResult save(@RequestBody AssessSet set) throws Exception {
        beforeSave ( set );
        service.save ( set );
        // 存储关系子项
        this.setItemBySet ( set );
        return ResponseResult.okResult ();
    }

    @Override
    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "ID存在修改，不存在添加")
    public ResponseResult update(@RequestBody AssessSet set) {
        set.setUpdateDate ( DateUtil.currentDateFormat () );
        service.updateById ( set );
        // 更新考核项与考核关系
        List<Integer> ids = assessAndItemService.list ( Wrappers.lambdaQuery ( AssessAndItem.class )
                .eq ( AssessAndItem::getAssessId, set.getId () ) ).stream ().map ( AssessAndItem::getId ).collect ( Collectors.toList () );
        assessAndItemService.deleteBatchIds ( ids );
        this.setItemBySet ( set );
        return ResponseResult.okResult ();
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        List<AssessDeclare> assessDeclareList = declareService.list ( Wrappers.lambdaQuery ( AssessDeclare.class ).eq ( AssessDeclare::getAssessId, id ) );
        // 1.判断该考核是否被使用
        if (assessDeclareList.size () > 0) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR.getCode (), "该考核已被使用，无法删除" );
        }
        // 2.删除考核与考核项关联记录
        assessAndItemService.remove ( Wrappers.lambdaQuery ( AssessAndItem.class ).eq ( AssessAndItem::getAssessId, id ) );
        // 3.删除考核记录
        service.removeById ( id );
        return ResponseResult.okResult ();
    }

    @GetMapping("/title/list")
    public ResponseResult getTitleList() {
        List<AssessSet> titles = service.list ( Wrappers.lambdaQuery ( AssessSet.class )
                .orderByDesc ( AssessSet::getUpdateDate )
                .orderByDesc ( AssessSet::getCrateDate ) );
        return ResponseResult.okResult ( titles );
    }

    @Override
    @GetMapping("/info/{id}")
    public AssessSet info(@PathVariable Integer id) {
        AssessSet set = service.getById ( id );
        service.getItemsBySet ( set );
        log.warn ( "绩效考核计划被调用了" );
        return set;
    }

    public void setItemBySet(AssessSet set) {
        List<Integer> items = set.getItemIdList ();
        List<AssessAndItem> list = new ArrayList<> ();
        Integer assessId = set.getId ();
        for (Integer id : items) {
            AssessAndItem item = new AssessAndItem ();
            item.setAssessId ( assessId );
            item.setItemId ( id );
            list.add ( item );
        }
        assessAndItemService.saveBatch ( list );
    }
}
