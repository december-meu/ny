package com.geovis.service;

import com.geovis.common.units.FileUtils;
import com.geovis.common.units.XMLParseUtil;
import com.geovis.metadata.support.data.ParserParams;
import com.geovis.model.DemParseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DemParseService {

    @Value("${file.dir}")
    private String fileDir;

    public List<DemParseResult> demParse(ParserParams params) {
        List<DemParseResult> result = new ArrayList<>();
        File originFile = new File(params.getFilePath());
        if (!originFile.exists()) {
            throw new RuntimeException("原始文件不存在");
        } else {
            System.out.println("---------------------------数字地形数据：{" + params.getFilePath() + "}文件开始解析-------------------------");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = df.format(new Date());
            String parentFolderName = originFile.getName().substring(0, originFile.getName().lastIndexOf(".")) + date;
            //1.解压原始文件
            List<String> fileNames = FileUtils.unPackZip(originFile, ".mat.xml", fileDir + File.separator + "parseData" + File.separator + parentFolderName + File.separator);
            for (String name : fileNames) {
                File temp = new File(name);
                //2.解析xml文件
                Map<String, Object> parserResult = new HashMap<>();
                XMLParseUtil.parse(temp, parserResult);
                result.add(mapToRes(parserResult));
                System.out.println("数字地形数据：{" + temp.getName() + "}文件解析完成");
            }
            FileUtils.delDirectory(new File(fileDir + File.separator + "parseData" + File.separator + parentFolderName));
        }
        System.out.println("---------------------------数字地形数据：{" + params.getFilePath() + "}文件完成解析-------------------------");
        return result;
    }

    public DemParseResult mapToRes(Map<String, Object> parserResult) {
        DemParseResult demParseResult = new DemParseResult();
        demParseResult.setFileName((String) parserResult.get("FileName"));
        demParseResult.setFilePath((String) parserResult.get("FilePath"));
        demParseResult.setDataProductionUnit((String) parserResult.get("DataProductionUnit"));
        demParseResult.setProductDescription((String) parserResult.get("ProductDescription"));
        demParseResult.setDataSource((String) parserResult.get("DataSource"));
        demParseResult.setDataTypes((String) parserResult.get("DataType"));
        demParseResult.setDataSubtype((String) parserResult.get("DataSubtype"));
        demParseResult.setProductionTime((String) parserResult.get("ProductionTime"));
        demParseResult.setGridSpacing((String) parserResult.get("GridSpacing"));
        demParseResult.setGridUnit((String) parserResult.get("GridUnit"));
        demParseResult.setRasterXSize((String) parserResult.get("RasterXSize"));
        demParseResult.setRasterYSize((String) parserResult.get("RasterYSize"));
        demParseResult.setElevationError((String) parserResult.get("ElevationError"));
        demParseResult.setElevationErrorUnit((String) parserResult.get("ElevationErrorUnit"));
        demParseResult.setUpLeftLon((String) parserResult.get("UpLeftLon"));
        demParseResult.setUpLeftLat((String) parserResult.get("UpLeftLat"));
        demParseResult.setUpRightLon((String) parserResult.get("UpRightLon"));
        demParseResult.setUpRightLat((String) parserResult.get("UpRightLat"));
        demParseResult.setLowRightLat((String) parserResult.get("LowRightLat"));
        demParseResult.setLowRightLon((String) parserResult.get("LowRightLon"));
        demParseResult.setLowLeftLat((String) parserResult.get("LowLeftLat"));
        demParseResult.setLowLeftLon((String) parserResult.get("LowLeftLon"));
        demParseResult.setUpLeftAbscissa((String) parserResult.get("UpLeftAbscissa"));
        demParseResult.setUpLeftOrdinate((String) parserResult.get("UpLeftOrdinate"));
        demParseResult.setUpRightAbscissa((String) parserResult.get("UpRightAbscissa"));
        demParseResult.setUpRightOrdinate((String) parserResult.get("UpRightOrdinate"));
        demParseResult.setLowRightAbscissa((String) parserResult.get("LowRightAbscissa"));
        demParseResult.setLowRightOrdinate((String) parserResult.get("LowRightOrdinate"));
        demParseResult.setLowLeftAbscissa((String) parserResult.get("LowLeftAbscissa"));
        demParseResult.setLowLeftOrdinate((String) parserResult.get("LowLeftOrdinate"));
        demParseResult.setMapProjection((String) parserResult.get("MapProjection"));
        demParseResult.setGeodeticDatum((String) parserResult.get("GeodeticDatum"));
        demParseResult.setElevationDatum((String) parserResult.get("ElevationDatum"));
        demParseResult.setCentralMeridian((String) parserResult.get("CentralMeridian"));
        demParseResult.setZoningMode((String) parserResult.get("ZoningMode"));
        demParseResult.setProjectionBandNum((String) parserResult.get("ProjectionBandNum"));
        demParseResult.setFormat((String) parserResult.get("Format"));
        demParseResult.setDataSize((String) parserResult.get("DataSize"));
        demParseResult.setSheetDesignation((String) parserResult.get("SheetDesignation"));
        demParseResult.setStoragetime((String) parserResult.get("Storagetime"));
        demParseResult.setAdnexa((String) parserResult.get("Adnexa"));
        demParseResult.setNotes((String) parserResult.get("Notes"));

        String shootingTime = (String) parserResult.get("ProductionTime");
        if (!StringUtils.isEmpty(shootingTime)) {
            shootingTime = shootingTime.replaceAll("-", "").replaceAll(" ", "").replaceAll("/", "");
            if (shootingTime.length() >= 8) shootingTime = shootingTime.substring(0, 8);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = null;
            try {
                date = simpleDateFormat.parse(shootingTime);
            } catch (ParseException e) {
                System.out.println("数据解析：采集时间格式错误");
//                e.printStackTrace();
            }
            if (date != null) demParseResult.setShootingTime(date.getTime());
        }

        String geo = "POLYGON((" + parserResult.get("UpLeftLon") + " " + parserResult.get("UpLeftLat") + ","
                + parserResult.get("UpRightLon") + " " + parserResult.get("UpRightLat") + ","
                + parserResult.get("LowRightLon") + " " + parserResult.get("LowRightLat") + ","
                + parserResult.get("LowLeftLon") + " " + parserResult.get("LowLeftLat") + ","
                + parserResult.get("UpLeftLon") + " " + parserResult.get("UpLeftLat") + "))";
        demParseResult.setGeo(geo);
        return demParseResult;
    }


    public static void main(String[] args) {
        DemParseService a = new DemParseService();
        ParserParams p = new ParserParams();
        p.setFilePath("D:/ny/dem_DN07450082.zip");
        a.demParse(p);
    }

}
