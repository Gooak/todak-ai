# 토닥토닥 🖋️✨

**AI가 당신의 지친 하루를 토닥여주는 따뜻한 감성 일기장**

나의 하루와 감정, 그리고 날씨를 기록하면, Gemini AI가 따뜻한 공감의 메시지를 전해주는 모바일 일기 앱입니다. 파스텔 톤의 부드러운 디자인과 함께 오롯이 나에게 집중하는 시간을 선물합니다.

---

## 📸 주요 화면 (Screenshots)

| 일기 목록 | 통계 화면 |
| :---: | :---: |
| <img width="216" height="384" alt="Screenshot_20250930_235754" src="https://github.com/user-attachments/assets/9bab24d7-af3a-423a-9ab8-9ae0923268c7" /> | ![통계 화면](https://via.placeholder.com/300x600.png?text=Statistics+Screen) |


---

## ✨ 주요 기능 (Features)

* **📝 일기 작성**: 날짜, 제목, 내용과 함께 그날의 기분과 날씨를 기록할 수 있습니다.
* **🤖 AI의 공감 답변**: 작성한 일기 내용과 기분을 바탕으로 Gemini AI가 따뜻한 공감의 메시지를 생성해 줍니다.
* **🗑️ 스와이프하여 삭제**: 부드러운 스와이프 제스처로 일기를 편리하게 삭제할 수 있습니다.
* **📊 통계 시각화**: 기록된 기분과 날씨 데이터를 원형 그래프로 한눈에 파악할 수 있습니다.
* **🎨 감성적인 UI/UX**: Jetpack Compose와 Material 3 기반의 파스텔 톤 디자인을 적용했습니다.

---

## 🛠️ 기술 스택 및 아키텍처 (Tech Stack & Architecture)

이 프로젝트는 Android 앱 개발의 최신 트렌드를 따르고 있으며, 확장성과 유지보수성을 고려하여 설계되었습니다.

### 아키텍처 (Architecture)

* **Modern App Architecture**: Google에서 권장하는 공식 아키텍처 가이드를 따릅니다.
* **Clean Architecture**: Presentation - Domain - Data 3개의 계층으로 역할을 명확히 분리했습니다.
* **MVVM (Model-View-ViewModel)**: Presentation Layer에서 UI와 비즈니스 로직을 분리하기 위해 사용합니다.
* **Repository Pattern**: 데이터 소스를 추상화하여 데이터 접근 방식을 일원화합니다.
* **UI Layer**: State Holder(ViewModel)를 사용하여 단방향 데이터 흐름(UDF)을 구현합니다.

### 사용된 기술 (Tech Stack)

* **Language**: Kotlin
* **UI**: Jetpack Compose, Material 3
* **Asynchronicity**: Kotlin Coroutines & Flow
* **Architecture**: ViewModel, Lifecycle
* **DI**: Hilt
* **Navigation**: Navigation Compose
* **AI**: Google Generative AI SDK (Gemini)
* **Build**: Gradle (Kotlin DSL & TOML Version Catalog)

---

## 📁 파일 구조 (Directory Structure)

프로젝트는 Clean Architecture의 3계층 구조를 기반으로 구성되어 있습니다.
```
.
└── app/src/main/java
└── com/example/today_diary_ai
├── 📂 data
│   ├── 📂 repository  (Repository 구현체)
│   ├── 📂 local       (Room DB, Dao, Entity 등)
│   └── 📂 remote      (Gemini API 관련)
│
├── 📂 domain
│   ├── 📂 model        (UI에서 사용할 데이터 모델)
│   ├── 📂 repository  (Repository 인터페이스)
│   ├── 📂 usecase     (비즈니스 로직 캡슐화)
│   └── 📂 enums       (MoodType, WeatherType 등)
│
├── 📂 presentation
│   ├── 📂 ui
│   │   ├── 📂 screens    (각 화면 Composable)
│   │   └── 📂 components (재사용 가능한 Composable)
│   ├── 📂 viewmodel   (ViewModel)
│   └── 📂 navigation  (Navigation 그래프)
│
└── 📂 di               (Hilt 의존성 주입 모듈)
```
