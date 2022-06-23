# Slack Clone coding🥘

## 프로젝트 소개 😁

슬랙에 실시간 채팅기능을 주된 기능구현의 목적으로 클론 코딩하였습니다! 


---
## 📚 기술스택 소개

<div align=center>

<p align="center">

<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white">
 <img src="https://img.shields.io/badge/java.js-000000?style=for-the-badge&logo=java.js&logoColor=black">
<img src="https://img.shields.io/badge/React-47A248?style=for-the-badge&logo=React&logoColor=white"> 
<img src="https://img.shields.io/badge/socket.js-339933?style=for-the-badge&logo=socket.js&logoColor=white">
<img src="https://img.shields.io/badge/stomp.js-000000?style=for-the-badge&logo=stomp.js&logoColor=white">
<img src="https://img.shields.io/badge/intellj -000000?style=for-the-badge&logo=stomp.js&logoColor=black">



  <br>



<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=grey">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=grey">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=grey">
<img src="https://img.shields.io/badge/Docker-000000?style=for-the-badge&logo=Docker&logoColor=black">
  <br>
</div>


---
## 👨‍💻 프로젝트 기간


2022년 6월 17일 ~ 2022년 6월 23일 (총 7일)

---
## 🎞 와이어 프레임
<br/>

---
##  실제 슬랙  
<img src="https://user-images.githubusercontent.com/107375500/175225603-3b4cd83d-2b60-4df2-867b-b890899faa18.gif" width="400" height="300">

---
##  클론 슬랙  
<img src="https://user-images.githubusercontent.com/107375500/175224632-38570521-6163-4394-ad58-c627627535eb.gif" width="500" height="350">





---
## 🔨 개발툴!


-   서버 배포 : S3(Frontend),EC2(Backend),Docker(Backend)
-   Language: Javascript(Frontend), java(Backend)
-   Tool : Git, Notion,Firestorage
-   Platform : React, spring


---  
## 🛠 구현한 기능 
- 로그인 
- 회원가입
- 로그아웃
- socket // stomp.js 실시간 채팅
- CRUD // 채널 생성 , 삭제
- 친구 초대 
- 목록 페이지

---
## 🛠 구현 페이지

- 스타트 페이지
- 로그인 
- 회원가입
- 메인 페이지
- 채널생성 모달창
- 친구 초대 모달창
- 프로필 창

 
---
## 👍SLACK clond coding TEAM👍 

- 최성우(front)  -김정훈(backend Team Leader)
- 하율찬(front) - 김이안(backend) 
                - 김창규(backend) 

---
## Trouble Shooting ❌
@FE
1. 소켓 서버에 연결이 바로 되는 것이 아니라, 채널에 입장할 때만 가능하게 싶었습니다.
->useEffect 안에서 조건문을 선언하고, deps로 채널 아이디를 지정해서 아이디가 변할 때 실행되게 만들었습니다.
2. 소켓 서버에 연결하고, subscribe는 채널 아이디를 지정되고 나서 들어가게 하고 싶었는데, 구독은 되지만, 메세지를 받지 못했습니다.
->connect 할 때 onConnected(서버와 연결되고 나서 작동되는 callback 함수)에 subscribe를 넣어야 제대로 메세지를 받을 수 있었습니다.
->채널을 지정하고나서 connect하는 것은 1번을 통해 해결했습니다.
3.채널에 추가할 인원을 추가한 후, 취소를 splice 메서드를 사용해서 하려했지만 실패했습니다.
->filter를 사용해서 해결하였습니다.

@BE
1. cors 설정에서 ExposedHeader를 *로 설정함
-> 로그아웃 후 바로 로그인 할시 ExposedHeader가 * 인데도 헤더에 접근이 불가능함
-> 새로 고침 후 로그인 하면 접근 가능
-> 프론트에서 axios 요청시 같은 요청인데 헤더에 접근이 가능했다 불가능했다 함
-> ExposedHeader를 Authorization으로 명시해 주니 항상 접근 가능하게 됨(해결은 됐지만 이유를 모르겠음)
2. 유저가 접근 가능한 채널 목록을 InvitedUserChannels 테이블에서 뽑아옴
-> 공개 채널은 모두 접근 가능해서 InvitedUserChannel 에 채널 정보가 없음
-> Channel 에서 공개채널 목록을 뽑아서 붙이는 로직이 추가되어야함
-> 두 로직을 한 로직으로 처리하기 위해 Channel Repository에서  findAllByIsPrivateOrInvitedUserChannels_UserId 함수를 씀 ( Channels 안의 InvitedUserChannels 안의 UserId로 조회)
3. 채널 삭제할 때 InvitedUserChannel , Channel 순으로 삭제함
-> Message를 보낸 상태에서 500 에러가 SQLIntegrityConstraintViolationException: 발생함
-> InvitedUserChannel , Message, Channel 순서로 삭제함 (해결)
