# Today Diary AI 프로젝트 구조

이 문서는 Today Diary AI 프로젝트의 전반적인 구조를 설명합니다.

## 루트 디렉토리

프로젝트의 루트 디렉토리에는 다음 파일과 디렉토리가 포함되어 있습니다:

*   **app/**: 이 디렉토리에는 소스 코드, 리소스 및 AndroidManifest.xml을 포함한 메인 애플리케이션 모듈이 있습니다.
*   **.idea/**: 이 디렉토리에는 Android Studio의 프로젝트별 설정이 저장됩니다.
*   **gradle/**: 이 디렉토리에는 특정 버전의 Gradle로 프로젝트를 빌드할 수 있도록 하는 Gradle 래퍼 파일이 포함되어 있습니다.
*   **gradlew**: macOS 및 Linux용 Gradle 래퍼 스크립트입니다.
*   **gradlew.bat**: Windows용 Gradle 래퍼 스크립트입니다.
*   **.gitignore**: 이 파일은 Git에서 무시해야 할 파일 및 디렉토리를 지정합니다.
*   **build.gradle.kts**: 전체 프로젝트의 메인 빌드 스크립트입니다. 프로젝트 수준 빌드 설정을 구성합니다.
*   **gradle.properties**: 이 파일은 Gradle 데몬의 JVM 인수와 같은 프로젝트 전체 Gradle 설정을 구성하는 데 사용됩니다.
*   **settings.gradle.kts**: 이 스크립트는 메인 프로젝트의 일부인 서브 프로젝트를 포함합니다.

이러한 기본 구조는 Android 프로젝트의 일반적인 형태입니다. 핵심 애플리케이션 코드는 `app` 디렉토리 내에 있습니다.
# today_diary_ai
