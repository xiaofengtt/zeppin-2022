package com.bbl.business.journalism.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.NonFinal;

/**
 * <p>
 * 
 * </p>
 *
 * @author bobo
 * @since 2019-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Journalism implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private String conten;

    private String createtime;

    private String type;

    @TableField(exist=false)
    private Integer look;


}
