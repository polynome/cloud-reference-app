package co.polynome.web;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/")
@Profile("web")
public class RootController {
    @RequestMapping(method = RequestMethod.GET)
    public String root() {
        return "redirect:/rappers";
    }
}
