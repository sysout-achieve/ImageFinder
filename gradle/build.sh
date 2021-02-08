#!/bin/bash
echo "kakao_api_key=\"$KAKAO_API_KEY\"" >> "./appkey.properties"
./gradlew clean build