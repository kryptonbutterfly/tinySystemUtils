package kryptonbutterfly.os;

import java.io.File;
import java.util.Objects;

import kryptonbutterfly.FileUtils;
import kryptonbutterfly.baseDir.KnownFolder;
import kryptonbutterfly.baseDir.XdgBaseDir;

public sealed abstract class BaseDirectory permits XdgBaseDir, KnownFolder
{
	protected static final String	DATA	= "data";
	protected static final String	CONFIG	= "config";
	protected static final String	STATE	= "state";
	protected static final String	CACHE	= "cache";
	
	private final String	organizationName;
	private final String	applicationName;
	
	public BaseDirectory(String organizationName, String applicationName)
	{
		Objects.requireNonNull(organizationName);
		Objects.requireNonNull(applicationName);
		
		this.organizationName	= preProcess(organizationName);
		this.applicationName	= preProcess(applicationName);
	}
	
	protected String preProcess(String fileName)
	{
		return fileName;
	}
	
	/**
	 * @return The current users home directory.
	 */
	public final File userHome()
	{
		return new File(System.getProperty("user.home"));
	}
	
	public abstract File dataHome();
	
	public abstract File configHome();
	
	public abstract File stateHome();
	
	public abstract File cacheHome();
	
	@FunctionalInterface
	public static interface BaseDirFactory
	{
		public BaseDirectory init(String organizationName, String applicationName);
	}
	
	protected File construct(File base, String specifier)
	{
		return FileUtils.newFile(base, organizationName, applicationName, specifier);
	}
}