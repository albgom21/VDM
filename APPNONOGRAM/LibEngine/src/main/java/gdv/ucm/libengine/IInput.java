package gdv.ucm.libengine;

import java.util.List;

public interface IInput {
    class TouchEvent{};
    List<TouchEvent> getTouchEvents();
}
