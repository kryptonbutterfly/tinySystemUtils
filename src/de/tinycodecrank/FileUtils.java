package de.tinycodecrank;

import java.io.File;

public final class FileUtils
{
	public static File newFile(File f, String... components)
	{
		var result = f;
		for (var c : components)
			result = new File(result, c);
		return result;
	}
	
	public static File newFile(String f, String... components)
	{
		return newFile(new File(f), components);
	}
}