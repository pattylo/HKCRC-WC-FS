package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.mail.javamail.JavaMailSender;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Data
@Component
@NoArgsConstructor
@RestController //返回Json的Controller
@RequestMapping("/user") //接口的路由
public class UserController {

  public static String currentTruckInfo= "None";

  String ESP32Data = "notupdate";

  @Resource
  UserMapper userMapper; //一般会写一个service类，control引入service， service引入mapper

  @PostMapping("/login")
  public Result<?> login(@RequestBody User user){ //前台传过来的对象映射成实体
    User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getDocketno, user.getDocketno()).eq(User::getDocketno, user.getDocketno()));
    if(res == null){
      return Result.error("-1", "用户名或密码错误");
    }
    return Result.success();
  }

  @PostMapping("/logins/{info}")//debug purpose
  public String updateCurrentTrucks22(@PathVariable String info){
    System.out.print("============"+info);

    return currentTruckInfo;
  }

  @PostMapping("/uploadfile")//upload file, like xml or jpg
  public Result<?> upload2(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
    String originalFilename = file.getOriginalFilename();
    //定义文件的唯一标识
    String flag = IdUtil.fastSimpleUUID();
    //String rootFilePath =  System.getProperty("user.dir") + "/springboot/src/main/resource/files/"+flag+"_"+originalFilename;
    String rootFilePath =  System.getProperty("user.dir") +"/"+originalFilename;
    FileUtil.writeBytes(file.getBytes(),rootFilePath);
    return Result.success("/files/"+flag);
  }

  @PostMapping
  public Result<?> save(@RequestBody User user)   { //前台传过来的对象映射成实体

    //readFile();

//    if(user.getPassword() == null)
//    {
//      user.setPassword("123456");
//    }
    userMapper.insert(user); //将数据传输到数据库
    return Result.success();
  }


  @GetMapping
  public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(defaultValue = "") String search,
                            @RequestParam(defaultValue = "") String site) throws Exception {
    if(search.equalsIgnoreCase("o") )
    {

      QueryWrapper<User> queryWrapper = new QueryWrapper<>();
      //queryWrapper.like("docketno", search);
      List<User> users = userMapper.selectList(queryWrapper);
      //ExportExcel<User> ee = new ExportExcel<>();
      String[] header = {"A","B","C","D","E","F"};
      ExportExcel<User> ee= new ExportExcel<>();
      ee.exportExcel(header,users,"test", null);

      GetDocument(users);

      SendEmail1();

      search="";
    }
//    LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
//    if(StrUtil.isNotBlank(search))
//    {
//      wrapper.eq(User::getDocketno,search);
//    }
//    Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
//    return Result.success(userPage);

    if(!StrUtil.isNotBlank(search))
    {
//      LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
//      Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
//      return Result.success(userPage);
      QueryWrapper<User> queryWrapper = new QueryWrapper<>();
      //queryWrapper.like("docketno", search); //屯門兆康 TMTL483
      queryWrapper.like("sitename", site); //屯門兆康 TMTL483
      //List<User> users = userMapper.selectList(queryWrapper);
      Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize),queryWrapper);
      return Result.success(userPage);
    }else{
      QueryWrapper<User> queryWrapper = new QueryWrapper<>();
      queryWrapper.like("docketno", search); //屯門兆康 TMTL483
      queryWrapper.like("sitename", site); //屯門兆康 TMTL483
      //List<User> users = userMapper.selectList(queryWrapper);
      Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize),queryWrapper);
      return Result.success(userPage);
    }
  }

  @PutMapping
  public Result<?> update(@RequestBody User user){ //前台传过来的对象映射成实体
    userMapper.updateById(user); //将数据传输到数据库
    return Result.success();
  }

  @DeleteMapping("/{id}")
  public Result<?> update(@PathVariable long id){ //前台传过来的对象映射成实体
    userMapper.deleteById(id);
    return Result.success();
  }

  @DeleteMapping("/deletelist/{num}")
  public Result<?> deleteList(@PathVariable long num){ //前台传过来的对象映射成实体
    int deletednum=0;

    for(int id=0;id<3*num;id++)
    {
      try{
        userMapper.deleteById(id);
        userMapper.deleteById(id);
        userMapper.deleteById(id);
      }
      catch (Exception e){
        continue;
      }
      deletednum++;
      //if(deletednum==num)break;
    }

    //userMapper.delete(userMapper);
    //userMapper.deleteById();
    return Result.success();
  }



  @PostMapping("/loadlist/{id}")
  public Result<?> load(@PathVariable int id)   { //前台传过来的对象映射成实体

    int dataNum = id;
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    //System.out.print("==============LoadList========================");
    log.info("==============LoadList========================");
    try {
      //https://dds.glorious.com.hk/gdds/dktGetByTrk.dds?trk=PC2865
      DocumentBuilder builder = factory.newDocumentBuilder();
      //Document d = builder.parse("C:\\Software\\IDEA_Projects\\GIT\\001hkcrc\\springboot\\src\\main\\resources\\file\\dktGetByTrk.xml");
      Document d = builder.parse("dktGetByTrk.xml");
      //Document d = builder.parse("https://dds.glorious.com.hk/GDDS/dktBySIT.dds?sit=1728");//"dktGetByTrk.xml"
      NodeList sList = d.getElementsByTagName("dockets");


      if(id==0) id=1;
      int insertNum=0;

      log.info("==============sList.getLength() = {}========================",sList.getLength());
      for (int i = 0; i <sList.getLength() ; i++) {
        boolean isNew = true;

        Node node = sList.item(i);
        NodeList childNodes = node.getChildNodes();
        int num = 0;

        User user = new User();
        for (int j = 0; j <childNodes.getLength() ; j++) {
          if (childNodes.item(j).getNodeType()==Node.ELEMENT_NODE) {
            //System.out.print(childNodes.item(j).getNodeName() + ":");
            //System.out.println(childNodes.item(j).getFirstChild().getNodeValue());

            if(num==0)user.setDocketno(childNodes.item(j).getFirstChild().getNodeValue().toString());
            else if(num==1)user.setSitename(childNodes.item(j).getFirstChild().getNodeValue().toString());
            else if(num==2)user.setLocation(childNodes.item(j).getFirstChild().getNodeValue().toString());
            else if(num==3)user.setTrucknumber(childNodes.item(j).getFirstChild().getNodeValue().toString());
            else if(num==4)
            {
              try {
                String val = childNodes.item(j).getFirstChild().getNodeValue();
                if (childNodes.item(j).getFirstChild().getNodeValue() == null) user.setDespatchtime(" ");
                else user.setDespatchtime(childNodes.item(j).getFirstChild().getNodeValue().toString());
              }catch(Exception e){
                user.setDespatchtime(" ");
              }
            }
            else if(num==5)
            {
              try {
                if (childNodes.item(j).getFirstChild().getNodeValue() == null) user.setArrivaltime(" ");
                else user.setArrivaltime(childNodes.item(j).getFirstChild().getNodeValue());
              }catch(Exception e){
                user.setArrivaltime(" ");
              }
            }
            else if(num==6)user.setBatchname(childNodes.item(j).getFirstChild().getNodeValue());
            else if(num==7)
            {
              try {
                if (childNodes.item(j).getFirstChild().getNodeValue() == null) user.setThisload(0.0);
                else user.setThisload(Double.valueOf(childNodes.item(j).getFirstChild().getNodeValue()));
              }catch(Exception e){
                user.setThisload(0.0);
              }
            }
            else if(num==8)
            {
              try {
                if (childNodes.item(j).getFirstChild().getNodeValue() == null) user.setCummulatedqty(0.0);
                else user.setCummulatedqty(Double.valueOf(childNodes.item(j).getFirstChild().getNodeValue()));
              }catch(Exception e){
                user.setCummulatedqty(0.0);
              }
            }
            else continue;

            user.setNum(dataNum + insertNum +1);
            user.setId(dataNum + insertNum +1);

            log.info("ALREADY HAVE Truck dataNum ={}, Num of insertNum  ={}",dataNum,insertNum);
            num++;

          }

        }
        //userMapper.insert(user);
        //check if already

        if(user!=null && user.getTrucknumber()!=null && user.getTrucknumber()!="")
        {
          for(int m=1; m<=dataNum; m++)
          {
            try{
              User user2 = userMapper.selectById(m);
              if(user2!=null) {
                String truckDockNum = user2.getDocketno();
                String despatchTime = user2.getDespatchtime();
                String batchName = user2.getBatchname();
                double qualoty = user2.getCummulatedqty();
                if (truckDockNum.equalsIgnoreCase(user.getDocketno()))
                {
                  if(!despatchTime.equalsIgnoreCase(user.getDespatchtime()) || !batchName.equalsIgnoreCase(user.getBatchname()) )
                  {
                    user2.setDespatchtime(user.getDespatchtime());
                    user2.setThisload(user.getThisload());
                    //user2.setCummulatedqty(user.getCummulatedqty());
                    user2.setBatchname(user.getBatchname());
                    userMapper.updateById(user2);
                    log.info("update user {}",user2.getDocketno());
                  }
                  log.info("Already have, docket={}",user2.getDocketno());
                  isNew = false;
                  break;
                }
              }
            }catch(Exception e){

            }
          }
          if(isNew){
            log.info("===new====and insert new user, dataNum = {}",dataNum);
            insertNum++;
            userMapper.insert(user);
          }
        }

      }

    }catch(Exception e){
      log.info("error hapepend in load list, {}",e);
      e.printStackTrace();
    }

    return Result.success();
  }

