package concurrent;

public class DCLSingleton {
    /* Для поля inst нужно добавить volatile, чтобы состояние поля было доступно для всех потоков */
    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }
}
