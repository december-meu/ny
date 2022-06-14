package com.geovis.common.units;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author GeL
 * @Date 2021/4/8 10:44
 */
public class XMLParseUtil {

    private final static Logger log = LoggerFactory.getLogger(XMLParseUtil.class);

    /**
     * 解析XML文件到result中
     *
     * @param file
     * @param result
     * @throws DocumentException
     */
    public static void parse(File file, Map<String, Object> result) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(file);
        } catch (DocumentException e) {
            log.error("读取XML文件失败,文件路径{},错误详情", file.getAbsolutePath(), e.getMessage());
        }
        Element root = document.getRootElement();
        //获取元数据element
        Iterator<Element> iterator = root.elementIterator();
        Element element;
        while (iterator.hasNext()) {
            element = iterator.next();
            result.put(element.getName(), element.getText());
        }
    }
}
