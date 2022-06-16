package concurrent;

public class DCLSingleton {
    private static DCLSingleton inst;

    public static DCLSingleton instOf() {
        /* Было дублирование проверки состояния inst,
        она находилась за пределами блока synchronized,
        т.о. переменная еще не заблокирована и она может быть в работе у другого потока */
        synchronized (DCLSingleton.class) {
            if (inst == null) {
                inst = new DCLSingleton();
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }
}
