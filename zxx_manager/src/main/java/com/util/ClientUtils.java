package com.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

@Component
public class ClientUtils {

    @Autowired
    private HttpServletRequest request;

    public String getServerIpAddress() {
//        String ipAddress = request.getHeader("X-Forwarded-For");
//        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("Proxy-Client-IP");
//        }
//        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
//            ipAddress = request.getRemoteAddr();
//        }
//        if (ipAddress != null && ipAddress.contains(",")) {
//            ipAddress = ipAddress.split(",")[0].trim();
//        }
//        return ipAddress;
        // 获取所有的网络接口
        Enumeration<NetworkInterface> nets = null;
        try {
            nets = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        assert nets != null;
        for (NetworkInterface netint : Collections.list(nets)) {
            // 获取该网络接口绑定的所有 IP 地址
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                // 判断该 IP 地址是否是宝塔所在的网卡对应的 IP 地址
                if (inetAddress.getHostAddress().startsWith("119.29.13.")) {
                    System.out.println("宝塔的 IP 地址为：" + inetAddress.getHostAddress());
                    return inetAddress.getHostAddress();
                }
            }
        }
        return "119.29.13.56";
    }

    public  String getLocalIpAddress() {
        String serverIpAddress = null;
        try {
            serverIpAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return serverIpAddress;
    }
}

