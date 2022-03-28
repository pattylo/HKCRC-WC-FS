package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("truck")
@Data
public class truck {
  @TableId(type = IdType.AUTO )
  private Integer no;
  private String docket;
  private String site;
  private String location;
  private String truck;
  private String despatch;
  private String arrival;
  private String batch;
  private Integer load;
  private Integer sum;
}
