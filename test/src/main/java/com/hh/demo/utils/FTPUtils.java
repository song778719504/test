package com.hh.demo.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Component
public class FTPUtils {

    @Value("${ftp.host}")
    private String ftpIp;
    @Value("${ftp.user}")
    private String ftpUser;
    @Value("${fpt.pass}")
    private String ftpPass;

    FTPClient ftpClient=new FTPClient();


    //step:1连接到ftp服务器
    public boolean connectFTPServer(){

        try {
            ftpClient.connect(ftpIp);
            return ftpClient.login(ftpUser,ftpPass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean uploadFile(List<File> fileList){
        return uploadFile("imgs",fileList);
    }

    public boolean uploadFile(String remotePath,List<File> fileList){

        FileInputStream fileInputStream =null;
        try {
            if (connectFTPServer()){
                //切换工作目录
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();//打开被动传输模式

                for (File file : fileList){
                    fileInputStream = new FileInputStream(file);
                    ftpClient.storeFile(file.getName(),fileInputStream);
                }
                return true;
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
