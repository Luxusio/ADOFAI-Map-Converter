import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group "io.luxus"
version "1.5.1"
//mainClassName = "io.luxus.adofai.Program"

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "io.luxus.adofai.Program"
        attributes["Implementation-Title"] = rootProject.name
        attributes["Implementation-Version"] = rootProject.version
    }
//    from {
//        configurations.runtimeClasspath.collect {it.isDirectory() ? it : zipTree(it) }
//    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    implementation("org.javatuples:javatuples:1.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.assertj:assertj-core:3.21.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
