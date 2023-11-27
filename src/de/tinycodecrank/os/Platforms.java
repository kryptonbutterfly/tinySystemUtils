package de.tinycodecrank.os;

import static java.io.File.*;

import java.io.File;
import java.util.Locale;

import de.tinycodecrank.baseDir.KnownFolder;
import de.tinycodecrank.baseDir.XdgBaseDir;
import de.tinycodecrank.os.BaseDirectory.BaseDirFactory;

public final class Platforms
{
	private Platforms()
	{}
	
	private static String appData = getAppDataForOs(OS.currentOS);
	
	private static final String getAppDataForOs(OS os)
	{
		return switch (os)
		{
		case MAC_OS -> System.getProperty("user.home") + separator + "Library" + separator + "Application Support";
		case WINDOWS -> System.getenv("APPDATA");
		case LINUX -> System.getProperty("user.home");
		default -> System.getProperty("user.dir");
		};
	}
	
	public static OS getOS()
	{
		return OS.currentOS;
	}
	
	public static String getOsName()
	{
		return OS.osName;
	}
	
	public static String getAppDataPath()
	{
		return appData;
	}
	
	public static File getAppDataFile()
	{
		return new File(getAppDataPath());
	}
	
	public static File getDesktop()
	{
		switch (OS.currentOS)
		{
		case MAC_OS:
		case WINDOWS:
		case LINUX:
			return new File(System.getProperty("user.home") + separator + "Desktop");
		case OTHER:
		default:
			return new File(System.getProperty("user.dir") + separator + "Desktop");
		}
	}
	
	public static BaseDirectory baseDir(String organizationName, String applicationName)
	{
		return OS.currentOS.baseDirFactory.init(organizationName, applicationName);
	}
	
	public static enum OS
	{
		WINDOWS(KnownFolder::new),
		LINUX(XdgBaseDir::new),
		MAC_OS,
		OTHER;
		
		private final BaseDirFactory baseDirFactory;
		
		OS()
		{
			this(null);
		}
		
		OS(BaseDirFactory baseDirFactory)
		{
			this.baseDirFactory = baseDirFactory;
		}
		
		/**
		 * @throws UnsupportedOperationException
		 *             If The implementation for the current OS is missing!
		 */
		public BaseDirFactory baseDir() throws UnsupportedOperationException
		{
			if (baseDirFactory != null)
				return baseDirFactory;
			throw new UnsupportedOperationException(
				"No BaseDir implementation is available for the current OS (%s)".formatted(
					System.getProperty("os.name")));
		}
		
		private static final String	osName		= System.getProperty("os.name", "other");
		private static final OS		currentOS	= getCurrent();
		
		private static final OS getCurrent()
		{
			String os = osName.toLowerCase(Locale.ENGLISH);
			if (os.contains("mac") || os.contains("darwin"))
			{
				return OS.MAC_OS;
			}
			else if (os.contains("win"))
			{
				return OS.WINDOWS;
			}
			else if (os.contains("nux"))
			{
				return OS.LINUX;
			}
			else
			{
				return OS.OTHER;
			}
		}
	}
}