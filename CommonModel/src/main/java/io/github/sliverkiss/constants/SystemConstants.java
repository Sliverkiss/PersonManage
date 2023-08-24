package io.github.sliverkiss.constants;

/**
 * 系统常量
 * IntelliJ IDEA
 *
 * @author 谭礼赞 2038940123
 * @date 2023/01/05
 * @since 2023-01-05-2023/1/5
 */

public class SystemConstants {
    // 文章是草稿
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /*审核状态*/
    public static final String PLANE_STATUS_DRAFT = "未开始";

    public static final String SYSTEM_STATUS_NONE = "审核中";

    public static final String SYSTEM_STATUS_YES = "通过";

    public static final String SYSTEM_STATUS_NO = "未通过";
    /*分页*/
    public static final Integer SYSTEM_DEFAULT_CURRENTPAGE = 1;
    public static final Integer SYSTEM_DEFAULT_PAGESIZE = 10;

    public static final String ASSESSSTAF_TYPE_REVIEWED = "受考核人员";
    public static final String ASSESSSTAF_TYPE_PERMISSION = "有审批权限人员";

    public static final Integer DECLARE_TYPE_ROOT = 0;
}
