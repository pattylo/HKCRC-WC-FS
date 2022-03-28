package com.example.demo.TCP;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.example.demo.TCP.SocketPool.add;
import static com.example.demo.TCP.SocketPool.remove;

@Slf4j
public class SocketHandler {

    /**
     *
     * @param socket
     * @return
     */
    public static ClientSocket register(Socket socket){
        ClientSocket clientSocket = new ClientSocket();
        clientSocket.setSocket(socket);
        try{
            clientSocket.setInputStream(new DataInputStream(socket.getInputStream()));
            clientSocket.setOutputStream(new DataOutputStream(socket.getOutputStream()));
            byte[] bytes = new byte[1024];
            clientSocket.getInputStream().read(bytes);
            clientSocket.setKey(new String(bytes, "utf-8"));
            add(clientSocket);
            return clientSocket;
        }catch(IOException e ){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向指定客户端发送信息
     * @param clientSocket
     * @param message
     */
    public static void sendMessage(ClientSocket clientSocket, String message){
        try {
            log.info("发送消息到客户端  : >>>>>" + message);
            clientSocket.getOutputStream().write(message.getBytes("utf-8"));
            //clientSocket.getOutputStream().writeUTF(message);
        } catch (IOException e) {
            log.error("发送信息异常：{}", e);
            close(clientSocket);
        }
    }

    /**
     * 获取指定客户端的上传信息
     * @param clientSocket
     * @return
     */
    public static void onMessage(ClientSocket clientSocket){
        byte[] keyByte = new byte[1024];
        byte[] msgByte = new byte[1];
        try {
            // 第一次先发送序列号
            if(StringUtils.isEmpty(clientSocket.getKey())) {
                clientSocket.getInputStream().read(keyByte);
                clientSocket.setKey(new String(keyByte, "UTF-8"));
                //return clientSocket.getKey();
                // 以后发送数据
            }else {

                String info = "";
                while (true) {
                    if (clientSocket.getInputStream().available() > 0) {
                        clientSocket.getInputStream().read(msgByte);
                        String tempStr = HexEcodeUtil.ByteArrayToHexStr(msgByte);
                        info += tempStr;
                        //已经读完
                        if (clientSocket.getInputStream().available() == 0) {
                            //重置,不然每次收到的数据都会累加起来
                            clientSocket.setMessage(info);
                            break;
                        }
                    }
                }
                //log.info("Receive Socket information, which is: "+info);
                //return clientSocket.getMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            close(clientSocket);
        }
        //return null;
    }

    /**
     * 指定Socket资源回收
     * @param clientSocket
     */
    public static void close(ClientSocket clientSocket){
        log.info("进行资源回收");
        if (clientSocket != null){
            log.info("开始回收socket相关资源，其Key为{}", clientSocket.getKey());
            remove(clientSocket.getKey());
            Socket socket = clientSocket.getSocket();
            try {
                socket.shutdownInput();
                socket.shutdownOutput();
            } catch (IOException e) {
                log.error("关闭输入输出流异常，{}", e);
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log.error("关闭socket异常{}", e);
                }
            }
        }
    }


    /**
     * 发送数据包，判断数据连接状态
     * @param clientSocket
     * @return
     */
    public static boolean isSocketClosed(ClientSocket clientSocket){
        try {
            clientSocket.getSocket().sendUrgentData(1);
            return false;
        } catch (IOException e) {
            return true;
        }
    }

}
