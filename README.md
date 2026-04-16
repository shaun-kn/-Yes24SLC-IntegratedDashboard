# OSR Dashboard

## 구조
- frontend: Vue (Vite)
- backend: Spring Boot

## 실행 방법

### backend
cd backend
./gradlew bootRun

### frontend
cd frontend
npm install
npm run dev

## 배포
- frontend: npm run build → nginx
- backend: jar 실행
