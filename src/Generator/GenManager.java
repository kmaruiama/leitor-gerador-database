package Generator;

import Parametros.DatabaseParametros;

import java.util.ArrayList;
public class GenManager {
    public static void genManager (ArrayList<DatabaseParametros.DBParametros> tabelas)
    {
        for (DatabaseParametros.DBParametros tabela : tabelas)
        {
            DAOGenerator.generate(tabela);
            ClassGenerator.generate(tabela);
            RandomInstantiationGenerator.generate(tabela);
        }
    }
}
