package software.bigbade.slimefunvoid.utils;

import software.bigbade.slimefunvoid.SlimefunVoid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.logging.Level;

public class ReflectionManager {
    //Private constructor to hide implicit public one
    private ReflectionManager() {}

    @Nonnull
    public static Field getField(@Nonnull Class<?> clazz, @Nonnull String name) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            SlimefunVoid.getPluginLogger().log(Level.SEVERE, "Could not field field");
        }
        Objects.requireNonNull(field);
        return field;
    }

    @Nonnull
    public static Object getValue(@Nonnull Field field, @Nullable Object instance) {
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            SlimefunVoid.getPluginLogger().log(Level.SEVERE, "Could not get field reflexively");
        }
        throw new IllegalStateException("Could not get value for field " + field.getName() + " with instance " + instance);
    }

    public static void setValue(@Nonnull Field field, @Nullable Object value, @Nullable Object instance) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            SlimefunVoid.getPluginLogger().log(Level.SEVERE, "Could not set field reflexively");
        }
    }
}
