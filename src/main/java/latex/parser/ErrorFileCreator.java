package latex.parser;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
class ErrorFileCreator implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ErrorFileCreator.class);

	File file = new File("error.png");

	@Override
	public void run(String... args) throws IOException {
		if (!file.exists()) {
			logger.info("Creating error file error.png");
			createErrorFile();
			logger.info("Error file error.png created");
		} else {
			logger.info("Error file error.png found");
		}
	}

	private void createErrorFile() throws IOException {
		InputStream in = new ClassPathResource("error.png").getInputStream();
		OutputStream out = new FileOutputStream(file);
		FileCopyUtils.copy(in, out);
	}

}
