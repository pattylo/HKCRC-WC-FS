package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Sensors;
import com.example.demo.mapper.SensorsMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/ESP32")
public class ESP32Controller {
    //@Value("${server.port}")
    //private String port;

    int deviceNum =0;

    @Resource
    SensorsMapper sensorsMapper;
    UserMapper userMapper;

    private static final String ip = "http://localhost";

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "100") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) throws Exception{

        LambdaQueryWrapper<Sensors> wrapper = Wrappers.<Sensors>lambdaQuery();
        Page<Sensors> sensorsPage = sensorsMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(sensorsPage);

    }

    /**
     *
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping (value = "/ESP32Sensors/{SensorsValue}", method = {RequestMethod.GET, RequestMethod.POST})
    public int weather(ModelAndView model, @PathVariable String SensorsValue){
        System.out.println("传感器值："+SensorsValue);
        //System.out.println("湿度为："+newDeviceID1);
//        Map<String, Object> WeatherMap = new HashMap<~>();
//        WeatherMap.put("10001",temperature);
//        WeatherMap.put("10002",humidity);
        //weather.WeatherSource(WeatherMap);
        //model.setViewName("main/STM32");
        String[] sensorsValue = SensorsValue.split(",");

        if(sensorsValue.length!=4) return 1;

        String newDeviceName = sensorsValue[0];
        System.out.println("newDeviceName："+newDeviceName);

        boolean isNewDevice = false;
        int newID =0;
        for(int i=1;true;i++)
        {
            Sensors sensors = sensorsMapper.selectById(i);
            if(sensors==null || i>10)
            {
                isNewDevice = true;
                newID = i;
                break;
            }
            String deviceName = sensors.getName();
            System.out.println("oldDeviceName："+deviceName);
            if(newDeviceName.equalsIgnoreCase(deviceName))
            {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String timer = formatter.format(date);
                sensors.setName(newDeviceName);
                //sensors.setId(newID);
                sensors.setTimestamp(timer);
                sensors.setNoicevalue(Double.valueOf(sensorsValue[1]));
                sensors.setPm25value(Double.valueOf(sensorsValue[2]));
                sensors.setChargevalue(Double.valueOf(sensorsValue[3]));
                sensorsMapper.updateById(sensors);
                break;
            }
        }
        if(isNewDevice)
        {
            Sensors sensors = new Sensors();
            sensors.setId(newID);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String timer = formatter.format(date);
            sensors.setName(newDeviceName);
            //sensors.setId(newID);
            sensors.setTimestamp(timer);
            sensors.setNoicevalue(Double.valueOf(sensorsValue[1]));
            sensors.setPm25value(Double.valueOf(sensorsValue[2]));
            sensors.setChargevalue(Double.valueOf(sensorsValue[3]));


            sensorsMapper.insert(sensors);
        }
        return 1;

    }


}