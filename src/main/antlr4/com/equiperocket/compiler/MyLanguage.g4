grammar MyLanguage;

// Regras de inicialização
prog: 'programa' decls commands 'fimprog';

// Declaração de variáveis
decls: decl*;
decl: type idList;

// Tipos de variáveis
type: 'numero' | 'texto' | 'bool';

// Lista de identificadores
idList: ID (',' ID)*;

// Comandos
commands: command*;
command : readInput
        | writeOutput
        | attribution
        | ifStmt
        | whileStmt
        | forStmt;

// Comando de leitura
readInput: 'leia' '(' ID ')';
// Comando de escrita
writeOutput: 'escreva' '(' (TEXT | expr | BOOL) ('+' (TEXT | expr | BOOL))* ')';
// Atribuição
attribution: ID '=' (expr | boolExpr | TEXT);

// Estrutura if ... else
ifStmt: 'if' '(' condition ')' block (('if else' '(' condition ')' block)* 'else' block)?;
// Estrutura while
whileStmt: 'while' '(' condition ')' block;
// Estrutura for
forStmt: 'for' '(' attribution ';' condition (';' attribution)? ')' block;

// Bloco de comandos
block: '{' (command)+ '}';

// Condição
condition: boolExpr;

// Expressões booleanas com operadores lógicos
boolExpr: 'NAO' boolExpr
        | boolExpr 'E' boolExpr
        | boolExpr 'OU' boolExpr
        | '(' boolExpr ')'
        | expr relOp expr
        | BOOL;

// Expressões aritméticas, obedecendo precedência
expr: '(' expr ')'
    | expr ('*' | '/') expr
    | expr ('+' | '-') expr
    | NUM
    | ID;

// Operadores relacionais
relOp: '<' | '>' | '<=' | '>=' | '==' | '!=';

// Tokens
ID: [a-zA-Z_][a-zA-Z_0-9]*;
NUM: [0-9]+ ('.' [0-9]+)?;
TEXT: '"' ( ~["\\] | '\\' . )* '"';
BOOL: 'VERDADEIRO' | 'FALSO';

// Ignorar espaços e comentários
WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;
COMMENT_MULTILINE: '/*' .*? '*/' -> skip;
