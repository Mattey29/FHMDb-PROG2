package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.HomeController;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {
    private HomeController instance;

    @Override
    public Object call(Class<?> aClass) {
        if (instance == null) {
            try {
                instance = (HomeController) aClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
