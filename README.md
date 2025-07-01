# 💌 봉투백서

> **“경조사비, 이젠 고민하지 마세요.”**
<br/>사회 초년생을 위한 경조사비 **추천 및 관리 올인원 서비스💫**


적절한 경조사비에 대한 기준이 없는 사회초년생들이
<br/>
합리적인 경조사비를 지불할 수 있도록, 유저 통계를 바탕으로 한 추천 가이드 제공합니다.
<br/>
<br/>


## **✨ Contributors**

|                              김종명 (Lead) <br> [@jm991014](https://github.com/jm991014)                   |                           김혜정 <br> [@mjeong21](https://github.com/mjeong21)                             |                  공승준 <br> [@seungjunGong](https://github.com/seungjunGong)                             |
|:--------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:|
| <img width="250" src="https://github.com/user-attachments/assets/efbb54de-67d0-4cc2-a190-e6c4a8fb0371"/> | <img width="250" src="https://github.com/user-attachments/assets/73547557-c07a-4049-bd8a-ebc345fa8212"/> | <img width="250" src="https://github.com/user-attachments/assets/bc43c218-6a6c-4a25-8ab1-8ec03d13e3e1"/> |
|                                              `금액추천`                                                    |                                              `온보딩`<br>`홈`                                               |                           `기록하기`<br>`상세내용`<br>                                                      |

<br/>

## **⚒️ Tech Stacks**
| 항목              | 기술 스택 |
|------------------|---------|
| Architecture     | Google Recommended Architecture |
| Pattern          | MVVM  |  
| DI               | Hilt  |
| Asynchronous     | Coroutine, Flow |
| Network          | Retrofit2, OkHttp |
| Navigation       | Single Activity Architecture (SAA), Jetpack Navigation |
| UI Framework     | Jetpack Compose          |
| Image Processing | Coil, Lottie             |
| Logging          | Timber                   |

<br/>

> **📌 도입 기술 선정 이유**

**1️⃣ Architecture: Google Recommended Architecture** <br/>
서비스의 규모나 복잡도를 고려했을 때 도메인 레이어까지 고정적으로 가져가기보다는<br/>
기본적인 아키텍처 구조는 따르되, 도메인 레이어는 필요한 경우에만 유연하게 추가할 수 있도록 구성했습니다.
초기에는 가볍게 시작하고, 기능이 확장될수록 자연스럽게 구조를 확장해갈 수 있다고 판단했습니다.

**2️⃣ Pattern: MVVM (Model-View-ViewModel)** <br/>
팀원 대부분이 익숙하게 사용해온 패턴이며, ViewModel을 중심으로 상태를 관리하기 때문에 Jetpack Compose와의 궁합이 좋아 선택하게 되었습니다.
UI와 로직을 명확히 분리할 수 있어서 협업 시 역할 구분이 쉬워지고, 테스트나 유지보수 측면에서도 유리합ㅂ니다.

**3️⃣ Dependency Injection: Hilt** <br/>
구글에서 권장하는 DI 라이브러리로, 보일러플레이트 코드를 줄이면서 의존성 관리의 일관성과 재사용성을 확보할 수 있어 도입했습니다.
또한 내부적으로 제공하는 컴포넌트들의 라이프사이클을 자동으로 관리해주기 때문에, 초기 설정 부담을 줄이고 생산성과 유지보수 효율을 높일 수 있습니다.

<br/>

## **📗 Convention**

📌 [컨벤션 문서 보러가기](https://www.notion.so/21ab0b17e916804d82def27e5b228fbb?source=copy_link)
- **Github Convention**
- **Naming Convention**
- **Packaging Convention**

<br/>

## **🗂️ Project Structure**
```
🗃️ app
├─ 🗃️ core
│  ├─ 🗃️ common
│  │  ├─ 📁 navigation
│  │  ├─ 📁 state
│  │  └─ 📁 type
│  ├─ 🗃️ designsystem 
│  │  ├─ 📁 component 
│  │  └─ 📁 theme
│  ├─ 🗃️ local
│  │  ├─ 📁 datastore
│  │  └─ 📁 room (필요시)
│  ├─ 🗃️ network
│  └─ 🗃️ util
│
├─ 🗃️ data (feature 기반)
│  ├─ 📁 datastore
│  ├─ 📁 datastoreimpl
│  ├─ 📁 di
│  ├─ 📁 dto
│  ├─ 📁 mapper
│  ├─ 📁 model
│  ├─ 📁 repository
│  ├─ 📁 repositoryimpl
│  └─ 📁 service
│
├─ 🗃️ domain
│  └─ 📁 usecase (필요시)
│
└─ 🗃️ presentation (view 기반)
   ├─ 📁 main
   ├─ 📁 home
   └─ ...

```
