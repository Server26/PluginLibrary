package me.staartvin.utils.pluginlibrary.statz.hooks.afkmanager;

import java.util.UUID;

public interface AFKManager {

    boolean isAFK(UUID uuid);

    boolean hasAFKData();

}
