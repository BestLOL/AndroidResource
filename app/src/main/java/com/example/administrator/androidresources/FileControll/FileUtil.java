package com.example.administrator.androidresources.FileControll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//文件操作类
public class FileUtil {


    /**
     * FileInputStream字节流方式
     * 获取MD5是一个耗时操作，所以需要在子线程中运行
     * 获取文件MD5值
     * @param file 文件
     * @return md5
     */
    public static String getFileMD5(File file){
        if(file==null || !file.exists()) return null;
        MessageDigest messageDigest;
        try(FileInputStream fis = new FileInputStream(file)){
            messageDigest = MessageDigest.getInstance("MD5");
            //普通流读取方式
            byte[] buffer = new byte[1024*1024];//1M
            int len;
            while((len=fis.read(buffer))>0){
                messageDigest.update(buffer,0,len);
            }
            //messageDigest.digest（）这个方法用来计算MD5的值
            BigInteger bigInt = new BigInteger(1,messageDigest.digest());
            String md5 = bigInt.toString(16);//转换为16进制，1表示符号位为正数
            while(md5.length()<32){
                md5 = "0"+md5;//不足32位补0
            }
            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RandomAccessFile 获取文件的MD5值
     * 这种方式更为快速
     * @param file 文件路径
     * @return md5
     */
    public static String getFileMD5New(File file) {
        if (file == null | !file.exists()) return null;
        MessageDigest messageDigest;
        try(RandomAccessFile randomAccessFile=new RandomAccessFile(file,"r")){
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes=new byte[1024*1024*10];
            int len=0;
            while ((len=randomAccessFile.read(bytes))!=-1){
                messageDigest.update(bytes,0, len);
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * FileChannel 获取文件的MD5值
     * MappedByteBuffer这个东西很可怕，这个回收是不确定的，在手机上测试FileChannel效率并不是最好的。如果要计算一个几百兆的大文件，
     * 发现FileChannel+MappedByteBuffer还很容易OOM,原因就是MappedByteBuffer内存占用、文件关闭不确定，
     * 被其打开的文件只有在垃圾回收的才会被关闭，而且这个时间点是不确定的。
     * @param file 文件路径
     * @return md5
     */
    public static String getFileMD5FileChannel(File file){
        if(file==null | !file.exists()) return null;
        MessageDigest messageDigest;
        try(FileInputStream fis = new FileInputStream(file);
            FileChannel ch = fis.getChannel()){
            messageDigest = MessageDigest.getInstance("MD5");

            //首先计算文件能被分成几个部分
            int size = 1024*1024*10;//每个部分大小为10M
            long part = file.length()/size+(file.length()%size >0?1:0);
            for(int i=0;i<part;i++){
                MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY,i*size,
                        i==part-1?file.length():(i+1)*size);
                messageDigest.update(byteBuffer);
                byteBuffer.clear();
            }
            //上述包装完成
            BigInteger bigInt = new BigInteger(1,messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length()<32){
                md5 = "0"+md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
