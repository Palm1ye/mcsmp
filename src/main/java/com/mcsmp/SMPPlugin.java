package com.mcsmp;

import net.md_5.bungee.api.plugin.Plugin;


import com.mcsmp.commands.CreateServerCommand;
import com.mcsmp.commands.InviteCommand;
import com.mcsmp.commands.JoinServerCommand;

public class SMPPlugin extends Plugin{
    private ServerManager serverManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager("config.yml");
        serverManager = new ServerManager(configManager);
        getProxy().getPluginManager().registerCommand(this, new CreateServerCommand(serverManager));
        getProxy().getPluginManager().registerCommand(this, new JoinServerCommand(serverManager));
        getProxy().getPluginManager().registerCommand(this, new InviteCommand(serverManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
