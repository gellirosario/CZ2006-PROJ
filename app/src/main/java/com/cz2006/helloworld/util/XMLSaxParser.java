package com.cz2006.helloworld.util;

import com.cz2006.helloworld.models.MapDetail;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLSaxParser {

    public static ArrayList<MapDetail> parse(InputStream is) {

        ArrayList<MapDetail> detailList = null;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLSaxHandler handler = new XMLSaxHandler();
            saxParser.parse(new InputSource(is), handler);
            detailList = handler.getDetails();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }

        return detailList;
    }
}
