package Generator;

import Parametros.DatabaseParametros;

import java.io.FileWriter;
import java.io.IOException;

public class RandomInstantiationGenerator {
    public static void generate(DatabaseParametros.DBParametros tabela) {
        String classeNaoConvertida = tabela.nomeTabela;
        String classeConvertida = classeNaoConvertida.substring(0, 1).toUpperCase() + classeNaoConvertida.substring(1);
        String objeto = classeNaoConvertida.substring(0, classeNaoConvertida.length() - 1);
        int repeticaoDeDados = tabela.nomeColuna.length;
        try {
            FileWriter writer = new FileWriter(tabela.nomeTabela + "Exemplo.java");
            writer.write("public class " + classeConvertida + "Exemplo {\n");
            writer.write("\n");
            writer.write("    " + classeConvertida + " " + objeto + " = new " + classeConvertida + "(\n");

            for (int i = 0; i < repeticaoDeDados; i++) {
                String aleatorio = switch (tabela.tipoColuna[i].toLowerCase()) {
                    case "serial", "int4" -> "0";
                    case "varchar" -> "\"abcdef\"";
                    case "date" -> "\"01/01/2000\"";
                    default -> "\"a\"";
                };

                if (i < repeticaoDeDados - 1) {
                    writer.write("        " + aleatorio + ",\n");
                } else {
                    writer.write("        " + aleatorio + "\n");
                }
            }
            writer.write("    );\n");
            writer.write("}\n");
            writer.close();

        } catch (IOException e) {
            System.out.println("Erro escrevendo a classe: " + e);
        }
    }
}
