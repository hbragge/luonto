#!/bin/sh

# check out and build new version from svn if available

set -e

if [ "$#" -ne "3" ]
    then
    echo "Please supply a branch/tag name and platform:"
    echo "$0 <-b/-t name> <platform>"
    exit 1
fi

BRANCH=$2
PLATFORM=$3

# Put url of your svn project here, subdirs
# tags, branches and/or trunk are expected to be found
SVNURL="http://svn/svn/yocto/source"

if [ "$1" == "-t" ]
    then
    SVNURL=${SVNURL}/tags/${BRANCH}
else
    # handle trunk as an exceptional branch url
    if [ "${BRANCH}" == "trunk" ]
	then
	SVNURL=${SVNURL}/${BRANCH}
    else
	SVNURL=${SVNURL}/branches/${BRANCH}
    fi
fi

RESULTS_BASE_PATH=$HOME/nightly-build/yocto-nightly-results/${BRANCH}
BUILD_BASE_PATH=$HOME/nightly-build/build
BUILD_PATH=${BUILD_BASE_PATH}/${BRANCH}
LOCKFILE=${BUILD_BASE_PATH}/${BRANCH}.lock

LATEST_SVN=0
LATEST_BUILT=0
LATEST_SUCCESSFUL=0

find_latest_successful() {
    iter=1
    latest=0
    while [ 1 ]
    do
        latest_prev=$latest
	# iterate result directories from newest to latest one by one
        latest=$(ls ${RESULTS_BASE_PATH}/|sort -rn|head -${iter}|tail -1)
        if [ "$latest_prev" == "$latest" ]
            then
            # end case, no succesful build found
            break
        fi
	# check if revision was successfully built
        if [ -d "${RESULTS_BASE_PATH}/$latest/images" ]
            then
	    LATEST_SUCCESSFUL=$latest
            break
        fi
        iter=$(($iter + 1))
    done
}

new_version_available() {
    LATEST_SVN=$(svn log -q -l 1 ${SVNURL}|grep -oE "^r[0-9]+"|grep -oE "[0-9]+")
    LATEST_BUILT=$(ls ${RESULTS_BASE_PATH}|sort -rn|head -1)

    if [ -z "${LATEST_SVN}" ]
	then
	LATEST_SVN=0
    fi

    if [ -z "${LATEST_BUILT}" ]
	then
	LATEST_BUILT=0
    fi

    if [ "${LATEST_SVN}" -gt "${LATEST_BUILT}" ]
	then
	return 0
    else
	return 1
    fi
}

START_TIME=$(date)

mkdir -p ${RESULTS_BASE_PATH}
mkdir -p ${BUILD_BASE_PATH}

# check if new versions can be found
if ! new_version_available
    then
    exit 0
fi

# stop if branch is locked
if [ -f ${LOCKFILE} ]
    then
    exit 1
fi

# remember to remove lockfile on error
trap "{ rm -f ${LOCKFILE} ; exit 1; }" SIGHUP SIGINT SIGTERM
trap "{ rm -f ${LOCKFILE} ; exit 0; }" EXIT

# lock build for this branch
touch ${LOCKFILE}

# remove old build directory
rm -rf ${BUILD_PATH}

# store latest successful version for later use
find_latest_successful

# checkout new version
cd ${BUILD_BASE_PATH}
svn co ${SVNURL} ${BRANCH}

# create new results directory
rm -rf ${RESULTS_BASE_PATH}/${LATEST_SVN}
mkdir -p ${RESULTS_BASE_PATH}/${LATEST_SVN}

BUILD_RESULT="OK"
REPORT_TITLE="Successfully built version ${LATEST_SVN} of branch ${BRANCH}"

# start building, try to stop on error
cd ${BUILD_PATH}
# Use a specific sstate-cache directory for nightly, so that it can be cleared periodically if needed
sed -i '/^SSTATE_DIR*/ s/yocto-sstate-cache"/yocto-sstate-cache-nightly"/' platform/${PLATFORM}/local.conf
make ${PLATFORM} &> ${RESULTS_BASE_PATH}/${LATEST_SVN}/build.log || BUILD_RESULT="FAIL"
#[ "${BUILD_RESULT}" == "OK" ] && make devel >> ${RESULTS_BASE_PATH}/${LATEST_SVN}/build.log 2>> ${RESULTS_BASE_PATH}/${LATEST_SVN}/build.log || BUILD_RESULT="FAIL"

# if successful, copy images to results directory
if [ "${BUILD_RESULT}" == "OK" ]
then
    cp -a ${BUILD_PATH}/platform/${PLATFORM}/build/tmp/deploy/images/${PLATFORM} ${RESULTS_BASE_PATH}/${LATEST_SVN}/images
else
    REPORT_TITLE="Failed to build version ${LATEST_SVN} of branch ${BRANCH}"
fi

RESULT_FILE=${RESULTS_BASE_PATH}/${LATEST_SVN}/result.txt
DOWNLOAD_URL="http://192.168.1.8/download/yocto-nightly-results/${BRANCH}/${LATEST_SVN}"

# report build result
echo ${REPORT_TITLE} > ${RESULT_FILE}

if [ "${BUILD_RESULT}" == "OK" ]
then
    echo "Binaries: ${DOWNLOAD_URL}/images" >> ${RESULT_FILE}
fi

echo "Build log: ${DOWNLOAD_URL}/build.log" >> ${RESULT_FILE}
echo "Build started at ${START_TIME}, ended at $(date)" >> ${RESULT_FILE}
echo "" >> ${RESULT_FILE}

if [ "${BUILD_RESULT}" != "OK" ]
then
    echo "Failure info: " >> ${RESULT_FILE}
    tail -100 ${RESULTS_BASE_PATH}/${LATEST_SVN}/build.log >> ${RESULT_FILE}
    echo "--------------------" >> ${RESULT_FILE}
fi

echo "Changelog: " >> ${RESULT_FILE}
svn log -v ${SVNURL} -r ${LATEST_SVN}:$((${LATEST_SUCCESSFUL}+1)) -l 16 >> ${RESULT_FILE}

rm -f ${RESULTS_BASE_PATH}/latest
ln -s ${RESULTS_BASE_PATH}/${LATEST_SVN} ${RESULTS_BASE_PATH}/latest

rm -f ${LOCKFILE}

exit 0
