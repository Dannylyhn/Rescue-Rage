package rescuerage.common.events;

import rescuerage.common.data.Entity;
import java.io.Serializable;

/**
 *
 * @author ander
 */
public class Event implements Serializable {

    private final Entity source;

    public Event(Entity source) {
        this.source = source;
    }

    public Entity getSource() {
        return source;
    }
}
