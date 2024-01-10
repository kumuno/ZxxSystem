package com.util;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class FtpUtil {

    //ftp服务器 ip地址
    private static final String FTP_ADDRESS = "119.29.13.56";
    //端口号
    private static final int FTP_PORT = 21;
    //用户
    private static final String FTP_USERNAME = "zxx";
    //密码
    private static final String FTP_PASSWORD = "Kj2ZPRiwZfCebi3T";
    //附件路径
    private static final String FTP_BASEPATH = "/images/";

    public static String uploadFile(String fileName, InputStream input, String openid, HttpServletRequest request) throws UnknownHostException {
        String FTP_BASEPATHNEW = FTP_BASEPATH + openid;
        //获得本机Ip（获取的是服务器的Ip）
//        InetAddress inetAddress = InetAddress.getLocalHost();
//        String ip = inetAddress.getHostAddress();
//        System.out.println(ip);
//        System.out.println(request.getScheme()+"://"+FTP_ADDRESS+':'+request.getServerPort()+FTP_BASEPATHNEW+'/'+fileName);
        //照片存放服务器的链接
//      //9090是存放照片的服务器中tomcat的端口 为了方便显示服务器上的照片
        String ImageUrl=request.getScheme()+"://"+FTP_ADDRESS+':'+"9090"+FTP_BASEPATHNEW+'/'+fileName;
        System.out.println(ImageUrl);
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.enterLocalPassiveMode();   //这个设置允许被动连接--访问远程ftp时需要
            ftp.connect(FTP_ADDRESS,FTP_PORT);//登录FTP服务器
            ftp.login(FTP_USERNAME,FTP_PASSWORD);//登录
//            ftp.enterLocalPassiveMode();
            reply = ftp.getReplyCode();
            //如果没有连上就断开
            if (!FTPReply.isPositiveCompletion(reply)){
                ftp.disconnect();
                return "false";
            }
            //文件类型
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //创建文件夹 不存在才会执行
            ftp.makeDirectory(FTP_BASEPATHNEW);
            //切换到改目录下
            ftp.changeWorkingDirectory(FTP_BASEPATHNEW);
            ftp.storeFile(fileName,input);
            input.close();
            ftp.logout();
            success=true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (ftp.isConnected()){
                try {
                    ftp.disconnect();
                }catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

        }
        if (success == true){
            return ImageUrl;
        }else {
            return "false";
        }
    }

}
