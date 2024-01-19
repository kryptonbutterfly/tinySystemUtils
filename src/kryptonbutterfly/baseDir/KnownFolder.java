package kryptonbutterfly.baseDir;

import java.io.File;

import kryptonbutterfly.FileUtils;
import kryptonbutterfly.monads.opt.Opt;
import kryptonbutterfly.os.BaseDirectory;

public final class KnownFolder extends BaseDirectory
{
	public KnownFolder(String organizationName, String applicationName)
	{
		super(organizationName, applicationName);
	}
	
	@Override
	protected String preProcess(String fileName)
	{
		return fileName.toLowerCase();
	}
	
	private static final String	LOCAL_APP_DATA	= "LOCALAPPDATA";
	private static final String	TEMP			= "TEMP";
	private static final String	APPDATA			= "APPDATA";
	
	private static final String	APP_DATA_FOLDER	= "AppData";
	private static final String	ROAMING_FOLDER	= "Roaming";
	private static final String	LOCAL_FOLDER	= "Local";
	
	@Override
	public File dataHome()
	{
		final var dataHomeDir = Opt.of(System.getenv(LOCAL_APP_DATA))
			.filter(v -> !v.isBlank())
			.map(File::new)
			.get(() -> FileUtils.newFile(userHome(), APP_DATA_FOLDER, LOCAL_FOLDER));
		return construct(dataHomeDir, DATA);
	}
	
	@Override
	public File configHome()
	{
		final var configHomeDir = Opt.of(System.getenv(APPDATA))
			.filter(v -> !v.isBlank())
			.map(File::new)
			.get(() -> FileUtils.newFile(userHome(), APP_DATA_FOLDER, ROAMING_FOLDER));
		return construct(configHomeDir, CONFIG);
	}
	
	@Override
	public File stateHome()
	{
		final var stateHomeDir = Opt.of(System.getenv(APPDATA))
			.filter(v -> !v.isBlank())
			.map(File::new)
			.get(() -> FileUtils.newFile(userHome(), APP_DATA_FOLDER, ROAMING_FOLDER));
		return construct(stateHomeDir, STATE);
	}
	
	@Override
	public File cacheHome()
	{
		final var cacheHomeDir = Opt.of(System.getenv(TEMP))
			.filter(v -> !v.isBlank())
			.map(File::new)
			.get(() -> FileUtils.newFile(userHome(), APP_DATA_FOLDER, LOCAL_FOLDER, "Temp"));
		return construct(cacheHomeDir, "cache");
	}
}