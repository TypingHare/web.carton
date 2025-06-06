plugins {
    kotlin("jvm") version "2.0.21"
}

group = "burrow"
version = "0.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(files(System.getProperty("user.home") + "/.local/share/burrow/lib/burrow.jar"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveBaseName.set("web.carton")
    archiveVersion.set("")
}