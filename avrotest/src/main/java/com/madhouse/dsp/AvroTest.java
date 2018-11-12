package com.madhouse.dsp;

import com.madhouse.dsp.Utils.User;
import com.madhouse.dsp.Utils.User1;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Madhouse on 2017/9/12.
 */
public class AvroTest {
    private static void serialize(String path) throws IOException {
        // 声明并初始化User对象
        // 方式一
        User user1 = new User();
        user1.setName("zhangsan");
        user1.setFavoriteNumber(21);
        user1.setFavoriteColor(null);

        // 方式二 使用构造函数
        // Alternate constructor
        User user2 = new User("Ben", 7, "red");

        // 方式三，使用Build方式
        // Construct via builder
        User user3 = User.newBuilder()
                .setName("Charlie")
                .setFavoriteColor("blue")
                .setFavoriteNumber(null)
                .build();

        //String path = "D:\\tmp\\user.avro"; // avro文件存放目录
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
        DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
        dataFileWriter.create(user1.getSchema(), new File(path));
        // 把生成的user对象写入到avro文件
        dataFileWriter.append(user1);
        dataFileWriter.append(user2);
        dataFileWriter.append(user3);
        dataFileWriter.close();
    }

    private static void deserialize(String path) throws IOException {
        // Deserialize Users from disk
        DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
        //serDatumReader.read()
        DataFileReader<User> dataFileReader = new DataFileReader<User>(new File(path), userDatumReader);
        User user = null;
        while (dataFileReader.hasNext()) {
            // Reuse user object by passing it to next(). This saves us from
            // allocating and garbage collecting many objects for files with
            // many items.
            user = dataFileReader.next(user);
            System.out.println(user);
        }
    }

    private static ByteArrayOutputStream write(Object datum, Schema
            schema) throws IOException {
        ReflectDatumWriter<Object> dWriter = new ReflectDatumWriter<Object>(schema);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Encoder e = EncoderFactory.get().binaryEncoder(os, null);
        dWriter.write(datum, e);
        e.flush();
        return os;
    }

    private static Object read(ByteArrayInputStream is, Schema writer,
                               Schema reader) throws IOException {
        return new ReflectDatumReader(writer, reader).read(null,
                DecoderFactory.get().binaryDecoder(is, null));
    }

    private static void serialize1(String path) throws IOException {
        // 声明并初始化User对象
        // 方式一
        User1 user1 = new User1();
        user1.setName("zhangsan");
        user1.setFavoriteNumber(21);
        user1.setFavoriteColor(null);
        user1.setScore(88);

        // 方式二 使用构造函数
        // Alternate constructor
        User1 user2 = new User1("Ben", 7, "red", 90);

        // 方式三，使用Build方式
        // Construct via builder
        User1 user3 = User1.newBuilder()
                .setName("Charlie")
                .setFavoriteColor("blue")
                .setFavoriteNumber(null)
                .setScore(77)
                .build();

        //String path = "D:\\tmp\\user.avro"; // avro文件存放目录
        DatumWriter<User1> userDatumWriter = new SpecificDatumWriter<User1>(User1.class);
        DataFileWriter<User1> dataFileWriter = new DataFileWriter<User1>(userDatumWriter);
        dataFileWriter.create(User1.getClassSchema(), new File(path));
        // 把生成的user对象写入到avro文件
        dataFileWriter.append(user1);
        dataFileWriter.append(user2);
        dataFileWriter.append(user3);
        dataFileWriter.close();
    }

    private static void deserializeWith2Format(String path) throws IOException {
        // Deserialize Users from disk
        //DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
        SpecificDatumReader<User> userDatumReader = new SpecificDatumReader<User>(User1.getClassSchema(), User.getClassSchema());
        //userDatumReader.read(null, DecoderFactory.get().resolvingDecoder())
        DataFileReader<User> dataFileReader = new DataFileReader<User>(new File(path), userDatumReader);
        User user = null;
        while (dataFileReader.hasNext()) {
            // Reuse user object by passing it to next(). This saves us from
            // allocating and garbage collecting many objects for files with
            // many items.
            user = dataFileReader.next(user);
            System.out.println(user);
        }
    }

    public static void main(String[] args) {
        System.out.println("start...");
        /*String path = "D:\\avro\\test\\test.avro";
        try {
            System.out.println("start to serialize..");
            serialize(path);
            System.out.println("serialize end..");
            System.out.println("start to deserialize..");
            deserialize(path);
            System.out.println("deserialize end..");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //test for processing with 2 format schema
        // 声明并初始化User对象
        // 方式一
        User user1 = new User();
        user1.setName("zhangsan");
        user1.setFavoriteNumber(21);
        user1.setFavoriteColor(null);

        // 方式二 使用构造函数
        // Alternate constructor
        User user2 = new User("Ben", 7, "red");

        // 方式三，使用Build方式
        // Construct via builder
        User user3 = User.newBuilder()
                .setName("Charlie")
                .setFavoriteColor("blue")
                .setFavoriteNumber(null)
                .build();


        //String path1 = "D:\\avro\\test\\test1.avro";
        try {
            ArrayList<Object> objectLists = new ArrayList<Object>();
            objectLists.add(user1);
            objectLists.add(user2);
            objectLists.add(user3);
            for (Object o : objectLists) {
                System.out.println("start to serialize..");
                ByteArrayOutputStream bo = write(o, User.getClassSchema());
                System.out.println("serialize end..");
                System.out.println("start to deserialize..");
                Object res = read(new ByteArrayInputStream(bo.toByteArray()),
                        User.getClassSchema(), User1.getClassSchema());
                System.out.println(res);
                System.out.println("deserialize end..");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*String path = "D:\\avro\\test\\test1.avro";
        try {
            System.out.println("start to serialize..");
            serialize1(path);
            System.out.println("serialize end..");
            System.out.println("start to deserialize..");
            deserializeWith2Format(path);
            System.out.println("deserialize end..");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        System.out.println("end...");
    }
}
