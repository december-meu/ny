package com.geovis.controller;

import com.geovis.metadata.support.data.DefinedMetaData;
import com.geovis.metadata.support.data.ParserParams;
import com.geovis.model.DemParseResult;
import com.geovis.service.DemParseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 数字地形数据
 */
@RestController
@RequestMapping("api/v1")
@Api(tags = "元信息")
public class DemParseContrlloer {

    @Autowired(required = false)
    private DemParseService demParseService;

    @Lazy
    @Autowired(required = false)
    private DefinedMetaData metaData;

    @ApiOperation(value = "解析接口")
    @PostMapping("/dem/parser")
    public List<DemParseResult> demParse(@Valid @RequestBody ParserParams params) {
        return demParseService.demParse(params);
    }

    @ApiOperation(value = "元数据接口")
    @GetMapping("/metadata")
    public List<Map<String, String>> getMetaData() {
        return metaData.getResultList();
    }


}
