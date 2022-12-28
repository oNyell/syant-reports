package com.zyramc.ojvzinn.reports;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.zyramc.ojvzinn.reports.listeners.PluginMessageListeners;
import com.zyramc.ojvzinn.reports.report.ReportManagerBukkit;
import database.DataBase;
import database.DataTypes;
import dev.syantmc.pewd.plugin.SyantPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Main extends SyantPlugin {

    private static Main plugin;
    private static String servidor;

    @Override
    public void start() {}

    @Override
    public void load() {
        plugin = this;
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "syant-reports");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "syant-reports", new PluginMessageListeners());
        saveDefaultConfig();
        servidor = getConfig().getString("servidor");
    }

    @Override
    public void enable() {
        DataBase.setupDataBases(DataTypes.MYSQL, this);
        ReportManagerBukkit.setupReports();

        getLogger().info("O plugin iniciou com sucesso!");
    }

    @Override
    public void disable() {
        getLogger().info("O plugin desligou com sucesso!");
    }

    public static Main getInstance() {
        return plugin;
    }

    public static String getServidor() {
        return servidor;
    }

    public static void sendServidor(Player player, String serverTarget) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(serverTarget);
        player.sendPluginMessage(getInstance(), "BungeeCord", output.toByteArray());
    }
}
