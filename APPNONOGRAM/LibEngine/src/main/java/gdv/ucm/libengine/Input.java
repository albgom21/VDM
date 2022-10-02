package gdv.ucm.libengine;

import java.util.List;

public interface Input {
    class TouchEvent{};
    List<TouchEvent> getTouchEvents();
}
