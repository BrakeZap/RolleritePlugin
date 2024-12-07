package xyz.brakezap.rolleritePlugin.utils;

import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * TPARequest
 */
public class TPARequest {

    public static enum Request {
        ACCEPT,
        DENY
    }

    private BukkitRunnable task;
    private UUID source;
    private UUID target;

    public TPARequest(UUID s, UUID t) {
        this.source = s;
        this.target = t;
    }

    public BukkitRunnable getTask() {
        return this.task;
    }

    public UUID getSourcePlayer() {
        return this.source;
    }

    public UUID getTargetPlayer() {
        return this.target;
    }

    public void setTask(BukkitRunnable task) {
        this.task = task;
    }

}
