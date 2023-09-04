package io.github.sliverkiss.controller;

import io.github.sliverkiss.controller.DTO.NoticeQueryDTO;
import io.github.sliverkiss.domain.ResponseResult;
import io.github.sliverkiss.domain.entity.Notice;
import io.github.sliverkiss.service.impl.NoticeServiceImpl;
import io.github.sliverkiss.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/14
 */
@Slf4j
@RestController
@RequestMapping("/admin/notice")
public class NoticeController extends BaseController<NoticeServiceImpl, Notice> {


    @GetMapping("/page")
    public ResponseResult selectPage(NoticeQueryDTO noticeQueryDTO) {
        return service.selectPage ( noticeQueryDTO );
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        log.warn ( "文件上传开始" );
        // 使用MultipartFile类型接受前端发来的文件
        // 获取到文件的全名
        String filename = file.getOriginalFilename ();
        // 给文件重新命名
        String newname = System.currentTimeMillis () + filename.substring ( filename.lastIndexOf ( '.' ) );
        System.out.println ( newname );
        // 设置文件存储目录
        String path = "/Volumes/Marginist/PersonManage/hros-ui/src/assets/img/upload_photo/";
        // 创建目录文件
        File newpath = new File ( path );
        // 判断目录是否存在，不存在则创建
        if (!newpath.exists ()) {
            newpath.mkdir ();
        }
        try {
            // 根据newpath和newname创建一个全新的File实例
            File newfile = new File ( newpath, newname );
            // 将前端的初始化file转换为全新的newfile
            file.transferTo ( newfile );
        } catch (IOException e) {
            throw new RuntimeException ( e );
        }
        log.warn ( "文件上传结束" );
        return newname;
    }

    @Override
    public void beforeSave(Notice entity) throws Exception {
        entity.setCreateDate ( DateUtil.currentDateFormat () );
    }
}