//  @GetMapping("/updateESP32data")
//  public String updateESP32Data(){
//    //System.out.print("============"+info);
//    ESP32Data = "a";
//    return currentTruckInfo;
//  }

  @GetMapping("/updateESP32data/{info}")
  public String updateESP32Data(@PathVariable String info){
    System.out.print("============"+info);
    ESP32Data = info;
    return ESP32Data;
  }

  @GetMapping("/GetCurrentTruckNO/{total}/{currentTruckID}")
  public Result<?> updateCurrentTrucks(@PathVariable int total,@PathVariable String currentTruckID){
    System.out.print("============"+currentTruckInfo);
    User user = new User();
    user.setDocketno(ESP32Data);

    return Result.success(user);
  }

  @GetMapping("/updateCurrentTruck/{total}/{currentTruckID}")
  public Result<?> updateCurrentTruck(@PathVariable int total, @PathVariable int currentTruckID)   { //前台传过来的对象映射成实体
    //copylatestfiles();
    String trkno = "truck", despatchTime = "", batchName="";
    double thisload = 0.0, loadQual = 0.0;
    int curTruckNum =0, isMatched = 0, curTruckID=0;
    User user = new User();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      //Document d = builder.parse("C:\\Software\\IDEA_Projects\\GIT\\001hkcrc\\springboot\\src\\main\\resources\\file\\dktGetDocketsCurrent.xml");
      Document d = builder.parse("dktGetDocketsCurrent.xml");
      NodeList sList = d.getElementsByTagName("dockets");

      int validData=0;
      for (int i = 0; i <sList.getLength() ; i++) {
        Node node = sList.item(i);
        NodeList childNodes = node.getChildNodes();
        int num = 0;
        for (int j = 0; j <childNodes.getLength() ; j++) {
          if (childNodes.item(j).getNodeType()==Node.ELEMENT_NODE) {
            System.out.print(childNodes.item(j).getNodeName() + ":");
            System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
            if(num==2)trkno = childNodes.item(j).getFirstChild().getNodeValue().toString();
            //if(num==3)despatchTime = childNodes.item(j).getFirstChild().getNodeValue().toString();
            //if(num==5)batchName = childNodes.item(j).getFirstChild().getNodeValue().toString();
            //if(num==6)thisload = Double.valueOf(childNodes.item(j).getFirstChild().getNodeValue());
            //if(num==7)loadQual = Double.valueOf(childNodes.item(j).getFirstChild().getNodeValue());

            //else continue;
            num++;
          }
        }
        //currentTruckInfo = "CurrentTK,"+trkno.toString()+","+batchName.toString()+","+thisload+","+loadQual;
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    for(int i=0;i<total; i++)
    {
      try{
        User user2 = userMapper.selectById(i);
        if(user2!=null) {
          String truck = user2.getTrucknumber();
          if (truck.equalsIgnoreCase(trkno))
          {
            curTruckNum = user2.getNum();
            curTruckID = i;
            user = user2;
            isMatched = 1;
            batchName = user2.getBatchname(); thisload = user2.getThisload();loadQual=user2.getCummulatedqty();
            currentTruckInfo = "CurrentTK,"+trkno.toString()+","+batchName.toString()+","+thisload+","+loadQual;
          }
        }
      }catch(Exception e){

      }
    }
    if(currentTruckID != curTruckNum && isMatched ==1) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss");
      Date date = new Date(System.currentTimeMillis());
      String timer = formatter.format(date);

      //user.setDespatchtime(despatchTime);
      //user.setThisload(thisload);
      //user.setCummulatedqty(loadQual);
      user.setArrivaltime(timer);

      userMapper.updateById(user);
    }

    return Result.success(user);
  }

  public static void copylatestfiles() {
    // TODO Auto-generated method stub
    String batPath = "C:\\Software\\IDEA_Projects\\GIT\\001hkcrc\\test.bat"; // 把你的bat脚本路径写在这里
    File batFile = new File(batPath);
    boolean batFileExist = batFile.exists();
    System.out.println("batFileExist:" + batFileExist);
    if (batFileExist) {
      callCmd(batPath);
    }
  }

  private static void  callCmd(String locationCmd){
    StringBuilder sb = new StringBuilder();
    try {
      Process child = Runtime.getRuntime().exec(locationCmd);
      InputStream in = child.getInputStream();
      BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
      String line;
      while((line=bufferedReader.readLine())!=null)
      {
        sb.append(line + "\n");
      }
      in.close();
      try {
        child.waitFor();
      } catch (InterruptedException e) {
        System.out.println(e);
      }
      System.out.println("sb:" + sb.toString());
      System.out.println("callCmd execute finished");
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public void GetDocument(List<User> resourceList) {
    String fileName = "API_Document.xlsx";
    org.apache.poi.xssf.usermodel.XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet("API");

    XSSFRow rowHead = sheet.createRow(0);
    rowHead.createCell(0).setCellValue("Function Category");
    rowHead.createCell(1).setCellValue("Summary");
    rowHead.createCell(2).setCellValue("Method");
    rowHead.createCell(3).setCellValue("URI");
    rowHead.createCell(4).setCellValue("Request Header");
    rowHead.createCell(5).setCellValue("Params");
    rowHead.createCell(6).setCellValue("Request Body");
    rowHead.createCell(7).setCellValue("Success Code");
    rowHead.createCell(8).setCellValue("Response Header");
    rowHead.createCell(9).setCellValue("Response Body");
    rowHead.createCell(10).setCellValue("Failure Code");
    rowHead.createCell(11).setCellValue("ETC");

    int rowCount = 1;

    for(User resource : resourceList) {
      XSSFRow row = sheet.createRow(rowCount++);

      row.createCell(0).setCellValue(resource.getDocketno());
      row.createCell(1).setCellValue(resource.getDocketno());
      row.createCell(2).setCellValue(resource.getDocketno());
      row.createCell(3).setCellValue(resource.getDocketno());
      row.createCell(4).setCellValue(resource.getDocketno());
      row.createCell(5).setCellValue(resource.getDocketno());
      row.createCell(6).setCellValue(resource.getDocketno());
      row.createCell(7).setCellValue(resource.getDocketno());
      row.createCell(8).setCellValue(resource.getDocketno());
      row.createCell(9).setCellValue(resource.getDocketno());
      row.createCell(10).setCellValue(resource.getDocketno());
      row.createCell(11).setCellValue(resource.getDocketno());
    }

    try {
      wb.write(new FileOutputStream(fileName));
      //Log.info("REST Resource Documentation Complete");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void SendEmail1()throws Exception, MessagingException {
    String USER_NAME = "halloworld1992@outlook.com";
    String PASSWORD = "lzl5536556";
    Properties prop = new Properties();
    //prop.setProperty("mail.debug","true");
    prop.put("mail.smtp.auth","true");
    prop.put("mail.host","smtp.office365.com");//smtp.office365.com
    prop.put("mail.transport.protocol","smtp");
    prop.put("mail.smtp.port","587");

    prop.put("mail.smtp.starttls.enable", "true");
//    prop.put("mail.smtp.socketFactory.port","587");
//    prop.put("mail.smtp.socketFactory.fallback","false");
    Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USER_NAME, PASSWORD);
      }
    });

    try {
      Message message = new MimeMessage(session);

      message.setFrom(new InternetAddress(USER_NAME));

      InternetAddress toAddress = new InternetAddress("15021771582@163.com");
      message.setRecipient(Message.RecipientType.TO, toAddress);

      message.setSubject("test");
      message.setText("main");

      //Transport transport = session.getTransport();
//    // 连接邮件服务器
      //Transport.(USER_NAME, PASSWORD);
      // 发送邮件
      Transport.send(message);
      int a =10;
    }catch (Exception err){
      System.out.println("Err");
    }
    // 关闭连接
    //transport.close();



