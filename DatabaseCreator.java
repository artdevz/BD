import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DatabaseCreator {

    private static final String URL = "jdbc:mysql://localhost:3306/"; 
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        
        String databaseName = "bigdandb";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement =  connection.createStatement()) {
            
                String sql = "CREATE DATABASE " + databaseName;
                
                statement.executeUpdate(sql);
                System.out.println("Banco de Dados '" + databaseName + "' criado com sucesso!");
                 
                String selectSQL = "USE " + databaseName + ";";
                statement.executeUpdate(selectSQL);
                System.out.println("Banco de dados '" + databaseName + "' selecionado com sucesso!");

                String createTableSQL = """
                            CREATE TABLE freio(
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            tensao FLOAT,
                            potencia FLOAT,
                            torque FLOAT
                        );
                        """;
                statement.executeUpdate(createTableSQL);

                createTableSQL = """
                        CREATE TABLE resolver(
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            excitacao FLOAT,
                            frequencia FLOAT
                        );
                        """;
                statement.executeUpdate(createTableSQL);

                createTableSQL = """
                        CREATE TABLE torque(
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            maximo FLOAT,
                            nominal FLOAT
                        );
                        """;
                statement.executeUpdate(createTableSQL);

                createTableSQL = """
                        CREATE TABLE corrente(
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            partida FLOAT,
                            nominal FLOAT
                        );
                        """;
                statement.executeUpdate(createTableSQL);

                createTableSQL = """
                        CREATE TABLE rpm(
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            maximo FLOAT,
                            minimo FLOAT
                        );
                        """;
                statement.executeUpdate(createTableSQL);

                createTableSQL = """
                        CREATE TABLE certificado(
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nome VARCHAR(100),
                            descricao VARCHAR(255)
                        );
                        """;
                statement.executeUpdate(createTableSQL);

                createTableSQL = """
                        CREATE TABLE protecao (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nome VARCHAR(4),
                            descricao VARCHAR(100)
                        );
                        """;
                statement.executeUpdate(createTableSQL);
                // Linha 106
                createTableSQL = """ 
                        CREATE TABLE motor(
                            id INT AUTO_INCREMENT PRIMARY KEY,	
                            marca VARCHAR(100),
                            data_de_fabricacao TIMESTAMP,
                            modelo VARCHAR(255),
                            tensao_de_operacao INT,
                            potencia_nominal INT,	
                            encoder VARCHAR(255),
                            numero_de_registro VARCHAR(255),
                            fabricacao VARCHAR(100),
                            classe_de_isolamento VARCHAR(1),
                            ptc FLOAT,
                            delta_da_temperatura FLOAT,
                            id_torque INT, 
                            CONSTRAINT fk_torque 
                                FOREIGN KEY (id_torque)
                                REFERENCES torque(id)
                                ON DELETE SET NULL,
                            id_corrente INT, 
                            CONSTRAINT fk_corrente 
                                FOREIGN KEY (id_corrente)
                                REFERENCES corrente(id)
                                ON DELETE SET NULL,
                            id_rpm INT,
                            CONSTRAINT fk_rpm 
                                FOREIGN KEY (id_rpm)
                                REFERENCES rpm(id)
                                ON DELETE SET NULL,
                            id_protecao INT,
                            CONSTRAINT fk_protecao
                                FOREIGN KEY (id_protecao)
                                REFERENCES protecao(id)
                                ON DELETE SET NULL,
                            id_freio INT,
                            CONSTRAINT fk_freio
                                FOREIGN KEY (id_freio)
                                REFERENCES freio(id)
                                ON DELETE SET NULL,
                            id_resolver INT,
                            CONSTRAINT fk_resolver 
                                FOREIGN KEY (id_resolver)
                                REFERENCES resolver(id)
                                ON DELETE SET NULL
                        );
                        """;
                statement.executeUpdate(createTableSQL);

                createTableSQL = """
                        CREATE TABLE motor_certificado(
                            id SERIAL PRIMARY KEY,
                            id_certificado INT,
                            CONSTRAINT fk_certificado
                                FOREIGN KEY (id_certificado)
                                REFERENCES certificado(id)
                                ON DELETE SET NULL,
                            id_motor INT,
                            CONSTRAINT fk_motor
                                FOREIGN KEY (id_motor)
                                REFERENCES motor(id)
                                ON DELETE SET NULL
                        );
                        """;
                statement.executeUpdate(createTableSQL);

                String insertTableSQL = """
                        INSERT INTO torque (maximo, nominal)
                            VALUES
                            ('21.0', '12.0')
                        """;
                statement.executeUpdate(insertTableSQL);
                
                
                insertTableSQL = """
                        INSERT INTO corrente (partida, nominal)
                            VALUES
                            ('21.0', '12.0')
                        """;
                statement.executeUpdate(insertTableSQL);
                

                insertTableSQL = """
                        INSERT INTO rpm (maximo, minimo)
                            VALUES
                            ('79000.0', '65000.0')
                        """;
                statement.executeUpdate(insertTableSQL);
                

                insertTableSQL = """
                        INSERT INTO protecao (nome, descricao)
                            VALUES
                            ('ABCD', 'ABCDEFGH')
                        """;
                statement.executeUpdate(insertTableSQL);
                

                insertTableSQL = """
                        INSERT INTO freio (tensao, potencia, torque)
                            VALUES
                            ('79.0', '5.0', '1.0')
                        """;
                statement.executeUpdate(insertTableSQL);
                

                insertTableSQL = """
                        INSERT INTO resolver (excitacao, frequencia)
                            VALUES
                            ('7.0', '10000.0')
                        """;
                statement.executeUpdate(insertTableSQL);
                

                insertTableSQL = """
                        INSERT INTO motor (marca, data_de_fabricacao, modelo, tensao_de_operacao, potencia_nominal, encoder, numero_de_registro, fabricacao, classe_de_isolamento, ptc, delta_da_temperatura, id_torque, id_corrente, id_rpm, id_protecao, id_freio, id_resolver)
                            VALUES
                            ('WEG', '2024-01-02 00:00:00', 'AC BRUSHLESS SERVOMOTOR', 230, 5400, 'Encoder?', '67890', 'FabricanteA', 'B', 155.0, 100.0, 1, 1, 1, 1, 1, 1)
                        """;
                statement.executeUpdate(insertTableSQL);                

        } catch (SQLException e) {
            
            System.err.println("Erro ao executar operações no banco de dados: " + e.getMessage());

        }

    }

}
