package me.staartvin.plugins.pluginlibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import me.staartvin.plugins.pluginlibrary.hooks.FactionsHook;
import me.staartvin.plugins.pluginlibrary.hooks.LibraryHook;
import me.staartvin.plugins.pluginlibrary.hooks.customstats.CustomStatsManager;
import me.staartvin.plugins.pluginlibrary.hooks.factions.Faction;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Rel;

/**
 * Main class of PluginLibrary
 * <p>
 * Date created: 14:06:30 12 aug. 2015
 * 
 * @author Staartvin
 * 
 */
public class PluginLibrary extends JavaPlugin {

	private final List<Library> loadedLibraries = new ArrayList<Library>();
	private CustomStatsManager customStatsManager;

	@Override
	public void onEnable() {

		loadedLibraries.clear();

		logMessage(ChatColor.GOLD + "***== Loading libraries ==***");
		logMessage(ChatColor.GOLD + "***== Loaded " + ChatColor.WHITE
				+ loadLibraries() + ChatColor.GOLD + " libraries! ==***");

		if (this.isLibraryLoaded(Library.STATS)) {
			// Register custom stats so that Stats has special mobs and food eaten requirement.
			setCustomStatsManager(new CustomStatsManager(this));
			this.getCustomStatsManager().registerCustomStats();
		}

		logMessage(ChatColor.GREEN
				+ "*** Ready for plugins to send/retrieve data. ***");

		for (Library l : loadedLibraries) {
			System.out.println("Library loaded: " + l.getPluginName());
		}
		
		FactionsHook hook = (FactionsHook) getLibrary(Library.FACTIONS);
		Faction fac = hook.getFactionByName("testFaction");
		Faction fac2 = hook.getFactionByUUID(UUID.fromString("c5f39a1d-3786-46a7-8953-d4efabf8880d"));
		
		System.out.println("Fac: " + fac.getDescription());
		
		for (Entry<String, Rel> i: fac2.getRelationWishes().entrySet()) {
			System.out.println("Fac 2: " + i.getKey() + " " + i.getValue());
		}
		
		
		

		logMessage(this.getDescription().getFullName() + " is now enabled!");
	}

	@Override
	public void onDisable() {

		loadedLibraries.clear();

		logMessage(this.getDescription().getFullName() + " is now disabled!");
	}

	/**
	 * Load all libraries, this will be done automatically by the plugin.
	 * 
	 * @return how many libraries were loaded.
	 */
	public int loadLibraries() {
		int count = 0;

		for (Library l : Library.values()) {
			if (l.getHook().isAvailable()) {

				// One more library loaded.
				if (l.getHook().hook()) {
					loadedLibraries.add(l);
					count++;
				}

			}
		}

		return count;
	}

	public void logMessage(String message) {
		// This makes sure it can support colours.
		this.getServer().getConsoleSender()
				.sendMessage(ChatColor.GRAY + "[PluginLibrary] " + message);
	}

	/**
	 * Get a list of all loaded libraries. <br>
	 * This list is unmodifiable and when you try to alter it, it will give an
	 * {@link UnsupportedOperationException}.
	 * 
	 * @return a list of loaded libraries.
	 */
	public List<Library> getLoadedLibraries() {
		return Collections.unmodifiableList(loadedLibraries);
	}

	/**
	 * Gets the library for a specific plugin. <br>
	 * Will throw a {@link IllegalArgumentException} when there is no library
	 * with the given name.
	 * 
	 * @param pluginName Name of the plugin. Case-insensitive!
	 * @return {@link me.staartvin.plugins.pluginlibrary.LibraryHook} class or
	 *         an error.
	 */
	public static LibraryHook getLibrary(String pluginName) {
		return Library.getEnum(pluginName).getHook();
	}

	/**
	 * @see #getLibrary(String)
	 * <br>
	 *      Returns the same as {@link #getLibrary(String)}.
	 * @param lib Library enum to get the library hook for.
	 * @return {@link me.staartvin.plugins.pluginlibrary.LibraryHook} class or
	 *         an error.
	 */
	public static LibraryHook getLibrary(Library lib) {
		return lib.getHook();
	}

	/**
	 * Checks to see whether the library is loaded and thus ready for use.
	 * 
	 * @param lib Library to check.
	 * @return true if the library is loaded; false otherwise.
	 */
	public boolean isLibraryLoaded(Library lib) {
		return loadedLibraries.contains(lib);
	}

	public CustomStatsManager getCustomStatsManager() {
		return customStatsManager;
	}

	public void setCustomStatsManager(CustomStatsManager customStatsManager) {
		this.customStatsManager = customStatsManager;
	}
}
