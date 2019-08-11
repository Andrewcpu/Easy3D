package constructs;

import java.util.ArrayList;
import java.util.List;

public class KeyboardManager {

    private static KeyboardManager instance = null;

    public static KeyboardManager getInstance(){
        return instance;
    }

    public KeyboardManager(){
        instance = this;
    }

    private List<Integer> keys = new ArrayList<>();

    public void keyPress(int keyCode){
        if(keys.contains(keyCode)) return;
        keys.add(keyCode);
    }

    public void keyRelease(int keyCode){
        if(!keys.contains(keyCode)) return;
        keys.remove((Integer)keyCode);
    }

    public boolean isPressing(int keyCode){
        return keys.contains(keyCode);
    }
}
