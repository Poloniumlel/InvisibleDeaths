package me.polonium.invisibledeaths;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import java.util.List;
import java.util.Random;

public final class InvisibleDeaths extends JavaPlugin implements Listener {
    private List<String> deathMessages;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        loadConfig();
    }

    private void loadConfig() {
        saveDefaultConfig();
        reloadConfig();
        FileConfiguration config = getConfig();
        deathMessages = config.getStringList("death-messages");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer != null && killer.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            String randomDeathMessage = getRandomDeathMessage();
            String hiddenDeathMessage = ChatColor.translateAlternateColorCodes('&', randomDeathMessage
                    .replaceAll("%Player%", player.getName()));
            event.setDeathMessage(hiddenDeathMessage);
        }
    }

    private String getRandomDeathMessage() {
        Random random = new Random();
        int index = random.nextInt(deathMessages.size());
        return deathMessages.get(index);
    }
}
