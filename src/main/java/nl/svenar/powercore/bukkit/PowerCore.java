package nl.svenar.powercore.bukkit;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import nl.svenar.powercore.bukkit.commands.MainCommand;
import nl.svenar.powercore.bukkit.commands.admin.BanCommand;
import nl.svenar.powercore.bukkit.commands.admin.KickCommand;
import nl.svenar.powercore.bukkit.commands.admin.StatsCommand;
import nl.svenar.powercore.bukkit.commands.admin.UnbanCommand;
import nl.svenar.powercore.bukkit.commands.compass.CompassAddWaypointCommand;
import nl.svenar.powercore.bukkit.commands.compass.CompassCommand;
import nl.svenar.powercore.bukkit.commands.compass.CompassDelWaypointCommand;
import nl.svenar.powercore.bukkit.commands.compass.CompassListWaypointsCommand;
import nl.svenar.powercore.bukkit.commands.gamemode.AdventureCommand;
import nl.svenar.powercore.bukkit.commands.gamemode.CreativeCommand;
import nl.svenar.powercore.bukkit.commands.gamemode.GamemodeCommand;
import nl.svenar.powercore.bukkit.commands.gamemode.SpectatorCommand;
import nl.svenar.powercore.bukkit.commands.gamemode.SurvivalCommand;
import nl.svenar.powercore.bukkit.commands.other.SpawnMobCommand;
import nl.svenar.powercore.bukkit.commands.player.BurnCommand;
import nl.svenar.powercore.bukkit.commands.player.EnderchestCommand;
import nl.svenar.powercore.bukkit.commands.player.FeedCommand;
import nl.svenar.powercore.bukkit.commands.player.FlyCommand;
import nl.svenar.powercore.bukkit.commands.player.GodCommand;
import nl.svenar.powercore.bukkit.commands.player.HealCommand;
import nl.svenar.powercore.bukkit.commands.player.InvseeCommand;
import nl.svenar.powercore.bukkit.commands.player.SpeedCommand;
import nl.svenar.powercore.bukkit.commands.player.SpeedInfoCommand;
import nl.svenar.powercore.bukkit.commands.spawn.SetspawnCommand;
import nl.svenar.powercore.bukkit.commands.spawn.SpawnCommand;
import nl.svenar.powercore.bukkit.commands.player.SeenCommand;
import nl.svenar.powercore.bukkit.commands.player.SmiteCommand;
import nl.svenar.powercore.bukkit.commands.teleport.BackCommand;
import nl.svenar.powercore.bukkit.commands.teleport.OfflineTeleportCommand;
import nl.svenar.powercore.bukkit.commands.teleport.TeleportCommand;
import nl.svenar.powercore.bukkit.commands.teleport.TopCommand;
import nl.svenar.powercore.bukkit.commands.time.DayCommand;
import nl.svenar.powercore.bukkit.commands.time.MidnightCommand;
import nl.svenar.powercore.bukkit.commands.time.NightCommand;
import nl.svenar.powercore.bukkit.commands.time.NoonCommand;
import nl.svenar.powercore.bukkit.commands.time.SunriseCommand;
import nl.svenar.powercore.bukkit.commands.time.SunsetCommand;
import nl.svenar.powercore.bukkit.commands.time.TimeAddCommand;
import nl.svenar.powercore.bukkit.commands.time.TimeCommand;
import nl.svenar.powercore.bukkit.commands.time.TimeSetCommand;
import nl.svenar.powercore.bukkit.commands.warp.SetWarpCommand;
import nl.svenar.powercore.bukkit.commands.warp.WarpCommand;
import nl.svenar.powercore.bukkit.commands.weather.RainCommand;
import nl.svenar.powercore.bukkit.commands.weather.SunCommand;
import nl.svenar.powercore.bukkit.commands.weather.ThunderCommand;
import nl.svenar.powercore.bukkit.commands.weather.WeatherCommand;
import nl.svenar.powercore.bukkit.commands.whitelist.WhitelistAddCommand;
import nl.svenar.powercore.bukkit.commands.whitelist.WhitelistDisableCommand;
import nl.svenar.powercore.bukkit.commands.whitelist.WhitelistEnableCommand;
import nl.svenar.powercore.bukkit.commands.whitelist.WhitelistListCommand;
import nl.svenar.powercore.bukkit.commands.whitelist.WhitelistRemoveCommand;
import nl.svenar.powercore.bukkit.events.PlayerListener;
import nl.svenar.powercore.bukkit.modules.compass.CompassHandler;
import nl.svenar.powercore.bukkit.modules.general.PCPlayer;
import nl.svenar.powercore.bukkit.storage.ConfigManager;
import nl.svenar.powercore.bukkit.storage.PCPlayerHandler;
import nl.svenar.powercore.bukkit.utils.Chat;
import nl.svenar.powercore.bukkit.utils.MojangUtils;
import nl.svenar.powercore.bukkit.utils.Util;
import nl.svenar.powercore.common.utils.PowerColor;

