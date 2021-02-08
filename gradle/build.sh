#!/bin/bash
echo "kakao.api.key=\"$KAKAO_API_KEY\"" >> "./appkey.properties"
./gradlew clean build