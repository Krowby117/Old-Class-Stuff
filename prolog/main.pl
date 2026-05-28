teacher(kevin).

person(kevin).

student(bob).

parent(pam, bob). % pam is the parent of bob
parent(tom, bob).
parent(tom, liz).
parent(bob, ann).
parent(bob, pat).
parent(pat, jim).

% parent(bob, X) will show everyone that bob is a parent of
% parent(X, bob) will show everyone that is a parent of bob
% parent(X, ann) parent(Y, X) who(X) is the parent of ann and who is their parent
