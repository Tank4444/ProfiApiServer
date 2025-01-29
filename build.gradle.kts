val exposed_version: String by project
val h2_version: String by project
val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.1.0"
    id("io.ktor.plugin") version "3.0.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
    //graalvm
    id("org.graalvm.buildtools.native") version "0.9.19"
}

group = "ru.chuikov"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.cio.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("io.ktor:ktor-server-swagger")
    implementation("io.ktor:ktor-server-openapi")
    implementation("io.ktor:ktor-server-forwarded-header")
    implementation("io.ktor:ktor-server-cors")
    implementation("io.ktor:ktor-server-host-common")
    implementation("io.ktor:ktor-server-request-validation")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")
    implementation("io.ktor:ktor-server-cio")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("com.zaxxer:HikariCP:3.4.2")
    implementation("io.ktor:ktor-server-default-headers")
    implementation("io.ktor:ktor-server-resources")

}

graalvmNative {
    binaries {

        named("main") {
            resources.autodetect()

            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(21))
                vendor.set(JvmVendorSpec.matching("GraalVM Community"))
            })

            fallback.set(false)
            verbose.set(true)

            with(buildArgs) {
                add("--initialize-at-build-time=ch.qos.logback")
                add("--initialize-at-build-time=io.ktor,kotlin,kotlinx.serialization")
                add("--initialize-at-build-time=org.slf4j.LoggerFactory")
                add("--initialize-at-build-time=ch.qos.logback.classic.Logger")

                add("--initialize-at-run-time=io.netty.handler.ssl.BouncyCastleAlpnSslUtils")
                add("--initialize-at-run-time=io.netty.channel.epoll.Epoll")
                add("--initialize-at-run-time=io.netty.channel.epoll.Native")
                add("--initialize-at-run-time=io.netty.channel.epoll.EpollEventLoop")
                add("--initialize-at-run-time=io.netty.channel.epoll.EpollEventArray")
                add("--initialize-at-run-time=io.netty.channel.DefaultFileRegion")
                add("--initialize-at-run-time=io.netty.channel.kqueue.KQueueEventArray")
                add("--initialize-at-run-time=io.netty.channel.kqueue.KQueueEventLoop")
                add("--initialize-at-run-time=io.netty.channel.kqueue.KQueue")
                add("--initialize-at-run-time=io.netty.channel.kqueue.Native")
                add("--initialize-at-run-time=io.netty.channel.unix.Errors")
                add("--initialize-at-run-time=io.netty.channel.unix.IovArray")
                add("--initialize-at-run-time=io.netty.channel.unix.Limits")
                add("--initialize-at-run-time=io.netty.util.internal.logging.Log4JLogger")

                add("-H:+UnlockExperimentalVMOptions")
                add("-H:+InstallExitHandlers")
                add("-H:+ReportUnsupportedElementsAtRuntime")
                add("-H:+ReportExceptionStackTraces")
                add("-H:+BuildOutputColorful")
            }
            imageName.set("graalvm-server")
        }
    }
}