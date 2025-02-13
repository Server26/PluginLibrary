package me.staartvin.utils.pluginlibrary.statz;

import me.staartvin.utils.pluginlibrary.statz.hooks.*;
import org.bukkit.Bukkit;

import java.util.Optional;

/**
 * This class holds all libraries PluginLibrary has.
 * <p>
 * Date created: 14:12:35 12 aug. 2015
 *
 * @author Staartvin
 */
public enum Library {

    STATZ("Statz", StatzHook.class, "Staartvin"),
    VAULT("Vault", VaultHook.class, "Kainzo");

    private final String internalPluginName;
    private final String authorName;
    private final Class<? extends LibraryHook> libraryClass;
    private LibraryHook hook;
    private String humanPluginName;
    private String mainClass;

    Library(String pluginName, String humanPluginName, Class<? extends LibraryHook> libraryClass, String authorName) {
        this.internalPluginName = pluginName;
        this.humanPluginName = humanPluginName;
        this.libraryClass = libraryClass;
        this.authorName = authorName;
    }

    Library(String pluginName, Class<? extends LibraryHook> libraryClass, String authorName) {
        this.internalPluginName = pluginName;
        this.libraryClass = libraryClass;
        this.authorName = authorName;
    }

    Library(String pluginName, Class<? extends LibraryHook> libraryClass, String authorName, String mainClass) {
        this.internalPluginName = pluginName;
        this.libraryClass = libraryClass;
        this.authorName = authorName;
        this.mainClass = mainClass;
    }

    Library(String pluginName, String humanPluginName, Class<? extends LibraryHook> libraryClass, String authorName,
            String mainClass) {
        this.internalPluginName = pluginName;
        this.humanPluginName = humanPluginName;
        this.libraryClass = libraryClass;
        this.authorName = authorName;
        this.mainClass = mainClass;
    }

    /**
     * Get a library programmatically. This method is the same as valueOf(), but is case-insensitive.
     *
     * @param value name of the library
     *
     * @return the Library object.
     *
     * @throws IllegalArgumentException When no library with the given name was found.
     */
    public static Library getEnum(String value) throws IllegalArgumentException {
        for (Library e : Library.values()) {
            if (e.getInternalPluginName().equalsIgnoreCase(value))
                return e;
        }

        throw new IllegalArgumentException("There is no library called '" + value + "'!");
    }

    public String getInternalPluginName() {
        return internalPluginName;
    }

    public Optional<LibraryHook> getHook() {

        // Check if hook is not initialized yet.
        if (hook == null) {
            try {
                hook = libraryClass.getDeclaredConstructor().newInstance();
            } catch (Exception | NoClassDefFoundError exception) {
                Bukkit.getConsoleSender().sendMessage("Could not grab hook of " + this.getHumanPluginName());
                exception.printStackTrace();
                return Optional.empty();
            }
        }

        return Optional.of(hook);
    }

    public String getAuthor() {
        return authorName;
    }

    public String getHumanPluginName() {
        if (humanPluginName == null) {
            return internalPluginName;
        }

        return humanPluginName;
    }

    /**
     * The main class field as described by the description file in the JAR.
     * It is used to distinguish plugins that have the same name, but are of the different authors.
     * @return string containing path to main class.
     */
    public String getMainClass() {
        return mainClass;
    }

    public boolean hasMainClass() {
        return mainClass != null;
    }


    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * Check if this plugin is installed on the server.
     *
     * @return true if the plugin is enabled, false otherwise.
     */

}
