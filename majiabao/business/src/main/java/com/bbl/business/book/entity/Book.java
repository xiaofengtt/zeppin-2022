package com.bbl.business.book.entity;

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
 * @since 2019-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    private String bookname;

    private String price;
    private String info;

    private String img;

    private String comment;

    private String commentcount;

    private String hot;

    private Integer level;


}
