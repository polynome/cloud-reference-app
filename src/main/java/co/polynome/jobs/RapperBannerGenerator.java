package co.polynome.jobs;

import co.polynome.domain.Rapper;
import co.polynome.service.RapperRepository;
import com.github.lalyos.jfiglet.FigletFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RapperBannerGenerator {
    @Autowired
    public RapperRepository rapperRepository;

    public void run(Long primaryKey) throws IOException {
        final Rapper rapper = rapperRepository.findOne(primaryKey);
        rapper.setBanner(FigletFont.convertOneLine(rapper.getName()));
        rapperRepository.save(rapper);
    }
}
