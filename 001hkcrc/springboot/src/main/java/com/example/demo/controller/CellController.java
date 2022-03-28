package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Cell;
import com.example.demo.entity.User;
import com.example.demo.mapper.CellMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.*;

import java.time.LocalDateTime;


@Slf4j
@Data
@Component
@NoArgsConstructor
@RestController //返回Json的Controller
@RequestMapping("/cell") //接口的路由
public class CellController {

  @Resource
  CellMapper cellMapper; //一般会写一个service类，control引入service， service引入mapper
  String CurrentUser = "";
  public static String CurrentCellInfo="";

  @PostMapping("/login/{user}")
  public Result<?> login(@RequestBody String user){ //前台传过来的对象映射成实体
    //User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getDocketno, user.getDocketno()).eq(User::getDocketno, user.getDocketno()));
//    if(res == null){
//      return Result.error("-1", "用户名或密码错误");
//    }
    CurrentUser = user;
    log.info("CurrentUser: "+CurrentUser);

    Cell cell = cellMapper.selectById(1);

    cell.setOperator(CurrentUser);

    cellMapper.updateById(cell);
    return Result.success();
  }

  @GetMapping("/testforLuo/{cellinfo}")
  public String UpdateCellinfotoDB2(@PathVariable String cellinfo)   {

    log.info("Have receive infor from wechat page to update operator...{}", cellinfo);
    String[] infortemp = cellinfo.split(",");
    if(infortemp.length>0) {
      CurrentUser = infortemp[0];
      Cell cell = cellMapper.selectById(1);
      cell.setOperator(CurrentUser);
      cellMapper.updateById(cell);

      log.info("Have receive infor from wechat page to update operator...{}", cellinfo);
    }

    return "Server have receive info: "+cellinfo;
  }

  @PostMapping("/logout")//debug purpose
  public String updateCurrentTrucks22(){
    CurrentUser="";
    Cell cell = cellMapper.selectById(1);

    cell.setOperator(CurrentUser);

    cellMapper.updateById(cell);

    return "0";
  }

  @GetMapping("/checkCellStatus")
  public Result<?>  checkCellStatus()
  {
    for(int i=1;true;i++) {
      try {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Cell cell = cellMapper.selectById(i);
        String time = cell.getLatesttimestamp();
        LocalDateTime latesttime = LocalDateTime.parse(time, df);
        long duration = Duration.between(latesttime,LocalDateTime.now()).toMinutes();
        log.info("latesttime = {},currentTime={},Duration={}",latesttime, LocalDateTime.now(), duration);

        //cell.setLatesttimestamp(LocalDateTime.now().format(df));
        if(duration>5)
        {
          cell.setStatus(2);
          cellMapper.updateById(cell);
        }
        if(cell.getStatus()==1)
        {
          cell.setOperator("xxx");
          cellMapper.updateById(cell);
        }

      } catch (Exception e) {
        log.info("failed to get Cell no.{},for:{}",i,e);
        break;
      }
    }
    return Result.success();
  }

  @GetMapping
  public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(defaultValue = "") String search,
                            @RequestParam(defaultValue = "") String site) throws Exception {
    //log.info("==============find page cell========================");
    UpdateCellinfotoDB();
    if(search.equalsIgnoreCase("o") )
    {

      QueryWrapper<Cell> queryWrapper = new QueryWrapper<>();
      //queryWrapper.like("docketno", search);
      List<Cell> cells = cellMapper.selectList(queryWrapper);
      String[] header = {"A","B","C","D","E","F"};
      ExportExcel<Cell> ee= new ExportExcel<>();
      ee.exportExcel(header,cells,"test", null);


      search="";
    }


//    if(!StrUtil.isNotBlank(search))
//    {
//      QueryWrapper<Cell> queryWrapper = new QueryWrapper<>();
//      queryWrapper.like("sitename", site); //屯門兆康 TMTL483
//      Page<Cell> cellPage = cellMapper.selectPage(new Page<>(pageNum, pageSize),queryWrapper);
//      return Result.success(cellPage);
//    }else{
      QueryWrapper<Cell> queryWrapper = new QueryWrapper<>();
      Page<Cell> cellPage = cellMapper.selectPage(new Page<>(pageNum, pageSize),queryWrapper);

      return Result.success(cellPage);
//    }
  }

  @PutMapping
  public Result<?> updateCellinfo(Cell cell){ //前台传过来的对象映射成实体
    cellMapper.updateById(cell); //将数据传输到数据库
    return Result.success();
  }

  @DeleteMapping("/{id}")
  public Result<?> update(@PathVariable long id){ //前台传过来的对象映射成实体
    cellMapper.deleteById(id);
    return Result.success();
  }

  @DeleteMapping("/deletelist/{num}")
  public Result<?> deleteList(@PathVariable long num){ //前台传过来的对象映射成实体
    int deletednum=0;

    for(int id=0;id<3*num;id++)
    {
      try{
        cellMapper.deleteById(id);
        cellMapper.deleteById(id);
        cellMapper.deleteById(id);
      }
      catch (Exception e){
        continue;
      }
      deletednum++;
      //if(deletednum==num)break;
    }

    return Result.success();
  }




  @GetMapping("/updatecellinfotoDB")
  public Result<?> UpdateCellinfotoDB()   { //前台传过来的对象映射成实体

    //log.info("===========1===receive cell infor========================{}",CurrentCellInfo);
    if(CurrentCellInfo=="") return Result.success();
    //log.info("=========2=====receive cell infor========================{}",CurrentCellInfo);
    try {
      String sitename = "";
      int floor = 0;
      String sensorNumber = "";
      int status = 0; //0:open, 1: cloased, 2: not connect
      double latestTimeStamp = 0.0;
      String operator = "";

      Cell cell = cellMapper.selectById(1);
      //Cell cell = new Cell();//cellMapper.selectById(1);

      String[] infodetails = CurrentCellInfo.split(",");

      for (int i=0;i<infodetails.length;i++)
      {
        String[] infortemp = infodetails[i].split(":");
        if(infortemp[0].contains("sitename"))     cell.setSitename(infortemp[1]);
        if(infortemp[0].contains("floor"))        cell.setFloor(Integer.parseInt(infortemp[1]));
        if(infortemp[0].contains("sensornumber")) cell.setSensornumber(infortemp[1]);
        if(infortemp[0].contains("status"))       cell.setStatus(Integer.parseInt(infortemp[1]));
        if(infortemp[0].contains("operator"))
        {
          if(!infortemp[1].contains("xx"))cell.setOperator(infortemp[1]);
        }
      }
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      cell.setLatesttimestamp(LocalDateTime.now().format(df));

      cellMapper.updateById(cell);

      //cellMapper2.insert(cell);

      int insertNum=0;

    }catch(Exception e){
      //log.info("error hapepend in analysis cell sensor data, {}",e);
      e.printStackTrace();
      CurrentCellInfo="";
    }
    CurrentCellInfo="";
    return Result.success();
  }




}
