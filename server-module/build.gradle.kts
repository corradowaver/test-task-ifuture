import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.6.5"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("jvm") version "1.6.10"
  kotlin("plugin.spring") version "1.6.10"
  kotlin("plugin.jpa") version "1.6.10"
}

group = "com.corradowaver"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.springframework.boot:spring-boot-starter-cache:2.6.5")
  implementation("org.mockito:mockito-core:4.4.0")
  implementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
  implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.5")
  implementation("org.springframework.retry:spring-retry:1.3.2")
  runtimeOnly("org.postgresql:postgresql")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
