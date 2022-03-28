package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
  @TableId(type = IdType.AUTO )
  private Integer id;
  private String docketno;
  private String sitename;
  private String location;
  private String trucknumber;
  private String despatchtime;
  private String arrivaltime;
  private String batchname;
  private double thisload;
  private double cummulatedqty;
  private Integer num;
}