public class PowerCore extends JavaPlugin {

    private PaperCommandManager acfManager;
    private CompassHandler compassHandler;
    private PCPlayerHandler pcPlayerHandler;

    private ConfigManager pluginConfigManager, playerConfigManager, spawnConfigManager, warpConfigManager,
            whitelistConfigManager;

    private Instant startupTime;

    private List<String> lines = Arrays.asList( //
            "██████   █████  %name %version by %author", //
            "██   ██ ██   ██ %mcversion", //
            "██████  ██      %startuptime", //
            "██      ██   ██ %web | %donate", //
            "██       █████  %reloadwarning" //
    );

    /**
     * Initialize the plugin and load all the modules
     */
    @Override
    public void onEnable() {
        startupTime = Instant.now();
        Chat chat = new Chat();

        setupConfig();
        setupListeners();
        setupCommands();
        setupHandlers();

        chat.console("");
        for (String line : lines) {
            String output = line;
            output = PowerColor.ChatColor.DARK_PURPLE + output;

            output = output.replace("%name", PowerColor.ChatColor.GREEN + getPluginName());
            output = output.replace("%version", PowerColor.ChatColor.GREEN + "v" + getPluginVersion());
            output = output.replace("%mcversion", PowerColor.ChatColor.GREEN + "Running on "
                    + Util.getServerType(getServer()) + " v" + Util.getServerVersion(getServer()));
            output = output.replace("%startuptime", PowerColor.ChatColor.GREEN + "Startup time: "
                    + (Duration.between(startupTime, Instant.now()).toMillis() + "ms"));
            output = output.replace("%author", PowerColor.ChatColor.GREEN + getDescription().getAuthors().get(0));
            output = output.replace("%web",
                    PowerColor.ChatColor.GREEN + "https://svenar.nl" + PowerColor.ChatColor.DARK_PURPLE);
            output = output.replace("%donate", PowerColor.ChatColor.YELLOW + "https://ko-fi.com/svenar");
            output = output.replace("%reloadwarning",
                    System.getProperty("POWERCORERUNNING", "").equals("TRUE")
                            ? (PowerColor.ChatColor.RED + "Reload detected, why do you hate yourself :C")
                            : "");

            chat.console(output);
        }
        chat.console("");

        System.setProperty("POWERCORERUNNING", "TRUE");
    }

    /**
     * Save and disable all the handlers
     */
    @Override
    public void onDisable() {
        Instant disableTime = Instant.now();

        if (this.compassHandler != null) {
            this.compassHandler.stop();
        }

        if (this.pcPlayerHandler != null) {
            this.pcPlayerHandler.savePlayers();
        }

        pluginConfigManager.saveConfig();
        playerConfigManager.saveConfig();
        spawnConfigManager.saveConfig();
        warpConfigManager.saveConfig();
        whitelistConfigManager.saveConfig();

        Chat chat = new Chat();
        chat.console("");
        for (String line : lines) {
            String output = line;
            output = PowerColor.ChatColor.RED + output;

            output = output.replace("%name", PowerColor.ChatColor.GREEN + getPluginName());
            output = output.replace("%version", PowerColor.ChatColor.GREEN + "v" + getPluginVersion());
            output = output.replace("%mcversion", PowerColor.ChatColor.GREEN + "Running on "
                    + Util.getServerType(getServer()) + " v" + Util.getServerVersion(getServer()));
            output = output.replace("%startuptime", PowerColor.ChatColor.GREEN + "Disable time: "
                    + (Duration.between(disableTime, Instant.now()).toMillis() + "ms"));
            output = output.replace("%author", PowerColor.ChatColor.GREEN + getDescription().getAuthors().get(0));
            output = output.replace("%web",
                    PowerColor.ChatColor.GREEN + "https://svenar.nl" + PowerColor.ChatColor.DARK_PURPLE);
            output = output.replace("%donate", PowerColor.ChatColor.YELLOW + "https://ko-fi.com/svenar");
            output = output.replace("%reloadwarning", PowerColor.ChatColor.DARK_RED + "Plugin is disabled!");

            chat.console(output);
        }
        chat.console("");
        chat = null;
    }

