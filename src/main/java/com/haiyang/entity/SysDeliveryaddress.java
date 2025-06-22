package com.haiyang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haiyang.common.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author L1n
 * @since 2025-06-22
 */
@Getter
@Setter
@TableName("sys_deliveryaddress")
@ApiModel(value = "SysDeliveryaddress对象", description = "")
public class SysDeliveryaddress extends BaseEntity<SysDeliveryaddress> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("送货地址编号")
    @TableId(value = "da_id", type = IdType.AUTO)
    private Integer daId;

    @ApiModelProperty("联系人姓名")
    @TableField("contact_name")
    private String contactName;

    @ApiModelProperty("联系人性别")
    @TableField("contact_sex")
    private Integer contactSex;

    @ApiModelProperty("联系人电话")
    @TableField("contact_tel")
    private String contactTel;

    @ApiModelProperty("送货地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("下单用户编号--sys_account表account_id")
    @TableField("account_id")
    private String accountId;

    @Override
    public Serializable pkVal() {
        return this.daId;
    }
}
