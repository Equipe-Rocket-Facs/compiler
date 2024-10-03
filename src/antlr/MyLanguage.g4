grammar MyLanguage;

// Aplicação começa com programa e termina com fimprog
prog: 'programa' decls bloco 'fimprog';

// Declarações de variáveis
decls: (decl ';')+;
decl: tipo idList;

// Tipos de variáveis
tipo: 'inteiro' | 'decimal' | 'string';

// Lista de identificadores
idList: ID (',' ID)*;

// Bloco de comandos
bloco: '{' comandos '}';

// Comandos
comandos: comando+;
comando: leitura | escrita | atribuicao | ifStmt | whileStmt | forStmt;

// Comando de leitura
leitura: 'leia' '(' ID ')';

// Comando de escrita
escrita: 'escreva' '(' (TEXT | ID) ')';

// Atribuição
atribuicao: ID '=' expr;

// Estrutura if ... else
ifStmt: 'if' '(' expr relOp expr ')' bloco ('else' bloco)?;

// Estrutura while
whileStmt: 'while' '(' expr relOp expr ')' bloco;

// Estrutura for
forStmt: 'for' '(' atribuicao ';' expr ';' atribuicao ')' bloco;

// Operadores relacionais
relOp: '<' | '>' | '<=' | '>=' | '==' | '!=';

// Expressões aritméticas
expr: expr ('+' | '-') termo | termo;
termo: termo ('*' | '/') fator | fator;
fator: NUM | ID | '(' expr ')';

// Tokens
ID: [a-zA-Z_][a-zA-Z_0-9]* ;
NUM: [0-9]+ ('.' [0-9]+)? ;
TEXT: '"' (~["\r\n])* '"' ;

// Ignorar espaços e comentários
WS: [ \t\r\n]+ -> skip ;
COMMENT: '//' ~[\r\n]* -> skip ;
