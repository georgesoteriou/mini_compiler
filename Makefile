all: build

build: dist
	./gradlew assemble

dist:
	./gradlew installDist

clean:
	./gradlew clean

test: all
	./gradlew test

.PHONY: all build clean test
