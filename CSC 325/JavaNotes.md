# Java Notes

**int** is a `primative type`, while **Integer** is a `reference type`

    int i = 3               Integer j = 3
    i --> 3                 j --> 0xABC --> 3

**Integer** can be set to `null` while **int** cannot

This is because `null` technically points towards memory address 0 which is **nothing**, or `null`

Since `reference types` are `classes` and all `classes` inherit from the **Object** class, this allows `reference types` to be used in **generics**

    int i = 3 --> 3            Integer i = 3 --> 0xABC --> 3
    int j = i --> 3            Integer j = i -----^
                                
                                i = 4 ~actually does~ i = new Integer(4)
                                so that j stays in reference to value 3

| `Value Type` | `Reference Type` |
  |:---:| :---:|
  | Is just a value and nothing else | Is an object of an entire class |
  | Holds the value itself | Holds a memory address to the value |
  | Doesn't have any methods | Has methods |
  | Cannot be set to null | Can be set to null |

    Scanner scanner = new Scanner(System.in);
    int x;
    int y = scanner.nextInt();

    if (y==0)
        x = 0;
    else
        x = 1;

This can be written in a much shorter way by using the `ternary operator`

    Scanner scanner = new Scanner(System.in);
    int x;
    int y = scanner.nextInt();

    x = (y == 0 ? 0 : 1);

The question mark goes after the syntax. If it evaluates to **TRUE** the **first value** is taken, if **FALSE** then the **second value** is taken

**Can also be written in python by doing**
    
    #   false true condition 
    x = ( 0 ,   1) [y == 0]
    #     0     1  [false/true]