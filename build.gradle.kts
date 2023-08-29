plugins {
	java
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
}

group = "com.doers"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation( "org.hsqldb:hsqldb:2.7.2")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation ("javax.servlet:javax.servlet-api:4.0.1")
	implementation ("io.springfox:springfox-swagger2:2.9.2")
	implementation ("io.springfox:springfox-swagger-ui:2.9.2")



	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// JUnit 5 libraries
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

	// Mockito for mocking
	testImplementation("org.mockito:mockito-junit-jupiter:3.12.4")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
