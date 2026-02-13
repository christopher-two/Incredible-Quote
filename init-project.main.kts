#!/usr/bin/env kotlin

/**
 * ========================================================================================
 * SCRIPT DE INICIALIZACIÓN DEL TEMPLATE
 * ========================================================================================
 * Este script transforma el proyecto template en un nuevo proyecto personalizado
 * renombrando packages, namespaces, y configuraciones automáticamente.
 * ========================================================================================
 */

import java.io.File
import java.time.LocalDateTime
import kotlin.system.exitProcess

// Colores ANSI para la terminal
object Colors {
    const val RESET = "\u001B[0m"
    const val GREEN = "\u001B[32m"
    const val RED = "\u001B[31m"
    const val YELLOW = "\u001B[33m"
    const val BLUE = "\u001B[34m"
    const val CYAN = "\u001B[36m"
    const val BOLD = "\u001B[1m"
}

// Configuración del proyecto
data class ProjectConfig(
    val projectName: String,
    val packageBase: String,
    val appId: String,
    val appDisplayName: String,
    val templateVersion: String = "1.0.0"
)

// Valores por defecto del template
object TemplateDefaults {
    const val PACKAGE_BASE = "org.override.tamplete"
    const val DATABASE_NAME = "tamplete_database"
}

fun main() {
    println("${Colors.CYAN}${Colors.BOLD}")
    println("╔═══════════════════════════════════════════════════════════════╗")
    println("║      INICIALIZADOR DE TEMPLATE - PROYECTO ANDROID            ║")
    println("╚═══════════════════════════════════════════════════════════════╝")
    print(Colors.RESET)

    val projectRoot = File(System.getProperty("user.dir"))

    // Verificar si ya fue inicializado
    val initMarker = File(projectRoot, ".template-initialized")
    if (initMarker.exists()) {
        println("${Colors.YELLOW}⚠️  Este proyecto ya ha sido inicializado.${Colors.RESET}")
        print("¿Deseas reinicializar? Esto SOBRESCRIBIRÁ los cambios (s/N): ")
        val response = readln().trim().lowercase()
        if (response != "s" && response != "si" && response != "yes") {
            println("${Colors.BLUE}Cancelado por el usuario.${Colors.RESET}")
            exitProcess(0)
        }
    }

    try {
        // Recopilar información del nuevo proyecto
        val config = collectProjectInfo()

        // Confirmar antes de proceder
        if (!confirmConfiguration(config)) {
            println("${Colors.BLUE}Cancelado por el usuario.${Colors.RESET}")
            exitProcess(0)
        }

        println("\n${Colors.CYAN}${Colors.BOLD}🚀 Iniciando transformación del template...${Colors.RESET}\n")

        // Crear backup opcional
        if (askForBackup()) {
            createBackup(projectRoot)
        }

        // Ejecutar transformación
        transformProject(projectRoot, config)

        // Marcar como inicializado
        initMarker.writeText("""
            Template Version: ${config.templateVersion}
            Initialized: ${LocalDateTime.now()}
            Project Name: ${config.projectName}
            Package: ${config.packageBase}
        """.trimIndent())

        println("\n${Colors.GREEN}${Colors.BOLD}✅ ¡Template inicializado exitosamente!${Colors.RESET}\n")
        println("${Colors.CYAN}Próximos pasos:${Colors.RESET}")
        println("  1. Sincroniza el proyecto con Gradle")
        println("  2. Limpia y reconstruye: ./gradlew clean build")
        println("  3. ¡Comienza a desarrollar!")
        println()

    } catch (e: Exception) {
        println("\n${Colors.RED}❌ Error durante la inicialización:${Colors.RESET}")
        println("${Colors.RED}${e.message}${Colors.RESET}")
        e.printStackTrace()
        exitProcess(1)
    }
}

fun collectProjectInfo(): ProjectConfig {
    println("${Colors.BOLD}Configuración del nuevo proyecto:${Colors.RESET}\n")

    // Nombre del proyecto
    print("${Colors.CYAN}Nombre del proyecto${Colors.RESET} (ej: MyAwesomeApp): ")
    val projectName = readln().trim().takeIf { it.isNotEmpty() }
        ?: throw IllegalArgumentException("El nombre del proyecto no puede estar vacío")

    // Package base
    print("${Colors.CYAN}Package base${Colors.RESET} (ej: com.mycompany.myapp): ")
    val packageBase = readln().trim().takeIf { it.isNotEmpty() }
        ?: throw IllegalArgumentException("El package no puede estar vacío")

    if (!isValidPackageName(packageBase)) {
        throw IllegalArgumentException("Package inválido. Debe seguir el formato: com.company.app")
    }

    // Application ID (por defecto igual al package)
    print("${Colors.CYAN}Application ID${Colors.RESET} [${packageBase}]: ")
    val appIdInput = readln().trim().takeIf { it.isNotEmpty() } ?: packageBase

    if (!isValidPackageName(appIdInput)) {
        throw IllegalArgumentException("Application ID inválido. Debe seguir el formato: com.company.app")
    }

    // Nombre visible de la app
    print("${Colors.CYAN}Nombre visible de la app${Colors.RESET} [${projectName}]: ")
    val appDisplayName = readln().trim().takeIf { it.isNotEmpty() } ?: projectName

    return ProjectConfig(
        projectName = projectName,
        packageBase = packageBase,
        appId = appIdInput,
        appDisplayName = appDisplayName
    )
}

fun isValidPackageName(packageName: String): Boolean {
    val regex = Regex("^[a-z][a-z0-9_]*(\\.[a-z][a-z0-9_]*)+$")
    return regex.matches(packageName)
}

fun confirmConfiguration(config: ProjectConfig): Boolean {
    println("\n${Colors.YELLOW}${Colors.BOLD}Confirma la configuración:${Colors.RESET}")
    println("  Nombre del proyecto: ${Colors.GREEN}${config.projectName}${Colors.RESET}")
    println("  Package base:        ${Colors.GREEN}${config.packageBase}${Colors.RESET}")
    println("  Application ID:      ${Colors.GREEN}${config.appId}${Colors.RESET}")
    println("  Nombre visible:      ${Colors.GREEN}${config.appDisplayName}${Colors.RESET}")
    println()

    print("¿Es correcta esta configuración? (S/n): ")
    val response = readln().trim().lowercase()
    return response != "n" && response != "no"
}

fun askForBackup(): Boolean {
    print("${Colors.YELLOW}¿Deseas crear un backup antes de continuar?${Colors.RESET} (S/n): ")
    val response = readln().trim().lowercase()
    return response != "n" && response != "no"
}

fun createBackup(projectRoot: File) {
    println("\n${Colors.CYAN}📦 Creando backup...${Colors.RESET}")

    val timestamp = LocalDateTime.now().toString().replace(":", "-").replace(".", "-")
    val backupDir = File(projectRoot.parentFile, "${projectRoot.name}_backup_$timestamp")

    projectRoot.copyRecursively(backupDir, overwrite = false)
    println("${Colors.GREEN}✅ Backup creado en: ${backupDir.absolutePath}${Colors.RESET}")
}

fun transformProject(projectRoot: File, config: ProjectConfig) {
    // 1. Actualizar settings.gradle.kts
    println("${Colors.CYAN}📝 Actualizando settings.gradle.kts...${Colors.RESET}")
    updateSettingsGradle(projectRoot, config)

    // 2. Actualizar app/build.gradle.kts
    println("${Colors.CYAN}📝 Actualizando app/build.gradle.kts...${Colors.RESET}")
    updateAppBuildGradle(projectRoot, config)

    // 3. Actualizar AndroidManifest.xml
    println("${Colors.CYAN}📝 Actualizando AndroidManifest.xml...${Colors.RESET}")
    updateAndroidManifest(projectRoot, config)

    // 4. Actualizar archivos XML de themes
    println("${Colors.CYAN}📝 Actualizando themes.xml...${Colors.RESET}")
    updateThemeFiles(projectRoot, config)

    // 5. Actualizar strings.xml
    println("${Colors.CYAN}📝 Actualizando strings.xml...${Colors.RESET}")
    updateStringsXml(projectRoot, config)

    // 6. Renombrar y actualizar packages de Kotlin
    println("${Colors.CYAN}📦 Reorganizando packages de Kotlin...${Colors.RESET}")
    reorganizePackages(projectRoot, config)

    println("${Colors.GREEN}✅ Todos los archivos actualizados${Colors.RESET}")
}

fun updateSettingsGradle(projectRoot: File, config: ProjectConfig) {
    val file = File(projectRoot, "settings.gradle.kts")
    val content = file.readText()
    val updated = content.replace(
        """rootProject.name = "Tamplete"""",
        """rootProject.name = "${config.projectName}""""
    )
    file.writeText(updated)
}

