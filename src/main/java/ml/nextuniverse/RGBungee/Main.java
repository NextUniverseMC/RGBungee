package ml.nextuniverse.RGBungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.io.IOException;

public class Main extends Plugin {
    private static String motd;
    private static Plugin plugin;

    JedisPoolConfig poolConfig;
    JedisPool jedisPool;
    Jedis subscriberJedis;
    Subscriber subscriber;

    @Override
    public void onEnable() {
        try {
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getPlugin().getDataFolder(), "config.yml"));
            OnPing.mainLine = ChatColor.translateAlternateColorCodes('&', configuration.getString("mainLine"));
            OnPing.lowerLine = ChatColor.translateAlternateColorCodes('&', configuration.getString("lowerLine"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        getProxy().getPluginManager().registerListener(this, new OnPing());
        plugin = this;

        poolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
        subscriberJedis = jedisPool.getResource();
        subscriber = new Subscriber();

        new Thread(new Runnable() {
            public void run() {
                try {
                    getLogger().info("Subscribing to \"RandomGame\". This thread will be blocked.");
                    subscriberJedis.subscribe(subscriber, "RandomGame");
                    getLogger().info("Subscription ended.");
                } catch (Exception e) {
                    getLogger().severe("Subscribing failed." + e);
                }
            }
        }).start();
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static String getMotd() {
        return motd;
    }
    public static void setMotd(String newmotd) {
        motd = newmotd;
    }

    public static Plugin getPlugin() {
        return plugin;
    }
    public static void publish(String message) {
        Jedis jedis = new Jedis("localhost");
        jedis.publish("RandomGame", message);
    }

}
