%% Gavin Dominique
%% Program 4 - Parser
%% Date - Feb 26, 2025

%%%%% Parsing and Pretty Printing %%%%%
%% use this predicate from SWI terminal, i.e. "parse_file."
parse_file :- parse_file("src.txt"). % invokes a version of parse_file that takes in a file
parse_file(FileName) :- 
        open(FileName, read, File),
        read_inputs(File),
        close(File).

read_inputs(File) :-
    read_line_to_string(File, Str),
    (   Str == end_of_file -> true
    ;   Str == "" -> read_inputs(File)  % Skip empty lines
    ;   parse_lines(Str),
        read_inputs(File)
    ).


parse_lines(Line) :-
    %write('Raw Line: "'), write(Line), write('"'), nl,  % Debug print
    clean_up(Line, CleanLine),
    make_tokens(CleanLine, Tokens),
    printy_winty(Tokens),
    unit(Tokens).


clean_up(Str, CleanStr) :-
    re_replace("^[ \t]+|[ \t]+$", "", Str, Fix1),                              2             % Trim spaces
    re_replace("\\s*([=+*])\\s*", " \\1 ", Fix1, Fix2),                                     % make space for operators
    re_replace("([0-9])([+*=])", "\\1 \\2 ", Fix2, Fix3),                                   % ^ for numbers
    re_replace("([+*=])([0-9a-zA-Z])", " \\1 \\2", Fix3, Fix4),                             % double check ^
    re_replace("\\s+", " ", Fix4, CleanStr).                                                % no multiple spaces


%printy_winty(Tokens) :- write(Tokens), write('-> ').
printy_winty([]) :- write('-> ').
printy_winty([T|Okens]) :-
    write(T),
    write(' '),
    printy_winty(Okens).


make_tokens(Str, Tokens) :-
    re_replace("\\s+", " ", Str, Fix1),                                                     % Remove extra spaces
    split_string(Fix1, " ", "", Parts),                                                     % Split into tokens
    exclude(=(""), Parts, CleanParts),                                                      % Remove empty elements
    maplist(fix_atoms, CleanParts, Tokens).


fix_atoms(String, Token) :-
    string_codes(String, Codes),                                                % Convert string to character codes
    \+ member(32, Codes),                                                       % Ensure it's not just a space
    atom_number(String, Num), !,                                                % Convert to number if possible
    Token = Num.
fix_atoms(String, Token) :-
    string_codes(String, Codes),                                                % Convert string to character codes
    \+ member(32, Codes),                                                       % Ensure it's not just a space
    atom_string(Token, String).                                                 % Convert to atom


%%%%% Grammar %%%%%
% unit
unit(Tokens) :- 
    (assignment(Tokens) ; print_call(Tokens)), !.

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
    (   nb_current(Ident, Val) 
    ->  true 
    ;   format("Error: Undefined variable ~w~n", [Ident]), fail).

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