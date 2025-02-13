package me.staartvin.utils.pluginlibrary.statz.hooks;

import me.armar.plugins.autorank.Autorank;
import me.armar.plugins.autorank.pathbuilder.requirement.AbstractRequirement;
import me.armar.plugins.autorank.pathbuilder.result.AbstractResult;
import me.staartvin.utils.pluginlibrary.statz.Library;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Autorank library,
 * <a href="https://www.spigotmc.org/resources/autorank.3239/">link</a>.
 * <p>
 * Date created: 14:21:44 12 aug. 2015
 *
 * @author Staartvin
 */
public class AutorankHook extends LibraryHook {

    private Autorank autorank;


    @Override
    public boolean isHooked() {
        return autorank != null;
    }

    /*
     * (non-Javadoc)
     *
     * @see me.staartvin.utils.pluginlibrary.LibraryHook#hook()
     */
    @Override
    public boolean hook() {
        if (!isPluginAvailable(Library.AUTORANK))
            return false;

        autorank = (Autorank) this.getServer().getPluginManager()
                .getPlugin(Library.AUTORANK.getInternalPluginName());

        return autorank != null;
    }

    /**
     * Gets playtime of a player in minutes on this local server.
     *
     * @param uuid UUID of the player
     * @return amount of minutes a player has played.
     */
    public int getLocalPlayTime(UUID uuid) throws ExecutionException, InterruptedException {
        if (!this.isHooked()) return -1;
        return autorank.getAPI().getLocalPlayTime(uuid).get();
    }

    /**
     * When Autorank is set up on multiple servers in the same network, it will
     * sum a player's time across all servers. <br>
     * If a player plays on server A for 10 minutes and on server B for 15
     * minutes, the global time will be 25 minutes. <br>
     * This method allows you to get the global time of a player in minutes.
     *
     * @param uuid UUID of the player
     * @return amount of minutes a player has played across all servers in the
     * network.
     */
    public int getGlobalPlayTime(UUID uuid) throws ExecutionException, InterruptedException {
        if (!this.isHooked()) return -1;
        return autorank.getAPI().getGlobalPlayTime(uuid).get();
    }

    /**
     * Gets a list of all permission groups a player is currently in. <br>
     * Most permission plugins only allow a player to be in one group at the
     * time, but some allow multiple.
     *
     * @param player Player to get the groups of.
     * @return a list of groups a player is part of.
     */
    public Collection<String> getPermissionGroups(Player player) {
        if (!this.isHooked()) return new ArrayList<>();
        return autorank.getPermPlugHandler().getPermissionPlugin().getPlayerGroups(player);
    }

    /**
     * Registers a custom requirement to Autorank. <br>
     * Users of Autorank can use different requirements already coded by the
     * author, <br>
     * but it's also possible to add your own requirements. <br>
     * For more info about custom requirements and results, see: <br>
     * <a href=
     * "https://github.com/Armarr/Autorank-2/wiki/Developer's-API#custom-requirements">
     * https://github.com/Armarr/Autorank-2/wiki/Developer's-API#custom-
     * requirements</a>
     *
     * @param requirementName Name of the requirement.
     * @param req             The custom requirement class for Autorank to use.
     */
    public void registerRequirement(String requirementName, Class<? extends AbstractRequirement> req) {
        if (!this.isHooked()) return;
        autorank.registerRequirement(requirementName, req);
    }

    /**
     * @param resultName The name of the result.
     * @param res        The custom result class for Autorank to use.
     * @see #registerRequirement(String, Class)
     */
    public void registerResult(String resultName,
                               Class<? extends AbstractResult> res) {
        if (!this.isHooked()) return;
        autorank.registerResult(resultName, res);
    }

}
