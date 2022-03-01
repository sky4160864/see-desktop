package org.uly.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * description
 *
 * @author C.H 2022/02/28 15:18
 */
@Controller
public class IndexController {
    @GetMapping
    public String index() {
        return "/index";
    }
}
