package kryptonbutterfly.shortcut;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;

import kryptonbutterfly.monads.opt.Opt;

public final class FreedesktopOrgShortcut
{
	public final File shortcutFile;
	
	public Opt<String>				shebang					= Opt.of("#!/usr/bin/env xdg-open\n");
	public Opt<String>				version					= Opt.empty();
	public Opt<String>				type					= Opt.empty();
	public Opt<String>				terminal				= Opt.empty();
	public Opt<String>				exec					= Opt.empty();
	public Opt<String>				icon					= Opt.empty();
	public Opt<String>				hidden					= Opt.empty();
	public Opt<String>				noDisplay				= Opt.empty();
	public Opt<LocalizableArgument>	name					= Opt.empty();
	public Opt<LocalizableArgument>	genericName				= Opt.empty();
	public Opt<LocalizableArgument>	comment					= Opt.empty();
	public Opt<String>				keywords				= Opt.empty();
	public Opt<String>				categories				= Opt.empty();
	public Opt<String>				xGnomeAutoStartEnabled	= Opt.empty();
	
	public FreedesktopOrgShortcut shebang(String shebang)
	{
		this.shebang = Opt.of(shebang);
		return this;
	}
	
	public FreedesktopOrgShortcut version(String version)
	{
		this.version = Opt.of(version);
		return this;
	}
	
	public FreedesktopOrgShortcut type(String type)
	{
		this.type = Opt.of(type);
		return this;
	}
	
	public FreedesktopOrgShortcut terminal(String terminal)
	{
		this.terminal = Opt.of(terminal);
		return this;
	}
	
	public FreedesktopOrgShortcut exec(String exec)
	{
		this.exec = Opt.of(exec);
		return this;
	}
	
	public FreedesktopOrgShortcut icon(String icon)
	{
		this.icon = Opt.of(icon);
		return this;
	}
	
	public FreedesktopOrgShortcut hidden(String hidden)
	{
		this.hidden = Opt.of(hidden);
		return this;
	}
	
	public FreedesktopOrgShortcut noDisplay(String noDisplay)
	{
		this.noDisplay = Opt.of(noDisplay);
		return this;
	}
	
	public FreedesktopOrgShortcut name(LocalizableArgument name)
	{
		this.name = Opt.of(name);
		return this;
	}
	
	public FreedesktopOrgShortcut genericName(LocalizableArgument genericName)
	{
		this.genericName = Opt.of(genericName);
		return this;
	}
	
	public FreedesktopOrgShortcut comment(LocalizableArgument comment)
	{
		this.comment = Opt.of(comment);
		return this;
	}
	
	public FreedesktopOrgShortcut keywords(String keywords)
	{
		this.keywords = Opt.of(keywords);
		return this;
	}
	
	public FreedesktopOrgShortcut categories(String categories)
	{
		this.categories = Opt.of(categories);
		return this;
	}
	
	public FreedesktopOrgShortcut xGnomeAutoStartEnabled(String xGnomeAutoStartEnabled)
	{
		this.xGnomeAutoStartEnabled = Opt.of(xGnomeAutoStartEnabled);
		return this;
	}
	
	public FreedesktopOrgShortcut(File shortcutFile)
	{
		this.shortcutFile = shortcutFile;
	}
	
	public void createShortcut() throws IOException
	{
		try (FileOutputStream fos = new FileOutputStream(shortcutFile))
		{
			try (OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF8"))
			{
				write(writer, shebang);
				write(writer, Opt.of("[Desktop Entry]"));
				write(writer, "Version", version);
				write(writer, "Type", type);
				write(writer, "Terminal", terminal);
				write(writer, "Exec", exec);
				write(writer, "Icon", icon);
				write(writer, "Hidden", hidden);
				write(writer, "NoDisplay", noDisplay);
				writeLocalizable(writer, "Name", name);
				writeLocalizable(writer, "GenericName", genericName);
				writeLocalizable(writer, "Comment", comment);
				write(writer, "Keywords", keywords);
				write(writer, "Categories", categories);
				write(writer, "X-GNOME-Autostart-enabled", xGnomeAutoStartEnabled);
			}
		}
	}
	
	private void write(OutputStreamWriter writer, Opt<String> arg) throws IOException
	{
		arg.if_Throws(argument -> {
			writer.write(argument);
			writer.write("\n");
		});
	}
	
	private void write(OutputStreamWriter writer, String argName, Opt<String> arg) throws IOException
	{
		arg.if_Throws(argument -> {
			writer.write(argName);
			writer.write("=");
			writer.write(argument);
			writer.write("\n");
		});
	}
	
	private void writeLocalizable(OutputStreamWriter writer, String argName, Opt<LocalizableArgument> arg)
		throws IOException
	{
		arg.if_Throws(argument -> argument.buildString(writer, argName));
	}
	
	public static final class LocalizableArgument
	{
		private final String					unlocalized;
		private final HashMap<String, String>	localizations	= new HashMap<>();
		
		public LocalizableArgument(String unlocalized)
		{
			this.unlocalized = unlocalized;
		}
		
		public void setLocalization(String languageCode, String localization)
		{
			this.localizations.put(languageCode, localization);
		}
		
		private void buildString(OutputStreamWriter writer, String argumentName) throws IOException
		{
			writer.write(argumentName);
			writer.write("=");
			writer.write(unlocalized);
			writer.write("\n");
			
			Iterator<Entry<String, String>> iterator = localizations.entrySet()
				.stream()
				.filter(Objects::nonNull)
				.filter(e -> Objects.nonNull(e.getKey()))
				.filter(e -> Objects.nonNull(e.getValue()))
				.iterator();
			while (iterator.hasNext())
			{
				Entry<String, String> entry = iterator.next();
				writer.write(argumentName);
				writer.write("[");
				writer.write(entry.getKey());
				writer.write("]=");
				writer.write(entry.getValue());
				writer.write("\n");
			}
		}
	}
}