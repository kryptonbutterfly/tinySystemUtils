package de.tinycodeccrank.autostart;

import java.io.File;

import de.tinycodeccrank.io.FileSystemUtils;
import de.tinycodeccrank.os.Platforms;
import de.tinycodecrank.monads.Opt;

public class AutoStartFactory
{
	public static Opt<AutoStart> get(String programName, String launchFileName)
	{
		Opt<File> runningJar = FileSystemUtils.getProgramMainJar();
		return runningJar.flatmap(f -> get(programName, launchFileName, f));
	}
	
	public static Opt<AutoStart> get(String programName, File launchFile)
	{
		Opt<File> runningJar = FileSystemUtils.getProgramMainJar();
		return runningJar.flatmap(f -> get(programName, launchFile, f));
	}
	
	public static Opt<AutoStart> get(String programName, String launchFileName, File runningJar)
	{
		switch (Platforms.getOS())
		{
			case WINDOWS:
				return Opt.of(new AutoStartWin(programName, launchFileName, runningJar));
			case LINUX:
				return Opt.of(new AutoStartGnome(programName, launchFileName, runningJar));
			default:
				return Opt.empty();
		}
	}
	
	public static Opt<AutoStart> get(String programName, File launchFile, File runningJar)
	{
		switch (Platforms.getOS())
		{
			case WINDOWS:
				return Opt.of(new AutoStartWin(programName, launchFile, runningJar));
			case LINUX:
				return Opt.of(new AutoStartGnome(programName, launchFile, runningJar));
			default:
				return Opt.empty();
		}
	}
}