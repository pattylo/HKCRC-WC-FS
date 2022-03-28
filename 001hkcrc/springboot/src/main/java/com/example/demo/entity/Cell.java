package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("cell")
@Data
public class Cell {
    @TableId(type = IdType.AUTO )
    private Integer id;
    private String sitename;
    private Integer floor;
    private String sensornumber;
    private Integer status; //0:open, 1:closed, 2: lost
    private String latesttimestamp;
    private String operator;
}
