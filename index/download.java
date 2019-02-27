package com.pzl.httpClient;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class httpClentDemo1 {
    @Test
    public void fun4() throws IOException, InterruptedException {
        String path  =  "F:/学习/JAVA/kiss.zip";
        URL url = new URL("http://localhost:8080/web/resources/12306Bypass_1.12.96.zip");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        File file = new File(path);
        long ressize = resourceSize(url);
        if(file.exists()){
            long size = file.length();
            if(size == ressize){
                System.out.println("资源已存在");
            }
        }
        //断点续传设置range头信息
        conn.addRequestProperty("range","bytes="+file.length()+"-");
        conn.connect();
        int code = conn.getResponseCode();
        if(code == 206){
            InputStream in = conn.getInputStream();
            //缓存流快速下载
//            FileOutputStream out= new FileOutputStream(file,true);
//            IOUtils.copy(in,out);
            //自写下载
            save(file,in,ressize);

        }
    }
//获取文件大小,不能直接在主方法中使用,因为主方法中使用了range头 获取到的文件大小是部分大小
    private long resourceSize(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        //获取文件大小
        long size = conn.getContentLength();
        conn.disconnect();
        return size;


    }

    private void save(File file, InputStream in, long size) throws IOException, InterruptedException {
        FileOutputStream out = new FileOutputStream(file, true);
        byte[] b = new byte[2048];
        int len;
        long fileSize = file.length();
        while((len=in.read(b))!=-1){
            out.write(b,0,len);
            fileSize+=len;
            System.out.println(String.format("%.2f%%",fileSize * 1.0 / size * 100));
            Thread.sleep(1);
        }
        out.close();
    }

    @Test
    public void fun3() throws IOException {
        //创建URL
        URL url = new URL("http://localhost:8080/web/resources/12306Bypass_1.12.96.zip");
        //打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方法
        conn.setRequestMethod("GET");
        conn.connect();

        if(conn.getResponseCode() == 200){
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream("F:/学习/JAVA/kiss.zip");
            IOUtils.copyLarge(in,out);
        }




    }
}
