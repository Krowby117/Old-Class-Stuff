// four states
// Stack
//  - Enter : read from the stack.txt file and load the stack data structure
//  - State : clear the screen, draw the stack, draw the menu, get user input, and process the user input
//  - Exit  : write to the stack.txt file

import java.util.HashMap;

// keys to dictionary
enum LightState {
    ON,
    OFF
}

// values in dictionary
interface ILightState {
    public void invoke();
}

class JumpeTableClass {
    private LightState state;
    private HashMap<LightState, ILightState> stateEnterMethods;
    private HashMap<LightState, ILightState> stateStayMethods;

    public JumpeTableClass() {
        stateEnterMethods = new HashMap<LightState, ILightState>();
        stateStayMethods = new HashMap<LightState, ILightState>();

        //stateStayMethods.put(LightState.ON, () -> {StateStayOn(); });
        //stateStayMethods.put(LightState.OFF, () -> {StateStayOff(); });
        stateEnterMethods.put(LightState.ON, this::StateEnterOn);
        stateEnterMethods.put(LightState.OFF, this::StateEnterOff);

        stateStayMethods.put(LightState.ON, this::StateStayOn);
        stateStayMethods.put(LightState.OFF, this::StateStayOff);

        state = LightState.OFF;
    }

    public void changeState(LightState newState) {
        if (state != newState) {
            state = newState;
            stateEnterMethods.get(state).invoke();
        }
    }
    public void doState() {
        stateStayMethods.get(state).invoke();
    }

    private void StateEnterOn() {
        System.out.println("I am entering the on state!");
    }
    private void StateEnterOff() {
        System.out.println("I am entering the off state!");
    }

    private void StateStayOn() {
        System.out.println("I am in the on state!");
    }
    private void StateStayOff() {
        System.out.println("I am in the off state!");
    }
}

public class JumpTableExample {
    public static void main(String[] args) {
        JumpeTableClass light = new JumpeTableClass();
        light.doState();
        light.changeState(LightState.ON);
        light.doState();
        light.changeState(LightState.OFF);
        light.doState();
    }
}