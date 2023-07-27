package honda.bookworm.Application;

import honda.bookworm.Business.Exceptions.GeneralPersistenceException;

public class Main {
    private static String dbName="BookWormDB";

    public static void setDBPathName(final String name) throws GeneralPersistenceException {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new GeneralPersistenceException("Unable to set dB path name");
        }
        dbName = name;
    }

    public static String getDBPathName() {
        return dbName;
    }
}
