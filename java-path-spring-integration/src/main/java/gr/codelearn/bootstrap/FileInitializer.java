package gr.codelearn.bootstrap;

import gr.codelearn.domain.Directory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@AllArgsConstructor
@Slf4j
public class FileInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("Attempting to initialize application directories.");
        Path fileDirectory = Path.of(Directory.FILE_DIRECTORY.getPath());
        if (!Files.exists(fileDirectory)) {
            log.info("Initialization of directories is needed.");
            try {
                Files.createDirectory(fileDirectory);
            } catch (IOException e) {
                log.error("Directory '{}' cannot be created.", fileDirectory);
            }
            log.info("Initialization of directories has ended.");
        } else {
            log.info("Initialization of directories is not needed.");
        }
    }

}