    /**
     * Setup and initialize the config files
     * The config should always load first
     */
    private void setupConfig() {
        pluginConfigManager = new ConfigManager(this, "config.yml", false);
        pluginConfigManager.addDefault("event.join.chat.silent", false);
        pluginConfigManager.addDefault("event.join.chat.message", "&0[&2+&0] &7{player} has joined the game");
        pluginConfigManager.addDefault("event.leave.chat.silent", false);
        pluginConfigManager.addDefault("event.leave.chat.message", "&0[&4-&0] &7{player} has left the game");
        pluginConfigManager.addDefault("player.default.compass.enabled", false);
        pluginConfigManager.addDefault("command.spawnmob.limit", 10);

        playerConfigManager = new ConfigManager(this, "players.yml", false);

        spawnConfigManager = new ConfigManager(this, "spawn.yml", false);

        warpConfigManager = new ConfigManager(this, "warps.yml", false);
        warpConfigManager.addDefault("warps", new String[] {});

        whitelistConfigManager = new ConfigManager(this, "whitelist.yml", false);
        whitelistConfigManager.addDefault("whitelist.enabled", false);
        whitelistConfigManager.addDefault("whitelist.kickmessage", "&cYou are not whitelisted on this server");
        whitelistConfigManager.addDefault("whitelist.players", new String[] {});
    }

