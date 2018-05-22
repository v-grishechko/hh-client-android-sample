#!/usr/bin/env bash
set -xe
DIR="$( cd "$( dirname "$BASH_SOURCE" )" && pwd )"

#swarmer version const
SWARMER_VERSION=0.2.2
SWARMER_PATH=/tmp/
SWARMER_FILE=swarmer-${SWARMER_VERSION}.jar

#composer version const
COMPOSER_VERSION=0.3.0
COMPOSER_PATH=/tmp/
COMPOSER_FILE=composer-${COMPOSER_VERSION}.jar

#go to the project dir
cd "$DIR/.."

#build apk with tests
./gradlew assembleDebugAndroidTest

#build app apk
./gradlew assembleDebug

#download composer
if ! [ -f ${COMPOSER_PATH}/${COMPOSER_FILE} ]; then
    curl --fail --location https://jcenter.bintray.com/com/gojuno/composer/composer/${COMPOSER_VERSION}/${COMPOSER_FILE} \
    --output ${COMPOSER_PATH}/${COMPOSER_FILE}
fi

#dowload swarmer
if ! [ -f ${SWARMER_PATH}/${SWARMER_FILE} ]; then
    curl --fail --location https://jcenter.bintray.com/com/gojuno/swarmer/swarmer/${SWARMER_VERSION}/${SWARMER_FILE} \
         --output ${SWARMER_PATH}/${SWARMER_FILE}
fi

#setup swarmer
java -jar ${SWARMER_FILE} start \
--emulator-name Nexus_5X_API_27 \
--package "system-images;android-27;google_apis_playstore;x86" \
--android-abi google_apis_playstore/x86 \
--path-to-config-ini ${DIR}/devices_configs/config.ini \

#setup composer
cd /tmp/
java -jar ${COMPOSER_FILE} --apk "${DIR}/../app/build/outputs/apk/debug/app-debug.apk" \
     --test-apk "${DIR}/../app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk" \
     --test-package "com.vgrishechco.hh.test" \
     --test-runner "android.support.test.runner.AndroidJUnitRunner"

#stop swarmer
java -jar ${SWARMER_FILE} stop