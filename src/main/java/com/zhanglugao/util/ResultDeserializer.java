package com.zhanglugao.util;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.zhanglugao.swagger.constant.Result;

import java.lang.reflect.Type;

public class ResultDeserializer implements ObjectDeserializer {


    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        String data=defaultJSONParser.getInput();
        System.out.println(data);
        JavaBeanDeserializer deserializer=new JavaBeanDeserializer(ParserConfig.getGlobalInstance(), Result.class,type);
        T t= deserializer.deserialze(defaultJSONParser,type,o);
        return t;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
