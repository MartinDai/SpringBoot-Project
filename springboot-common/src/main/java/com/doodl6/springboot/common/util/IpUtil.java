package com.doodl6.springboot.common.util;

import com.google.common.collect.Lists;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

/**
 * IP工具类
 */
public final class IpUtil {

    private IpUtil() {
    }

    /**
     * 获取本地所有IP4的地址
     */
    public static List<String> getAllIp4Address() {
        Enumeration allNetInterfaces = null;
        List<String> ipList = Lists.newArrayList();
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (java.net.SocketException e) {
            e.printStackTrace();
        }

        InetAddress ip;
        while (allNetInterfaces != null && allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = (InetAddress) addresses.nextElement();
                if (ip instanceof Inet4Address) {
                    ipList.add(ip.getHostAddress());
                }
            }
        }

        return ipList;
    }

    public static void main(String[] args) {
        System.out.println(getAllIp4Address());
    }

}
