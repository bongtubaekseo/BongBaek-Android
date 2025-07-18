# 봉투백서


<img width="4935" height="1758" alt="img_bongbaek_banner" src="https://github.com/user-attachments/assets/819d999b-3b57-4adc-b91a-44c104cf0a98" />

<br/>
<br/>

### **💌 “경조사비, 얼마 내야 할지 매번 고민되시나요?”**
**봉투백서**는 **사회초년생을 위한 맞춤형 경조사비를 추천**하고, 효율적으로 관리할 수 있도록 도와주는 앱 서비스입니다.<br/>
매번 헷갈리던 경조사 금액을 알려드립니다.<br/>

더 이상 포털사이트에서 ‘결혼식 축의금 얼마’를 검색하지 마세요!

## 🌟주요 기능

### 1️⃣ 온보딩 – 나를 위한 경조사비 기준 설정<br/>
유저의 상황을 반영한 맞춤형 추천을 위해<br/>
간단한 온보딩 과정에서 수입 등 기본 정보를 입력받아요.<br/>

이 정보를 바탕으로 더 정교한 경조사비 추천이 이루어집니다.<br/>

### 2️⃣ 홈 – 추천 금액과 행사 정보를 한눈에<br/>
홈 화면에서는 개인별 맞춤 금액과 경조사 세부 정보,<br/>
그리고 다가오는 일정을 한눈에 확인할 수 있어요.<br/>

이제 어떤 행사에 얼마를 내야 할지 더 이상 고민하지 않아도 돼요.<br/>

### 3️⃣ 금액 추천 - 상황에 맞는 금액, 정확하게<br/>
사용자의 입력 정보, 연락 빈도, 만남 정도를 바탕으로<br/>
가장 적절한 경조사비를 추천해줘요.<br/>

최소~최대 범위 제시로 유연하게 선택할 수 있고,<br/>
합리적인 금액 범위를 안내해요.<br/>

### 4️⃣ 기록하기 – 지난 경조사 내역도 꼼꼼하게<br/>
지금까지 참석했던 경조사 내역을 체계적으로 정리하여<br/>
언제, 누구에게, 얼마를 냈는지 관리가 쉬워집니다.<br/>

다음 경조사 때 참고하거나 지출을 관리할 때 유용해요.<br/>

<br/>

## **✨ Contributors**

|                              김종명 (Lead) <br> [@jm991014](https://github.com/jm991014)                   |                           김혜정 <br> [@mjeong21](https://github.com/mjeong21)                             |                  공승준 <br> [@seungjunGong](https://github.com/seungjunGong)                             |
|:--------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:|
| <img width="250" src="https://github.com/user-attachments/assets/5bb885ec-7a6d-4644-a88f-560d2a44ce78"/> | <img width="250" src="https://github.com/user-attachments/assets/23d1d4dc-4ffe-4e8d-8b37-7a3875a6bd21"/> | <img width="250" src="https://github.com/user-attachments/assets/91b7b03d-d8c4-499b-b1fd-1a242df92829"/> |
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
UI와 로직을 명확히 분리할 수 있어서 협업 시 역할 구분이 쉬워지고, 테스트나 유지보수 측면에서도 유리합니다.

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
