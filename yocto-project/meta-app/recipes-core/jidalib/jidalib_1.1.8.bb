DESCRIPTION = "JIDA Library"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
PR = "r0"

inherit localapp lib_package

EXTRA_OEMAKE = " \
 CC_PREFIX=i686-pc-linux-gnu- \
"

do_compile() {
	oe_runmake
}

do_install() {
	oe_runmake DESTDIR="${D}" install 
}
