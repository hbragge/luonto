DESCRIPTION = "Socket CAN communication module"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d85064f0fa931974668d281ab83cc97e"

inherit localapp

PR = "r1"

EXTRA_OEMAKE = " \
	KERNEL_DIR=${STAGING_KERNEL_DIR} \
	DESTDIR="${D}" \
"

do_install() {
	install -d ${D}/usr/bin
 	install -m 0755 candump ${D}/usr/bin
 	install -m 0755 cangen ${D}/usr/bin
 	install -m 0755 cansend ${D}/usr/bin
 	install -m 0755 cangw ${D}/usr/bin
 	install -m 0755 cansniffer ${D}/usr/bin
}
