package gdv.ucm.libengine;

import java.util.List;

public interface IInput {
    public static enum InputTouchType{
        PRESSED,
        CLICKED,
        RELEASED
//        MOVE
    }
//    public static enum InputKeyType{
//        KEY_DOWN,
//        KEY_UP,
//        KEY_LONG
//    }

    public static class Event{
        public int x;
        public int y;
        public InputTouchType type;
        public int index;

        public Event(int x, int y,int index, InputTouchType type){
            this.x = x;
            this.y = y;
            this.index = index;
            this.type = type;
        }

//        public Event(int x, int y,int index, InputKeyType type){}
    }
    public List<Event> getEvents();
    public void clearEvents();

//    class TouchEvent{};
//    List<TouchEvent> getTouchEvents();

}
