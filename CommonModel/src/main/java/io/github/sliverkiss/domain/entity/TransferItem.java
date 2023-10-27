package io.github.sliverkiss.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 调岗审核子表(TransferItem)表实体类
 *
 * @author tistzach
 * @since 2023-09-30 15:17:11
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("transfer_item")
public class TransferItem extends BaseEntity implements Serializable {

    // 调岗审核项id
    private Integer id;
    // 调岗审核id
    private Integer transferId;
    // 调岗审核项流程：调出部门0,调出部门1,人事处2
    private Integer approveType;
    // 审核意见
    private String reason;
    // 审核状态
    private String status;
    // 审核人
    private String director;
    // 审核时间
    private String approveDate;


}
