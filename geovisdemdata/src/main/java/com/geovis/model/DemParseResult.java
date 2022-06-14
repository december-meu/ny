package com.geovis.model;

import com.geovis.metadata.support.annotation.FieldMeta;
import com.geovis.metadata.support.annotation.GeovisMetaData;
import lombok.Data;

import java.util.List;

/**
 * 解析数字地形数据
 */
@Data
@GeovisMetaData
public class DemParseResult {

    @FieldMeta(
            nameCn = "数据名称",
            dataType = String.class
    )
    private String fileName;

    @FieldMeta(
            nameCn = "数据目录",
            dataType = String.class
    )
    private String filePath;

    @FieldMeta(
            nameCn = "数据生产单位",
            dataType = String.class
    )
    private String dataProductionUnit;

    @FieldMeta(
            nameCn = "产品说明",
            dataType = String.class
    )
    private String productDescription;

    @FieldMeta(
            nameCn = "数据来源",
            dataType = String.class
    )
    private String dataSource;

    @FieldMeta(
            nameCn = "数据类型",
            dataType = String.class
    )
    private String dataTypes;

    @FieldMeta(
            nameCn = "数据子类型",
            dataType = String.class
    )
    private String dataSubtype;

    @FieldMeta(
            nameCn = "数据生产时间",
            dataType = String.class
    )
    private String productionTime;

    @FieldMeta(
            nameCn = "格网间隔",
            dataType = String.class
    )
    private String gridSpacing;

    @FieldMeta(
            nameCn = "格网单位",
            dataType = String.class
    )
    private String gridUnit;

    @FieldMeta(
            nameCn = "格网行数",
            dataType = String.class
    )
    private String rasterXSize;

    @FieldMeta(
            nameCn = "格网列数",
            dataType = String.class
    )
    private String rasterYSize;

    @FieldMeta(
            nameCn = "高程中误差（数值）",
            dataType = String.class
    )
    private String elevationError;

    @FieldMeta(
            nameCn = "高程中误差计量单位",
            dataType = String.class
    )
    private String elevationErrorUnit;

    @FieldMeta(
            nameCn = "左上角经度",
            dataType = String.class
    )
    private String upLeftLon;

    @FieldMeta(
            nameCn = "左上角纬度",
            dataType = String.class
    )
    private String upLeftLat;

    @FieldMeta(
            nameCn = "右上角经度",
            dataType = String.class
    )
    private String upRightLon;

    @FieldMeta(
            nameCn = "右上角纬度",
            dataType = String.class
    )
    private String upRightLat;

    @FieldMeta(
            nameCn = "右下角经度",
            dataType = String.class
    )
    private String lowRightLon;

    @FieldMeta(
            nameCn = "右下角纬度",
            dataType = String.class
    )
    private String lowRightLat;

    @FieldMeta(
            nameCn = "左下角经度",
            dataType = String.class
    )
    private String lowLeftLon;

    @FieldMeta(
            nameCn = "左下角纬度",
            dataType = String.class
    )
    private String lowLeftLat;

    @FieldMeta(
            nameCn = "左上角横坐标",
            dataType = String.class
    )
    private String upLeftAbscissa;

    @FieldMeta(
            nameCn = "左上角纵坐标",
            dataType = String.class
    )
    private String upLeftOrdinate;

    @FieldMeta(
            nameCn = "右上角横坐标",
            dataType = String.class
    )
    private String upRightAbscissa;


    @FieldMeta(
            nameCn = "右上角纵坐标",
            dataType = String.class
    )
    private String upRightOrdinate;


    @FieldMeta(
            nameCn = "右下角横坐标",
            dataType = String.class
    )
    private String lowRightAbscissa;

    @FieldMeta(
            nameCn = "右下角纵坐标",
            dataType = String.class
    )
    private String lowRightOrdinate;

    @FieldMeta(
            nameCn = "左下角横坐标",
            dataType = String.class
    )
    private String lowLeftAbscissa;

    @FieldMeta(
            nameCn = "左下角纵坐标",
            dataType = String.class
    )
    private String lowLeftOrdinate;
    @FieldMeta(
            nameCn = "地图投影",
            dataType = String.class
    )
    private String mapProjection;

    @FieldMeta(
            nameCn = "大地基准",
            dataType = String.class
    )
    private String geodeticDatum;
    @FieldMeta(
            nameCn = "高程基准",
            dataType = String.class
    )
    private String elevationDatum;

    @FieldMeta(
            nameCn = "中央经线",
            dataType = String.class
    )
    private String centralMeridian;
    @FieldMeta(
            nameCn = "分带方式",
            dataType = String.class
    )
    private String zoningMode;

    @FieldMeta(
            nameCn = "投影带号",
            dataType = String.class
    )
    private String projectionBandNum;
    @FieldMeta(
            nameCn = "数据格式",
            dataType = String.class
    )
    private String format;

    @FieldMeta(
            nameCn = "数据大小",
            dataType = String.class
    )
    private String dataSize;
    @FieldMeta(
            nameCn = "图幅编号",
            dataType = String.class
    )
    private String sheetDesignation;

    @FieldMeta(
            nameCn = "数据上传时间",
            dataType = String.class
    )
    private String storagetime;

    @FieldMeta(
            nameCn = "附件",
            dataType = String.class
    )
    private String adnexa;

    @FieldMeta(
            nameCn = "备注",
            dataType = String.class
    )
    private String notes;

    @FieldMeta(
            nameCn = "wkt",
            dataType = String.class
    )
    private String geo;

    @FieldMeta(
            nameCn = "采集时间",
            dataType = Long.class
    )
    private Long shootingTime;

}
