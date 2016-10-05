JRLISP
======

Mini-lisp implementation for the first programming contest in memory of
Josep Mª Ribó Balust.

#Compilation

This project uses Maven to manage all the dependencies and compile the code. 
To do so, having Maven already installed, open a command line on the project
directory and type:

`$ mvn compile`

#REPL Execution

Once the project has been compiled, to run the REPL type:

`$ java -cp target/classes/ cat.udl.eps.butterp.main.Repl`

##Examples

Here I list some snippets that show some of the operations supported by the 
mini-lisp interpreter.

####Factorial

```common-lisp
> (define factorial 
#   (lambda (n)
#     (if (eq n 0)
#         1
#         (mult n
#               (factorial (add n - 1))))))
              
> (factorial 6)
720

```
