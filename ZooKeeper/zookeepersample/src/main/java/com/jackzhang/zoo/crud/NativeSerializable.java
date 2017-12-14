package com.jackzhang.zoo.crud;

import java.io.*;

/**
 * 使用Java提供的方法来序列化与反序列化
 * Created by Jack
 */
public class NativeSerializable {

    /**
     * 字节数组转对象
     * @param bytes
     * @return
     */
    public static Object byteToObject(byte[] bytes){
        Object object=null;

        ByteArrayInputStream byteArrayInputStream=null;
        ObjectInputStream objectInputStream=null;
        try{
            byteArrayInputStream=new ByteArrayInputStream(bytes);
            objectInputStream=new ObjectInputStream(byteArrayInputStream);

            object=objectInputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(byteArrayInputStream);
            close(objectInputStream);
        }
        return object;
    }

    /**
     * 对象转字节数组
     * @param object
     * @return
     */
    public static byte[] objectToByte(Object object){
        byte[] bytes=null;
        ByteArrayOutputStream byteArrayOutputStream=null;
        ObjectOutputStream objectOutputStream=null;
        try{
            byteArrayOutputStream=new ByteArrayOutputStream();
            objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            bytes=byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(byteArrayOutputStream);
            close(objectOutputStream);
        }
        return bytes;
    }
    public static void close(Closeable closeable){
        if (closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
