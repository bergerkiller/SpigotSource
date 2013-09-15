package net.minecraft.server;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.crypto.SecretKey;

public class PendingConnection extends Connection {

    private static Random random = new Random();
    private byte[] d;
    private final MinecraftServer server;
    public final INetworkManager networkManager;
    public boolean b;
    private int f;
    private String g;
    private volatile boolean h;
    private String loginKey = Long.toString(random.nextLong(), 16); // CraftBukkit - Security fix
    private boolean j;
    private SecretKey k;
    public String hostname = ""; // CraftBukkit - add field

    public PendingConnection(MinecraftServer minecraftserver, org.spigotmc.netty.NettyNetworkManager networkManager) {
        this.server = minecraftserver;
        this.networkManager = networkManager;
    }

    public PendingConnection(MinecraftServer minecraftserver, Socket socket, String s) throws java.io.IOException { // CraftBukkit - throws IOException
        this.server = minecraftserver;
        this.networkManager = new NetworkManager(minecraftserver.getLogger(), socket, s, this, minecraftserver.H().getPrivate());
        // this.networkManager.e = 0;
    }

    // CraftBukkit start
    public Socket getSocket() {
        return this.networkManager.getSocket();
    }
    // CraftBukkit end

    public void d() {
        if (this.h) {
            this.e();
        }

        if (this.f++ == 600) {
            this.disconnect("Took too long to log in");
        } else {
            this.networkManager.b();
        }
    }

