This is the grammar used for parsing javap output
S -> Compiled from "[a-Z]*.java" \n ClassLine \n InnerLine*
ClassLine -> modifiers* class [a-Z, .]+ ClassTail
ClassTail -> extends [a-Z, .]+ Interfaces | ε 
Interfaces -> implements [a-Z, .] { | ε 
InnerLine -> modifiers* InnerTail  | ε 
Break traditional parsing rules here. Each line from here on will either be a field or a method. A method will contain (*); when a field will not.
Since the representations for these are substantially different, it is simpler to determine which kind of line is being read initially.
MethodLine -> modifiers* TypeVoid ( Params );
TypeVoid -> DataType | void
Params -> [DataType,]*

FieldLine -> modifiers* DataType [a-Z]*;