package io.github.sliverkiss.service;

import io.github.sliverkiss.controller.DTO.NoticeQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Notice;

/**
 * 公告(Notice)表服务接口
 *
 * @author tistzach
 * @since 2023-08-14 15:35:47
 */
public interface NoticeService extends ICrudService<Notice> {

    ResponseResult selectPage(NoticeQueryDTO noticeQueryDTO);
}

