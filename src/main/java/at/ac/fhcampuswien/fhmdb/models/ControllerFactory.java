package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.HomeController;
import javafx.util.Callback;

/**
 * Die Klasse ControllerFactory implementiert das Callback-Interface und wird verwendet, um eine Instanz
 * der HomeController-Klasse zu erstellen.
 */
public class ControllerFactory implements Callback<Class<?>, Object> {
    private HomeController instance;

    /**
     * Erstellt eine Instanz der HomeController-Klasse.
     */
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
