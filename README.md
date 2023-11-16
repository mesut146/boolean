# boolean

a java library for boolean algebra that supports infinite variables

# grammar

| operator   | grammar                                                                        |
|------------|--------------------------------------------------------------------------------|
| and        | expression = expression ( ```and``` , ```&``` , ```*``` , ```.``` ) expression |
| or         | expression = expression ( ```or```   , &#124; , ```+``` ) expression           |
| xor        | expression = expression ( ```xor```  , ```^```) expression                     |
| nor        | expression = expression ( ```nor``` ) expression                               |
| nand       | expression = expression ( ```nand``` ) expression                              |
| xnor       | expression = expression ( ```xnor``` )  expression                             |
| not/invert | expression = expression ```'```                                                |
| not/invert | expression = ```~``` expression                                                |

# examples
**full adder**
```java
func sum = func.parse("(a xor b) xor c");
func carry_out = func.parse("(c and (a xor b)) or (b and a)");
System.out.println(new TruthTable(sum, carry_out).toString());
```
*output:*
```
F1=(a xor b) xor c
F2=(c and (a xor b)) or (b and a)
a b c | F1 F2
------------
0 0 0 | 0 0
0 0 1 | 1 0
0 1 0 | 1 0
0 1 1 | 0 1
1 0 0 | 1 0
1 0 1 | 0 1
1 1 0 | 0 1
1 1 1 | 1 1
```

**invert**
```java
func f = func.parse("a and (b xor c)");
System.out.println(f.not());
```
*output*:

`a nand (b xor c)`


**print with only and , or gates**

```java
System.out.println(f.alternate());
```

output:

`a and ((b and c') or (b' and c))`



