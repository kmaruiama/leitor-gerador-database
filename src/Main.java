import Generator.GenManager;
import Parametros.DatabaseParametros;

import java.util.ArrayList;

public class Main {
    public static void main (String[] args) {
        DatabaseReader databaseReader = new DatabaseReader();
        ArrayList<DatabaseParametros.DBParametros> tabelas = databaseReader.getMetadata();
        GenManager.genManager(tabelas);
    }
}
