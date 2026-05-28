%% Gavin Dominique
% P4 - Parser

%%%%% Parsing and Pretty Printing %%%%%
%% use this predicate from SWI terminal, i.e. "parse_file."
parse_file :- parse_file("src.txt"). % invokes a version of parse_file that takes in a file
parse_file(FileName) :- 
        open(FileName, read, File),
        read_inputs(File),
        close(File).

read_inputs(File) :-
        read_line_to_string(File, Str),
        (   Str == end_of_file -> true;
            parse_lines(Str),
            read_inputs(File)
        ).

parse_lines(Line) :-
        make_tokens(Line, Tokens),
        printy_winty(Tokens),
        unit(Tokens).

printy_winty(Tokens) :- write(Tokens), write(' -> ').

make_tokens(Str, Tokens) :-
    re_replace("^[ \t]+|[ \t]+$" , "", Str, TrimmedStr),  % Trim spaces
    re_replace("\\s+", " ", TrimmedStr, CleanStr),  % Normalize spaces
    re_replace("([=+*])", " \\1 ", CleanStr, SpacedStr),  % Ensure operators have spaces around them
    split_string(SpacedStr, " ", "", Parts),  % Split properly
    maplist(fix_atoms, Parts, Tokens).       % Convert to atoms/numbers


fix_atoms(String, Token) :-
    string_codes(String, Codes),     % Convert string to character codes
    \+ member(32, Codes),            % Ensure it's not just a space (ASCII 32)
    atom_number(String, Num), !,      % Convert to number if possible
    Token = Num.
fix_atoms(String, Token) :-
    string_codes(String, Codes),     % Convert string to character codes
    \+ member(32, Codes),            % Ensure it's not just a space
    atom_string(Token, String).       % Convert to an atom

%%%%% Grammar %%%%%
% unit
unit(Tokens) :- assignment(Tokens), !.
unit(Tokens) :- print_call(Tokens), !.

% assignment
assignment(['set', Ident, '='| Val]) :-
        ident(Ident),                                               %% check ident is good
        rvalue(Val, Return), nb_setval(Ident, Return),              %% if val is good then set variable
        write(Ident), write(' = '), write(Return), nl.              %% print out Var = val

% print
print_call(['print' | Var]) :- rvalue(Var, Result), write(Result), nl.

% rvalue
rvalue([Var], Result) :- rvalue(Var, Result).
rvalue(Tokens, Result) :- math_exp(Tokens, Result).                 %% rvalue if it is a math expression
rvalue(Num, Val) :- num(Num, Val).                                  %% rvalue if it is jsut a number
rvalue(Ident, Val) :-           
        ident(Ident),
        nb_current(Ident, Val).                                     %% rvalue if it is an indentifier

% math_exp
math_exp([Num], Num) :- number(Num).

math_exp([Left, '+', Right | Rest], Result) :- 
        rvalue(Left, L),
        rvalue(Right, R),
        Sum is L + R,
        math_exp([Sum | Rest], Result).

math_exp([Left, '*', Right | Rest], Result) :- 
        rvalue(Left, L),
        rvalue(Right, R),
        Sum is L * R,
        math_exp([Sum | Rest], Result).

%%%%% Terminal Tokens %%%%%
set('set').
print('print').
equals('=').
add('+').
mul('*').

ident(Ident) :- atom(Ident), \+ number(Ident).                      %% match an identifier
num(Num, Val) :- number(Num), Val is Num.                           %% match a number and get its value