fun updateAppBuildGradle(projectRoot: File, config: ProjectConfig) {
    val file = File(projectRoot, "app/build.gradle.kts")
    var content = file.readText()

    // Primero reemplazar la variable nameProject
    content = content.replace(
        """val nameProject = "tamplete"""",
        """val nameProject = "${config.projectName.lowercase()}""""
    )

    // Ahora reemplazar las referencias al namespace y applicationId
    // Necesitamos escapar el $ para buscar el texto literal "$nameProject"
    content = content.replace(
        "namespace = \"org.override.\$nameProject\"",
        "namespace = \"${config.packageBase}\""
    )

    content = content.replace(
        "applicationId = \"org.override.\$nameProject\"",
        "applicationId = \"${config.appId}\""
    )

    file.writeText(content)
}

fun updateAndroidManifest(projectRoot: File, config: ProjectConfig) {
    val file = File(projectRoot, "app/src/main/AndroidManifest.xml")
    val content = file.readText()

    val themeName = "Theme.${config.projectName.replace(" ", "")}"

    val updated = content
        .replace("""@style/Theme.Tamplete""", """@style/$themeName""")

    file.writeText(updated)
}

fun updateThemeFiles(projectRoot: File, config: ProjectConfig) {
    val themeName = "Theme.${config.projectName.replace(" ", "")}"
    val themeFiles = listOf(
        "app/src/main/res/values/themes.xml",
        "app/src/main/res/values-night/themes.xml",
        "app/src/main/res/values-v31/themes.xml"
    )

    themeFiles.forEach { path ->
        val file = File(projectRoot, path)
        if (file.exists()) {
            val content = file.readText()
            val updated = content.replace(
                """<style name="Theme.Tamplete"""",
                """<style name="$themeName""""
            )
            file.writeText(updated)
        }
    }
}

fun updateStringsXml(projectRoot: File, config: ProjectConfig) {
    val file = File(projectRoot, "app/src/main/res/values/strings.xml")
    val content = file.readText()
    val updated = content.replace(
        """<string name="app_name">Tamplete</string>""",
        """<string name="app_name">${config.appDisplayName}</string>"""
    )
    file.writeText(updated)
}

fun reorganizePackages(projectRoot: File, config: ProjectConfig) {
    val sourceSets = listOf(
        "app/src/main/java",
        "app/src/test/java",
        "app/src/androidTest/java"
    )

    sourceSets.forEach { sourceSet ->
        val sourceDir = File(projectRoot, sourceSet)
        if (sourceDir.exists()) {
            reorganizeSourceSet(sourceDir, config)
        }
    }
}

fun reorganizeSourceSet(sourceDir: File, config: ProjectConfig) {
    val oldPackagePath = TemplateDefaults.PACKAGE_BASE.replace(".", "/")
    val oldPackageDir = File(sourceDir, oldPackagePath)

    if (!oldPackageDir.exists()) {
        println("${Colors.YELLOW}  ⚠️  Directorio no encontrado: ${oldPackageDir.path}${Colors.RESET}")
        return
    }

    // Crear nuevo directorio de package
    val newPackagePath = config.packageBase.replace(".", "/")
    val newPackageDir = File(sourceDir, newPackagePath)
    newPackageDir.mkdirs()

    // Contar y mover archivos
    val files = oldPackageDir.walkTopDown()
        .filter { it.isFile && it.extension == "kt" }
        .toList()

    println("${Colors.CYAN}  📄 Procesando ${files.size} archivos Kotlin en ${sourceDir.name}...${Colors.RESET}")

    files.forEach { file ->
        val relativePath = file.relativeTo(oldPackageDir).path
        val newFile = File(newPackageDir, relativePath)
        newFile.parentFile.mkdirs()

        // Leer y actualizar contenido
        val content = file.readText()
        val updatedContent = updateKotlinFilePackages(content, config)
        newFile.writeText(updatedContent)
    }

    // Eliminar directorio antiguo
    oldPackageDir.deleteRecursively()

    // Limpiar directorios vacíos
    cleanEmptyDirectories(sourceDir)

    println("${Colors.GREEN}  ✅ Completado: ${files.size} archivos actualizados${Colors.RESET}")
}

fun updateKotlinFilePackages(content: String, config: ProjectConfig): String {
    val updated = content
        .replace("package ${TemplateDefaults.PACKAGE_BASE}", "package ${config.packageBase}")
        .replace("import ${TemplateDefaults.PACKAGE_BASE}.", "import ${config.packageBase}.")
        .replace("\"${TemplateDefaults.PACKAGE_BASE}\"", "\"${config.packageBase}\"")
        .replace("\"${TemplateDefaults.DATABASE_NAME}\"", "\"${config.projectName.lowercase()}_database\"")

    return updated
}

fun cleanEmptyDirectories(dir: File) {
    dir.walkBottomUp()
        .filter { it.isDirectory && it.listFiles()?.isEmpty() == true }
        .forEach { it.delete() }
}

// Ejecutar el script
main()

