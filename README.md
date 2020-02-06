# boolean

a java library for boolean algebra that supports infinite variables

# grammar

- and: expression = expression ( 'and' | '&&' | '&' | '.' | '\*' ) expression

- or: expression = expression ( 'or' | '||' | '|' | '+' ) expression

- xor: expression = expression ( 'xor' | '^' ) expression

- nor: expression = expression ( 'nor' ) expression

- nand: expression = expression ( 'nand' ) expression

- xnor: expression = expression ( 'xnor' ) expression

- not/invert: expression = expression ( ' | ! )

- not/invert: expression = ~ expression

# examples

`func f = func.parse("a and (b xor c)");`

`System.out.println(f);`

output: a and (b xor c)

prints inverted expression

`System.out.println(f.not());`

output: a nand (b xor c)

`System.out.println(f.not().alternate());`

output: a nand (b xor c)

prints only with and , or gates

`System.out.println(f.alternate());`

output:

prints truth table

`System.out.println(f.truthTable());`

output:
