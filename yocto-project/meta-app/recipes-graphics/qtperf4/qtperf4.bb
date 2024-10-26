DESCRIPTION = "QTPerformance application"
LICENSE = "MIT"
SECTION = "x11/apps"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r1"

SRC_URI = "file://qtperf4.tar.gz"

SRC_URI[md5sum] = "6730fcd0a39fd3704761178ee2d77c29"
SRC_URI[sha256sum] = "2f8dd956a1a283e04d0750cbda15b1b277e0630835b690467de4a19a23de60bb"

inherit qt4x11

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/${BPN} ${D}${bindir}
}
