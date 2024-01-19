module kryptonbutterfly.System
{
	exports kryptonbutterfly.autostart;
	exports kryptonbutterfly.os;
	exports kryptonbutterfly.io;
	exports kryptonbutterfly.shortcut;
	exports kryptonbutterfly;
	
	requires transitive kryptonbutterfly.Functional;
	requires kryptonbutterfly.Collections;
	requires kryptonbutterfly.Concurrent;
	requires kryptonbutterfly.Monads;
}