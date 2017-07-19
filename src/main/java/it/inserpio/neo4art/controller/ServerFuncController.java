package it.inserpio.neo4art.controller;

import it.inserpio.neo4art.service.ServerFuncService;
import it.inserpio.neo4art.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by lsy on 2017/7/12.
 */
@Controller
@RequestMapping("/serverFunc")
public class ServerFuncController {
    @Autowired
    ServerFuncService serverFuncService;

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/graph",method = RequestMethod.GET,produces="application/json")
    public Map<String,Object> graph(@RequestParam(value = "limit",required = false) Integer limit){
        return serverFuncService.graph(limit == null ? 100 : limit);
    }
}
