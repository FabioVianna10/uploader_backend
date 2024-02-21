Backend do Projeto de Upload de Arquivos XML


Este projeto consiste no backend de uma aplicação web desenvolvida com Java e Spring Boot. O objetivo principal do backend é fornecer endpoints para lidar com o upload de arquivos XML para um banco de dados PostgreSQL.


Funcionalidades
Armazenamento de Arquivos no Banco de Dados: O backend recebe os arquivos XML enviados pelos usuários e os armazena no banco de dados PostgreSQL.
Tabela para Arquivos Binários: Uma tabela específica é criada no banco de dados para armazenar os arquivos XML como dados binários.
Endpoints para Upload e Download: O backend fornece endpoints para fazer o upload de arquivos XML e para baixar arquivos armazenados no banco de dados.
Tecnologias Utilizadas
Java: O backend foi desenvolvido utilizando a linguagem de programação Java, oferecendo uma base sólida e amplamente utilizada para desenvolvimento de aplicativos web.
Spring Boot: O framework Spring Boot foi utilizado para facilitar o desenvolvimento do backend, oferecendo configuração simplificada, injeção de dependência e outros recursos que aceleram o processo de desenvolvimento.
PostgreSQL: O banco de dados PostgreSQL foi escolhido para armazenar os arquivos XML enviados pelos usuários, garantindo segurança e confiabilidade no armazenamento dos dados.


- Como Executar o Projeto
  - Clone o repositório do backend para o seu ambiente local.
  - Certifique-se de ter o Java JDK 21 e o Maven instalados em sua máquina.
  - Configure as credenciais de acesso ao banco de dados PostgreSQL no arquivo de configuração do Spring Boot.
  - Execute o comando mvn spring-boot:run para iniciar o servidor.
  - O backend estará acessível através dos endpoints definidos no projeto, geralmente em http://localhost:8080.
 
    
  -   - Video do projeto rodando - https://www.loom.com/share/0e6e848d110a49b3906048269101829a?sid=8ffde4a1-2e68-458c-96f6-a0d355e0b3a4
