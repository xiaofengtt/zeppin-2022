package com.bbl.business.teacher.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bobo
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;

    private String name;

    private String remark;

    private String img;

    private Integer students;
    private String money;

    private Integer type;

    private Integer bak1;
    private Integer bak2;
    private Integer bak3;
    private String createtime;

    private String endtime;

    private Integer sort;


}
