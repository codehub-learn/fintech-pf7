package gr.codelearn.bootstrap;

import gr.codelearn.base.AbstractLogEntity;
import gr.codelearn.domain.Directory;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@AllArgsConstructor
public class FileInitializer extends AbstractLogEntity implements CommandLineRunner {

    @Override
    public void run(String... args) {
        logger.info("Attempting to initialize application directories.");
        Path fileDirectory = Path.of(Directory.FILE_DIRECTORY.getPath());
        if (!Files.exists(fileDirectory)) {
            logger.info("Initialization of directories is needed.");
            try {
                Files.createDirectory(fileDirectory);
            } catch (IOException e) {
                logger.error("Directory '{}' cannot be created.", fileDirectory);
            }
            logger.info("Initialization of directories has ended.");
        } else {
            logger.info("Initialization of directories is not needed.");
        }
    }

}
