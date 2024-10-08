import Parametros.DatabaseParametros;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseReader {
    private ArrayList<DatabaseParametros.DBParametros> tabelas = new ArrayList<>();
    private Connection conn;
    private DatabaseMetaData metaData;

    public DatabaseReader() {
        try {
            conn = DBConnection.conectaDb();
            metaData = conn.getMetaData();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e);
        }
    }

    public ArrayList<DatabaseParametros.DBParametros> getMetadata() {
        try {
            ResultSet rsTables = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (rsTables.next()) {
                String tabelaAtual = rsTables.getString("TABLE_NAME");
                ArrayList<String> colunas = new ArrayList<>();
                ArrayList<String> tipos = new ArrayList<>();

                ResultSet rsColumns = metaData.getColumns(null, null, tabelaAtual, null);
                while (rsColumns.next()) {
                    colunas.add(rsColumns.getString("COLUMN_NAME"));
                    tipos.add(rsColumns.getString("TYPE_NAME"));
                }
                rsColumns.close();

                DatabaseParametros.DBParametros tabela = new DatabaseParametros.DBParametros();
                tabela.nomeTabela = tabelaAtual;
                tabela.nomeColuna = colunas.toArray(new String[0]);
                tabela.tipoColuna = tipos.toArray(new String[0]);
                tabelas.add(tabela);
            }
            rsTables.close();
        } catch (SQLException e) {
            System.out.println("Erro no método de leitura: " + e);
        }
        return tabelas;
    }
}
