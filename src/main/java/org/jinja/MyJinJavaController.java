package org.jinja;

import com.google.common.base.Charsets;
import com.hubspot.jinjava.Jinjava;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
public class MyJinJavaController {
    @Autowired
    private JinjaView jinjaView;
    @GetMapping
    public String renderFileName(@RequestParam String fileName) throws Exception {
        return jinjaView.renderMergedTemplateModel(fileName, System.err);
    }
}
