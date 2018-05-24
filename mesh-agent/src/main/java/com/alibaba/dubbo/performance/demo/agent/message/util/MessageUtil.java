package com.alibaba.dubbo.performance.demo.agent.message.util;

import com.alibaba.dubbo.performance.demo.agent.message.MessageBucket;
import com.alibaba.dubbo.performance.demo.agent.message.MessageImpl;
import com.alibaba.dubbo.performance.demo.agent.message.model.Message;
import com.alibaba.fastjson.JSON;


/**
 * utils class
 */
public class MessageUtil {
    /**
     *
     * @param data
     * @param start
     * @param end
     * @return
     */
    public static Message decode(byte[] data, int start, int end) {
        return null;
    }

    public static byte[] encode(Message msg) {
        return null;
    }

    public static String msgToString(Message msg) {
//        return JSON.toJSONString(msg);
        String parameter = (String)msg.getBody();
        String body = parameter.split("&")[3].split("=")[1];
        return msg.getId() + "#" + body;
    }

    public static MessageImpl stringToMsg(String data) {
//        return JSON.parseObject(data, MessageImpl.class);
//        System.out.println("data" + data);
        String[] msgArray = data.split("#");
        return new MessageImpl(Integer.valueOf(msgArray[0]), msgArray[1]);
    }

    public static String messageBucketEncode(MessageBucket messageBucket) {
        return JSON.toJSONString(messageBucket);
    }

    public static MessageBucket messageBucketDecode(String data) {
        return JSON.parseObject(data, MessageBucket.class);
    }
}
