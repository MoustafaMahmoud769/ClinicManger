package gui;

import java.io.InputStream;

public class LoadImage {

	public static InputStream load(String path) {
		System.out.println(path);
		InputStream input = LoadImage.class.getResourceAsStream(path);
		if (input == null) {
			input = LoadImage.class.getResourceAsStream("/" + path);
		}
		return input;
	}

}
