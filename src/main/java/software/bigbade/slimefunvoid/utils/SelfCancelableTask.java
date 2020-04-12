package software.bigbade.slimefunvoid.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import software.bigbade.slimefunvoid.SlimefunVoid;

public class SelfCancelableTask implements Runnable {
    private int id;
    @Getter
    private int loops = 0;
    private Runnable runnable;

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void start(long initalDelay, long delay) {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SlimefunVoid.getInstance(), this, initalDelay, delay);
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(id);
    }

    @Override
    public void run() {
        runnable.run();
        loops += 1;
    }
}
