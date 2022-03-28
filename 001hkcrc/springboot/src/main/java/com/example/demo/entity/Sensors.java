package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sensors")
@Data
public class Sensors {
  @TableId(type = IdType.AUTO )
  private Integer id;
  private String name;
  private String timestamp;
  private double noicevalue;
  private double pm25value;
  private double chargevalue;
}
