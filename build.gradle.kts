plugins {
	java
	id("org.springframework.boot") version "2.7.13"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "kr.co.cr"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	//	db
	implementation("org.springframework.boot:spring-boot-starter-jdbc") // Jdbc, Driver
	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("com.h2database:h2")
	implementation("com.h2database:h2")

	// lombok
	implementation("org.projectlombok:lombok")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
