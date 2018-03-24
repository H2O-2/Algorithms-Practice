all:
	@echo "Compiling..."
	javac-algs4 Run.java
run:
test: clean
	@echo "Testing..."
	javac-algs4 WordNet.java
	java-algs4 WordNet ../test/wordnet/synsets.txt ../test/wordnet/hypernyms.txt
	# javac-algs4 SAP.java
	# java-algs4 SAP ../test/wordnet/digraph-wordnet.txt
	# javac-algs4 Outcast.java
	# java-algs4 Outcast ../test/wordnet/synsets.txt ../test/wordnet/hypernyms.txt ../test/wordnet/outcast5.txt ../test/wordnet/outcast8.txt ../test/wordnet/outcast11.txt
	# java-algs4 Outcast ../test/wordnet/synsets.txt ../test/wordnet/hypernyms.txt ../test/wordnet/outcast8.txt
clean:
	@echo "Clean up..."
	rm -rf *.class
