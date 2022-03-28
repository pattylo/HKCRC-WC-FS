package com.example.demo.TCP;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.demo.TCP.SocketHandler.register;

//启动服务socket
@Slf4j
@Data
@Component
@NoArgsConstructor
public class SocketServer {

    private Integer port = 8888;
    private boolean started;
    private ServerSocket serverSocket;

    // 防止重复创建socket线程链接对象浪费资源
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void start(){
        start(null);
    }

    public void start(Integer port){
        log.info("port: {}, {}", this.port, port);
        //serverSocket.set
        try {
            serverSocket =  new ServerSocket(this.port);
            //serverSocket =  new ServerSocket(this.port, 50, InetAddress.getByName("192.168.10.23"));
            //serverSocket =  new ServerSocket(this.port, 50, InetAddress.getLocalHost("192.168.10.23"));
            //serverSocket = new ServerSocket();
            //serverSocket.bind(new InetSocketAddress("110.42.218.135",this.port));




            started = true;
            log.info("Socket服务已启动，地址{}, 占用端口： {}, 其他：{}", serverSocket.getLocalSocketAddress(), serverSocket.getLocalPort(), serverSocket.getInetAddress());
        } catch (IOException e) {
            log.error("端口冲突,异常信息：{}", e);
            System.exit(0);
        }


        while  (true){
            try {
                log.info("socket listening... ...");
                // 开启socket监听
                Socket socket = serverSocket.accept();
                ClientSocket register = register(socket);
                // 在此判断是否重复创建socket对象线程
                if (register != null){
                    executorService.submit(register);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
