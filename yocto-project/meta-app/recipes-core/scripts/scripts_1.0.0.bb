DESCRIPTION = "Scripts collection"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
PR = "r6"

inherit localapp

do_configure() {
	echo "done"
}

do_compile() {
	echo "done"
}

do_install() {
	oe_runmake PLATFORM_XM=1 CC_PREFIX=i686-pc-linux-gnu- DESTDIR="${D}" install
}