//    //1. 创建session
//    //PasswordAuthentication passwordAuthentication = new PasswordAuthentication("15021771582@163.com", "lzl553655612,");
//    Session session = Session.getDefaultInstance(prop,authenticator);
//    // 开启Session的Debug模式，这样就可以查看到程序发送e-mail的运行状态
//    session.setDebug(true);
//
//    //2. 通过Session得到transport对象
//    Transport ts = session.getTransport();
//    //3. 连上邮件服务器
//    ts.connect("smtp.163.com","15021771582","lzl5536556,");
//    //4. 创建邮件
//    Message message =createAttachMail(session);
//    //5. 发送邮件
//    ts.sendMessage(message,message.getAllRecipients());
//    ts.close();
  }

  public MimeMessage createAttachMail(Session session)throws Exception{
    MimeMessage message = new MimeMessage(session);

    //发件人
    message.setFrom(new InternetAddress("15021771582@163.com"));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress("15021771582@163.com"));
    message.setSubject("Just for testing!");

    //邮件正文，为了避免正文中的乱码现象，需要使用charset=UTF-8指名字符编码
    MimeBodyPart text = new MimeBodyPart();
    text.setContent("使用JavaMail创建的带附件的邮件","text/html;charset=UTF-8");
    //创建邮件附件
    MimeBodyPart attach = new MimeBodyPart();
    DataHandler dh = new DataHandler(new FileDataSource("C:\\12.png"));
    attach.setDataHandler(dh);
    attach.setFileName(dh.getName());

    //创建容器描述数据关系
    MimeMultipart mp = new MimeMultipart();
    mp.addBodyPart(text);
    mp.addBodyPart(attach);
    mp.setSubType("mixed");

    message.setContent(mp);
    message.saveChanges();
    //将创建的Email写入到E盘存储
    message.writeTo(new FileOutputStream("C:\\attachMail.eml"));
    //返回生成邮件
    return message;
  }


  private static void sendMail2()throws Exception{
//    JavaMailSender javaMailSender = new JavaMailSender();
//    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

  }

}
