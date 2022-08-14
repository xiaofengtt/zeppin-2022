package com.bbl.business.coinlog.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.bbl.business.teacher.entity.Teacher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bobo
 * @since 2019-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Coinlog implements Serializable {

    private static final long serialVersionUID = 1L;
//    private Integer id;

    private int teacherid;

    private int userid;
    private  int type;
    private  int min;

    private String teachername;

    private String coin;

    private String starttime;

    private String endtime;

    @TableField(exist = false)
    private Teacher teacher;


    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        if(starttime.indexOf (".")>=0){
            starttime=starttime.substring (0,starttime.indexOf ("."));
        }
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        if(endtime.indexOf (".")>=0){
            endtime=endtime.substring (0,endtime.indexOf ("."));
        }
        this.endtime = endtime;
    }
}
