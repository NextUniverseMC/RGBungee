package ml.nextuniverse.RGBungee;

import net.md_5.bungee.api.plugin.Plugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Main extends Plugin {
    private static String motd;
    private static Plugin plugin;

    JedisPoolConfig poolConfig;
    JedisPool jedisPool;
    Jedis subscriberJedis;
    Subscriber subscriber;

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new OnPing());
        motd = "Selecting minigame...";
        plugin = this;

        poolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
        subscriberJedis = jedisPool.getResource();
        subscriber = new Subscriber();

        Jedis jedis = new Jedis("localhost");


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
