package com.solid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//SRP
//Only one reason to change, the class only has to have an only responsibility
public class SingleResponsibilityPrinciple {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Journal journal = new Journal();
		journal.addEntry("I ate pizza");
		journal.addEntry("I wrote a book");
		System.out.println(journal.toString());

		Persistence persistance = new Persistence();
		String filename = "C:\\temp\\journal.txt";
		persistance.save(journal, filename, true);
		Runtime.getRuntime().exec("notepad.exe " + filename);

	}
}

class Journal {
	private final List<String> entries = new ArrayList<String>();
	private static int count = 0;

	public void addEntry(String text) {
		entries.add("" + (++count) + ":" + text);
	}

	public void removeEntry(int index) {
		entries.remove(index);
	}

	@Override
	public String toString() {
		return String.join(System.lineSeparator(), entries);
	}

	/*
	 * breaks the srp public void save(String filename) throws FileNotFoundException
	 * { try(PrintStream out = new PrintStream(filename)){ out.println(toString());
	 * } }
	 *
	 * public void loadFromFile(String filename) {} public void loadFromUrl(URL url)
	 * {}
	 */
}

/*
 * correct way
 */
class Persistence {
	public void save(Journal journal, String filename, boolean overwrite) throws FileNotFoundException {
		if (overwrite || new File(filename).exists()) {
			try (PrintStream out = new PrintStream(filename)) {
				out.println(toString());
			}
		}
	}

	public Journal loadFromFile(String filename) {
		return null;
	}

	public Journal loadFromUrl(URL url) {
		return null;
	}

}
