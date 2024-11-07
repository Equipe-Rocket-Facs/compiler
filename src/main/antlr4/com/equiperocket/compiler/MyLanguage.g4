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
attribution: ID '=' (expr | boolExpr | TEXT);

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

// Expressões booleanas
boolExpr: boolExpr 'OU' boolTerm
        | boolTerm;

boolTerm: boolTerm 'E' boolFactor
        | boolFactor;

boolFactor: boolFactor '==' boolExprBase
          | boolFactor '!=' boolExprBase
          | boolExprBase;

boolExprBase: 'NAO' boolExpr
            | relExpr
            | BOOL
            | ID
            | '(' boolExpr ')';

// Expressões relacionais
relExpr: expr relOp expr;

// Operadores relacionais
relOp: '<' | '>' | '<=' | '>=' | '==' | '!=';

// Expressões aritméticas, obedecendo precedência
expr: expr '+' term
    | expr '-' term
    | term;

term: term '*' factor
    | term '/' factor
    | factor;

factor: NUM_INT
      | NUM_DEC
      | ID
      | '(' expr ')';

// Tokens
BOOL: 'VERDADEIRO' | 'FALSO';
ID: [a-zA-Z_][a-zA-Z_0-9]*;
NUM_INT: [-+]?[0-9]+;
NUM_DEC: [-+]?[0-9]+ '.' [0-9]+;
TEXT: '"' ( ~["\\] | '\\' . )* '"';

// Ignorar espaços e comentários
WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;
COMMENT_MULTILINE: '/*' .*? '*/' -> skip;
