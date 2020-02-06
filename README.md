# boolean

a java library for boolean algebra that supports infinite variables

# grammar

expression = expression ( 'and' | '&&' | '&' | '.' | '\*' ) expression

expression = expression ( 'or' | '||' | '|' | '+' ) expression

expression = expression ( 'xor' | '^' ) expression

expression = expression ( 'nor' ) expression

expression = expression ( 'nand' ) expression

expression = expression ( 'xnor' ) expression

# example

func f = func.parse("a and (b xor c)");
System.out.println(f);
output: a and (b xor c)

System.out.println(f.not()); //prints inverted expression
output: a' or (b xnor c)

System.out.println(f.truthTable()); //prints truth table
output:
