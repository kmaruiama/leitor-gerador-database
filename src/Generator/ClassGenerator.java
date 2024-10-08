package Generator;

import Parametros.DatabaseParametros;

import java.io.FileWriter;
import java.io.IOException;

import static Generator.DAOGenerator.letraMaiuscula;

public class ClassGenerator {
    public static void generate(DatabaseParametros.DBParametros tabela) {
        String classeNaoConvertida = tabela.nomeTabela;
        String classeConvertida = classeNaoConvertida.substring(0, 1).toUpperCase() + classeNaoConvertida.substring(1);
        int repeticaoDeDados = tabela.nomeColuna.length;

        try {
            FileWriter writer = new FileWriter(tabela.nomeTabela + ".java");

            writer.write("public class " + classeConvertida + " {\n");
            writer.write("\n");
            for (int i = 0; i < repeticaoDeDados; i++) {
                String tipoConvertido = switch (tabela.tipoColuna[i].toLowerCase()) {
                    case "serial", "int4" -> "int";
                    case "varchar" -> "String";
                    case "date" -> "Date";
                    default -> letraMaiuscula(tabela.tipoColuna[i]);
                };
                writer.write("    private " + tipoConvertido + " " + tabela.nomeColuna[i] + ";\n");
            }
            writer.write("\n");
            writer.write("    public " + classeConvertida + " (");
            for (int i = 0; i < repeticaoDeDados; i++) {
                String tipoConvertido = switch (tabela.tipoColuna[i].toLowerCase()) {
                    case "serial", "int4" -> "int";
                    case "varchar" -> "String";
                    case "date" -> "Date";
                    default -> letraMaiuscula(tabela.tipoColuna[i]);
                };
                if (i < repeticaoDeDados - 1) {
                    writer.write(tipoConvertido + " " + tabela.nomeColuna[i] + ", ");
                } else {
                    writer.write(tipoConvertido + " " + tabela.nomeColuna[i] + ") {\n");
                }
            }
            for (int i = 0; i < repeticaoDeDados; i++) {
                writer.write("        this." + tabela.nomeColuna[i] + " = " + tabela.nomeColuna[i] + ";\n");
            }
            writer.write("    }\n");
            writer.write("\n");
            for (int i = 0; i < repeticaoDeDados; i++) {
                String tipoConvertido = switch (tabela.tipoColuna[i].toLowerCase()) {
                    case "serial", "int4" -> "int";
                    case "varchar" -> "String";
                    case "date" -> "Date";
                    default -> letraMaiuscula(tabela.tipoColuna[i]);
                };
                writer.write("    public " + tipoConvertido + " get" + letraMaiuscula(tabela.nomeColuna[i]) + "() {\n");
                writer.write("        return " + tabela.nomeColuna[i] + ";\n");
                writer.write("    }\n");
                writer.write("\n");
                writer.write("    public void set" + letraMaiuscula(tabela.nomeColuna[i]) + "(" + tipoConvertido + " " + tabela.nomeColuna[i] + ") {\n");
                writer.write("        this." + tabela.nomeColuna[i] + " = " + tabela.nomeColuna[i] + ";\n");
                writer.write("    }\n");
                writer.write("\n");
            }
            writer.write("}");
            writer.close();

        } catch (IOException e) {
            System.out.println("Erro escrevendo a classe: " + e);
        }
    }
}
