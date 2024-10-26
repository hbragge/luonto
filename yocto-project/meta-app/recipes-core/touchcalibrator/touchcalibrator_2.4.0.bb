DESCRIPTION = "Touchcalibrator"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
PR = "r0"

# Since the touchcalibrator isn't done the way other applications are
# some fiddling is needed
inherit base externalsrc qt4x11

DEPENDS ="appapi"

EXTERNALSRC = "${COREBASE}/../apps/${PN}/Proj/Linux/TouchCalibrator"
EXTERNALSRC_BUILD = "${EXTERNALSRC}"

do_install() {
	oe_runmake PLATFORM_XM=1 CC_PREFIX=i686-pc-linux-gnu- INSTALL_ROOT="${D}" install 
}
