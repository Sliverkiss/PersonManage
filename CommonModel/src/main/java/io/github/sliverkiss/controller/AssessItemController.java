package io.github.sliverkiss.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.sliverkiss.controller.DTO.AssessItemQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.assess.AssessAndItem;
import io.github.sliverkiss.domain.entity.assess.AssessItem;
import io.github.sliverkiss.enums.AppHttpCodeEnum;
import io.github.sliverkiss.service.AssessAndItemService;
import io.github.sliverkiss.service.impl.AssessItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/17
 */
@RestController
@RequestMapping("/admin/assess/item")
public class AssessItemController extends BaseController<AssessItemServiceImpl, AssessItem> {
    @Autowired
    private AssessAndItemService assessAndItemService;

    @GetMapping("/list")
    public ResponseResult list() {
        return ResponseResult.okResult ( service.list () );
    }

    @GetMapping("/page")
    public ResponseResult selectPage(AssessItemQueryDTO dto) {
        return service.selectPage ( dto );
    }

    /**
     * 删除考核项
     * result->1.考核项已被使用，无法删除；2.删除成功
     *
     * @param id
     *
     * @return {@link ResponseResult}
     */
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable Integer id) {
        List<AssessAndItem> list = assessAndItemService.list ( Wrappers.lambdaQuery ( AssessAndItem.class )
                .eq ( AssessAndItem::getItemId, id ) );
        if (list.size () > 0) {
            return ResponseResult.errorResult ( AppHttpCodeEnum.SYSTEM_ERROR.getCode (), "该考核项已被使用，无法删除" );
        }
        return service.deleteEntity ( id );
    }


}
