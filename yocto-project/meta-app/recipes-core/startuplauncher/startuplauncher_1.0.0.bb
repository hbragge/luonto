DESCRIPTION = "StartupLauncher"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
PR = "r0"

inherit localapp qt4x11 update-rc.d

DEPENDS ="appapi"

do_install() {
	oe_runmake PLATFORM_XM=1 CC_PREFIX=i686-pc-linux-gnu- INSTALL_ROOT="${D}" install 
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 ${THISDIR}/files/startuplauncher-init ${D}${sysconfdir}/init.d/startuplauncher
}

INITSCRIPT_PACKAGES="startuplauncher"
INITSCRIPT_NAME="startuplauncher"
INITSCRIPT_PARAMS="start 50 2 3 5 . stop 50 1 6 ."
