package com.martintonts.config;

import com.martintonts.model.Sector;
import com.martintonts.repository.SectorsRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitialImportConfiguration {
    private final SectorsRepository sectorsRepository;

    @Value("${import.html-file-location}")
    private Resource htmlFile;

    /**
     * Imports sectors from provided html file.
     * @throws IOException
     */
    @PostConstruct
    public void initialImport() throws IOException {
        log.info("Starting initial sectors import");

        Reader reader = new InputStreamReader(htmlFile.getInputStream(), StandardCharsets.UTF_8);
        String htmlContent = FileCopyUtils.copyToString(reader);

        Pattern optionsPattern = Pattern.compile("<option.*</option>");
        Matcher optionsMatcher = optionsPattern.matcher(htmlContent);

        AtomicInteger index = new AtomicInteger();
        List<Sector> sectors = new ArrayList<>();
        optionsMatcher.results().forEach(optionsMatch -> {
            String s = optionsMatch.group();
            String value = s.substring(s.indexOf("value=\"") + 7, s.indexOf("\">"));
            String text = s.substring(s.indexOf(">") + 1,s.indexOf("<", s.indexOf(">")));
            int spacesCount = StringUtils.countOccurrencesOf(text, "&nbsp;");
            Sector sector = Sector.builder()
                    .val(value)
                    .name(
                            text.replaceAll("&nbsp;", "").replaceAll("&amp;", "&")
                    )
                    .level(spacesCount/4)
                    .pos(index.getAndAdd(1))
                    .build();
            sectors.add(sector);
        });
        sectorsRepository.saveAll(sectors);

        log.info("done");
    }
}