    public void disconnect(String s) {
        try {
            this.server.getLogger().info("Disconnecting " + this.getName() + ": " + s);
            this.networkManager.queue(new Packet255KickDisconnect(s));
            this.networkManager.d();
            this.b = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static final java.util.regex.Pattern validName = java.util.regex.Pattern.compile("^[a-zA-Z0-9_-]{2,16}$"); // Spigot
    public void a(Packet2Handshake packet2handshake) {
        // CraftBukkit start
        if (this.g != null) {
            this.disconnect("Invalid username " + this.g);
        }
        this.hostname = packet2handshake.c == null ? "" : packet2handshake.c + ':' + packet2handshake.d;
        // CraftBukkit end
        this.g = packet2handshake.f();
        if (!this.g.equals(StripColor.a(this.g)) || !validName.matcher( this.g ).matches() ) { // Spigot
            this.disconnect("Invalid username!");
        } else {
            PublicKey publickey = this.server.H().getPublic();

            if (packet2handshake.d() != 74) {
                if (packet2handshake.d() > 74) {
                    this.disconnect(org.spigotmc.SpigotConfig.outdatedServerMessage); // Spigot
                } else {
                    this.disconnect(org.spigotmc.SpigotConfig.outdatedClientMessage); // Spigot
                }
            } else {
                this.loginKey = this.server.getOnlineMode() ? Long.toString(random.nextLong(), 16) : "-";
                this.d = new byte[4];
                random.nextBytes(this.d);
                this.networkManager.queue(new Packet253KeyRequest(this.loginKey, publickey, this.d));
            }
        }
    }

    public void a(Packet252KeyResponse packet252keyresponse) {
        PrivateKey privatekey = this.server.H().getPrivate();

        this.k = packet252keyresponse.a(privatekey);
        if (!Arrays.equals(this.d, packet252keyresponse.b(privatekey))) {
            this.disconnect("Invalid client reply");
        }

        this.networkManager.queue(new Packet252KeyResponse());
    }

    public void a(Packet205ClientCommand packet205clientcommand) {
        if (packet205clientcommand.a == 0) {
            if (this.j) {
                this.disconnect("Duplicate login");
                return;
            }

            this.j = true;
            if (true) { // Spigot - Always fire
                (new ThreadLoginVerifier(this, server.server)).start(); // CraftBukkit - add CraftServer
            } else {
                this.h = true;
            }
        }
    }

    public void a(Packet1Login packet1login) {}

    public void e() {
        // CraftBukkit start
        EntityPlayer s = this.server.getPlayerList().attemptLogin(this, this.g, this.hostname);

        if (s == null) {
            // this.disconnect(s);
            return;
            // CraftBukkit end
        } else {
            EntityPlayer entityplayer = this.server.getPlayerList().processLogin(s); // CraftBukkit - this.g -> s

            if (entityplayer != null) {
                this.server.getPlayerList().a((INetworkManager) this.networkManager, entityplayer);
            }
        }

        this.b = true;
    }

    public void a(String s, Object[] aobject) {
        this.server.getLogger().info(this.getName() + " lost connection");
        this.b = true;
    }

    public void a(Packet254GetInfo packet254getinfo) {
        if (this.networkManager.getSocket() == null) return; // CraftBukkit - fix NPE when a client queries a server that is unable to handle it.
        try {
            PlayerList playerlist = this.server.getPlayerList();
            String s = null;
            // CraftBukkit
            org.bukkit.event.server.ServerListPingEvent pingEvent = org.bukkit.craftbukkit.event.CraftEventFactory.callServerListPingEvent(this.server.server, getSocket().getInetAddress(), this.server.getMotd(), playerlist.getPlayerCount(), playerlist.getMaxPlayers());
            if (false) { // Spigot: TODO: Use trick from Bungee maybe?
                // CraftBukkit
                s = pingEvent.getMotd() + "\u00A7" + playerlist.getPlayerCount() + "\u00A7" + pingEvent.getMaxPlayers();
            } else {
                // CraftBukkit start - Don't create a list from an array
                Object[] list = new Object[] { 1, 74, this.server.getVersion(), pingEvent.getMotd(), playerlist.getPlayerCount(), pingEvent.getMaxPlayers() };

                StringBuilder builder = new StringBuilder();
                for (Object object : list) {
                    if (builder.length() == 0) {
                        builder.append('\u00A7');
                    } else {
                        builder.append('\0');
                    }

                    builder.append(org.apache.commons.lang.StringUtils.replace(object.toString(), "\0", ""));
                }
                s = builder.toString();
                // CraftBukkit end
            }

            InetAddress inetaddress = null;

            if (this.networkManager.getSocket() != null) {
                inetaddress = this.networkManager.getSocket().getInetAddress();
            }

            this.networkManager.queue(new Packet255KickDisconnect(s));
            this.networkManager.d();
            // Spigot start
            if ( inetaddress != null )
            {
                if ( this.server.ag() instanceof DedicatedServerConnection )
                {
                    ((DedicatedServerConnection) this.server.ag()).a(inetaddress);
                } else
                {
                    ((org.spigotmc.netty.NettyServerConnection)this.server.ag()).unThrottle( inetaddress );
                }
            }
            // Spigot end

            this.b = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onUnhandledPacket(Packet packet) {
        this.disconnect("Protocol error");
    }

    public String getName() {
        return this.g != null ? this.g + " [" + this.networkManager.getSocketAddress().toString() + "]" : this.networkManager.getSocketAddress().toString();
    }

    public boolean a() {
        return true;
    }

    public boolean c() {
        return this.b;
    }

    static String a(PendingConnection pendingconnection) {
        return pendingconnection.loginKey;
    }

    static MinecraftServer b(PendingConnection pendingconnection) {
        return pendingconnection.server;
    }

    static SecretKey c(PendingConnection pendingconnection) {
        return pendingconnection.k;
    }

    static String d(PendingConnection pendingconnection) {
        return pendingconnection.g;
    }

    static boolean a(PendingConnection pendingconnection, boolean flag) {
        return pendingconnection.h = flag;
    }

    // Spigot start
    @Override
    public void a(Packet250CustomPayload pcp) {
        if (pcp.tag.equals("BungeeCord") && org.spigotmc.SpigotConfig.bungee && org.spigotmc.SpigotConfig.bungeeAddresses.contains(getSocket().getInetAddress().getHostAddress())) {
            com.google.common.io.ByteArrayDataInput in = com.google.common.io.ByteStreams.newDataInput(pcp.data);
            String subTag = in.readUTF();
            if (subTag.equals("Login")) {
                networkManager.setSocketAddress(new java.net.InetSocketAddress(in.readUTF(), in.readInt()));
            }
        }
    }
    // Spigot end
}
