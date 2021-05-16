# 간단한 서블릿 필터, 스프링 인터셉터에서 값 꺼내오기 예제

- 주의 클린하지 않은 코드입니다. (귀차니즘)

## 테스트 방법

```sh
curl -X "POST" "http://localhost:8080/login" \
-H 'Filter: Value' \
-H 'Interceptor: Value' \
-H 'Content-Type: application/json; charset=utf-8' \
-d $'{
"filter": "value",
"interceptor": "value"
}'
```

로 테스트 해볼 수 있습니다.
