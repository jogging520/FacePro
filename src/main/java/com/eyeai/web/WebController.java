package com.eyeai.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


@Controller
public class WebController {

    @RequestMapping(value="/hello",method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }
	@ApiOperation(value="图猫首页", notes="返回index页面")
    @RequestMapping(value="/",method = RequestMethod.GET )
    public String index(ModelMap map) {
        return "index";
    }
	@ApiOperation(value="登录页面", notes="返回login页面")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
	@ApiOperation(value="上传单个文件页面", notes="返回上传单个文件页面")
    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String upload() {
        return "upload";
    }
	@ApiOperation(value="上传多个个文件页面", notes="返回上传多个文件页面")
    @RequestMapping(value = "/uploadMuti",method = RequestMethod.GET)
    public String uploadMuti() {
        return "uploadMuti";
    }
}