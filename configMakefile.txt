cat Makefile.original | awk '{gsub("LOCALIP","192.168.1.110");printf("%s\n",$0);}'> Makefile
