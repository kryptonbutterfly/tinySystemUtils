module de.tinycodecrank.System
{
	exports de.tinycodecrank.autostart;
	exports de.tinycodecrank.os;
	exports de.tinycodecrank.io;
	exports de.tinycodecrank.shortcut;
	exports de.tinycodecrank;
	
	requires de.tinycodecrank.Collections;
	requires de.tinycodecrank.Concurrent;
	requires transitive de.tinycodecrank.Functional;
	requires de.tinycodecrank.Monads;
}