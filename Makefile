todo: todo.o
	java -classpath C:\Users\Vinamra\Documents\NetBeansProjects\java Todo

todo.o:
	javac Todo.java

test: todo install
	npm run test

clean:
	rm -f Todo.class package-lock.json
	rm -rf node_modules

install:
	npm install
