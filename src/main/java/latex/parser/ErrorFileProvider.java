package latex.parser;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Component;

@Component
class ErrorFileProvider {

	File file = new File("error.png");

	File getErrorFile() throws FileNotFoundException {
		if (file.exists()) {
			return file;
		} else {
			throw new FileNotFoundException("File error.png does not exist.");
		}
	}

}
