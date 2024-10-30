grammar MyLanguage;

// Regras de inicialização
prog: 'programa' decls commands 'fimprog';

// Declaração de variáveis com tipo
decls: decl*;
decl: type declList;

// Lista de itens declarados
declList: ID (',' ID)*;

// Tipos de variáveis
type: 'inteiro' | 'decimal' | 'texto' | 'bool';

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
writeOutput: 'escreva' '(' (TEXT | BOOL | expr) ('+' (TEXT | BOOL | expr))* ')';
// Atribuição
attribution: ID '=' (boolExpr | expr | TEXT);

// Estrutura if else
ifStmt: 'if' '(' condition ')' block (('if else' '(' condition ')' block)* 'else' block)?;
// Estrutura while
whileStmt: 'while' '(' condition ')' block;
// Estrutura for
forStmt: 'for' '(' attribution ';' condition (';' attribution)? ')' block;

// Bloco de comandos
block: '{' commands '}';

// Condição
condition: boolExpr;

// Expressões booleanas com operadores lógicos
boolExpr: boolExpr 'OU' boolExpr
        | boolExpr 'E' boolExpr
        | boolExpr '==' boolExpr
        | boolExpr '!=' boolExpr
        | 'NAO' boolExpr
        | relExpr
        | '(' boolExpr ')'
        | BOOL
        | ID;

relExpr: expr relOp expr;

// Operadores relacionais
relOp: '<' | '>' | '<=' | '>=' | '==' | '!=';

// Expressões aritméticas, obedecendo precedência
expr: '(' expr ')'
    | expr mathOp expr
    | NUM_INT
    | NUM_DEC
    | ID;

// Operadores matemáticos
mathOp: ('*' | '/') | ('+' | '-');

// Tokens
BOOL: 'VERDADEIRO' | 'FALSO';
ID: [a-zA-Z_][a-zA-Z_0-9]*;
NUM_INT: [0-9]+;
NUM_DEC: [0-9]+ '.' [0-9]+;
TEXT: '"' ( ~["\\] | '\\' . )* '"';

// Ignorar espaços e comentários
WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;
COMMENT_MULTILINE: '/*' .*? '*/' -> skip;
