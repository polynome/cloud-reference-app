package co.polynome.web;

import co.polynome.domain.Rapper;
import co.polynome.jobs.RapperBannerGenerator;
import co.polynome.service.RapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequestMapping(value="/rappers")
public class RapperController {
    @Autowired
    private RapperRepository repository;

    @Autowired
    private RapperBannerGenerator bannerGenerator;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public String index(Model model) {
        final Page<Rapper> rappers = repository.findAll(new PageRequest(0, 10));
        model.addAttribute("rappers", rappers);
        model.addAttribute("rapper", new Rapper());

        StringBuilder result = new StringBuilder("<html><body>");
        result.append("<ul>");
        for (final Rapper r : rappers) {
            result.append("<li>");
            result.append(r.getName());
            result.append("</li>");
        }
        result.append("</ul>");
        result.append("</body></html>");


        //return result.toString();
        return "index";
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public String create(@ModelAttribute Rapper rapper, Model model) throws IOException {
        final Rapper saved = repository.save(rapper);

        // TODO make async / worker
        bannerGenerator.run(saved.getId());

        return "redirect:/rappers";
    }
}
