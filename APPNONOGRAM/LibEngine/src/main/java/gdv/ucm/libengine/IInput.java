package gdv.ucm.libengine;

import java.util.List;

public interface IInput {
    public static enum InputTouchType{
        TOUCH_DOWN,
        TOUCH_UP,
        TOUCH_MOVE
    }
    public static enum InputKeyType{
        KEY_DOWN,
        KEY_UP,
        KEY_LONG
    }
    public static class Event{
        public int x;
        public int y;
        public InputTouchType type;
        public int index;
        public Event(int x, int y,int index, InputTouchType type){

        }
        public Event(int x, int y,int index, InputKeyType type){

        }
    }
    class TouchEvent{};
    List<TouchEvent> getTouchEvents();
}