    /**
     * Setup the listeners for the plugin
     * Load order for the listeners does not matter
     */
    private void setupListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
    }

    /**
     * Setup the commands for the plugin
     * Load order for the commands does not matter
     */
    private void setupCommands() {
        this.acfManager = new PaperCommandManager(this);
        this.acfManager.enableUnstableAPI("brigadier");

        this.acfManager.getCommandReplacements().addReplacement("powercorecommand", "powercore|pcore|core");

        this.acfManager.getCommandCompletions().registerAsyncCompletion("time",
                c -> Arrays.asList("0", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000",
                        "11000", "12000", "13000", "14000", "15000", "16000", "17000", "18000", "19000", "20000",
                        "21000", "22000", "23000"));

        this.acfManager.getCommandCompletions().registerAsyncCompletion("pcplayers",
                c -> pcPlayerHandler.getPlayers().stream().collect(Collectors.toList()));

        this.acfManager.getCommandCompletions().registerAsyncCompletion("warps",
                c -> {
                    if (warpConfigManager.getConfig().getConfigurationSection("warps") == null) {
                        return Arrays.asList();
                    }
                    return warpConfigManager.getConfig().getConfigurationSection("warps").getKeys(false).stream()
                            .collect(Collectors.toList());
                });

        this.acfManager.getCommandCompletions().registerAsyncCompletion("whitelistedplayers", c -> {
            try {
                List<String> players = new ArrayList<>();
                for (String uuid : whitelistConfigManager.getConfig().getStringList("whitelist.players")) {
                    PCPlayer pcPlayer = pcPlayerHandler.getPlayer(uuid);
                    if (pcPlayer != null) {
                        players.add(pcPlayer.getName());
                    } else {
                        players.add(MojangUtils.getNameFromAPI(uuid));
                    }
                }
                return players;
            } catch (Exception e) {
                return Arrays.asList();
            }
        });

        this.acfManager.getCommandCompletions()
                .registerAsyncCompletion("bannedpcplayers",
                        c -> pcPlayerHandler
                                .getPlayers()
                                .stream()
                                .map(playerName -> pcPlayerHandler.getPlayer(playerName))
                                .filter(PCPlayer::isBanned)
                                .map(PCPlayer::getName)
                                .collect(Collectors.toList()));

        // Register commands
        this.acfManager.registerCommand(new MainCommand(this));

        // Admin commands
        this.acfManager.registerCommand(new StatsCommand(this));
        this.acfManager.registerCommand(new KickCommand(this));
        this.acfManager.registerCommand(new BanCommand(this));
        this.acfManager.registerCommand(new UnbanCommand(this));

        // Compass commands
        this.acfManager.registerCommand(new CompassCommand(this));
        this.acfManager.registerCommand(new CompassAddWaypointCommand(this));
        this.acfManager.registerCommand(new CompassDelWaypointCommand(this));
        this.acfManager.registerCommand(new CompassListWaypointsCommand(this));

        // Weather commands
        this.acfManager.registerCommand(new WeatherCommand(this));
        this.acfManager.registerCommand(new SunCommand(this));
        this.acfManager.registerCommand(new RainCommand(this));
        this.acfManager.registerCommand(new ThunderCommand(this));

        // Time commands
        this.acfManager.registerCommand(new TimeCommand(this));
        this.acfManager.registerCommand(new TimeSetCommand(this));
        this.acfManager.registerCommand(new TimeAddCommand(this));
        this.acfManager.registerCommand(new DayCommand(this));
        this.acfManager.registerCommand(new NoonCommand(this));
        this.acfManager.registerCommand(new SunsetCommand(this));
        this.acfManager.registerCommand(new NightCommand(this));
        this.acfManager.registerCommand(new MidnightCommand(this));
        this.acfManager.registerCommand(new SunriseCommand(this));

        // Gamemode commands
        this.acfManager.registerCommand(new GamemodeCommand(this));
        this.acfManager.registerCommand(new SurvivalCommand(this));
        this.acfManager.registerCommand(new CreativeCommand(this));
        this.acfManager.registerCommand(new AdventureCommand(this));
        this.acfManager.registerCommand(new SpectatorCommand(this));

        // Teleport commands
        this.acfManager.registerCommand(new TeleportCommand(this));
        this.acfManager.registerCommand(new OfflineTeleportCommand(this));
        this.acfManager.registerCommand(new TopCommand(this));
        this.acfManager.registerCommand(new BackCommand(this));

        // Player commands
        this.acfManager.registerCommand(new FlyCommand(this));
        this.acfManager.registerCommand(new SpeedCommand(this));
        this.acfManager.registerCommand(new SpeedInfoCommand(this));
        this.acfManager.registerCommand(new SeenCommand(this));
        this.acfManager.registerCommand(new HealCommand(this));
        this.acfManager.registerCommand(new FeedCommand(this));
        this.acfManager.registerCommand(new EnderchestCommand(this));
        this.acfManager.registerCommand(new InvseeCommand(this));
        this.acfManager.registerCommand(new GodCommand(this));
        this.acfManager.registerCommand(new BurnCommand(this));
        this.acfManager.registerCommand(new SmiteCommand(this));

        // Spawn commands
        this.acfManager.registerCommand(new SpawnCommand(this));
        this.acfManager.registerCommand(new SetspawnCommand(this));

        // Warp commands
        this.acfManager.registerCommand(new WarpCommand(this));
        this.acfManager.registerCommand(new SetWarpCommand(this));

        // Whitelist commands
        this.acfManager.registerCommand(new WhitelistListCommand(this));
        this.acfManager.registerCommand(new WhitelistAddCommand(this));
        this.acfManager.registerCommand(new WhitelistRemoveCommand(this));
        this.acfManager.registerCommand(new WhitelistEnableCommand(this));
        this.acfManager.registerCommand(new WhitelistDisableCommand(this));

        // Other commands
        this.acfManager.registerCommand(new SpawnMobCommand(this));
    }

    /**
     * Setup the handlers for the plugin
     * This should always load after the config
     * 
     */
    private void setupHandlers() {
        pcPlayerHandler = new PCPlayerHandler(this);
        pcPlayerHandler.loadPlayers();

        compassHandler = new CompassHandler(this);
        compassHandler.start();
    }

    /**
     * Get the name of the plugin
     * 
     * @return String
     */
    public String getPluginName() {
        return getDescription().getName();
    }

    /**
     * Get the version of the plugin
     * 
     * @return String
     */
    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    /**
     * Get the startup time of the plugin
     * 
     * @return Instant
     */
    public Instant getStartupTime() {
        return startupTime;
    }

    /**
     * Get the ACF PaperCommandManager instance
     * 
     * @return PaperCommandManager
     */
    public PCPlayerHandler getPCPlayerHandler() {
        return pcPlayerHandler;
    }

    /**
     * Get the Config manager for the plugin
     * 
     * @return ConfigManager
     */
    public ConfigManager getPluginConfigManager() {
        return pluginConfigManager;
    }

    /**
     * Get the Config manager for player data
     * 
     * @return ConfigManager
     */
    public ConfigManager getPlayerConfigManager() {
        return playerConfigManager;
    }

    /**
     * Get the Config manager for spawn data
     * 
     * @return ConfigManager
     */
    public ConfigManager getSpawnConfigManager() {
        return spawnConfigManager;
    }

    /**
     * Get the Config manager for warp data
     * 
     * @return ConfigManager
     */
    public ConfigManager getWarpConfigManager() {
        return warpConfigManager;
    }

    /**
     * Get the Config manager for whitelist data
     * 
     * @return ConfigManager
     */
    public ConfigManager getWhitelistConfigManager() {
        return whitelistConfigManager;
    }
}
