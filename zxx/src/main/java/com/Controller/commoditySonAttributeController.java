package com.Controller;

import com.Pojo.commoditySonAttribute;
import com.service.impl.commoditySonAttributeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/commoditySonAttributeController")
public class commoditySonAttributeController {
    @Resource
    commoditySonAttributeServiceImpl commoditySonAttributeService;

    @RequestMapping("/findAllCommoditySonAttributeByid/{id}")
    @ResponseBody
    public List<commoditySonAttribute> findAllCommoditySonAttributeByid(@PathVariable String id) throws IOException {
        List<commoditySonAttribute> list = new ArrayList<>();
        System.out.println(id);
        list = commoditySonAttributeService.findAllCommoditySonAttributeByid(id);
        System.out.println(list.toString());
        return list;
    }
}
