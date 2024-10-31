# Compiler

## 📜 Descrição

A aplicação tem o objetivo servir como compilador para conversão de programas escritos em uma linguagem específica, definida pelo grupo, para
uma linguagem de programação destino, como Java, JS, Python, entre outras. Esse compilador irá processar o arquivo de entrada contendo o código
fonte na linguagem original, e gerar um código equivalente na linguagem destino, sem que existam erros.

## 🚀 Tecnologias

- Java
- Maven
- Antlr4

## 📦 Instruções de Uso

1. Clone o repositório com o Git.
```bash
git clone https://github.com/Equipe-Rocket-Facs/compiler.git
```

2. Entre na pasta raiz do projeto.
```bash
cd compiler
```

3. Faça a compilação do projeto.
```bash
mvn clean compile
```

4. Execute a aplicação especificando um arquivo de entrada:
```bash
mvn exec:java -Dexec.args="/caminho/para/o/arquivo.txt"
```

5. Compile o código java gerado
```bash
javac Main.java
```

6. Execute o código java compilado
```bash
java Main
```

## 👥 Membros da Equipe

Esses são os integrantes da equipe responsáveis pelo andamento do projeto, indivíduos que de certa forma contribuíram para a realização do que foi solicitado:

- Diego Pimenta dos Anjos
- Fernando de Caires Gonçalves
- Hélio José da Silva Júnior
- Lucca Cintra Poggio
- Reinan Carvalho Amaral
