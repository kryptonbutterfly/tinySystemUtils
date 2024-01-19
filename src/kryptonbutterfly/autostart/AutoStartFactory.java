package kryptonbutterfly.autostart;

import java.io.File;

import kryptonbutterfly.io.FileSystemUtils;
import kryptonbutterfly.monads.opt.Opt;
import kryptonbutterfly.os.Platforms;

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
		return switch (Platforms.getOS())
		{
			case WINDOWS -> Opt.of(new AutoStartWin(programName, launchFileName, runningJar));
			case LINUX -> Opt.of(new AutoStartGnome(programName, launchFileName, runningJar));
			default -> Opt.empty();
		};
	}
	
	public static Opt<AutoStart> get(String programName, File launchFile, File runningJar)
	{
		return switch (Platforms.getOS())
		{
			case WINDOWS -> Opt.of(new AutoStartWin(programName, launchFile, runningJar));
			case LINUX -> Opt.of(new AutoStartGnome(programName, launchFile, runningJar));
			default -> Opt.empty();
		};
	}
}