########################################
#~ définitions
########################################

# nom de l'executable
BIN=./bin/tpnote

# liste des fichiers sources a completer 
SRCS=afaire.c

default: $(BIN)

########################################
#~ regles pour l'executable
########################################

obj/%.o: %.c
	gcc -Wall -Iinclude -c $< -o $@

bin/tpnote: $(SRCS:%.c=obj/%.o)
	gcc  -o $@ obj/tpnote.o $+ -lpthread

clean:
	rm -f $(BIN) obj/afaire.o *~
