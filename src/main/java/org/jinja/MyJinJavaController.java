package org.jinja;

import com.google.common.base.Charsets;
import com.hubspot.jinjava.Jinjava;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
public class MyJinJavaController {
    @GetMapping
    public String renderFileName(@RequestParam String fileName) throws Exception {
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(JinjaAutoConfiguration.class);
//        JinjaView jinjaView = ctx.getBean(JinjaView.class);
        JinjaView jinjaView1 = new JinjaView();
        jinjaView1.setEngine(new Jinjava());
        return jinjaView1.renderMergedTemplateModel(fileName, System.err);
    }
}
