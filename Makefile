all: build  dist

build:
	./gradlew assemble

dist:
	./gradlew installDist

clean:
	./gradlew clean

test:
	./gradlew test

.PHONY: all build dist clean test
