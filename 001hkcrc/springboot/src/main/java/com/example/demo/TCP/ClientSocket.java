package com.example.demo.TCP;

import com.example.demo.controller.CellController;
import com.example.demo.controller.UserController;
import com.example.demo.entity.Cell;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

import static com.example.demo.TCP.SocketHandler.*;

@Slf4j
@Data
public class ClientSocket implements Runnable {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String key;
    private String message;

    @Override
    public void run() {
        //CellController cellController = new CellController();
        while (true){
            try {
                onMessage(this);
                log.info(LocalDateTime.now()+"当前设备,key:"+this.key);
                //log.info(LocalDateTime.now()+"接收到数据,Message: " + this.message);
                //log.info(LocalDateTime.now()+"接收到数据,Message: " + this.getKey());

                String[] infodetails = this.key.split(",");

                //log.info(LocalDateTime.now()+"infodetails.length: " + infodetails.length);
                String sitename="XHJ", sensornumber="", operator="";
                String floor="-1", status="-1";

                for (int i=0;i<infodetails.length;i++)
                {
                    String[] infortemp = infodetails[i].split(":");
                    log.info(LocalDateTime.now()+"infortemp: " + infortemp[0]);
                    if(infortemp[0].contains("sitename"))    sitename=infortemp[1];
                    if(infortemp[0].contains("floo"))        floor=infortemp[1];
                    if(infortemp[0].contains("sensornumber"))sensornumber=infortemp[1];
                    if(infortemp[0].contains("status"))      status=infortemp[1];
                    //if(infortemp[0]=="latestTimeStamp")cell.setLatesttimestamp(Double.parseDouble(infortemp[1]) );
                    if(infortemp[0].contains("operator"))
                    {
                        String[] temp = infortemp[1].split("}");
                        operator=temp[0];
                    }
                }

                if(status!="-1" && floor!="-1")CellController.CurrentCellInfo = "sitename:"+sitename+", floor:"+floor+", sensornumber:"+sensornumber+", status:"+status+", latestTimeStamp:"+LocalDateTime.now()+", operator:"+operator;
                //cellController.UpdateCellinfo("sitename:a, floor:4, sensornumber:b, status:0, latestTimeStamp:12, operator:lo");
                //System.out.print("==========asassa=="+LocalDateTime.now()+"当前设备:"+this.key+" 接收到数据: <<<<<<" + this.message);
                //sendMessage(this, "$"+UserController.currentTruckInfo+"#");
                this.setKey("");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isSocketClosed(this)){
                log.info("客户端已关闭,其Key值为：{}", this.getKey());
                //关闭对应的服务端资源
                close(this);
                break;
            }
        }
    }
}
