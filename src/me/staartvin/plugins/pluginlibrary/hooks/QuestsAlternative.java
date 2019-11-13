package me.staartvin.plugins.pluginlibrary.hooks;


import com.leonardobishop.quests.Quests;
import com.leonardobishop.quests.player.QPlayer;
import com.leonardobishop.quests.player.questprogressfile.QuestProgress;
import com.leonardobishop.quests.quests.Quest;
import me.staartvin.plugins.pluginlibrary.Library;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;

/**
 * Quests,
 * <a href="https://www.spigotmc.org/resources/%E2%96%B6-quests-%E2%97%80-set-up-goals-for-players.23696/">link</a>.
 * <p>
 *
 * @author Staartvin
 */
public class QuestsAlternative extends LibraryHook {

    private Quests quests;

    /*
     * (non-Javadoc)
     *
     * @see me.staartvin.plugins.pluginlibrary.LibraryHook#isAvailable()
     */
    @Override
    public boolean isAvailable() {
        // TODO Auto-generated method stub
        Plugin plugin = this.getPlugin().getServer().getPluginManager().getPlugin(Library.QUESTS_ALTERNATIVE
                .getInternalPluginName());

        return plugin != null && plugin.isEnabled();
    }

    //

    /*
     * (non-Javadoc)
     *
     * @see me.staartvin.plugins.pluginlibrary.LibraryHook#hook()
     */
    @Override
    public boolean hook() {
        // TODO Auto-generated method stub

        if (!isAvailable())
            return false;

        Plugin plugin = this.getPlugin().getServer().getPluginManager()
                .getPlugin(Library.QUESTS_ALTERNATIVE.getInternalPluginName());

        if (!(plugin instanceof Quests))
            return false;

        quests = (Quests) plugin;

        return quests != null;
    }

    /**
     * Get the number of quests a player has completed.
     *
     * @param uuid UUID of the player
     *
     * @return the number of completed quests or -1 if no data was available
     */
    public int getNumberOfCompletedQuests(UUID uuid) {
        if (!this.isAvailable()) return -1;

        Map<String, Quest> quests = Quests.get().getQuestManager().getQuests();

        int completedQuests = 0;

        for (Map.Entry<String, Quest> questEntry : quests.entrySet()) {
            if (this.isQuestCompleted(uuid, questEntry.getKey())) {
                completedQuests++;
            }
        }

        return completedQuests;
    }

    /**
     * Get the number of quests a player has currently active
     *
     * @param uuid UUID of the player
     *
     * @return the number of active quests or -1 if no data was available
     */
    public int getNumberOfActiveQuests(UUID uuid) {
        if (!this.isAvailable()) return -1;

        QPlayer playerData = Quests.get().getPlayerManager().getPlayer(uuid);

        return playerData.getQuestProgressFile().getStartedQuests().size();
    }

    /**
     * Check whether a player has completed a quest.
     *
     * @param uuid      UUID of the player
     * @param questName Name of the quest to check
     *
     * @return true if the player has completed the quest, false otherwise.
     */
    public boolean isQuestCompleted(UUID uuid, String questName) {
        if (!this.isAvailable()) return false;

        Quest questData = Quests.get().getQuestManager().getQuestById(questName);
        QPlayer playerData = Quests.get().getPlayerManager().getPlayer(uuid);

        QuestProgress progress = playerData.getQuestProgressFile().getQuestProgress(questData);

        // No progress tracked of this quest (for this player), so it cannot have completed the quest.
        if (progress == null) {
            return false;
        }

        return progress.isCompletedBefore();
    }


}