package Generator;

import Parametros.DatabaseParametros;

import java.io.FileWriter;
import java.io.IOException;

public class DAOGenerator {
    public static void generate(DatabaseParametros.DBParametros tabela) {
        String classeNaoConvertida = tabela.nomeTabela;
        String classeConvertida = classeNaoConvertida.substring(0, 1).toUpperCase() + classeNaoConvertida.substring(1);
        String objeto = classeNaoConvertida.substring(0, classeNaoConvertida.length() - 1);
        int repeticaoDeDados = tabela.nomeColuna.length;

        try {
            FileWriter writer = new FileWriter(tabela.nomeTabela + "DAO.java");

            writer.write("public class " + tabela.nomeTabela + "DAO {\n");
            writer.write("\n");
            writer.write("    public void insert(" + classeConvertida + " " + objeto + ") throws SQLException {\n");
            writer.write("        Connection connection = null; // Depende da implementação \n");
            writer.write("        PreparedStatement statement = connection.prepareStatement(\n");
            writer.write("            \"INSERT INTO " + classeNaoConvertida + " (");

            for (int i = 0; i < repeticaoDeDados; i++) {
                writer.write(tabela.nomeColuna[i]);
                if (i < repeticaoDeDados - 1) {
                    writer.write(", ");
                } else {
                    writer.write(")");
                }
            }

            writer.write(" VALUES (");
            for (int i = 0; i < repeticaoDeDados; i++) {
                writer.write("?");
                if (i < repeticaoDeDados - 1) {
                    writer.write(", ");
                } else {
                    writer.write(")\");\n");
                }
            }

            writer.write("\n");
            writer.write("        int index = 1;\n");
            for (int i = 0; i < repeticaoDeDados; i++) {
                String tipoConvertido = switch (tabela.tipoColuna[i].toLowerCase()) {
                    case "serial", "int4" -> "Int";
                    case "varchar" -> "String";
                    case "date" -> "Date";
                    default -> letraMaiuscula(tabela.tipoColuna[i]);
                };
                if (tipoConvertido.equals("Int")) {
                    writer.write("        statement.setInt(index++, " + objeto + ".get" + letraMaiuscula(tabela.nomeColuna[i]) + "());\n");
                } else {
                    writer.write("        statement.setString(index++, " + objeto + ".get" + letraMaiuscula(tabela.nomeColuna[i]) + "());\n");
                }
            }

            writer.write("\n");
            writer.write("        statement.execute();\n");
            writer.write("        statement.close();\n");
            writer.write("        connection.close();\n");
            writer.write("    }\n");
            writer.write("\n");
            writer.write("    public " + classeConvertida + " findById(int id) throws SQLException {\n");
            writer.write("        Connection connection = null; // Depende da implementação \n");
            writer.write("        PreparedStatement statement = connection.prepareStatement(\n");
            writer.write("            \"SELECT * FROM " + classeNaoConvertida + " WHERE clientid = ?\");\n");
            writer.write("        statement.setInt(1, id);\n");
            writer.write("\n");
            writer.write("        ResultSet resultSet = statement.executeQuery();\n");
            writer.write("        " + classeConvertida + " " + objeto + " = null;\n");
            writer.write("        if (resultSet.next()) {\n");
            writer.write("            " + objeto + " = new " + classeConvertida + "(\n");

            for (int i = 0; i < repeticaoDeDados; i++) {
                String tipoConvertido = switch (tabela.tipoColuna[i].toLowerCase()) {
                    case "serial", "int4" -> "Int";
                    case "varchar" -> "String";
                    case "date" -> "Date";
                    default -> letraMaiuscula(tabela.tipoColuna[i]);
                };
                writer.write("                resultSet.get" + tipoConvertido + "(\"" + tabela.nomeColuna[i] + "\")");
                if (i < repeticaoDeDados - 1) {
                    writer.write(",\n");
                } else {
                    writer.write("\n");
                }
            }

            writer.write("            );\n");
            writer.write("        }\n");
            writer.write("        statement.close();\n");
            writer.write("        connection.close();\n");
            writer.write("\n");
            writer.write("        return " + objeto + ";\n");
            writer.write("    }\n");
            writer.write("\n");
            writer.write("    public void update(" + classeConvertida + " " + objeto + ", int id) throws SQLException {\n");
            writer.write("        Connection connection = null; // Depende da implementação \n");
            writer.write("        PreparedStatement statement = connection.prepareStatement(\n");
            writer.write("            \"UPDATE " + classeNaoConvertida + " SET ");

            for (int i = 0; i < repeticaoDeDados; i++) {
                writer.write(tabela.nomeColuna[i] + " = ?");
                if (i < repeticaoDeDados - 1) {
                    writer.write(", ");
                } else {
                    writer.write(" WHERE id = ?\");\n");
                }
            }

            writer.write("        int index = 1;\n");
            for (int i = 0; i < repeticaoDeDados; i++) {
                String tipoConvertido = switch (tabela.tipoColuna[i].toLowerCase()) {
                    case "serial", "int4" -> "Int";
                    case "varchar" -> "String";
                    case "date" -> "Date";
                    default -> letraMaiuscula(tabela.tipoColuna[i]);
                };
                if (tipoConvertido.equals("Int")) {
                    writer.write("        statement.setInt(index++, " + objeto + ".get" + letraMaiuscula(tabela.nomeColuna[i]) + "());\n");
                } else {
                    writer.write("        statement.setString(index++, " + objeto + ".get" + letraMaiuscula(tabela.nomeColuna[i]) + "());\n");
                }
            }

            writer.write("        statement.setInt(index, " + objeto + ".getClientid());\n");
            writer.write("        statement.execute();\n");
            writer.write("        statement.close();\n");
            writer.write("        connection.close();\n");
            writer.write("    }\n");
            writer.write("\n");
            writer.write("    public void delete(int id) throws SQLException {\n");
            writer.write("        Connection connection = null; // Depende da implementação \n");
            writer.write("        PreparedStatement statement = connection.prepareStatement(\n");
            writer.write("            \"DELETE FROM " + classeNaoConvertida + " WHERE clientid = ?\");\n");
            writer.write("        statement.setInt(1, id);\n");
            writer.write("\n");
            writer.write("        statement.execute();\n");
            writer.write("        statement.close();\n");
            writer.write("        connection.close();\n");
            writer.write("    }\n");
            writer.write("}\n");

            writer.close();
        } catch (IOException e) {
            System.out.println("Erro escrevendo o DAO: " + e);
        }
    }

    public static String letraMaiuscula(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
