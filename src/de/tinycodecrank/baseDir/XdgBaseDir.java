package de.tinycodecrank.baseDir;

import java.io.File;

import de.tinycodecrank.FileUtils;
import de.tinycodecrank.monads.opt.Opt;
import de.tinycodecrank.os.BaseDirectory;

/**
 * @see <a href=
 *      "https://specifications.freedesktop.org/basedir-spec/latest/index.html">
 *      XDG Base Directory Specification </a>
 */
public final class XdgBaseDir extends BaseDirectory
{
	public XdgBaseDir(String organizationName, String applicationName)
	{
		super(organizationName, applicationName);
	}
	
	@Override
	protected String preProcess(String fileName)
	{
		return super.preProcess(fileName.replace(' ', '_'));
	}
	
	private static final String	XDG_DATA_HOME	= "XDG_DATA_HOME";
	private static final String	XDG_CONFIG_HOME	= "XDG_CONFIG_HOME";
	private static final String	XDG_STATE_HOME	= "XDG_STATE_HOME";
	private static final String	XDG_CACHE_HOME	= "XDG_CACHE_HOME";
	
	/**
	 * <p>
	 * <b>Environment variable:</b> ${@value XdgBaseDir#XDG_DATA_HOME}
	 * </p>
	 * 
	 * @see <a href=
	 *      "https://specifications.freedesktop.org/basedir-spec/latest/ar01s03.html">
	 *      XDG Base Directory Specification</a>
	 * @return The base directory for user-specific data files.
	 */
	@Override
	public File dataHome()
	{
		final var dataHomeDir = Opt.of(System.getenv(XDG_DATA_HOME))
			.filter(v -> !v.isBlank())
			.map(File::new)
			.get(() -> FileUtils.newFile(userHome(), ".local", "share"));
		
		return construct(dataHomeDir, DATA);
	}
	
	/**
	 * <p>
	 * <b>Environment variable:</b> ${@value XdgBaseDir#XDG_CONFIG_HOME}
	 * </p>
	 * 
	 * @see <a href=
	 *      "https://specifications.freedesktop.org/basedir-spec/latest/ar01s03.html">
	 *      XDG Base Directory Specification</a>
	 * @return The base directory for configuration files.
	 */
	@Override
	public File configHome()
	{
		final var configHomeDir = Opt.of(System.getenv(XDG_CONFIG_HOME))
			.filter(v -> !v.isBlank())
			.map(File::new)
			.get(() -> new File(userHome(), ".config"));
		return construct(configHomeDir, CONFIG);
	}
	
	/**
	 * State files are files like logs, history, recently used files, …
	 * 
	 * <p>
	 * <b>Environment variable:</b> ${@value XdgBaseDir#XDG_STATE_HOME}
	 * </p>
	 * 
	 * @see <a href=
	 *      "https://specifications.freedesktop.org/basedir-spec/latest/ar01s03.html">
	 *      XDG Base Directory Specification</a>
	 * 		
	 * @return The base directory for state files.
	 */
	@Override
	public File stateHome()
	{
		final var stateHomeDir = Opt.of(System.getenv(XDG_STATE_HOME))
			.filter(v -> !v.isBlank())
			.map(File::new)
			.get(() -> FileUtils.newFile(userHome(), ".local", "state"));
		return construct(stateHomeDir, STATE);
	}
	
	/**
	 * <p>
	 * <b>Environment variable:</b> ${@value XdgBaseDir#XDG_CACHE_HOME}
	 * </p>
	 * 
	 * @see <a href=
	 *      "https://specifications.freedesktop.org/basedir-spec/latest/ar01s03.html">
	 *      XDG Base Directory Specification</a>
	 * 		
	 * @return The base directory for non-essential data files.
	 */
	@Override
	public File cacheHome()
	{
		final var cacheHomeDir = Opt.of(System.getenv(XDG_CACHE_HOME))
			.filter(v -> !v.isBlank())
			.map(File::new)
			.get(() -> new File(userHome(), ".cache"));
		return construct(cacheHomeDir, CACHE);
	}
}