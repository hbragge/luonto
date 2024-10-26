DESCRIPTION = "LPC driver module"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

inherit localmodule update-rc.d

PR = "r0"

export INSTALL_MOD_DIR="kernel/drivers/cc-extra"

PKG_${PN} = "kernel-module-${PN}"

do_install_append() {
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 ${THISDIR}/files/lpc-init ${D}${sysconfdir}/init.d/lpc
}

FILES_kernel-module-${PN} += "${sysconfdir}/init.d/lpc"

INITSCRIPT_PACKAGES="kernel-module-${PN}"
INITSCRIPT_NAME="lpc"
INITSCRIPT_PARAMS="start 20 S . stop 90 1 6 ."
