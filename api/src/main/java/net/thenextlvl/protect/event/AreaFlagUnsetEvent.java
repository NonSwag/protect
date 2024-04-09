package net.thenextlvl.protect.event;

import lombok.Getter;
import lombok.Setter;
import net.thenextlvl.protect.area.Area;
import net.thenextlvl.protect.flag.Flag;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event that is triggered when a flag is unset in an area.
 * Cancelling this event results in the flag not being unset.
 *
 * @param <T> The type of the flag value.
 */
@Getter
@Setter
public class AreaFlagUnsetEvent<T> extends AreaFlagEvent<T> {

    public AreaFlagUnsetEvent(@NotNull Area area, @NotNull Flag<T> flag) {
        super(area, flag);
    }
}
