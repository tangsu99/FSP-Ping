package cn.tangsu99.fpsping;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "fps-ping",
        name = "FSP-Ping",
        version = BuildConstants.VERSION,
        url = "https://github.com/tangsu99/FSP-Ping",
        authors = {"tangsu99"}
)
public class FSP_Ping {
    @Inject
    private Logger logger;
    @Inject
    private ProxyServer server;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
    }

    @Subscribe
    public void onPing(ProxyPingEvent event) {
        List<ServerPing.SamplePlayer> samplePlayers = new ArrayList<>();
        for (RegisteredServer s : server.getAllServers()) {
            for (Player p : s.getPlayersConnected()) {
                samplePlayers.add(new ServerPing.SamplePlayer("[" + s.getServerInfo().getName() +"] " + p.getUsername(), p.getUniqueId()));
            }
        }
        ServerPing ping = event.getPing();
        ServerPing newPing = ping.asBuilder().samplePlayers(samplePlayers.toArray(ServerPing.SamplePlayer[]::new)).build();
        event.setPing(newPing);
    }
}
