DESCRIPTION = "serial number broadcaster"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
PR = "r1"

inherit localapp update-rc.d

DEPENDS ="appapi"

EXTRA_OEMAKE = " \
 CC_PREFIX=i686-pc-linux-gnu- \
 PLATFORM_XM=1 \
 LINUX=1 \
 API_INC_DIR=${S}/../appapi \
 API_LIB_DIR=${S}/../appapi \
"

do_install() {
	oe_runmake PLATFORM_XM=1 CC_PREFIX=i686-pc-linux-gnu DESTDIR="${D}" install
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 ${S}/debian/init.d ${D}${sysconfdir}/init.d/appsnbd
}

INITSCRIPT_PACKAGES="snb"
INITSCRIPT_NAME="appsnbd"
INITSCRIPT_PARAMS="start 99 S . stop 01 1 6 ."
