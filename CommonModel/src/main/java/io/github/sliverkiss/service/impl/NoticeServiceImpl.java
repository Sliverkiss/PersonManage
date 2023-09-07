package io.github.sliverkiss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.sliverkiss.controller.DTO.NoticeQueryDTO;
import io.github.sliverkiss.dao.NoticeDao;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Notice;
import io.github.sliverkiss.domain.vo.NoticeVo;
import io.github.sliverkiss.service.NoticeService;
import org.springframework.stereotype.Service;
import xin.altitude.cms.common.util.EntityUtils;

import java.util.List;

/**
 * 公告(Notice)表服务实现类
 *
 * @author tistzach
 * @since 2023-08-14 15:35:47
 */
@Service("noticeService")
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {

    @Override
    public ResponseResult selectPage(NoticeQueryDTO noticeQueryDTO) {
        Page<Notice> page = this.toPage ( noticeQueryDTO );
        String title = noticeQueryDTO.getTitle ();
        String createDate = noticeQueryDTO.getCreateDate ();
        String director = noticeQueryDTO.getDirector ();
        try {
            // 模糊查询
            LambdaQueryWrapper<Notice> wrapper = Wrappers.lambdaQuery ( Notice.class )
                    .like ( StringUtils.isNotBlank ( title ), Notice::getTitle, title )
                    .like ( StringUtils.isNotBlank ( createDate ), Notice::getCreateDate, createDate )
                    .like ( StringUtils.isNotBlank ( director ), Notice::getDirector, director );
            Page<Notice> NoticeVoPage = this.page ( page, wrapper );
            IPage<NoticeVo> NoticeVoIPage = EntityUtils.toPage ( NoticeVoPage, NoticeVo::new );
            // 属性注入
            return ResponseResult.okResult ( NoticeVoIPage );
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 返回公告通知的时间最新的10条数据
     *
     * @return {@link List}<{@link Notice}>
     */
    @Override
    public List<Notice> getNoticeList() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<Notice> ();
        wrapper.orderByDesc ( "create_date" ).last ( "limit 10" );
        List<Notice> result = this.list ( wrapper );
        return result;
    }


}

