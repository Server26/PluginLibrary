package me.staartvin.utils.pluginlibrary.statz.hooks;

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.stats.Stat;
import me.staartvin.utils.pluginlibrary.statz.Library;

import java.util.Locale;
import java.util.UUID;

/**
 * AureliumSkills library,
 * <a href="https://www.spigotmc.org/resources/aurelium-skills-advanced-skills-stats-abilities-and-more.81069/">link</a>
 * .
 * <p>
 *
 * @author Staartvin
 */
public class AureliumSkillsHook extends LibraryHook {

    @Override
    public boolean isHooked() {
        return isPluginAvailable(Library.AURELIUM_SKILLS);
    }

    /*
     * (non-Javadoc)
     *
     * @see me.staartvin.plugins.pluginlibrary.hooks.LibraryHook#hook()
     */
    @Override
    public boolean hook() {
        // All api calls are done static, so there is no need to get the plugin
        // class.
        // We only check if the plugin is available.

        return isPluginAvailable(Library.AURELIUM_SKILLS);
    }

    /**
     * Get the level of a statistic of a player
     *
     * @param uuid     UUID of the player
     * @param statType Type of Stat
     * @return zero if the stat could not be found. Level of stat otherwise.
     */
    public double getStatLevel(UUID uuid, String statType) {

        Stat stat = null;

        try {
            stat = Stat.valueOf(statType.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return 0;
        }

        return AureliumAPI.getStatLevel(uuid, stat);
    }


    /**
     * Get the level of a skill for the given player
     *
     * @param uuid      UUID of the player
     * @param skillName Name of the skill (use {@link Skill} as reference)
     * @return level of a skill or zero if the skill or player cannot be found.
     */
    public int getSkillLevel(UUID uuid, String skillName) {
        Skill skill = null;

        try {
            skill = Skill.valueOf(skillName.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return 0;
        }


        return AureliumAPI.getSkillLevel(uuid, skill);
    }

    /**
     * Get the XP of a specific skill for the given player.
     *
     * @param uuid      UUID of the player
     * @param skillName Name of the skill
     * @return experience points of the skill or zero if the skill could not be found.
     */
    public double getXP(UUID uuid, String skillName) {
        Skill skill = null;

        try {
            skill = Skill.valueOf(skillName.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return 0;
        }

        return AureliumAPI.getXp(uuid, skill);
    }

    /**
     * Get the amount of mana a given player has
     *
     * @param uuid UUID of the player
     * @return current mana of the player
     */
    public double getMana(UUID uuid) {
        return AureliumAPI.getMana(uuid);
    }
}
