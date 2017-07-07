package org.bot.server;

public class ServerProvider {

    private final ServerLoader<?> loader;
    private final ServerManifest manifest;

    public ServerProvider(ServerLoader<?> loader, ServerManifest manifest) {
        this.loader = loader;
        this.manifest = manifest;
    }

    public ServerManifest getManifest() {
        return manifest;
    }

    public ServerLoader<?> getLoader() {
        return loader;
    }

}